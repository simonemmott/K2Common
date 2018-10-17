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
import com.k2.app.K2Config;
import com.k2.common.domain.K2DomainManager;
import com.k2.core.K2CoreDomainManager;



public class K2Tests {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	@Test
	public void configTest() throws JAXBException {
		K2Config config = new K2Config();
		Properties prop = new Properties();
		prop.setProperty("prop1", "prop1 value");
		prop.setProperty("prop2", "prop2 value");
		prop.setProperty("prop3", "prop3 value");
		
		config.setProperties(K2CoreDomainManager.class, prop);
		
		assertEquals(1, config.getDomainManagerClasses().size());
		assertEquals(K2CoreDomainManager.class, config.getDomainManagerClasses().get(0));
		assertEquals("prop1 value", config.getProperties(K2CoreDomainManager.class).getProperty("prop1"));
		assertEquals("prop2 value", config.getProperties(K2CoreDomainManager.class).getProperty("prop2"));
		assertEquals("prop3 value", config.getProperties(K2CoreDomainManager.class).getProperty("prop3"));
		
		File configFile = new File("src/test/resource/configTest.conf");
		config.write(configFile);
	}
	
	
	@Test
	public void startK2FromFileTest() throws FileNotFoundException {
		File config = new File("src/test/resource/test.conf");
		
		assertTrue(config.exists());
		assertTrue(config.isFile());
		
		K2 app = K2.start(config);
		
		assertNotNull(app);
		
		assertEquals(1, app.getDomainNames().size());
		assertEquals("K2 Core", app.getDomainNames().get(0));
		
		K2DomainManager dm = app.getDomainManager("K2 Core");
		
		assertNotNull(dm);
		
		assertEquals("prop1 value", dm.getProperty("prop1"));
		assertEquals("prop2 value", dm.getProperty("prop2"));
		assertEquals("prop3 value", dm.getProperty("prop3"));
		
		assertNotNull(dm.getDomain());
		
	}
	
	@Test
	public void startK2FromClassAndFileTest() throws FileNotFoundException {
		File config = new File("src/test/resource/test.conf");
		
		assertTrue(config.exists());
		assertTrue(config.isFile());
		
		K2 app = K2.start(TestApp.class, config);
		
		assertNotNull(app);
		
		assertEquals(1, app.getDomainNames().size());
		assertEquals("K2 Core", app.getDomainNames().get(0));
		
		K2DomainManager dm = app.getDomainManager("K2 Core");
		
		assertNotNull(dm);
		
		assertEquals("prop1 value", dm.getProperty("prop1"));
		assertEquals("prop2 value", dm.getProperty("prop2"));
		assertEquals("prop3 value", dm.getProperty("prop3"));
		
		assertNotNull(dm.getDomain());
		
		assertEquals("Test Application", app.getName());
		assertEquals("This is a test application", app.getDescription());

		assertEquals(1, app.getVersion().major());
		assertEquals(2, app.getVersion().minor());
		assertEquals(3, app.getVersion().point());
		assertTrue(app.getVersion() instanceof VersionAndBuild);
		assertEquals(4, ((VersionAndBuild)app.getVersion()).buildNumber());

	}
	
	
	
	
	
	
}
