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
import com.k2.common.reflector.K2Reflector;
import com.k2.core.model.K2Class;



public class K2ReflectorTests {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	
	@Test
	public void createReflectorTest() {
		
		EntitiesMap em = new EntitiesMap();
		K2Reflector reflector = K2Reflector.create(em);
		assertEquals(em, reflector.getEntityMap());
	}
	
	@Test
	public void reflectK2ClassTest() {
		
		K2Reflector reflector = K2Reflector.create(new EntitiesMap());
		
		K2Class k2Cls = (K2Class) reflector.reflect(K2Class.class);
		
		assertEquals(Long.valueOf(3), k2Cls.getId());
		assertEquals("com.k2.core.model.k2Class", k2Cls.getName());
		
		
	}
	
	
	
	
	
	
}
