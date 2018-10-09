package com.k2.common.dao;

public class UniversalKey<E,K> {
	
	private final Class<E> entityClass;
	private final K key;
	public UniversalKey(Class<E> entityClass, K key) {
		this.entityClass = entityClass;
		this.key = key;
	}
	
	public Class<E> getEntityClass() { return entityClass; }
	public K getKey() { return key; }

}
