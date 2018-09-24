package com.k2.common.sequence;

public interface K2Sequence<T> {
	
	public Class<T> getJavaType();
	public long nextValue();
	public long currentValue();

}
