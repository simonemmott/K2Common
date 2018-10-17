package com.k2.common;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.invoke.MethodHandles;
import java.util.Properties;

import javax.xml.bind.JAXBException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.k2.Util.Version.VersionAndBuild;
import com.k2.app.K2;
import com.k2.app.K2Cli;
import com.k2.app.K2Config;
import com.k2.common.domain.K2DomainManager;
import com.k2.core.K2CoreDomainManager;

import picocli.CommandLine;



public class K2CliTests {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	@Test
	public void helpTest() throws ClassNotFoundException {
		K2Cli cli = new K2Cli();
		new CommandLine(cli).parse("com.k2.common.TestApp");
		assertFalse(cli.showHelp());
		assertEquals(TestApp.class, cli.getAppClass());
		
		cli = new K2Cli();
		new CommandLine(cli).parse("-h", "com.k2.common.TestApp");
		assertTrue(cli.showHelp());

		cli = new K2Cli();
		new CommandLine(cli).parse("--help", "com.k2.common.TestApp");
		assertTrue(cli.showHelp());

		CommandLine.usage(cli, System.out);
	}
	
	@Test
	public void usageTest() throws ClassNotFoundException {
		CommandLine.usage(new K2Cli(), System.out);
	}
	
	@Test
	public void homeTest() throws ClassNotFoundException, FileNotFoundException, JAXBException {
		K2Cli cli = new K2Cli();
		new CommandLine(cli).parse("com.k2.common.TestApp");
		assertEquals("/Users/simon/K2/K2Core", cli.getK2Home().getAbsolutePath());
		K2Config conf = K2Config.read(cli.getConfigFile());
		assertEquals("prop1 value", conf.getProperties(K2CoreDomainManager.class).getProperty("prop1"));
		
		cli = new K2Cli();
		new CommandLine(cli).parse("-d", "/Users/simon/K2/K2Test", "com.k2.common.TestApp");
		assertEquals("/Users/simon/K2/K2Test", cli.getK2Home().getAbsolutePath());
		conf = K2Config.read(cli.getConfigFile());
		assertEquals("test value", conf.getProperties(K2CoreDomainManager.class).getProperty("test"));

		cli = new K2Cli();
		new CommandLine(cli).parse("--home", "/Users/simon/K2/K2Test", "com.k2.common.TestApp");
		assertEquals("/Users/simon/K2/K2Test", cli.getK2Home().getAbsolutePath());
		conf = K2Config.read(cli.getConfigFile());
		assertEquals("test value", conf.getProperties(K2CoreDomainManager.class).getProperty("test"));

	}
	
	@Test
	public void configTest() throws ClassNotFoundException, FileNotFoundException, JAXBException {
		K2Cli cli = new K2Cli();
		new CommandLine(cli).parse("com.k2.common.TestApp");
		assertEquals("/Users/simon/K2/K2Core", cli.getK2Home().getAbsolutePath());
		K2Config conf = K2Config.read(cli.getConfigFile());
		assertEquals("prop1 value", conf.getProperties(K2CoreDomainManager.class).getProperty("prop1"));
		
		cli = new K2Cli();
		new CommandLine(cli).parse("--config", "/Users/simon/K2/K2Test/conf/k2.conf", "com.k2.common.TestApp");
		assertEquals("/Users/simon/K2/K2Core", cli.getK2Home().getAbsolutePath());
		conf = K2Config.read(cli.getConfigFile());
		assertEquals("test value", conf.getProperties(K2CoreDomainManager.class).getProperty("test"));

	}
	
	
	
	
	
	
}
