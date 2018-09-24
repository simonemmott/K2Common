package com.k2.common.dao.memoryDao;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.LongBinaryOperator;

import com.k2.common.sequence.K2Sequence;

public class MemoryK2Sequence<T> implements K2Sequence<T> {
	
	public MemoryK2Sequence(Class<T> javaType) {
		this.javaType = javaType;
		this.sequence = new AtomicLong(0);
		this.step = 1;
	}
	
	public MemoryK2Sequence(Class<T> javaType, long step) {
		this.javaType = javaType;
		this.sequence = new AtomicLong(0);
		this.step = step;
	}
	
	public MemoryK2Sequence(Class<T> javaType, long start, long step) {
		this.javaType = javaType;
		this.sequence = new AtomicLong(start);
		this.step = step;
	}
	
	private final long step;
	private final AtomicLong sequence;

	private Class<T> javaType;
	@Override
	public Class<T> getJavaType() {
		return javaType;
	}

	@Override
	public long nextValue() {
		return sequence.accumulateAndGet(step, new LongBinaryOperator() {
			@Override
			public long applyAsLong(long left, long right) {
				return left+right;
			}});
	}

	@Override
	public long currentValue() {
		return sequence.get();
	}

}
