package com.k2.common.dao;

import java.lang.reflect.Field;

import com.k2.Util.classes.ClassUtil;

public class FieldFieldInitialiser<T> implements FieldInitialiser<T> {

	protected FieldFieldInitialiser(Class<T> initialiseClass, String targetField, String sourceField) {
		this.targetField = ClassUtil.getField(initialiseClass, targetField);
		this.sourceField = ClassUtil.getField(initialiseClass, sourceField);
	}
	
	private final Field targetField;
	private final Field sourceField;

	@Override
	public boolean initialise(T target) {
		try {
			if (toBeInitialised(target)) {
				targetField.set(target, sourceField.get(target));
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
			return (targetField.get(target) == null && sourceField.get(target) != null);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new K2DaoError("Unable to access field during initialisatation", e);
		}
	}

}
