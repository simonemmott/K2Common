package com.k2.common.dao.memoryDao;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.LongBinaryOperator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.k2.common.sequence.K2Sequence;

public class MemoryK2Sequence<T> implements K2Sequence<T> {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

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
		logger.trace("Sequence[{}].nextValue(): {}", javaType.getName(), sequence.get()+step);
		return sequence.accumulateAndGet(step, new LongBinaryOperator() {
			@Override
			public long applyAsLong(long left, long right) {
				return left+right;
			}});
	}

	@Override
	public long currentValue() {
		logger.trace("Sequence[{}].currentValue(): {}", javaType.getName(), sequence.get()+step);
		return sequence.get();
	}

}
