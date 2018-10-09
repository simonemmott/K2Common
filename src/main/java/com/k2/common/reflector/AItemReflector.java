package com.k2.common.reflector;

public abstract class AItemReflector<I,R> implements ItemReflector<I, R> {

	public AItemReflector(Class<I> itemType, Class<R> reflectionType, K2Reflector reflector) {
		this.itemType = itemType;
		this.reflectionType = reflectionType;
		this.scope = GeneralScopes.GENERAL;
		this.reflector = reflector;
	}
	
	public AItemReflector(Class<I> itemType, Class<R> reflectionType, ReflectionScope scope, K2Reflector reflector) {
		this.itemType = itemType;
		this.reflectionType = reflectionType;
		this.scope = scope;
		this.reflector = reflector;
	}
	
	private final Class<I> itemType;
	@Override
	public Class<I> getItemType() {
		return itemType;
	}

	private final Class<R> reflectionType;
	@Override
	public Class<R> getReflectionType() {
		return reflectionType;
	}

	private final ReflectionScope scope;
	@Override
	public ReflectionScope getReflectionScope() {
		return scope;
	}

	private final K2Reflector reflector;
	@Override
	public K2Reflector getReflector() {
		return reflector;
	}

}
