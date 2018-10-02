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
import com.k2.common.dao.K2DaoFactory;
import com.k2.common.dao.ParameterMap;
import com.k2.common.dao.memoryDao.AbstractMemoryK2Dao;
import com.k2.common.dao.memoryDao.MemoryK2DaoFactory;
import com.k2.common.dao.memoryDao.MemoryK2Sequence;
import com.k2.common.dao.memoryDao.MemoryK2ListCriteriaBuilder;
import com.k2.common.sequence.K2Sequence;
import com.k2.common.sequence.SequenceFieldInitialiser;
import com.k2.core.model.K2Class;
import com.k2.core.model.K2Component;
import com.k2.core.model.K2Field;
import com.k2.core.model.K2Type;
import com.k2.core.model.K2TypeValue;



public class MemoryK2DaoFactoryTests {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
		
	@Test
	public void createFactoryTest() {
		
		K2DaoFactory daoFactory = K2DaoFactory.reflect("com.k2.common");
		
		assertNotNull(daoFactory);
		
	}
	
	@Test
	public void listEntitiesTest() {
		
		K2DaoFactory daoFactory = K2DaoFactory.reflect("com.k2.common");
		
		List<Class<?>> list = daoFactory.getManagedEntities();
		
		Comparator<Class<?>> comp = new Comparator<Class<?>>() {

			@Override
			public int compare(Class<?> c1, Class<?> c2) {
			    if (c1 == c2) {
			        return 0;
			    }
			    if (c1 == null) {
			        return -1;
			    }
			    if (c2 == null) {
			        return 1;
			    }
			    return c1.getName().compareTo(c2.getName());
			}
		};
		
		Collections.sort(list, comp);
		
		assertEquals(EntityTest.class, list.get(0));
		assertEquals(EntityTest2.class, list.get(1));
		assertEquals(2, list.size());
		
	}
	
	@Test
	public void getDaoTest() {

		K2DaoFactory daoFactory = K2DaoFactory.reflect("com.k2.common");
		
		K2Dao<EntityTest,Long> dao = daoFactory.getDao(EntityTest.class);
		
		assertNotNull(dao);

	}
	
	@Test
	public void coreDaoTest() {

		K2DaoFactory daoFactory = K2DaoFactory.reflect("com.k2.core");
		
		K2Dao<K2Component,Long> k2ComponentDao = daoFactory.getDao(K2Component.class);
		K2Dao<K2Class,Long> k2ClassDao = daoFactory.getDao(K2Class.class);
		K2Dao<K2Type,Long> k2TypeDao = daoFactory.getDao(K2Type.class);
		K2Dao<K2Field,Long> k2FieldDao = daoFactory.getDao(K2Field.class);
		K2Dao<K2TypeValue,Long> k2TypeValueDao = daoFactory.getDao(K2TypeValue.class);
		
		assertNotNull(k2ComponentDao);
		assertNotNull(k2ClassDao);
		assertNotNull(k2TypeDao);
		assertNotNull(k2FieldDao);
		assertNotNull(k2TypeValueDao);
		
		K2Component k2Comp = k2ComponentDao.fetch(Long.valueOf(3));
		K2Class k2Cls = k2ClassDao.fetch(Long.valueOf(3));
		
		assertNotNull(k2Comp);
		assertEquals("K2Class", k2Comp.getSimpleName());
		
		assertNotNull(k2Cls);
		assertEquals("K2Class", k2Cls.getSimpleName());
		
		logger.info("Found {} components", k2ComponentDao.list().size());
		for (K2Component comp : k2ComponentDao.list()) {
			logger.info("Reflected component: {} - id: {}", comp.getName(), comp.getId());
		}

		logger.info("Found {} class instances", k2ClassDao.list().size());
		for (K2Class k2cls : k2ClassDao.list()) {
			logger.info("Reflected class: {} - id: {}", k2cls.getName(), k2cls.getId());
		}

		logger.info("Found {} type instances", k2TypeDao.list().size());
		for (K2Type k2type : k2TypeDao.list()) {
			logger.info("Reflected type: {} - id: {}", k2type.getName(), k2type.getId());
		}

		List<K2Field> fields = k2FieldDao.list();
		Comparator<K2Field> fComparator = new Comparator<K2Field>() {
			@Override
			public int compare(K2Field f1, K2Field f2) {
				if (f1==f2) return 0;
				if (f1==null) return -1;
				if (f2==null) return 1;
				return (f1.getDeclaringClass().getName()+"."+f1.getAlias()).compareTo(f2.getDeclaringClass().getName()+"."+f2.getAlias());
			}
		};
		Collections.sort(fields, fComparator);
		logger.info("Found {} field instances", fields.size());
		for (K2Field k2Field : fields) {
			logger.info("Reflected field: {}.{} - id: {}", k2Field.getDeclaringClass().getName(), k2Field.getAlias(), k2Field.getId());
		}

		List<K2TypeValue> typeValues = k2TypeValueDao.list();
		Comparator<K2TypeValue> tvComparator = new Comparator<K2TypeValue>() {
			@Override
			public int compare(K2TypeValue tv1, K2TypeValue tv2) {
				if (tv1==tv2) return 0;
				if (tv1==null) return -1;
				if (tv2==null) return 1;
				return (tv1.getDefiningType().getName()+"."+tv1.getAlias()).compareTo(tv2.getDefiningType().getName()+"."+tv2.getAlias());
			}
		};
		Collections.sort(typeValues, tvComparator);
		logger.info("Found {} type values", typeValues.size());
		for (K2TypeValue k2TypeValue : typeValues) {
			logger.info("Reflected type value: {}.{} - id: {}", k2TypeValue.getDefiningType().getName(), k2TypeValue.getAlias(), k2TypeValue.getId());
		}

	}
	
	
	
	
	
	
}
