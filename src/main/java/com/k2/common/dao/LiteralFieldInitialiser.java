package com.k2.common.dao;

import java.lang.reflect.Field;

import com.k2.Util.classes.ClassUtil;

public class LiteralFieldInitialiser<T> implements FieldInitialiser<T> {

	public LiteralFieldInitialiser(Class<T> initialiseClass, String targetField, Object literal) {
		this.targetField = ClassUtil.getField(initialiseClass, targetField);
		this.literal = literal;
	}
	
	private final Field targetField;
	private final Object literal;

	@Override
	public boolean initialise(T target) {
		try {
			if (toBeInitialised(target)) {
				targetField.set(target, literal);
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
			return (targetField.get(target) == null && literal != null);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new K2DaoError("Unable to access field during initialisatation", e);
		}
	}


}
