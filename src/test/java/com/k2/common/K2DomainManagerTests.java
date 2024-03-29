package com.k2.common;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.k2.EntityMap.EntitiesMap;
import com.k2.JavaAssembly.JavaWidgetFactory;
import com.k2.Util.ObjectUtil;
import com.k2.Util.classes.ClassUtil;
import com.k2.common.domain.K2DomainManager;
import com.k2.common.model.K2Class;
import com.k2.common.model.K2Domain;
import com.k2.common.model.K2Type;
import com.k2.common.reflector.K2Reflector;
import com.k2.common.types.ClassType;
import com.k2.core.K2CoreDomainManager;
import com.k2.core.K2CoreSequences;



public class K2DomainManagerTests {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	
	@Test
	public void createDomainManagerFromPackagesTest() {
		
		K2DomainManager domainManager = K2DomainManager.reflect("com.k2.common", "com.k2.core");
		
		assertNotNull(domainManager);
		assertNotNull(domainManager.getDomain());
		assertNotNull(domainManager.getDaoFactory());
		
		assertEquals(Long.valueOf(1), domainManager.getDomain().getId());
		assertEquals("K2 Core", domainManager.getDomain().getName());
		assertEquals("This is the K2 core development domain", domainManager.getDomain().getDescription());
		assertEquals("com.k2.core.K2CoreDomainManager", domainManager.getDomain().getDomainClassName());
		
	}
	
	@Test
	public void createDomainManagerFromClassTest() {
		
		K2DomainManager domainManager = K2DomainManager.start(K2CoreDomainManager.class);
		
		assertNotNull(domainManager);
		assertNotNull(domainManager.getDomain());
		assertNotNull(domainManager.getDaoFactory());
		
		assertEquals(Long.valueOf(1), domainManager.getDomain().getId());
		assertEquals("K2 Core", domainManager.getDomain().getName());
		assertEquals("This is the K2 core development domain", domainManager.getDomain().getDescription());
		assertEquals("com.k2.core.K2CoreDomainManager", domainManager.getDomain().getDomainClassName());
		
	}
	
	@Test
	public void DomainManagerAwareFromPackagesTest() {
		
		K2DomainManager domainManager = K2DomainManager.reflect("com.k2.common", "com.k2.core");
		
		K2Domain domain = domainManager.getDomain();
		
		assertNotNull(domain.getDomainManager());
		
		assertEquals(domainManager, domain.getDomainManager());
		
	}
	
	@Test
	public void DomainManagerAwareFromClassTest() {
		
		K2DomainManager domainManager = K2DomainManager.start(K2CoreDomainManager.class);
		
		K2Domain domain = domainManager.getDomain();
		
		assertNotNull(domain.getDomainManager());
		
		assertEquals(domainManager, domain.getDomainManager());
		
	}
	
	
	
	
	
	
}
