package com.k2.common.dao;

import com.k2.common.sequence.SequenceFieldInitialiser;

public interface FieldInitialiser<T> {

	public static <T> FieldInitialiser<T> fromField(Class<T> initialiseClass, String targetField, String sourceField) {
		return new FieldFieldInitialiser<T>(initialiseClass, targetField, sourceField);
	}

	public static <T> FieldInitialiser<T> fromLiteral(Class<T> initialiseClass, String targetField, Object literal) {
		return new LiteralFieldInitialiser<T>(initialiseClass, targetField, literal);
	}
	
	public static <T> FieldInitialiser<T> fromSequence(Class<T> initialiseClass, String targetField) {
		return new SequenceFieldInitialiser<T>(initialiseClass, targetField);
	}
	
	public static <T> FieldInitialiser<T> fromParameter(Class<T> initialiseClass, String targetField, String parameterName) {
		return new ParameterFieldInitialiser<T>(initialiseClass, targetField, parameterName);
	}
	
	public boolean toBeInitialised(T target);
	public boolean initialise(T target);

}
