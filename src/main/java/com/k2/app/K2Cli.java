package com.k2.app;

import java.io.File;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(
		name="k2",
		description="Start a K2 application from the command line",
		footer="Copyright(c) 2018"
	)
public class K2Cli {
	
	@Option(names= {"-h", "--help"}, usageHelp = true, description = "display this help and exit")
	private boolean help = false;
	public boolean showHelp() { return help; }
	
	@Option(names = {"-d", "--home"}, description="Set the K2 home directory. This option overrides the value of the environment variable $K2_HOME")
	private File k2Home = null;
	public File getK2Home() { 
		if (k2Home == null) {
			String k2HomeEnv = System.getenv("K2_HOME");
			if (k2HomeEnv == null)
				throw new K2CliError("No value defined for the location of the K2 home directory");
			k2Home = new File(k2HomeEnv);
		}
		if (!k2Home.exists())
			throw new K2CliError("The defined K2 home directory {} does not exist", k2Home.getPath());
		if (k2Home.isFile())
			throw new K2CliError("The defined K2 home directory {} is not a directory", k2Home.getPath());
		return k2Home; 
	}
	
	@Option(names={"--config"}, description="The location of the k2 configuration file for the application if the file is 'k2.conf' in the conf directory of the k2 home directory")
	private File configFile= null;
	public File getConfigFile() {
		if (configFile == null) {
			configFile = getK2Home().toPath().resolve("conf").resolve("k2.conf").toFile();
		}
		if (!configFile.exists())
			throw new K2CliError("The configuration file {} does not exist", configFile.getPath());
		if (configFile.isDirectory())
			throw new K2CliError("The defined configuration file {} is a directory", configFile.getPath());
		return configFile;
	}
	
	@Parameters(index="0", description="The class defining the application to start.")
	private String appClass;
	public Class<?> getAppClass() throws ClassNotFoundException {
		return Class.forName(appClass);
	}

}
