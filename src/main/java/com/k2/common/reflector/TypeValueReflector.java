package com.k2.common.reflector;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.k2.Util.StringUtil;
import com.k2.Util.classes.ClassUtil;
import com.k2.Util.entity.EntityUtil;
import com.k2.common.K2MetaDataError;
import com.k2.common.annotation.MetaComponent;
import com.k2.common.annotation.MetaField;
import com.k2.common.annotation.MetaTypeValue;
import com.k2.common.model.K2Class;
import com.k2.common.model.K2Component;
import com.k2.common.model.K2Entity;
import com.k2.common.model.K2Field;
import com.k2.common.model.K2Transient;
import com.k2.common.model.K2Type;
import com.k2.common.model.K2TypeValue;
import com.k2.common.model.aModel.AK2Native;
import com.k2.common.model.aModel.AK2Primitive;
import com.k2.common.types.FieldType;

@Reflector
public class TypeValueReflector extends AItemReflector<Field, K2TypeValue> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	public TypeValueReflector(K2Reflector reflector) {
		super(Field.class, K2TypeValue.class, reflector);
	}

	@Override
	public K2TypeValue reflect(Field item) {
		logger.trace("Reflecting type value {}.{}", item.getDeclaringClass().getName(), item.getName());
		Long id = null;
		if (item.isAnnotationPresent(MetaTypeValue.class)) {
			MetaTypeValue mtv = item.getAnnotation(MetaTypeValue.class);
			id = mtv.id();
		}
		
		if (id == null || id == 0) {
			id = getReflector().getSequence(K2TypeValue.class).nextValue();
		}
		
		K2TypeValue typeValue = getReflector().getEntityMap().get(K2TypeValue.class, id);
		if (typeValue != null)
			return typeValue;
		
		typeValue = new K2TypeValue(id);
		getReflector().getEntityMap().put(typeValue);
		
		populate(item, typeValue);
		
		return typeValue;
		
	}

	private void populate(Field field, K2TypeValue instance) {
		String name = null;
		String description = null;
		
		instance.setAlias(field.getName());
		if (field.isAnnotationPresent(MetaTypeValue.class)) {
			MetaTypeValue mtv = field.getAnnotation(MetaTypeValue.class);
			name = mtv.name();
			description = mtv.description();
		}
		
		if ( ! StringUtil.isSet(name))
			name = StringUtil.initialUpperCase(field.getName().toLowerCase());
		
		instance.setName(name);
		instance.setDescription(description);
		instance.setDefiningType((K2Type) getReflector().reflect(field.getDeclaringClass(), K2Component.class));
		

	}
	


}
