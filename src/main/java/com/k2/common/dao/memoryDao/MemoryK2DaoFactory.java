package com.k2.common.dao.memoryDao;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.k2.EntityMap.EntitiesMap;
import com.k2.EntityMap.EntityMap;
import com.k2.Util.StringUtil;
import com.k2.Util.Identity.IdentityUtil;
import com.k2.Util.classes.ClassUtil;
import com.k2.common.annotation.MetaComponent;
import com.k2.common.annotation.MetaDomain;
import com.k2.common.annotation.MetaSequences;
import com.k2.common.dao.FieldInitialiser;
import com.k2.common.dao.K2Dao;
import com.k2.common.dao.K2DaoError;
import com.k2.common.dao.K2DaoFactory;
import com.k2.common.domain.K2DomainError;
import com.k2.common.model.K2Component;
import com.k2.common.model.K2Domain;
import com.k2.common.reflector.K2Reflector;
import com.k2.common.reflector.K2ReflectorError;
import com.k2.common.sequence.K2Sequence;
import com.k2.common.sequence.K2SequenceFactory;

public class MemoryK2DaoFactory implements K2DaoFactory {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private static Class<? extends K2SequenceFactory> getSequenceClass(String ... packageNames) {
		
		for (String packageName : packageNames) {
			for (Class<?> cls : ClassUtil.getClasses(packageName, MetaSequences.class)) {
				if (K2SequenceFactory.class.isAssignableFrom(cls))
					return (Class<? extends K2SequenceFactory>)cls;
			}
		}
		return null;

	}
	
	public static MemoryK2DaoFactory reflect(String ... packageNames) {
		return new MemoryK2DaoFactory(getSequenceClass(packageNames), packageNames);
	}

	public static MemoryK2DaoFactory reflect(Class<? extends K2SequenceFactory> sequencesClass, String ... packageNames) {
		return new MemoryK2DaoFactory(sequencesClass, packageNames);
	}

	private K2Reflector reflector;
//	private Set<Class<?>> managedEntities = new HashSet<Class<?>>();
	private List<Class<?>> managedEntities = new ArrayList<Class<?>>();
	private Map<Class<?>, K2Dao<?,?>> daos = new HashMap<Class<?>, K2Dao<?,?>>();
	
	private MemoryK2DaoFactory(Class<? extends K2SequenceFactory> sequencesClass, String[] packageNames) {

		
//		boolean found = false;
//		for (String packageName : packageNames) {
//			for (Class<?> cls : ClassUtil.getClasses(packageName, MetaSequences.class)) {
//				reflector = K2Reflector.create(cls, EntitiesMap.create());
//				found = true;
//				break;
//			}
//			if (found)
//				break;
//		}
//		if (! found) {
//			reflector = K2Reflector.create();
//		}
		
		if (sequencesClass == null)
			reflector = K2Reflector.create();
		else
			reflector = K2Reflector.create(sequencesClass);
		
		Set<Class<?>> foundClasses = new HashSet<Class<?>>();
		for (String packageName : packageNames) {
			for (Class<?> cls : ClassUtil.getClasses(packageName, MetaComponent.class)) {
				logger.trace("Managing meta component class {} in memory managed entities map", cls.getName());
				if (foundClasses.contains(cls))
					logger.trace("Already managing class {}", cls.getName());
				else {
					foundClasses.add(cls);
					reflector.reflect(cls, K2Component.class);
					if (cls.isAnnotationPresent(Entity.class)) {
						managedEntities.add(cls);
					}
					
				}
			}
		}
		
		for (Class<?> entityClass : managedEntities) {
			daos.put(entityClass, createDao(entityClass));
		}
		
		boolean found = false;
		for (String packageName : packageNames) {
			for (Class<?> cls : ClassUtil.getClasses(packageName, MetaDomain.class)) {
				reflector.reflect(cls, K2Domain.class);
				found = true;
				break;
			}
			if (found)
				break;
		}
//		if (! found) {
//			throw new K2DomainError("No domain manager class found in packages {}", StringUtil.braceConcatenate("[", ", ", "]", (Object[])packageNames));
//		}

	}


	private <E> K2Dao<E,?> createDao(Class<E> entityClass) {
		return createDao(entityClass, IdentityUtil.getKeyClass(entityClass));
	}
	
	@SuppressWarnings("unchecked")
	private <E,K> K2Dao<E,K> createDao(Class<E> entityClass, Class<K> keyClass) {

		K2Dao<E,K> dao;
		K2Sequence<E> seq = reflector.getSequence(entityClass);
		EntityMap<E, K> map = (EntityMap<E, K>) reflector.getEntityMap().getClassMap(entityClass);
		
		if (keyClass.equals(Long.class)) {
			dao = new AbstractMemoryK2Dao<E, K>(entityClass, keyClass, map, seq, FieldInitialiser.fromSequence(entityClass, "id")) {};
		} else {
			dao = new AbstractMemoryK2Dao<E, K>(entityClass, keyClass, map, seq) {};
		}
		return dao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E, K> K2Dao<E, K> getDao(Class<E> entityClass) {
		return (K2Dao<E, K>) daos.get(entityClass);
	}

	@Override
	public List<Class<?>> getManagedEntities() {
		
		List<Class<?>> list = new ArrayList<Class<?>>(managedEntities.size());
		for (Class<?> cls : managedEntities)
			list.add(cls);
		return list;
	}

	@Override
	public <E> K2Sequence<E> getSequence(Class<E> entityClass) {
		return reflector.getSequence(entityClass);
	}
	
	public EntitiesMap getEntitiesMap() {
		return reflector.getEntityMap();
	}


}
