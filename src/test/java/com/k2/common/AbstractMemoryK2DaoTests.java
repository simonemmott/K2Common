package com.k2.common;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import com.k2.common.dao.K2DaoError;
import com.k2.common.dao.ParameterMap;
import com.k2.common.dao.memoryDao.AbstractMemoryK2Dao;
import com.k2.common.dao.memoryDao.MemoryK2Sequence;
import com.k2.common.dao.memoryDao.MemoryK2ListCriteriaBuilder;
import com.k2.common.sequence.K2Sequence;
import com.k2.common.sequence.SequenceFieldInitialiser;



public class AbstractMemoryK2DaoTests {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
		
	@Test
	public void fetchTest() {
		K2Sequence<EntityTest> seq = new MemoryK2Sequence<EntityTest>(EntityTest.class);
		
		EntityMap<EntityTest, Long> map = EntityMap.create(EntityTest.class, Long.class);
		
		K2Dao<EntityTest, Long> dao = new AbstractMemoryK2Dao<EntityTest, Long>(EntityTest.class, Long.class, map, seq, FieldInitialiser.fromSequence(EntityTest.class, "id")) {};
		
		assertEquals(seq, dao.getSequence());
		assertEquals(EntityTest.class, dao.getEntityClass());
		assertEquals(Long.class, dao.getKeyClass());
		
		EntityTest e = dao.newInstance();
		e.name = "e1";
		e.description = "This is e1";
		dao.insert(e);
		
		e = dao.newInstance();
		e.name = "e2";
		e.description = "This is e2";
		dao.insert(e);
		
		e = dao.newInstance();
		e.name = "e3";
		e.description = "This is e3";
		dao.insert(e);
		
		e = dao.fetch(2L);
		
		assertNotNull(e);
		assertEquals(Long.valueOf(2), e.id);
		assertEquals("e2", e.name);
		assertEquals("This is e2", e.description);
		
	}
	
	@Test
	public void fetchCriteriaTest() {
		K2Sequence<EntityTest> seq = new MemoryK2Sequence<EntityTest>(EntityTest.class);
		
		EntityMap<EntityTest, Long> map = EntityMap.create(EntityTest.class, Long.class);
		
		K2Dao<EntityTest, Long> dao = new AbstractMemoryK2Dao<EntityTest, Long>(EntityTest.class, Long.class, map, seq, FieldInitialiser.fromSequence(EntityTest.class, "id")) {};
		
		assertEquals(seq, dao.getSequence());
		assertEquals(EntityTest.class, dao.getEntityClass());
		assertEquals(Long.class, dao.getKeyClass());
		
		EntityTest e = dao.newInstance();
		e.name = "e1";
		e.description = "This is e1";
		dao.insert(e);
		
		e = dao.newInstance();
		e.name = "e2";
		e.description = "This is e2";
		dao.insert(e);
		
		e = dao.newInstance();
		e.name = "e3";
		e.description = "This is e3";
		dao.insert(e);
		
		MemoryK2ListCriteriaBuilder lcb = MemoryK2ListCriteriaBuilder.create(EntityTest.class);
		
		K2ListCriteria<EntityTest> criteria = new EntityTestForNameIs_e2(lcb);
		
		e = dao.fetch(criteria);
		
		assertNotNull(e);
		assertEquals(Long.valueOf(2), e.id);
		assertEquals("e2", e.name);
		assertEquals("This is e2", e.description);
		
	}
	
	@Test
	public void fetchCriteriaParameterTest() {
		K2Sequence<EntityTest> seq = new MemoryK2Sequence<EntityTest>(EntityTest.class);
		
		EntityMap<EntityTest, Long> map = EntityMap.create(EntityTest.class, Long.class);
		
		K2Dao<EntityTest, Long> dao = new AbstractMemoryK2Dao<EntityTest, Long>(EntityTest.class, Long.class, map, seq, FieldInitialiser.fromSequence(EntityTest.class, "id")) {};
		
		assertEquals(seq, dao.getSequence());
		assertEquals(EntityTest.class, dao.getEntityClass());
		assertEquals(Long.class, dao.getKeyClass());
		
		EntityTest e = dao.newInstance();
		e.name = "e1";
		e.description = "This is e1";
		dao.insert(e);
		
		e = dao.newInstance();
		e.name = "e2";
		e.description = "This is e2";
		dao.insert(e);
		
		e = dao.newInstance();
		e.name = "e3";
		e.description = "This is e3";
		dao.insert(e);
		
		MemoryK2ListCriteriaBuilder lcb = MemoryK2ListCriteriaBuilder.create(EntityTest.class);
		
		K2ListCriteria<EntityTest> criteria = new EntityTestForName(lcb);
		
		e = dao.fetch(criteria, ParameterMap.create("name", "e1"));
		
		assertNotNull(e);
		assertEquals(Long.valueOf(1), e.id);
		
	}
	
	@Test
	public void insertTest() {
		K2Sequence<EntityTest> seq = new MemoryK2Sequence<EntityTest>(EntityTest.class);
		
		EntityMap<EntityTest, Long> map = EntityMap.create(EntityTest.class, Long.class);
		
		K2Dao<EntityTest, Long> dao = new AbstractMemoryK2Dao<EntityTest, Long>(EntityTest.class, Long.class, map, seq, FieldInitialiser.fromSequence(EntityTest.class, "id")) {};
		
		assertEquals(seq, dao.getSequence());
		assertEquals(EntityTest.class, dao.getEntityClass());
		assertEquals(Long.class, dao.getKeyClass());
		
		EntityTest e = dao.newInstance();
		e.name = "e1";
		e.description = "This is e1";
		dao.insert(e);
		
		e = dao.newInstance();
		e.name = "e2";
		e.description = "This is e2";
		dao.insert(e);
		
		e = dao.newInstance();
		e.name = "e3";
		e.description = "This is e3";
		dao.insert(e);
		
		e = dao.fetch(2L);
		
		try {
			dao.insert(e);
			fail("An error should be thrown");
		} catch(Throwable t) {
			assertEquals(K2DaoError.class, t.getClass());
		}
		
	}
	
	@Test
	public void newInstanceCriteriaTest() {
		K2Sequence<EntityTest> seq = new MemoryK2Sequence<EntityTest>(EntityTest.class);
		
		EntityMap<EntityTest, Long> map = EntityMap.create(EntityTest.class, Long.class);
		
		K2Dao<EntityTest, Long> dao = new AbstractMemoryK2Dao<EntityTest, Long>(EntityTest.class, Long.class, map, seq, FieldInitialiser.fromSequence(EntityTest.class, "id")) {};
		
		assertEquals(seq, dao.getSequence());
		assertEquals(EntityTest.class, dao.getEntityClass());
		assertEquals(Long.class, dao.getKeyClass());
		
		MemoryK2ListCriteriaBuilder lcb = MemoryK2ListCriteriaBuilder.create(EntityTest.class);
		
		K2ListCriteria<EntityTest> criteria = new EntityTestForNameIs_e2(lcb);

		EntityTest e = dao.newInstance(criteria);
		
		assertEquals("e2", e.name);
				
	}
	
	@Test
	public void newInstanceCriteriaParameterTest() {
		K2Sequence<EntityTest> seq = new MemoryK2Sequence<EntityTest>(EntityTest.class);
		
		EntityMap<EntityTest, Long> map = EntityMap.create(EntityTest.class, Long.class);
		
		K2Dao<EntityTest, Long> dao = new AbstractMemoryK2Dao<EntityTest, Long>(EntityTest.class, Long.class, map, seq, FieldInitialiser.fromSequence(EntityTest.class, "id")) {};
		
		assertEquals(seq, dao.getSequence());
		assertEquals(EntityTest.class, dao.getEntityClass());
		assertEquals(Long.class, dao.getKeyClass());
		
		MemoryK2ListCriteriaBuilder lcb = MemoryK2ListCriteriaBuilder.create(EntityTest.class);
		
		K2ListCriteria<EntityTest> criteria = new EntityTestForName(lcb);
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
		System.out.println(gson.toJson(criteria.getRootNode()));

		EntityTest e = dao.newInstance(criteria, ParameterMap.create("name", "This is a name"));
		
		assertEquals("This is a name", e.name);
				
	}
	
	@Test
	public void updateTest() {
		K2Sequence<EntityTest> seq = new MemoryK2Sequence<EntityTest>(EntityTest.class);
		
		EntityMap<EntityTest, Long> map = EntityMap.create(EntityTest.class, Long.class);
		
		K2Dao<EntityTest, Long> dao = new AbstractMemoryK2Dao<EntityTest, Long>(EntityTest.class, Long.class, map, seq, FieldInitialiser.fromSequence(EntityTest.class, "id")) {};
		
		assertEquals(seq, dao.getSequence());
		assertEquals(EntityTest.class, dao.getEntityClass());
		assertEquals(Long.class, dao.getKeyClass());
		
		EntityTest e = dao.newInstance();
		e.name = "e1";
		e.description = "This is e1";
		dao.insert(e);
		
		e = dao.newInstance();
		e.name = "e2";
		e.description = "This is e2";
		dao.insert(e);
		
		e = dao.newInstance();
		e.name = "e3";
		e.description = "This is e3";
		dao.insert(e);
		
		e = dao.fetch(2L);
		
		e.name = "Updated Name";
		dao.update(e);
		
		e = null;
		
		e = dao.fetch(2L);
		assertEquals("Updated Name", e.name);
		
		e = dao.newInstance();
		e.name = "e2";
		e.description = "This is e2";
		try {
			dao.update(e);
			fail("An error should be thrown");
		} catch(Throwable t) {
			assertEquals(K2DaoError.class, t.getClass());
		}

		
	}
	
	@Test
	public void deleteTest() {
		K2Sequence<EntityTest> seq = new MemoryK2Sequence<EntityTest>(EntityTest.class);
		
		EntityMap<EntityTest, Long> map = EntityMap.create(EntityTest.class, Long.class);
		
		K2Dao<EntityTest, Long> dao = new AbstractMemoryK2Dao<EntityTest, Long>(EntityTest.class, Long.class, map, seq, FieldInitialiser.fromSequence(EntityTest.class, "id")) {};
		
		assertEquals(seq, dao.getSequence());
		assertEquals(EntityTest.class, dao.getEntityClass());
		assertEquals(Long.class, dao.getKeyClass());
		
		EntityTest e = dao.newInstance();
		e.name = "e1";
		e.description = "This is e1";
		dao.insert(e);
		
		e = dao.newInstance();
		e.name = "e2";
		e.description = "This is e2";
		dao.insert(e);
		
		e = dao.newInstance();
		e.name = "e3";
		e.description = "This is e3";
		dao.insert(e);
		
		e = dao.fetch(2L);
		
		assertNotNull(e);
		
		dao.delete(e);
		
		e = dao.fetch(2L);
		
		assertNull(e);
		
		e = dao.fetch(1L);
		
		EntityTest e2 = ObjectUtil.clone(e);
		e2.id = 2L;
		
		try {
			dao.delete(e2);
			fail("An error should be thrown");
		} catch (Throwable t) {
			assertEquals(K2DaoError.class, t.getClass());
		}
		
		
	}
	
	@Test
	public void listTest() {
		K2Sequence<EntityTest> seq = new MemoryK2Sequence<EntityTest>(EntityTest.class);
		
		EntityMap<EntityTest, Long> map = EntityMap.create(EntityTest.class, Long.class);
		
		K2Dao<EntityTest, Long> dao = new AbstractMemoryK2Dao<EntityTest, Long>(EntityTest.class, Long.class, map, seq, FieldInitialiser.fromSequence(EntityTest.class, "id")) {};
		
		assertEquals(seq, dao.getSequence());
		assertEquals(EntityTest.class, dao.getEntityClass());
		assertEquals(Long.class, dao.getKeyClass());
		
		EntityTest e = dao.newInstance();
		e.name = "e1";
		e.description = "This is e1";
		dao.insert(e);
		
		e = dao.newInstance();
		e.name = "e2";
		e.description = "This is e2";
		dao.insert(e);
		
		e = dao.newInstance();
		e.name = "e3";
		e.description = "This is e3";
		dao.insert(e);
		
		List<EntityTest> list = dao.list();
		
		assertEquals(3, list.size());
		
		Comparator<EntityTest> comp = new Comparator<EntityTest>() {

			@Override
			public int compare(EntityTest o1, EntityTest o2) {
				return Long.valueOf(o1.id - o2.id).intValue();
			}};
			
		Collections.sort(list, comp);
		
		assertEquals(Long.valueOf(1), list.get(0).id);
		assertEquals(Long.valueOf(2), list.get(1).id);
		assertEquals(Long.valueOf(3), list.get(2).id);
		
	}
	
	@Test
	public void criteriaListTest() {
		K2Sequence<EntityTest> seq = new MemoryK2Sequence<EntityTest>(EntityTest.class);
		
		EntityMap<EntityTest, Long> map = EntityMap.create(EntityTest.class, Long.class);
		
		K2Dao<EntityTest, Long> dao = new AbstractMemoryK2Dao<EntityTest, Long>(EntityTest.class, Long.class, map, seq, FieldInitialiser.fromSequence(EntityTest.class, "id")) {};
		
		assertEquals(seq, dao.getSequence());
		assertEquals(EntityTest.class, dao.getEntityClass());
		assertEquals(Long.class, dao.getKeyClass());
		
		EntityTest e = dao.newInstance();
		e.name = "e1";
		e.description = "This is e1";
		dao.insert(e);
		
		e = dao.newInstance();
		e.name = "e2";
		e.description = "This is e2";
		dao.insert(e);
		
		e = dao.newInstance();
		e.name = "e3";
		e.description = "This is e3";
		dao.insert(e);
		
		MemoryK2ListCriteriaBuilder lcb = MemoryK2ListCriteriaBuilder.create(EntityTest.class);
		
		K2ListCriteria<EntityTest> criteria = new EntityTestForNameIs_e2(lcb);
		
		assertNotNull(criteria.getRootNode());
		
		List<EntityTest> list = dao.list(criteria);
		
		assertEquals(1, list.size());
				
		assertEquals(Long.valueOf(2), list.get(0).id);
		
	}
	
	@Test
	public void criteriaParametersListTest() {
		K2Sequence<EntityTest> seq = new MemoryK2Sequence<EntityTest>(EntityTest.class);
		
		EntityMap<EntityTest, Long> map = EntityMap.create(EntityTest.class, Long.class);
		
		K2Dao<EntityTest, Long> dao = new AbstractMemoryK2Dao<EntityTest, Long>(EntityTest.class, Long.class, map, seq, FieldInitialiser.fromSequence(EntityTest.class, "id")) {};
		
		assertEquals(seq, dao.getSequence());
		assertEquals(EntityTest.class, dao.getEntityClass());
		assertEquals(Long.class, dao.getKeyClass());
		
		EntityTest e = dao.newInstance();
		e.name = "e1";
		e.description = "This is e1";
		dao.insert(e);
		
		e = dao.newInstance();
		e.name = "e2";
		e.description = "This is e2";
		dao.insert(e);
		
		e = dao.newInstance();
		e.name = "e3";
		e.description = "This is e3";
		dao.insert(e);
		
		MemoryK2ListCriteriaBuilder lcb = MemoryK2ListCriteriaBuilder.create(EntityTest.class);
		
		K2ListCriteria<EntityTest> criteria = new EntityTestForName(lcb);
		
		assertNotNull(criteria.getRootNode());
		
		List<EntityTest> list = dao.list(criteria, ParameterMap.create("name", "e1"));
		
		assertEquals(1, list.size());
				
		assertEquals(Long.valueOf(1), list.get(0).id);
		
	}
	
	
	
	
	
	
	
}
