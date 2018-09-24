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
import com.k2.EntityMap.EntityMap;
import com.k2.JavaAssembly.JavaWidgetFactory;
import com.k2.Util.ObjectUtil;
import com.k2.Util.classes.ClassUtil;
import com.k2.common.criteria.K2Criteria;
import com.k2.common.criteria.K2ListCriteria;
import com.k2.common.dao.AbstractInitialisingK2Dao;
import com.k2.common.dao.FieldInitialiser;
import com.k2.common.dao.K2Dao;
import com.k2.common.dao.ParameterMap;
import com.k2.common.dao.memoryDao.AbstractMemoryK2Dao;
import com.k2.common.dao.memoryDao.MemoryK2ListCriteriaBuilder;
import com.k2.common.dao.memoryDao.MemoryK2Sequence;
import com.k2.common.sequence.K2Sequence;
import com.k2.common.sequence.SequenceFieldInitialiser;



public class AbstractInitialisingK2DaoTests {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
		
	@Test
	public void newInstanceTest() {
		K2Sequence<EntityTest> seq = new MemoryK2Sequence<EntityTest>(EntityTest.class);
		
		K2Dao<EntityTest,Long> dao = new AbstractInitialisingK2Dao<EntityTest, Long>(EntityTest.class, Long.class, seq) {
			@Override public EntityTest fetch(Long key) {return null; }
			@Override public EntityTest fetch(K2ListCriteria<EntityTest> criteria) {return null; }
			@Override public EntityTest fetch(K2ListCriteria<EntityTest> criteria, ParameterMap parameters) {return null; }
			@Override public EntityTest insert(EntityTest entity) {return null; }
			@Override public EntityTest update(EntityTest entity) { return null; }
			@Override public void delete(EntityTest entity) { }
			@Override public List<EntityTest> list() { return null; }
			@Override public List<EntityTest> list(K2ListCriteria<EntityTest> criteria) { return null; }
			@Override public List<EntityTest> list(K2ListCriteria<EntityTest> criteria, ParameterMap parameters) { return null; }
		};
		
		assertEquals(seq, dao.getSequence());
		assertEquals(EntityTest.class, dao.getEntityClass());
		assertEquals(Long.class, dao.getKeyClass());
		
		EntityTest e = dao.newInstance();
		
		assertNotNull(e);
		assertNull(e.id);
		assertNull(e.name);
		assertNull(e.description);
		
	}
	
	@Test
	public void newInstanceWithInitialisationTest() {
		K2Sequence<EntityTest> seq = new MemoryK2Sequence<EntityTest>(EntityTest.class, 1000, 10);
		
		K2Dao<EntityTest,Long> dao = new AbstractInitialisingK2Dao<EntityTest, Long>(
				EntityTest.class, 
				Long.class, 
				seq,
				FieldInitialiser.fromSequence(EntityTest.class, "id"),
				FieldInitialiser.fromLiteral(EntityTest.class, "name", "This is the name")) {
			@Override public EntityTest fetch(Long key) {return null; }
			@Override public EntityTest fetch(K2ListCriteria<EntityTest> criteria) {return null; }
			@Override public EntityTest fetch(K2ListCriteria<EntityTest> criteria, ParameterMap parameters) {return null; }
			@Override public EntityTest insert(EntityTest entity) {return null; }
			@Override public EntityTest update(EntityTest entity) { return null; }
			@Override public void delete(EntityTest entity) { }
			@Override public List<EntityTest> list() { return null; }
			@Override public List<EntityTest> list(K2ListCriteria<EntityTest> criteria) { return null; }
			@Override public List<EntityTest> list(K2ListCriteria<EntityTest> criteria, ParameterMap parameters) { return null; }
		};
		
		assertEquals(seq, dao.getSequence());
		assertEquals(EntityTest.class, dao.getEntityClass());
		assertEquals(Long.class, dao.getKeyClass());
		
		EntityTest e = dao.newInstance();
		
		assertNotNull(e);
		assertEquals(Long.valueOf(1010), e.id);
		assertEquals("This is the name", e.name);
		assertNull(e.description);
		
		EntityTest e2 = dao.newInstance();
		
		assertNotNull(e2);
		assertEquals(Long.valueOf(1020), e2.id);
		assertEquals("This is the name", e2.name);
		assertNull(e2.description);
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void newInstanceWithFieldInitialisationTest() {
		K2Sequence<EntityTest> seq = new MemoryK2Sequence<EntityTest>(EntityTest.class, 1000, 10);
		
		K2Dao<EntityTest,Long> dao = new AbstractInitialisingK2Dao<EntityTest, Long>(
				EntityTest.class, 
				Long.class, 
				seq,
				FieldInitialiser.fromSequence(EntityTest.class, "id"),
				FieldInitialiser.fromLiteral(EntityTest.class, "name", "This is the name"),
				FieldInitialiser.fromField(EntityTest.class, "description", "name")) {
			@Override public EntityTest fetch(Long key) {return null; }
			@Override public EntityTest fetch(K2ListCriteria<EntityTest> criteria) {return null; }
			@Override public EntityTest fetch(K2ListCriteria<EntityTest> criteria, ParameterMap parameters) {return null; }
			@Override public EntityTest insert(EntityTest entity) {return null; }
			@Override public EntityTest update(EntityTest entity) { return null; }
			@Override public void delete(EntityTest entity) { }
			@Override public List<EntityTest> list() { return null; }
			@Override public List<EntityTest> list(K2ListCriteria<EntityTest> criteria) { return null; }
			@Override public List<EntityTest> list(K2ListCriteria<EntityTest> criteria, ParameterMap parameters) { return null; }
		};
		
		assertEquals(seq, dao.getSequence());
		assertEquals(EntityTest.class, dao.getEntityClass());
		assertEquals(Long.class, dao.getKeyClass());
		
		EntityTest e = dao.newInstance();
		
		assertNotNull(e);
		assertEquals(Long.valueOf(1010), e.id);
		assertEquals("This is the name", e.name);
		assertEquals("This is the name", e.description);
		
		EntityTest e2 = dao.newInstance();
		
		assertNotNull(e2);
		assertEquals(Long.valueOf(1020), e2.id);
		assertEquals("This is the name", e2.name);
		assertEquals("This is the name", e2.description);
		
		EntityTest e3 = dao.newInstance(
				FieldInitialiser.fromLiteral(EntityTest.class, "id", 10L),
				FieldInitialiser.fromLiteral(EntityTest.class, "name", "My name")
			);
		
		assertNotNull(e3);
		assertEquals(Long.valueOf(10), e3.id);
		assertEquals("My name", e3.name);
		assertEquals("My name", e3.description);
		
	}
	
	@Test
	public void newInstanceCriteriaTest() {
		K2Sequence<EntityTest> seq = new MemoryK2Sequence<EntityTest>(EntityTest.class);
		
		K2Dao<EntityTest,Long> dao = new AbstractInitialisingK2Dao<EntityTest, Long>(
				EntityTest.class, 
				Long.class, 
				seq,
				FieldInitialiser.fromSequence(EntityTest.class, "id"),
				FieldInitialiser.fromLiteral(EntityTest.class, "name", "This is the name")) {
			@Override public EntityTest fetch(Long key) {return null; }
			@Override public EntityTest fetch(K2ListCriteria<EntityTest> criteria) {return null; }
			@Override public EntityTest fetch(K2ListCriteria<EntityTest> criteria, ParameterMap parameters) {return null; }
			@Override public EntityTest insert(EntityTest entity) {return null; }
			@Override public EntityTest update(EntityTest entity) { return null; }
			@Override public void delete(EntityTest entity) { }
			@Override public List<EntityTest> list() { return null; }
			@Override public List<EntityTest> list(K2ListCriteria<EntityTest> criteria) { return null; }
			@Override public List<EntityTest> list(K2ListCriteria<EntityTest> criteria, ParameterMap parameters) { return null; }
		};
		
		assertEquals(seq, dao.getSequence());
		assertEquals(EntityTest.class, dao.getEntityClass());
		assertEquals(Long.class, dao.getKeyClass());
				
		MemoryK2ListCriteriaBuilder lcb = MemoryK2ListCriteriaBuilder.create(EntityTest.class);
		
		K2ListCriteria<EntityTest> criteria = new EntityTestForNameIs_e2(lcb);

		EntityTest e = dao.newInstance(criteria);
		
		assertEquals(Long.valueOf(1), e.id);
		assertEquals("e2", e.name);
		assertNull(e.description);
				
	}
	
	@Test
	public void newInstanceCriteriaParameterTest() {
		K2Sequence<EntityTest> seq = new MemoryK2Sequence<EntityTest>(EntityTest.class);
		
		K2Dao<EntityTest,Long> dao = new AbstractInitialisingK2Dao<EntityTest, Long>(
				EntityTest.class, 
				Long.class, 
				seq,
				FieldInitialiser.fromSequence(EntityTest.class, "id"),
				FieldInitialiser.fromLiteral(EntityTest.class, "name", "This is the name")) {
			@Override public EntityTest fetch(Long key) {return null; }
			@Override public EntityTest fetch(K2ListCriteria<EntityTest> criteria) {return null; }
			@Override public EntityTest fetch(K2ListCriteria<EntityTest> criteria, ParameterMap parameters) {return null; }
			@Override public EntityTest insert(EntityTest entity) {return null; }
			@Override public EntityTest update(EntityTest entity) { return null; }
			@Override public void delete(EntityTest entity) { }
			@Override public List<EntityTest> list() { return null; }
			@Override public List<EntityTest> list(K2ListCriteria<EntityTest> criteria) { return null; }
			@Override public List<EntityTest> list(K2ListCriteria<EntityTest> criteria, ParameterMap parameters) { return null; }
		};
		
		assertEquals(seq, dao.getSequence());
		assertEquals(EntityTest.class, dao.getEntityClass());
		assertEquals(Long.class, dao.getKeyClass());
				
		MemoryK2ListCriteriaBuilder lcb = MemoryK2ListCriteriaBuilder.create(EntityTest.class);
		
		K2ListCriteria<EntityTest> criteria = new EntityTestForName(lcb);

		EntityTest e = dao.newInstance(criteria, ParameterMap.create("name", "Overriden name"));
		
		assertEquals(Long.valueOf(1), e.id);
		assertEquals("Overriden name", e.name);
		assertNull(e.description);
				
	}
	

	
	
	
	
}
