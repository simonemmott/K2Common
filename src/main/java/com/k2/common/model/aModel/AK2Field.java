package com.k2.common.model.aModel;

import java.lang.reflect.Field;

import com.k2.Util.StringUtil;
import com.k2.Util.classes.ClassUtil;
import com.k2.common.annotation.MetaComponent;
import com.k2.common.model.K2Class;

@MetaComponent(id=7)
public abstract class AK2Field {
	
	public abstract String getAlias();
	public abstract K2Class getDeclaringClass();
	
	public String getGetterName() {
		return "get"+StringUtil.initialUpperCase(getAlias());
	}

	public String getSetterName() {
		return "set"+StringUtil.initialUpperCase(getAlias());
	}

	private Field field;
	public Field field() {
		if (field != null)
			return field;
		field = ClassUtil.getField(getDeclaringClass().javaType(), getAlias());
		return field;
	}
}
