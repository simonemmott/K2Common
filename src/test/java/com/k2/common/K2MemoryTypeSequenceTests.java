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
import com.k2.common.dao.memoryDao.MemoryK2Sequence;



public class K2MemoryTypeSequenceTests {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	@Test
	public void defaultSequenceTest() {
		MemoryK2Sequence<Object> mts = new MemoryK2Sequence<Object>(Object.class);
		
		assertEquals(0, mts.currentValue());
		assertEquals(1, mts.nextValue());
		assertEquals(1, mts.currentValue());
		assertEquals(2, mts.nextValue());
		assertEquals(2, mts.currentValue());
		assertEquals(3, mts.nextValue());
		assertEquals(3, mts.currentValue());
		assertEquals(4, mts.nextValue());
		assertEquals(4, mts.currentValue());
		
		assertEquals(Object.class, mts.getJavaType());
		
	}
	
	@Test
	public void steppedSequenceTest() {
		MemoryK2Sequence<Object> mts = new MemoryK2Sequence<Object>(Object.class, 10);
		
		assertEquals(0, mts.currentValue());
		assertEquals(10, mts.nextValue());
		assertEquals(10, mts.currentValue());
		assertEquals(20, mts.nextValue());
		assertEquals(20, mts.currentValue());
		assertEquals(30, mts.nextValue());
		assertEquals(30, mts.currentValue());
		assertEquals(40, mts.nextValue());
		assertEquals(40, mts.currentValue());
	}
	
	@Test
	public void initialValueSteppedSequenceTest() {
		MemoryK2Sequence<Object> mts = new MemoryK2Sequence<Object>(Object.class, 1000, 10);
		
		assertEquals(1000, mts.currentValue());
		assertEquals(1010, mts.nextValue());
		assertEquals(1010, mts.currentValue());
		assertEquals(1020, mts.nextValue());
		assertEquals(1020, mts.currentValue());
		assertEquals(1030, mts.nextValue());
		assertEquals(1030, mts.currentValue());
		assertEquals(1040, mts.nextValue());
		assertEquals(1040, mts.currentValue());
	}
	
	
	
	
	
	
	
}
