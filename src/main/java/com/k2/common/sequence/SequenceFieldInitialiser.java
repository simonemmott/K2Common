package com.k2.common.sequence;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.k2.Util.classes.ClassUtil;
import com.k2.common.dao.FieldInitialiser;
import com.k2.common.dao.K2DaoError;

public class SequenceFieldInitialiser<T> implements FieldInitialiser<T> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	public SequenceFieldInitialiser(Class<T> initialiseClass, String targetField) {
		this.targetField = ClassUtil.getField(initialiseClass, targetField);
	}
	
	private final Field targetField;
	
	private K2Sequence<T> sequence;
	public void setSequence(K2Sequence<T> sequence) {
		this.sequence = sequence;
	}

	@Override
	public boolean initialise(T target) {
		logger.trace("Initialising field {}.{} with next sequence value", targetField.getDeclaringClass().getName(), targetField.getName());
		try {
			if (toBeInitialised(target)) {
				targetField.set(target, sequence.nextValue());
				return true;
			}
			return false;
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new K2DaoError("Unable to access field during initialisatation", e);
		}
		
	}

	@Override
	public boolean toBeInitialised(T target) {
		try {
			return (targetField.get(target) == null );
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new K2DaoError("Unable to access field during initialisatation", e);
		}
	}

}
