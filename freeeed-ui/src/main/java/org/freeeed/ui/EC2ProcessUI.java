/*
 *
 * Copyright SHMsoft, Inc. 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.freeeed.ui;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.*;

import org.freeeed.ec2.ProcessAgent;
import org.freeeed.ec2.SSHAgent;
import org.freeeed.services.Project;
import org.freeeed.services.Stats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mark
 */
public class EC2ProcessUI extends javax.swing.JDialog implements ActionListener {

    private static final Logger logger = LoggerFactory.getLogger(EC2ProcessUI.class);
    /**
     * A return status code - returned if Cancel button has been pressed
     */
    public static final int RET_CANCEL = 0;
    /**
     * A return status code - returned if OK button has been pressed
     */
    public static final int RET_OK = 1;
    private Thread processingThread;
    private ProcessAgent processAgent;
    private Timer timer;

    /**
     * Creates new form EC2ProcessUI
     * @param parent
     *
     */
    public EC2ProcessUI(final Frame parent) {
        super(parent, false);
        initComponents();

        // Close the dialog when Esc is pressed
        String cancelName = "cancel";
        InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
        ActionMap actionMap = rootPane.getActionMap();
        actionMap.put(cancelName, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doClose(RET_CANCEL);
            }
        });
    }

    /**
     * @return the return status of this dialog - one of RET_OK or RET_CANCEL
     */
    public int getReturnStatus() {
        return returnStatus;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {
        GridBagConstraints gridBagConstraints;

        okButton = new javax.swing.JButton();
        uploadCheck = new javax.swing.JCheckBox();
        processCheck = new javax.swing.JCheckBox();
        downloadCheck = new javax.swing.JCheckBox();
        shutdownCheck = new javax.swing.JCheckBox();
        progressUpload = new javax.swing.JProgressBar();
        progressProcess = new javax.swing.JProgressBar();
        progressDownload = new javax.swing.JProgressBar();
        goButton = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();
        detail1Button = new javax.swing.JButton();
        progressLabel = new javax.swing.JLabel();
        detail2Button = new javax.swing.JButton();
        detail3Button = new javax.swing.JButton();

        setTitle("EC2 Processing");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                closeDialog(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        okButton.setText("OK");
        okButton.addActionListener(this::okButtonActionPerformed);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 14;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 10;
        gridBagConstraints.ipadx = 14;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(54, 165, 16, 0);
        getContentPane().add(okButton, gridBagConstraints);
        getRootPane().setDefaultButton(okButton);

        uploadCheck.setSelected(true);
        uploadCheck.setText("Upload project");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 11, 0, 0);
        getContentPane().add(uploadCheck, gridBagConstraints);

        processCheck.setSelected(true);
        processCheck.setText("Run processing");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 11, 0, 0);
        getContentPane().add(processCheck, gridBagConstraints);

        downloadCheck.setSelected(true);
        downloadCheck.setText("Download results");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(16, 11, 0, 0);
        getContentPane().add(downloadCheck, gridBagConstraints);

        shutdownCheck.setText("Shut down cluster when finished");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 12, 0, 0);
        getContentPane().add(shutdownCheck, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 227;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 10, 0, 0);
        getContentPane().add(progressUpload, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 227;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(16, 10, 0, 0);
        getContentPane().add(progressProcess, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 227;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(21, 10, 0, 0);
        getContentPane().add(progressDownload, gridBagConstraints);

        goButton.setText("GO!");
        goButton.addActionListener(this::goButtonActionPerformed);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.ipadx = 37;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(54, 15, 16, 0);
        getContentPane().add(goButton, gridBagConstraints);

        stopButton.setText("Stop");
        stopButton.addActionListener(this::stopButtonActionPerformed);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(54, 34, 16, 0);
        getContentPane().add(stopButton, gridBagConstraints);

        detail1Button.setText("...");
        detail1Button.addActionListener(this::detail1ButtonActionPerformed);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 23;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 11;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipady = -9;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 15, 0, 9);
        getContentPane().add(detail1Button, gridBagConstraints);

        progressLabel.setText("Progress");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(7, 10, 0, 0);
        getContentPane().add(progressLabel, gridBagConstraints);

        detail2Button.setText("...");
        detail2Button.addActionListener(this::detail2ButtonActionPerformed);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 23;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 11;
        gridBagConstraints.ipady = -9;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(16, 15, 0, 9);
        getContentPane().add(detail2Button, gridBagConstraints);

        detail3Button.setText("...");
        detail3Button.addActionListener(this::detail3ButtonActionPerformed);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 23;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 11;
        gridBagConstraints.ipady = -9;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(16, 15, 0, 9);
        getContentPane().add(detail3Button, gridBagConstraints);

        pack();
    }

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {
        doClose(RET_OK);
    }

    /**
     * Closes the dialog
     */
    private void closeDialog(WindowEvent evt) {
        doClose(RET_CANCEL);
    }

    private void goButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (!verifyProject()) {
            return;
        }
        goProcessProject();
    }

    private void detail1ButtonActionPerformed(java.awt.event.ActionEvent evt) {
        ProcessAgent pa = new ProcessAgent();
        String uploadPlan = "Could not get the upload plan";
        try {
            uploadPlan = pa.getUploadPlan();
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        UploadPlanUI ui = new UploadPlanUI(null, true);
        ui.setUploadPlan(uploadPlan);
        ui.setVisible(true);
    }

    private void detail2ButtonActionPerformed(java.awt.event.ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "This will show the processing detail, \n"
                + "but for now please see the History window");
    }

    private void detail3ButtonActionPerformed(java.awt.event.ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "This will show the download plan, "
                + "for possible optimization,\n"
                + "but for now please see the History window");
    }

    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {
        stopTheJob();
    }

    private void doClose(int retStatus) {
        returnStatus = retStatus;
        setVisible(false);
        dispose();
    }
    private javax.swing.JButton detail1Button;
    private javax.swing.JButton detail2Button;
    private javax.swing.JButton detail3Button;
    private javax.swing.JCheckBox downloadCheck;
    private javax.swing.JButton goButton;
    private javax.swing.JButton okButton;
    private javax.swing.JCheckBox processCheck;
    private javax.swing.JProgressBar progressDownload;
    private javax.swing.JLabel progressLabel;
    private javax.swing.JProgressBar progressProcess;
    private javax.swing.JProgressBar progressUpload;
    private javax.swing.JCheckBox shutdownCheck;
    private javax.swing.JButton stopButton;
    private javax.swing.JCheckBox uploadCheck;
    private int returnStatus = RET_CANCEL;

    private void goProcessProject() {
        // delete previous report
        Stats.getInstance().getStatsFile().delete();
        // prepare the processing and start it in its own thread
        processAgent = new ProcessAgent();
        processAgent.setUpload(uploadCheck.isSelected());
        processAgent.setProcess(processCheck.isSelected());
        processAgent.setDownload(downloadCheck.isSelected());
        processAgent.setShutdown(shutdownCheck.isSelected());
        processingThread = new Thread(processAgent);
        processingThread.start();
        // start the timer for progress updates, also in its thread
        timer = new Timer(5000, this);
        timer.setInitialDelay(1000);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        progressUpload.setValue(processAgent.getUploadPercent());
        progressProcess.setValue(processAgent.getProcessPercent());
        SSHAgent sshAgent = processAgent.getSshAgent();
        if (sshAgent != null) {
            String lastLine = sshAgent.getLastOutputLine();
            logger.trace(lastLine);
        }
        progressDownload.setValue(processAgent.getDownloadPercent());
        if (processAgent.isDone()) {
            timer.stop();
        }
    }

    private boolean verifyProject() {
        Project project = Project.getCurrentProject();
        if (project.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Please open a project first");
            return false;
        }
        if (!project.hasStagedData()) {
            JOptionPane.showMessageDialog(rootPane, "Staging files not found, maybe you need to stage first");
            return false;
        }
        return true;
    }

    private void stopTheJob() {
        try {
            ProcessAgent terminator = new ProcessAgent();
            terminator.killAllJobs();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
