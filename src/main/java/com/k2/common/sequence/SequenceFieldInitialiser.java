package com.k2.common.sequence;

import java.lang.reflect.Field;

import com.k2.Util.classes.ClassUtil;
import com.k2.common.dao.FieldInitialiser;
import com.k2.common.dao.K2DaoError;

public class SequenceFieldInitialiser<T> implements FieldInitialiser<T> {

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
