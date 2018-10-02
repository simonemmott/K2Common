package com.k2.common.reflector;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.k2.EntityMap.EntitiesMap;
import com.k2.Util.StringUtil;
import com.k2.Util.classes.ClassUtil;
import com.k2.Util.entity.EntityUtil;
import com.k2.common.K2MetaDataError;
import com.k2.common.annotation.MetaComponent;
import com.k2.common.annotation.MetaField;
import com.k2.common.annotation.MetaTypeValue;
import com.k2.common.dao.memoryDao.MemoryK2Sequence;
import com.k2.common.sequence.K2Sequence;
import com.k2.common.sequence.K2SequenceFactory;
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

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
//	private Map<Class<?>,K2Sequence<?>> sequences = new HashMap<Class<?>, K2Sequence<?>>();
	private K2SequenceFactory sequences;
	private Long idOrSequence(Class<?> cls, Long id) {
		if (id == null || id.longValue() == 0)
			return sequences.getSequence(cls).nextValue();
		return id;
	}

	public static K2Reflector create(EntitiesMap entityMap) {
		return new K2Reflector(entityMap);
		
	}
	
	public static K2Reflector create(Class<?> sequenceFactoryClass, EntitiesMap entityMap) {
		return new K2Reflector(sequenceFactoryClass, entityMap);
		
	}
	
	public <T> K2Sequence<T> getSequence(Class<T> sequenceForClass) {
		return sequences.getSequence(sequenceForClass);
	}
	
	public K2Reflector(EntitiesMap entityMap) {
		this.entityMap = entityMap;
		sequences = K2SequenceFactory.create();
		reflectPrimitives();
		reflectNatives();
	}

	public K2Reflector(K2SequenceFactory sequences, EntitiesMap entityMap) {
		this.entityMap = entityMap;
		this.sequences = sequences;
		reflectPrimitives();
		reflectNatives();
	}

	public K2Reflector(Class<?> sequenceFactoryClass, EntitiesMap entityMap) {
		this.entityMap = entityMap;
		reflectPrimitives();
		reflectNatives();
		
		try {
			this.sequences = (K2SequenceFactory) sequenceFactoryClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new K2ReflectorError("Unable to create a new instance of sequence factory from class {}. No zero arg constructor?", e, sequenceFactoryClass.getName());
		}
	}

	private void reflectPrimitives() {
		reflect(int.class);
		reflect(long.class);
		reflect(float.class);
		reflect(double.class);
		reflect(boolean.class);
		reflect(byte.class);
		reflect(short.class);
		reflect(char.class);
		reflect(void.class);
	}
	private void reflectNatives() {
		reflect(Integer.class);
		reflect(Long.class);
		reflect(Float.class);
		reflect(Double.class);
		reflect(Boolean.class);
		reflect(Byte.class);
		reflect(Short.class);
		reflect(Character.class);
		reflect(String.class);
		reflect(Date.class);
	}
	private final EntitiesMap entityMap;
	public EntitiesMap getEntityMap() {
		return entityMap;
	}
	

	// Component reflection -----------------------------------------------------
	public K2Component reflect(Class<?> cls) {
		logger.trace("Reflecting class {}", cls.getName());
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
			
			Long id = idOrSequence(K2Component.class, mComp.id());
			k2Comp = entityMap.get(K2Component.class, id);
			if (k2Comp != null) {
				if ( ! k2Comp.getName().equals(cls.getName()))
					throw new K2MetaDataError("Duplicate component id {} detected on {} and {}", id, cls.getName(), k2Comp.getName());
				logger.trace("Found reflection prior reflection of class {}", cls.getName());
				return k2Comp;
			}
			for (K2Component c : entityMap.list(K2Component.class)) {
				if (c.getName().equals(cls.getName())) {
					logger.trace("Found reflection prior reflection of class {}", cls.getName());
					return c;
				}
			}
			
			k2Comp = newComponentInstance(cls, id);

			entityMap.put(k2Comp);
			
			populate(cls, k2Comp);
			
			logger.trace("Reflected {}", cls.getName());
			
			return k2Comp;
			
		} else {
			throw new K2ReflectorError("Unable to reflect the class {}. It is not annotated with @MetaComponent", cls.getName());
		}
	}
	
	private K2Component newComponentInstance(Class<?> cls, Long id) {
		
		if (cls.isAnnotationPresent(Entity.class))
			return new K2Entity(id);
		if (cls.isEnum())
			return new K2Type(id);
		
		return new K2Transient(id);
	}

	private void populate(Class<?> cls, K2Component instance) {
		if (instance instanceof K2Entity)
			populateEntity(cls, (K2Entity)instance);
		else if (instance instanceof K2Type)
			populateType(cls, (K2Type)instance);
		else if (instance instanceof K2Transient)
			populateTransient(cls, (K2Transient)instance);
		else
			throw new K2ReflectorError("Unable to polulate the relfection of the class {}. Unecpected component type {}", cls.getName(), instance.getClass().getName());
	}
	
	private void populateEntity(Class<?> cls, K2Entity instance) {
		populateClass(cls, instance);
		// TODO reflect entity classes
	}

	private void populateType(Class<?> cls, K2Type instance) {
		logger.trace("Populating K2Type from enumeration {}", cls.getName());
		populateComponent(cls, instance);
		List<K2TypeValue> values = new ArrayList<K2TypeValue>();
		
		for (Field f : ClassUtil.getDeclaredFields(cls)) {
			values.add(this.reflectTypeValue(f));
		}
		
		instance.setValues(values);
	}

	private void populateTransient(Class<?> cls, K2Transient instance) {
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
		
		Comparator<K2Field> fieldComparator = new Comparator<K2Field>() {
			@Override
			public int compare(K2Field f1, K2Field f2) {
				return (f1.getSortOrder() - f2.getSortOrder())*1000000+f1.getId().intValue()-f2.getId().intValue();
			}
		};
		Collections.sort(fields, fieldComparator);
		instance.setFields(fields);
		
		if (cls.isAnnotationPresent(DiscriminatorColumn.class)) {
			DiscriminatorColumn dc = cls.getAnnotation(DiscriminatorColumn.class);
			Field discriminatorField = EntityUtil.getColumnByName(cls, dc.name());
			instance.setDiscriminatorField(reflect(discriminatorField));
		}
		
		if (cls.isAnnotationPresent(DiscriminatorValue.class)) {
			DiscriminatorValue dv = cls.getAnnotation(DiscriminatorValue.class);
			logger.trace("Reflecting DiscriminatorValue for class {} and value '{}'", cls.getName(), dv.value());
			
			K2Field discriminatorField = instance.getSuperDiscriminatorField();
			
			logger.trace("Identified discriminator field {}.{} as discrtiminator for class {}", discriminatorField.getDeclaringClass().getName(), discriminatorField.getAlias(), cls.getName());
			
			K2Type k2Type = (K2Type) reflect(discriminatorField.field().getType());
			
			logger.trace("Discriminatoing type for class {} is {}", cls.getName(), k2Type.getName());
			
			boolean found = false;
			for (K2TypeValue value : k2Type.getValues()) {
				if (value.getAlias().equals(dv.value())) {
					found = true;
					logger.trace("Using discriminator value '{}' for class {}", value.getAlias(), cls.getName());
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
		
		Long id = idOrSequence(K2Component.class, mField.id());
		K2Field k2Field = entityMap.get(K2Field.class, mField.id());
		if (k2Field != null) {
			if ( ! (k2Field.getAlias().equals(field.getName()) && (k2Field.getDeclaringClass().getName().equals(field.getDeclaringClass().getName()))))
				throw new K2MetaDataError("Duplicate field id {} detected on {}.{} and {}.{}"
						, id, 
						field.getDeclaringClass().getName(),
						field.getName(), 
						k2Field.getDeclaringClass().getName(),
						k2Field.getAlias());
			return k2Field;
		}
		
		k2Field = newFieldInstance(field, id);
		entityMap.put(k2Field);
		
		populate(field, k2Field);
		
		return k2Field;
	}
	
	private K2Field newFieldInstance(Field field, Long id) {
		return new K2Field(id);
	}

	private void populate(Field field, K2Field instance) {
		
		MetaField metaField = field.getAnnotation(MetaField.class);
		
		instance.setAlias(field.getName());
		if (Collection.class.isAssignableFrom(field.getType())) {
			instance.setDataType(reflect(ClassUtil.getFieldGenericTypeClass(field, 0)));
		} else {
			instance.setDataType(reflect(field.getType()));
		}
		instance.setDeclaringClass((K2Class) reflect(field.getDeclaringClass()));
		
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
		
	}
	
	// TypeValue reflection ------------------------------------------------------------
	private K2TypeValue reflectTypeValue(Field field) {
		Long id = null;
		if (field.isAnnotationPresent(MetaTypeValue.class)) {
			MetaTypeValue mtv = field.getAnnotation(MetaTypeValue.class);
			id = mtv.id();
		}
		
		if (id == null || id == 0)
			id = getSequence(K2TypeValue.class).nextValue();
		
		K2TypeValue typeValue = entityMap.get(K2TypeValue.class, id);
		if (typeValue != null)
			return typeValue;
		
		typeValue = new K2TypeValue(id);
		entityMap.put(typeValue);
		
		populate(field, typeValue);
		
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
		instance.setDefiningType((K2Type) reflect(field.getDeclaringClass()));
		

	}
	



}
