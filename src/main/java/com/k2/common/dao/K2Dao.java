package com.k2.common.dao;

import java.util.List;

import com.k2.common.criteria.K2ListCriteria;
import com.k2.common.sequence.K2Sequence;

public interface K2Dao<E,K> {

	public Class<E> getEntityClass();
	public Class<K> getKeyClass();
	public E newInstance();
	@SuppressWarnings("unchecked")
	public E newInstance(FieldInitialiser<E> ... initialisers);
	public E newInstance(K2ListCriteria<E> criteria);
	public E newInstance(K2ListCriteria<E> criteria, ParameterMap parameters);
	public E fetch(K key);
	public E fetch(K2ListCriteria<E> criteria);
	public E fetch(K2ListCriteria<E> criteria, ParameterMap parameters);
	public E insert(E entity);
	public E update(E entity);
	public void delete(E entity);
	public List<E> list();
	public List<E> list(K2ListCriteria<E> criteria);
	public List<E> list(K2ListCriteria<E> criteria, ParameterMap parameters);
	public K2Sequence<E> getSequence();
	
}
