package com.k2.common.reflector;

public interface ItemReflector<I,R> {
	
	public Class<I> getItemType();
	public Class<R> getReflectionType();
	public ReflectionScope getReflectionScope();
	public K2Reflector getReflector();
	public R reflect(I item);

}
