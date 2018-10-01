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
import com.k2.common.sequence.K2Sequence;
import com.k2.common.sequence.K2SequenceFactory;



public class K2SequenceFactoryTests {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	
	@Test
	public void newSequenceFactoryTest() {
		
		K2SequenceFactory sequenceFactory = K2SequenceFactory.create();
		
		assertNotNull(sequenceFactory);
	}
		
	@Test
	public void newSequenceFactoryWithSequenceClassTest() {
		
		K2SequenceFactory sequenceFactory = K2SequenceFactory.create(MemoryK2Sequence.class);
		
		assertNotNull(sequenceFactory);
	}
		
	@Test
	public void initialiseSequencesTest() {
		
		K2SequenceFactory sf = K2SequenceFactory.create(MemoryK2Sequence.class);
		
		sf.initialise(EntityTest.class, 120L);
		
		K2Sequence<EntityTest> entityTestSeq = sf.getSequence(EntityTest.class);
		
		K2Sequence<EntityTest2> entityTest2Seq = sf.getSequence(EntityTest2.class);
		
		assertEquals(120L, entityTestSeq.currentValue());
		assertEquals(121L, entityTestSeq.nextValue());
		
		assertEquals(0L, entityTest2Seq.currentValue());
		assertEquals(1L, entityTest2Seq.nextValue());
		
	}
	
	
	
	
	
}
