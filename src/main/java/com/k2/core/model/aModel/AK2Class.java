package com.k2.core.model.aModel;

import java.lang.reflect.Field;

import com.k2.Util.classes.ClassUtil;
import com.k2.common.K2MetaDataError;
import com.k2.common.annotation.MetaComponent;
import com.k2.core.model.K2Class;
import com.k2.core.model.K2Component;
import com.k2.core.model.K2Field;
import com.k2.core.model.K2Type;
import com.k2.core.model.K2TypeValue;
import com.k2.core.types.ComponentType;

@MetaComponent(id=34)
public abstract class AK2Class extends K2Component {

	public AK2Class(Long id, ComponentType type) {
		super(id, type);
	}
	
	public abstract K2Class getExtendsClass();
	public abstract K2Field getDiscriminatorField();
	
	private Class<?> javaType;
	public Class<?> javaType() {
		if (javaType != null)
			return javaType;
		try {
			javaType = Class.forName(getName());
		} catch (ClassNotFoundException e) {
			throw new K2MetaDataError("The given name {} does not identify a loadable class", e, getName());
		}
		return javaType;
	}

	public K2Field getSuperDiscriminatorField() {
		if (getExtendsClass() == null) 
			return null;
		
		K2Class superClass = getExtendsClass();
		if (superClass.getDiscriminatorField() != null)
			return superClass.getDiscriminatorField();
			
		return superClass.getSuperDiscriminatorField();

	}
	
}
