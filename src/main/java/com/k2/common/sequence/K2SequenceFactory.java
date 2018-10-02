package com.k2.common.sequence;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.k2.common.K2MetaDataError;
import com.k2.common.dao.memoryDao.MemoryK2Sequence;


public class K2SequenceFactory {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	public static K2SequenceFactory create() {
		return new K2SequenceFactory();
	}

	public static K2SequenceFactory create(Class<?> sequenceClass) {
		return new K2SequenceFactory(sequenceClass);
	}
	
	protected K2SequenceFactory() {
		this.sequenceClass = MemoryK2Sequence.class;
		setConstructors();
	}
	
	protected K2SequenceFactory(Class<?> sequenceClass) {
		this.sequenceClass = sequenceClass;
		setConstructors();
	}
	
	private void setConstructors() {
		try {
			sequenceConstructor = sequenceClass.getConstructor(Class.class, long.class, long.class);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new K2MetaDataError("Unable to identiy sequence constructor for sequence class {}", e, sequenceClass.getName());
		}
	}
	private Class<?> sequenceClass;
	private Constructor<?> sequenceConstructor;
	private Map<Class<?>, K2Sequence<?>> sequences = new HashMap<Class<?>, K2Sequence<?>>();

	public <T> void initialise(Class<T> cls, long initialValue) {
		try {
			@SuppressWarnings("unchecked")
			K2Sequence<T> sequence = (K2Sequence<T>) sequenceConstructor.newInstance(cls, initialValue, 1);
			logger.trace("Initialising sequence for class {} with initial value {}. Sequence current value {}", cls.getName(), initialValue, sequence.currentValue());
			sequences.put(cls, sequence);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new K2MetaDataError("Unable to execute sequence constructor for sequence class {}", e, sequenceClass.getName());
		}
		
	}

	@SuppressWarnings("unchecked")
	public <T> K2Sequence<T> getSequence(Class<T> cls) {
		K2Sequence<T> sequence = (K2Sequence<T>) sequences.get(cls);
		if (sequence != null)
			return sequence;
		initialise(cls,0);
		return (K2Sequence<T>) sequences.get(cls);
	}

	public Set<Class<?>> getSequnceClasses() {
		return sequences.keySet();
	}

}
