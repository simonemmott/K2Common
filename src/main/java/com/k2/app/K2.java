package com.k2.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.k2.Util.Version.Version;
import com.k2.common.annotation.K2Application;
import com.k2.common.domain.K2DomainError;
import com.k2.common.domain.K2DomainManager;
import com.k2.core.K2CoreDomainManager;

import picocli.CommandLine;

public class K2 {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	public static K2 start(K2Cli cli) throws FileNotFoundException, ClassNotFoundException {
		return new K2(cli);
	}

	public static K2 start(File configFile) throws FileNotFoundException {
		return new K2(configFile);
	}

	public static K2 start(Class<?> appCls, File configFile) throws FileNotFoundException {
		return new K2(appCls, configFile);
	}

	private final Map<String, K2DomainManager> domains = new HashMap<String, K2DomainManager>();
	private final K2Config config;
	
	private K2(K2Cli cli) throws FileNotFoundException, ClassNotFoundException {
		
		logger.info("Starting the K2 application defined by the class {}", cli.getAppClass().getName());
		logger.info("Starting application with home directory {}", cli.getK2Home().getAbsolutePath());
		logger.info("Loading configuration from {}", cli.getConfigFile().getAbsolutePath());
		
		k2Home = cli.getK2Home();

		try {
			this.config = K2Config.read(cli.getConfigFile());
		} catch (JAXBException e) {
			throw new K2DomainError("Unable to parse the configuration file {}", e, cli.getConfigFile().getAbsolutePath());
		}
		K2Application app = cli.getAppClass().getAnnotation(K2Application.class);
		name = app.name();
		description = app.description();
		version = Version.create(app.version().major(), app.version().minor(), app.version().point(), app.version().build());
		logger.info("Starting {} version: {}", name, version.toString());
		
		for (Class<? extends K2DomainManager> domainManagerClass : app.domainManagers()) {
			K2DomainManager domainManager = K2DomainManager.start(domainManagerClass);
			domainManager.setK2(this);
			domainManager.setProperties(config.getProperties(domainManagerClass));
			domains.put(domainManager.getDomain().getName(), domainManager);
		}
	}
	
	private K2(File configFile) throws FileNotFoundException {

		logger.info("Starting an anonymous K2 application");
		logger.info("Loading configuration from {}", configFile.getAbsolutePath());
		logger.info("Setting the home directory from the environment '{}'", System.getenv("K2_HOME"));
		
		k2Home = new File(System.getenv("K2_HOME"));

		try {
			this.config = K2Config.read(configFile);
		} catch (JAXBException e) {
			throw new K2DomainError("Unable to parse the configuration file {}", e, configFile.getAbsolutePath());
		}
		for (Class<? extends K2DomainManager> domainManagerClass : config.getDomainManagerClasses()) {
			K2DomainManager domainManager = K2DomainManager.start(domainManagerClass);
			domainManager.setK2(this);
			domainManager.setProperties(config.getProperties(domainManagerClass));
			domains.put(domainManager.getDomain().getName(), domainManager);
		}
	}
	
	private K2(Class<?> appCls, File configFile) throws FileNotFoundException {
		
		logger.info("Starting the K2 application defined by the class {}", appCls.getName());
		logger.info("Loading configuration from {}", configFile.getAbsolutePath());
		logger.info("Setting the home directory from the environment '{}'", System.getenv("K2_HOME"));
		
		k2Home = new File(System.getenv("K2_HOME"));
		
		try {
			this.config = K2Config.read(configFile);
		} catch (JAXBException e) {
			throw new K2DomainError("Unable to parse the configuration file {}", e, configFile.getAbsolutePath());
		}
		K2Application app = appCls.getAnnotation(K2Application.class);
		name = app.name();
		description = app.description();
		version = Version.create(app.version().major(), app.version().minor(), app.version().point(), app.version().build());
		logger.info("Starting {} version: {}", name, version.toString());
		
		for (Class<? extends K2DomainManager> domainManagerClass : app.domainManagers()) {
			K2DomainManager domainManager = K2DomainManager.start(domainManagerClass);
			domainManager.setK2(this);
			domainManager.setProperties(config.getProperties(domainManagerClass));
			domains.put(domainManager.getDomain().getName(), domainManager);
		}
	}
	
	protected K2(K2Config config) {
		this.config = config;
	}

	public K2DomainManager getDomainManager(String domainName) {
		K2DomainManager dm = domains.get(domainName);
		if (dm == null)
			throw new K2DomainError("This K2 application does not manage a domain named {}", domainName);
		return dm;
	}
	
	public List<String> getDomainNames() {
		List<String> list = Lists.newArrayList(domains.keySet());
		Collections.sort(list);
		return list;
	}
	
	private String name = "Unknown";
	public String getName() { return name; }
	
	private String description = "";
	public String getDescription() { return description; }
	
	private Version version = Version.create();
	public Version getVersion() { return version; }
	
	private File k2Home;
	public File getK2Home() { return k2Home; }

	public List<Class<? extends K2DomainManager>> getDomainManagerClasses() {
		return config.getDomainManagerClasses();
	}
	
	public static void main(String[] args) throws ClassNotFoundException, FileNotFoundException {
		K2Cli cli = new K2Cli();
		
		new CommandLine(cli).parse(args);
		
		if (cli.showHelp()) {
			CommandLine.usage(cli, System.out);
			System.exit(0);
		}
		
		K2 k2 = K2.start(cli);

	}


	
}
