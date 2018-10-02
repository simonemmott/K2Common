package com.k2.common.dao.memoryDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;

import com.k2.EntityMap.EntitiesMap;
import com.k2.EntityMap.EntityMap;
import com.k2.Util.Identity.IdentityUtil;
import com.k2.Util.classes.ClassUtil;
import com.k2.common.annotation.MetaComponent;
import com.k2.common.annotation.MetaSequences;
import com.k2.common.dao.FieldInitialiser;
import com.k2.common.dao.K2Dao;
import com.k2.common.dao.K2DaoFactory;
import com.k2.common.reflector.K2Reflector;
import com.k2.common.sequence.K2Sequence;

public class MemoryK2DaoFactory implements K2DaoFactory {
	
	public static MemoryK2DaoFactory reflect(String ... packageNames) {
		return new MemoryK2DaoFactory(packageNames);
	}

	private K2Reflector reflector;
	private List<Class<?>> managedEntities = new ArrayList<Class<?>>();
	private Map<Class<?>, K2Dao<?,?>> daos = new HashMap<Class<?>, K2Dao<?,?>>();
	
	private MemoryK2DaoFactory(String[] packageNames) {
		
		boolean found = false;
		for (String packageName : packageNames) {
			for (Class<?> cls : ClassUtil.getClasses(packageName, MetaSequences.class)) {
				reflector = K2Reflector.create(cls, EntitiesMap.create());
				found = true;
				break;
			}
			if (found)
				break;
		}
		if (! found) {
			reflector = K2Reflector.create(EntitiesMap.create());
		}
		
		for (String packageName : packageNames) {
			for (Class<?> cls : ClassUtil.getClasses(packageName, MetaComponent.class)) {
				reflector.reflect(cls);
				if (cls.isAnnotationPresent(Entity.class)) {
					managedEntities.add(cls);
				}
			}
		}
		
		for (Class<?> entityClass : managedEntities) {
			daos.put(entityClass, createDao(entityClass));
		}
		
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
		return managedEntities;
	}

	@Override
	public <E> K2Sequence<E> getSequence(Class<E> entityClass) {
		return reflector.getSequence(entityClass);
	}
	
	public EntitiesMap getEntitiesMap() {
		return reflector.getEntityMap();
	}


}
