/*
 * ProjectSettingsUI.java
 *
 * Created on Jun 29, 2011, 6:33:58 PM
 */
package org.freeeed.ui;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.DefaultListModel;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import org.apache.commons.configuration.Configuration;
import org.freeeed.main.FreeEedMain;
import org.freeeed.main.ParameterProcessing;

/**
 *
 * @author mark
 */
public class ProjectSettingsUI extends javax.swing.JDialog {

    /** A return status code - returned if Cancel button has been pressed */
    public static final int RET_CANCEL = 0;
    /** A return status code - returned if OK button has been pressed */
    public static final int RET_OK = 1;

    /** Creates new form ProjectSettingsUI */
    public ProjectSettingsUI(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        // Close the dialog when Esc is pressed
        String cancelName = "cancel";
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
        ActionMap actionMap = getRootPane().getActionMap();
        actionMap.put(cancelName, new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                doClose(RET_CANCEL);
            }
        });
    }

    /** @return the return status of this dialog - one of RET_OK or RET_CANCEL */
    public int getReturnStatus() {
        return returnStatus;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupProcessing = new javax.swing.ButtonGroup();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        projectNameLabel = new javax.swing.JLabel();
        projectTextField = new javax.swing.JTextField();
        projectInputsLabel = new javax.swing.JLabel();
        projectInputsScrollPanel = new javax.swing.JScrollPane();
        projectInputsList = new javax.swing.JList();
        addFileButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();
        processingLabel = new javax.swing.JLabel();
        radioLocal = new javax.swing.JRadioButton();
        radioEc2 = new javax.swing.JRadioButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        cullingLabel = new javax.swing.JLabel();
        cullingScrollPanel = new javax.swing.JScrollPane();
        cullingText = new javax.swing.JTextArea();
        helpLabel = new javax.swing.JLabel();
        addNetworkButton = new javax.swing.JButton();
        addInputLabel = new javax.swing.JLabel();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        projectNameLabel.setText("Project name");

        projectInputsLabel.setText("Project inputs");

        projectInputsScrollPanel.setViewportView(projectInputsList);

        addFileButton.setText("File path");
        addFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFileButtonActionPerformed(evt);
            }
        });

        removeButton.setText("Remove");
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });

        processingLabel.setText("Processing");

        buttonGroupProcessing.add(radioLocal);
        radioLocal.setSelected(true);
        radioLocal.setText("Local");

        buttonGroupProcessing.add(radioEc2);
        radioEc2.setText("EC2");
        radioEc2.setEnabled(false);

        buttonGroupProcessing.add(jRadioButton1);
        jRadioButton1.setText("My cluster");
        jRadioButton1.setEnabled(false);

        cullingLabel.setText("Culling expressions");

        cullingText.setColumns(20);
        cullingText.setRows(5);
        cullingScrollPanel.setViewportView(cullingText);

        helpLabel.setForeground(new java.awt.Color(0, 0, 255));
        helpLabel.setText("Help");
        helpLabel.setToolTipText("<html>Each line is treated as a separate keyword or search expression<br>\nAll lines are considered as connected by a non-exclusive \"OR\"<br>\nField names are required, so for example you can have<br><br>\ncontent:email<br>\ncontent:data<br>\ntitle:meeting<br><br>\nFor the syntax of search expressions click on this \"Help\"</html>");
        helpLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                helpLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                helpLabelMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                helpLabelMousePressed(evt);
            }
        });

        addNetworkButton.setText("Network location");
        addNetworkButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNetworkButtonActionPerformed(evt);
            }
        });

        addInputLabel.setForeground(new java.awt.Color(0, 0, 255));
        addInputLabel.setLabelFor(addNetworkButton);
        addInputLabel.setText("Add");
        addInputLabel.setToolTipText("<html>Add a local file path or local directory<br>\nOr, add a network resource location in the URI syntax<br>\nClick here for help on URI<br>\n</html>");
        addInputLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addInputLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                addInputLabelMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                addInputLabelMousePressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cullingScrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 615, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(projectInputsScrollPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 615, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(processingLabel)
                                .addGap(30, 30, 30)
                                .addComponent(radioLocal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(radioEc2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 169, Short.MAX_VALUE)
                                .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cancelButton))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(projectNameLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(projectTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(projectInputsLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 131, Short.MAX_VALUE)
                        .addComponent(addInputLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(addFileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addNetworkButton, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(removeButton)
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cullingLabel)
                        .addGap(31, 31, 31)
                        .addComponent(helpLabel)
                        .addContainerGap())))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelButton, okButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(projectNameLabel)
                    .addComponent(projectTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(removeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(addInputLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(addFileButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(addNetworkButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(projectInputsLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(projectInputsScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cullingLabel)
                    .addComponent(helpLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cullingScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(okButton)
                    .addComponent(processingLabel)
                    .addComponent(jRadioButton1)
                    .addComponent(radioEc2)
                    .addComponent(radioLocal))
                .addContainerGap())
        );

        getRootPane().setDefaultButton(okButton);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        doClose(RET_OK);
    }//GEN-LAST:event_okButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        doClose(RET_CANCEL);
    }//GEN-LAST:event_cancelButtonActionPerformed

    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        doClose(RET_CANCEL);
    }//GEN-LAST:event_closeDialog

	private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed
            removeInput();
	}//GEN-LAST:event_removeButtonActionPerformed

	private void addFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFileButtonActionPerformed
            addFileInput();
	}//GEN-LAST:event_addFileButtonActionPerformed

	private void helpLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_helpLabelMouseEntered
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}//GEN-LAST:event_helpLabelMouseEntered

	private void helpLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_helpLabelMouseExited
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}//GEN-LAST:event_helpLabelMouseExited

	private void helpLabelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_helpLabelMousePressed
            openLuceneSyntaxBrowser();
	}//GEN-LAST:event_helpLabelMousePressed

	private void addNetworkButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNetworkButtonActionPerformed
            addUriInput();
	}//GEN-LAST:event_addNetworkButtonActionPerformed

	private void addInputLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addInputLabelMouseEntered
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}//GEN-LAST:event_addInputLabelMouseEntered

	private void addInputLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addInputLabelMouseExited
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}//GEN-LAST:event_addInputLabelMouseExited

	private void addInputLabelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addInputLabelMousePressed
            openUriSyntaxBrowser();
	}//GEN-LAST:event_addInputLabelMousePressed

    private void doClose(int retStatus) {
        returnStatus = retStatus;
        if (returnStatus == RET_OK) {
            if (collectData() == false) {
                return;
            }
            FreeEedUI.getInstance().updateTitle(FreeEedMain.getInstance().getProcessingParameters());
        }
        setVisible(false);
        dispose();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                ProjectSettingsUI dialog = new ProjectSettingsUI(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addFileButton;
    private javax.swing.JLabel addInputLabel;
    private javax.swing.JButton addNetworkButton;
    private javax.swing.ButtonGroup buttonGroupProcessing;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel cullingLabel;
    private javax.swing.JScrollPane cullingScrollPanel;
    private javax.swing.JTextArea cullingText;
    private javax.swing.JLabel helpLabel;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JButton okButton;
    private javax.swing.JLabel processingLabel;
    private javax.swing.JLabel projectInputsLabel;
    private javax.swing.JList projectInputsList;
    private javax.swing.JScrollPane projectInputsScrollPanel;
    private javax.swing.JLabel projectNameLabel;
    private javax.swing.JTextField projectTextField;
    private javax.swing.JRadioButton radioEc2;
    private javax.swing.JRadioButton radioLocal;
    private javax.swing.JButton removeButton;
    // End of variables declaration//GEN-END:variables
    private int returnStatus = RET_CANCEL;

    @Override
    public void setVisible(boolean b) {
        if (b) {
            Configuration processingParameters = FreeEedMain.getInstance().getProcessingParameters();
            if (processingParameters == null) {
                JOptionPane.showMessageDialog(rootPane, "Create or open a project first");
                return;
            }
            showData();
        }
        super.setVisible(b);
    }

    @SuppressWarnings("unchecked")
    private void showData() {
        Configuration processingParameters = FreeEedMain.getInstance().getProcessingParameters();
        if (processingParameters.containsKey(ParameterProcessing.PROJECT_FILE_NAME)) {
            setTitle(processingParameters.getString(ParameterProcessing.PROJECT_FILE_NAME));
        }
        if (processingParameters.containsKey(ParameterProcessing.PROJECT_NAME)) {
            projectTextField.setText(processingParameters.getString(ParameterProcessing.PROJECT_NAME));
        }
        DefaultListModel model = new DefaultListModel();
        String[] dirs = processingParameters.getStringArray(ParameterProcessing.PROJECT_INPUTS);
        String[] custodians = processingParameters.getStringArray(ParameterProcessing.PROJECT_CUSTODIANS);
        if (dirs != null) {
            for (int i = 0; i < dirs.length; ++i) {
                String line = custodians[i] + ": " + dirs[i];
                model.addElement(line);
            }
        }
        projectInputsList.setModel(model);
        cullingText.setText((String) processingParameters.getProperty(ParameterProcessing.CULLING));
    }

    private boolean collectData() {
        Configuration processingParameters = FreeEedMain.getInstance().getProcessingParameters();
        processingParameters.setProperty(ParameterProcessing.PROJECT_NAME, projectTextField.getText());
        ListModel model = projectInputsList.getModel();
        String[] dirs = new String[model.getSize()];
        String[] custodians = new String[model.getSize()];
        for (int i = 0; i < model.getSize(); ++i) {
            String line = (String) model.getElementAt(i);
            int twodots = line.indexOf(":");            
            String custodian = line.substring(0, twodots);
            String uri = line.substring(twodots);
            custodians[i] = custodian;
            dirs[i] = uri;
        }
        processingParameters.setProperty(ParameterProcessing.PROJECT_INPUTS, dirs);
        processingParameters.setProperty(ParameterProcessing.PROJECT_CUSTODIANS, custodians);
        processingParameters.setProperty(ParameterProcessing.PROCESS_WHERE, ParameterProcessing.LOCAL);
        processingParameters.setProperty(ParameterProcessing.CULLING, cullingText.getText());
        return true;
    }

    private void removeInput() {
        int index = projectInputsList.getSelectedIndex();
        if (index >= 0) {
            ((DefaultListModel) projectInputsList.getModel()).remove(index);
        }
    }

    @SuppressWarnings("unchecked")
    private void addFileInput() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        File f = null;
        try {
            f = new File(new File(".").getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        chooser.setCurrentDirectory(f);
        chooser.showOpenDialog(this);
        File file = chooser.getSelectedFile();
        if (file == null) {
            return;
        }
        String custodian = JOptionPane.showInputDialog("Please enter custodian");
        if (custodian == null) {
            return;
        }
        ((DefaultListModel) projectInputsList.getModel()).addElement(custodian + ": " + file.getPath());
    }

    private void openLuceneSyntaxBrowser() {
        String url = "http://lucene.apache.org/java/2_4_0/queryparsersyntax.html";
        openBrowser(url);
    }

    private void openUriSyntaxBrowser() {
        String url = "http://en.wikipedia.org/wiki/URI_scheme#Generic_syntax";
        openBrowser(url);
    }

    private void openBrowser(String url) {
        boolean success = false;
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = java.awt.Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                    URI uri = new URI(url);
                    desktop.browse(uri);
                    success = true;
                }
            }
        } catch (Exception e) {
            success = false;
        }
        if (!success) {
            JOptionPane.showMessageDialog(this, "Can't open the browser - just go to\n" + url);
        }
    }
    private void addUriInput() {
        String uri = JOptionPane.showInputDialog(this, "Enter input's network locations as URI");
        if (uri == null) {
            return;
        }
        // TODO verify URI?        
        String custodian = JOptionPane.showInputDialog("Please enter custodian");
        if (custodian == null) {
            return;
        }
        ((DefaultListModel) projectInputsList.getModel()).addElement(custodian + ": " + uri);
    }
}
