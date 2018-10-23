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
public class FieldReflector extends AItemReflector<Field, K2Field> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	public FieldReflector(K2Reflector reflector) {
		super(Field.class, K2Field.class, reflector);
	}

	@Override
	public K2Field reflect(Field item) {
		logger.trace("Reflecting field {}.{}", item.getDeclaringClass().getName(), item.getName());
		if (!item.isAnnotationPresent(MetaField.class))
			throw new K2ReflectorError("Unable to reflect the field {}.{}. It is not annotated with @MetaField", item.getDeclaringClass().getName(), item.getName());
		MetaField mField = item.getAnnotation(MetaField.class);
		
		Long id = getReflector().idOrSequence(K2Field.class, mField.id());
		K2Field k2Field = getReflector().getEntityMap().get(K2Field.class, mField.id());
		if (k2Field != null) {
			if ( ! (k2Field.getAlias().equals(item.getName()) && (k2Field.getDeclaringClass().getName().equals(item.getDeclaringClass().getName()))))
				throw new K2MetaDataError("Duplicate field id {} detected on {}.{} and {}.{}"
						, id, 
						item.getDeclaringClass().getName(),
						item.getName(), 
						k2Field.getDeclaringClass().getName(),
						k2Field.getAlias());
			return k2Field;
		}
		
		k2Field = newFieldInstance(item, id);
		getReflector().getEntityMap().put(k2Field);
		
		populate(item, k2Field);
		
		return k2Field;
	}

	private K2Field newFieldInstance(Field field, Long id) {
		return new K2Field(id);
	}

	private void populate(Field field, K2Field instance) {
		
		MetaField metaField = field.getAnnotation(MetaField.class);
		
		instance.setAlias(field.getName());
		if (Collection.class.isAssignableFrom(field.getType())) {
			instance.setDataType(getReflector().reflect(ClassUtil.getFieldGenericTypeClass(field, 0), K2Component.class));
		} else {
			instance.setDataType(getReflector().reflect(field.getType(), K2Component.class));
		}
		instance.setDeclaringClass((K2Class) getReflector().reflect(field.getDeclaringClass(), K2Component.class));
		
		if (field.isAnnotationPresent(Column.class)) {
			Column  column = field.getAnnotation(Column.class);
			if (column.name() != "") 
				instance.setColumnName(column.name());
			else
				instance.setColumnName(StringUtil.staticCase(field.getName()));
		}
		
		if (field.isAnnotationPresent(Id.class)) 
			instance.setIsPrimaryKey(true);
		else
			instance.setIsPrimaryKey(false);
		
		instance.setSortOrder(metaField.sortOrder());
		
		Class<?> fieldType = field.getType();
		if (fieldType.isPrimitive())
			instance.setFieldType(FieldType.PRIMITIVE);
		else if (fieldType.isEnum())
			instance.setFieldType(FieldType.TYPE);
		else if (Collection.class.isAssignableFrom(fieldType))
			instance.setFieldType(FieldType.COLLECTION);
		else if (
				fieldType.equals(Integer.class) ||
				fieldType.equals(Long.class) ||
				fieldType.equals(Double.class) ||
				fieldType.equals(Float.class) ||
				fieldType.equals(Boolean.class) ||
				fieldType.equals(Date.class) ||
				fieldType.equals(Character.class) ||
				fieldType.equals(String.class)
				)
			instance.setFieldType(FieldType.NATIVE);
		else 
			instance.setFieldType(FieldType.LINKED);
			
		
	}
	


}
