package com.k2.common.reflector;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.k2.EntityMap.EntitiesMap;
import com.k2.Util.classes.ClassUtil;
import com.k2.Util.entity.EntityUtil;
import com.k2.common.annotation.MetaComponent;
import com.k2.common.annotation.MetaField;
import com.k2.core.model.K2Class;
import com.k2.core.model.K2Component;
import com.k2.core.model.K2Entity;
import com.k2.core.model.K2Field;
import com.k2.core.model.K2Transient;
import com.k2.core.model.K2Type;
import com.k2.core.model.K2TypeValue;
import com.k2.core.model.aModel.AK2Native;
import com.k2.core.model.aModel.AK2Primitive;

public class K2Reflector {

	public static K2Reflector create(EntitiesMap entityMap) {
		return new K2Reflector(entityMap);
		
	}
	
	public K2Reflector(EntitiesMap entityMap) {
		this.entityMap = entityMap;
	}

	private final EntitiesMap entityMap;
	public EntitiesMap getEntityMap() {
		return entityMap;
	}

	// Component reflection -----------------------------------------------------
	public K2Component reflect(Class<?> cls) {
		K2Component k2Comp;
		if (cls == int.class) 				{ k2Comp = AK2Primitive.INT; 		entityMap.put(k2Comp); return k2Comp; } 
		else if (cls == long.class) 		{ k2Comp = AK2Primitive.LONG; 		entityMap.put(k2Comp); return k2Comp; }
		else if (cls == float.class) 		{ k2Comp = AK2Primitive.FLOAT; 		entityMap.put(k2Comp); return k2Comp; }
		else if (cls == double.class) 		{ k2Comp = AK2Primitive.DOUBLE; 	entityMap.put(k2Comp); return k2Comp; }
		else if (cls == boolean.class) 		{ k2Comp = AK2Primitive.BOOLEAN; 	entityMap.put(k2Comp); return k2Comp; }
		else if (cls == byte.class) 		{ k2Comp = AK2Primitive.BYTE; 		entityMap.put(k2Comp); return k2Comp; }
		else if (cls == short.class) 		{ k2Comp = AK2Primitive.SHORT; 		entityMap.put(k2Comp); return k2Comp; }
		else if (cls == char.class) 		{ k2Comp = AK2Primitive.CHAR; 		entityMap.put(k2Comp); return k2Comp; }
		else if (cls == void.class) 		{ k2Comp = AK2Primitive.VOID; 		entityMap.put(k2Comp); return k2Comp; }
		else if (cls == Integer.class) 		{ k2Comp = AK2Native.INTEGER; 		entityMap.put(k2Comp); return k2Comp; }
		else if (cls == Long.class) 		{ k2Comp = AK2Native.LONG; 			entityMap.put(k2Comp); return k2Comp; }
		else if (cls == Float.class) 		{ k2Comp = AK2Native.FLOAT; 		entityMap.put(k2Comp); return k2Comp; }
		else if (cls == Double.class) 		{ k2Comp = AK2Native.DOUBLE; 		entityMap.put(k2Comp); return k2Comp; }
		else if (cls == Boolean.class) 		{ k2Comp = AK2Native.BOOLEAN; 		entityMap.put(k2Comp); return k2Comp; }
		else if (cls == Byte.class) 		{ k2Comp = AK2Native.BYTE; 			entityMap.put(k2Comp); return k2Comp; }
		else if (cls == Short.class) 		{ k2Comp = AK2Native.SHORT; 		entityMap.put(k2Comp); return k2Comp; }
		else if (cls == Character.class)	{ k2Comp = AK2Native.CHARACTER; 	entityMap.put(k2Comp); return k2Comp; }
		else if (cls == String.class) 		{ k2Comp = AK2Native.STRING; 		entityMap.put(k2Comp); return k2Comp; }
		else if (cls == Date.class) 		{ k2Comp = AK2Native.DATE; 			entityMap.put(k2Comp); return k2Comp; }
		else if (cls.isAnnotationPresent(MetaComponent.class)){
			
			MetaComponent mComp = cls.getAnnotation(MetaComponent.class);
			
			k2Comp = entityMap.get(K2Component.class, mComp.id());
			if (k2Comp != null)
				return k2Comp;
			
			k2Comp = newInstance(cls, mComp);
			if (k2Comp instanceof K2Entity)
				entityMap.put(k2Comp);
			
			populate(cls, k2Comp);
			
			return k2Comp;
			
		} else {
			throw new K2ReflectorError("Unable to reflect the class {}. It is not annotated with @MetaComponent", cls.getName());
		}
	}
	
	private K2Component newInstance(Class<?> cls, MetaComponent mComp) {
		
		K2Component k2Comp;
		if (cls.isAnnotationPresent(Entity.class))
			return new K2Entity(mComp.id());
		if (cls.isEnum())
			return new K2Type(mComp.id());
		
		return new K2Transient(mComp.id());
	}

	private void populate(Class<?> cls, K2Component instance) {
		throw new K2ReflectorError("Unable to polulate the relfection of the class {}. Unecpected component type {}", cls.getName(), instance.getClass().getName());
	}
	
	private void populate(Class<?> cls, K2Entity instance) {
		populateClass(cls, instance);
		// TODO reflect entity classes
	}

	private void populate(Class<?> cls, K2Type instance) {
		populateComponent(cls, instance);
		// TODO reflect entity classes
	}

	private void populate(Class<?> cls, K2Transient instance) {
		populateClass(cls, instance);
		// TODO reflect entity classes
	}
	
	private void populateClass(Class<?> cls, K2Class instance) {
		populateComponent(cls, instance);
		
		if (!cls.getSuperclass().equals(Object.class))
			instance.setExtendsClass((K2Class) reflect(cls.getSuperclass()));
		
		List<K2Field> fields = new ArrayList<K2Field>();
		for (Field field : ClassUtil.getAnnotatedFields(cls, MetaField.class))
			fields.add(reflect(field));		
		instance.setFields(fields);
		
		if (cls.isAnnotationPresent(DiscriminatorColumn.class)) {
			DiscriminatorColumn dc = cls.getAnnotation(DiscriminatorColumn.class);
			Field discriminatorField = EntityUtil.getColumnByName(cls, dc.name());
			instance.setDiscriminatorField(reflect(discriminatorField));
		}
		
		if (cls.isAnnotationPresent(DiscriminatorValue.class)) {
			DiscriminatorValue dv = cls.getAnnotation(DiscriminatorValue.class);
			
			K2Field discriminatorField = instance.getSuperDiscriminatorField();
			
			
			K2Type k2Type = (K2Type) reflect(discriminatorField.field().getType());
			
			boolean found = false;
			for (K2TypeValue value : k2Type.getValues()) {
				if (value.getAlias().equals(dv.value())) {
					found = true;
					instance.setDiscriminatorValue(value);
					break;
				}
			}
			if (! found)
				throw new K2ReflectorError("The class {} defines a descriminator value {} for which an instance of K2TypeValue cannot be identified", cls.getName(), dv.value());
			
		}
		
	}
	
	private void populateComponent(Class<?> cls, K2Component instance) {
		instance.setName(cls.getName());
		instance.setDescription(cls.getAnnotation(MetaComponent.class).description());
	}

	// Field reflection ----------------------------------------------------------------
	private K2Field reflect(Field field) {
		if (!field.isAnnotationPresent(MetaField.class))
			throw new K2ReflectorError("Unable to reflect the field {}.{}. It is not annotated with @MetaField", field.getDeclaringClass().getName(), field.getName());
		MetaField mField = field.getAnnotation(MetaField.class);
		
		K2Field k2Field = entityMap.get(K2Field.class, mField.id());
		if (k2Field != null)
			return k2Field;
		
		k2Field = newInstance(field, mField);
		entityMap.put(k2Field);
		
		populate(field, k2Field);
		
		return k2Field;
	}
	
	private K2Field newInstance(Field field, MetaField mField) {
		return new K2Field(mField.id());
	}

	private void populate(Field field, K2Field instance) {
		
		instance.setAlias(field.getName());
		instance.setDataType(reflect(field.getType()));
		instance.setDeclaringClass((K2Class) reflect(field.getDeclaringClass()));
	}

}
