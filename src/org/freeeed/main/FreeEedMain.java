package org.freeeed.main;

import java.awt.Desktop;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class FreeEedMain {

	private static final String defaultParameterFile = "default.freeeed.properties";
	private Configuration processingParameters;

	public static String getVersion() {
		return "FreeEed V0.1.6";
	}
	private Options options = formOptions();

	private Options formOptions() {
		Options buildOptions = new Options();
		for (FreeEedOption o : FreeEedOption.values()) {
			buildOptions.addOption(o.getName(), o.isHasArg(), o.getHelp());
		}
		return buildOptions;
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		FreeEedMain instance = new FreeEedMain(args);
		instance.processOptions(args);
	}

	public FreeEedMain(String[] args) {
	}

	private void processOptions(String[] args) {
		try {
			BasicParser parser = new BasicParser();
			CommandLine cl = parser.parse(options, args);

			// one-time actions
			if (cl.hasOption(FreeEedOption.HELP.getName())
					|| cl.getOptions().length == 0) {
				HelpFormatter f = new HelpFormatter();
				f.printHelp("java -jar FreeEed.jar [options]\n\n"
						+ "where options include:", options);
			} else if (cl.hasOption(FreeEedOption.VERSION.getName())) {
				System.out.println(getVersion());
			} else if (cl.hasOption(FreeEedOption.SEARCH.getName())) {
				openBrowserForSearch();
				System.exit(0);
			} else if (cl.hasOption(FreeEedOption.DOC.getName())) {
				openBrowserGitHub();
				System.exit(0);
			}
			// independent actions
			String customParameterFile = null;
			if (cl.hasOption(FreeEedOption.PARAM_FILE.getName())) {
				customParameterFile = cl.getOptionValue(FreeEedOption.PARAM_FILE.getName());
			}
			processingParameters = collectProcessingParameters(customParameterFile);
			echoProcessingParameters(processingParameters);
			if (cl.hasOption(FreeEedOption.DRY.getName())) {
				System.out.println("Dry run - exiting now.");
				System.exit(0);
			}
			if (cl.hasOption(FreeEedOption.INPUT.getName())) {
				processInputOption(cl.getOptionValues(FreeEedOption.INPUT.getName()));
			}
			if (cl.hasOption(FreeEedOption.PROCESS.getName())) {
				runProcessing(cl.getOptionValues(FreeEedOption.PROCESS.getName()));
			}
		} catch (Exception e) {
			// TODO use logging
			e.printStackTrace(System.out);

		}
	}

	private void runProcessing(String[] args) {
		System.out.println("Processing with arguments:");
		for (String arg : args) {
			System.out.println(arg);
		}
		if (args[0].equals("local")) {
			try {
				String[] processingArguments = new String[1];
				processingArguments[0] = processingParameters.getString("output.dir");
				if (new File(processingArguments[0]).exists()) {
					System.out.println("Please remove output directory " + processingArguments[0]);
					System.out.println("For example, in Linux you can do rm -fr " + processingArguments[0]);
					return;
				}
				FreeEedProcess.main(processingArguments);
			} catch (Exception e) {
				e.printStackTrace(System.out);
			}
		}
	}

	private void openBrowserForSearch() {
		try {
			Desktop desktop = Desktop.getDesktop();
			File currentDir = new File(".");
			String uriStr = "file:///" + currentDir.getAbsolutePath()
					+ System.getProperty("file.separator") + "search.html";
			URI uri = new URI(uriStr);
			desktop.browse(uri);
		} catch (Exception e) {
			System.out.println("Oops! Something did not work :(");
			e.printStackTrace(System.out);
		}
	}

	private void openBrowserGitHub() {
		try {
			Desktop desktop = Desktop.getDesktop();
			URI uri = new URI("https://github.com/markkerzner/FreeEed");
			desktop.browse(uri);
		} catch (Exception e) {
			System.out.println("Oops! Something did not work :(");
			e.printStackTrace(System.out);
		}
	}

	private void processInputOption(String[] dirs) {
		System.out.println("Packaging (staging) the following directories for processing:");
		PackageArchive packageArchive = new PackageArchive();
		// TODO - set custom packaging parameters		
		try {

			for (String dir : dirs) {
				System.out.println(dir);
				packageArchive.packageArchive(dir);
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
			// follow the "fail-fast" design pattern
			System.exit(1);
		}
	}

	private Configuration collectProcessingParameters(String customParametersFile) {
		CompositeConfiguration cc = new CompositeConfiguration();
		try {
			Configuration defaults = new PropertiesConfiguration(defaultParameterFile);
			if (customParametersFile != null) {
				Configuration customProperties = new PropertiesConfiguration(customParametersFile);
				cc.addConfiguration(customProperties);
			}
			cc.addConfiguration(defaults);
		} catch (Exception e) {
			e.printStackTrace(System.out);
			// follow the "fail-fast" design pattern
			System.exit(1);
		}
		return cc;
	}

	private void echoProcessingParameters(Configuration configuration)
			throws ConfigurationException, MalformedURLException {
		SimpleDateFormat fileNameFormat = new SimpleDateFormat(
				"yyMMdd_HHmmss");
		String runParameterFileName = "freeeed.parameters."
				+ fileNameFormat.format(new Date()) + ".properties";
		PropertiesConfiguration configToSave = new PropertiesConfiguration();
		configToSave.append(configuration);
		configToSave.save(runParameterFileName);
		System.out.println("Processing parameters are saved to " + runParameterFileName);
	}
}
