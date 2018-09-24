package com.k2.common.dao;

import java.lang.reflect.Field;

import com.k2.Util.classes.ClassUtil;
import com.k2.common.sequence.K2Sequence;

public class ParameterFieldInitialiser<T> implements FieldInitialiser<T> {

	public ParameterFieldInitialiser(Class<T> initialiseClass, String targetField, String parameterName) {
		this.targetField = ClassUtil.getField(initialiseClass, targetField);
		this.parameterName = parameterName;
	}
	
	private final Field targetField;
	private final String parameterName;
	
	private ThreadLocal<ParameterMap> parameters = new ThreadLocal<ParameterMap>();
	public void setCurrentParameters(ParameterMap parameterMap) {
		this.parameters.set(parameterMap);
	}

	@Override
	public boolean initialise(T target) {
		try {
			if (toBeInitialised(target)) {
				targetField.set(target, parameters.get().getParameterValue(parameterName));
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
			return (targetField.get(target) == null && parameters.get() != null && parameters.get().getParameterValue(parameterName) != null);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new K2DaoError("Unable to access field during initialisatation", e);
		}
	}

}
