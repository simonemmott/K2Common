package com.k2.common.dao;

import java.util.List;

import com.k2.common.dao.memoryDao.MemoryK2DaoFactory;
import com.k2.common.sequence.K2Sequence;
import com.k2.common.sequence.K2SequenceFactory;

public interface K2DaoFactory {

	public static K2DaoFactory reflect(String ... packageNames) {
		return MemoryK2DaoFactory.reflect(packageNames);
	}
	
	public static K2DaoFactory reflect(Class<? extends K2SequenceFactory> sequencesClass, String ... packageNames) {
		return MemoryK2DaoFactory.reflect(sequencesClass, packageNames);
	}
	
	public <E,K> K2Dao<E,K> getDao(Class<E> entityClass);
	public List<Class<?>> getManagedEntities();
	public <E> K2Sequence<E> getSequence(Class<E> entityClass);
	
	
}
