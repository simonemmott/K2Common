package com.k2.common.reflector;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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
import com.k2.EntityMap.EntityMap;
import com.k2.Util.StringUtil;
import com.k2.Util.classes.ClassUtil;
import com.k2.Util.entity.EntityUtil;
import com.k2.common.K2MetaDataError;
import com.k2.common.annotation.MetaComponent;
import com.k2.common.annotation.MetaDomain;
import com.k2.common.annotation.MetaField;
import com.k2.common.annotation.MetaTypeValue;
import com.k2.common.dao.memoryDao.MemoryK2Sequence;
import com.k2.common.sequence.K2Sequence;
import com.k2.common.sequence.K2SequenceFactory;
import com.k2.core.model.K2Class;
import com.k2.core.model.K2Component;
import com.k2.core.model.K2Domain;
import com.k2.core.model.K2Entity;
import com.k2.core.model.K2Field;
import com.k2.core.model.K2Transient;
import com.k2.core.model.K2Type;
import com.k2.core.model.K2TypeValue;
import com.k2.core.model.aModel.AK2Native;
import com.k2.core.model.aModel.AK2Primitive;
import com.k2.core.types.FieldType;

public class K2Reflector {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	private K2SequenceFactory sequences;
	public Long idOrSequence(Class<?> cls, Long id) {
		if (id == null || id.longValue() == 0) {
			return sequences.getSequence(cls).nextValue();
		}
		return id;
	}
	
	public static K2Reflector create() {
		return create(EntitiesMap.create(), "com.k2.common.reflector");
	}

	public static K2Reflector create(EntitiesMap entityMap) {
		return create(entityMap, "com.k2.common.reflector");
		
	}
	
	public static K2Reflector create(EntitiesMap entityMap, String ... scanPackages) {
		return new K2Reflector(entityMap).scan(scanPackages);
		
	}
	
	public static K2Reflector create(Class<?> sequenceFactoryClass) {
		return create(sequenceFactoryClass, EntitiesMap.create(), "com.k2.common.reflector");
		
	}
	
	public static K2Reflector create(Class<?> sequenceFactoryClass, EntitiesMap entityMap) {
		return create(sequenceFactoryClass, entityMap, "com.k2.common.reflector");
		
	}
	
	public static K2Reflector create(Class<?> sequenceFactoryClass, EntitiesMap entityMap, String ... scanPackages) {
		return new K2Reflector(sequenceFactoryClass, entityMap).scan(scanPackages);
		
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
		entityMap.put(AK2Primitive.INT);
		entityMap.put(AK2Primitive.LONG);
		entityMap.put(AK2Primitive.FLOAT);
		entityMap.put(AK2Primitive.DOUBLE);
		entityMap.put(AK2Primitive.BOOLEAN);
		entityMap.put(AK2Primitive.BYTE);
		entityMap.put(AK2Primitive.SHORT);
		entityMap.put(AK2Primitive.CHAR);
		entityMap.put(AK2Primitive.VOID);
	}
	private void reflectNatives() {
		entityMap.put(AK2Native.INTEGER);
		entityMap.put(AK2Native.LONG);
		entityMap.put(AK2Native.FLOAT);
		entityMap.put(AK2Native.DOUBLE);
		entityMap.put(AK2Native.BOOLEAN);
		entityMap.put(AK2Native.BYTE);
		entityMap.put(AK2Native.SHORT);
		entityMap.put(AK2Native.CHARACTER);
		entityMap.put(AK2Native.STRING);
		entityMap.put(AK2Native.DATE);
	}
	private final EntitiesMap entityMap;
	public EntitiesMap getEntityMap() {
		return entityMap;
	}

	
	
	
	private Map<Class<?>, Map<Class<?>, Map<ReflectionScope, ItemReflector<?,?>>>> reflectors = new HashMap<Class<?>, Map<Class<?>, Map<ReflectionScope, ItemReflector<?,?>>>>();
	
	public <I,R> ItemReflector<I,R> getItemReflector(Class<I> itemType, Class<R> reflectionType) {
		return getItemReflector(itemType, reflectionType, GeneralScopes.GENERAL);
	}
	public <I,R> ItemReflector<I,R> getItemReflector(Class<I> itemType, Class<R> reflectionType, ReflectionScope scope) {
		Map focus = reflectors.get(itemType);
		if (focus == null)
			throw new K2ReflectorError("No reflectors defined for items of type {}", itemType.getName());
		focus = (Map) focus.get(reflectionType);
		if (focus == null)
			throw new K2ReflectorError("No reflectors defined for items of type {} which produde a reflection of type {}", itemType.getName(), reflectionType.getName());
		ItemReflector<I,R> reflector = (ItemReflector<I, R>) focus.get(scope);
		if (reflector == null) 
			throw new K2ReflectorError("No reflectors defined for items of type {} which produde a reflection of type {} with the scope '{}'", 
					itemType.getName(), 
					reflectionType.getName(),
					scope.getScope());
		return reflector;
	}
	
	private void registerReflector(ItemReflector reflector) {
		ReflectionScope scope = reflector.getReflectionScope();
		
		Map<Class<?>, Map<ReflectionScope, ItemReflector<?,?>>> reflectionTypeMap = reflectors.get(reflector.getItemType());
		if (reflectionTypeMap == null) {
			reflectionTypeMap = new HashMap<Class<?>, Map<ReflectionScope, ItemReflector<?,?>>>();
			reflectors.put(reflector.getItemType(), reflectionTypeMap);
		}
		
		Map<ReflectionScope, ItemReflector<?,?>> scopeMap = reflectionTypeMap.get(reflector.getReflectionType());
		if (scopeMap == null) {
			scopeMap = new HashMap<ReflectionScope, ItemReflector<?,?>>();
			reflectionTypeMap.put(reflector.getReflectionType(), scopeMap);
		}
		
		scopeMap.put(reflector.getReflectionScope(), reflector);
	}
	
	private ItemReflector createReflector(Class<ItemReflector> reflectorClass) {
		Constructor<ItemReflector> constructor;
		try {
			constructor = reflectorClass.getConstructor(K2Reflector.class);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new K2ReflectorError("Unable to create a new instance the reflector class {}. Missing K2Reflector construcgtor", e, reflectorClass.getName());
		}
		
		ItemReflector reflector;
		try {
			reflector = constructor.newInstance(this);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new K2ReflectorError("Unable to create a new instance the reflector class {}. Missing K2Reflector construcgtor", e, reflectorClass.getName());
		}
		
		return reflector;
	}
	
	public K2Reflector scan(String ... packageNames) {
		for (String packageName : packageNames) {
			for (Class<?> cls : ClassUtil.getClasses(packageName, Reflector.class)) {
				if (! cls.isInterface() && ! cls.isEnum() && ! cls.isPrimitive()) {
					if (ItemReflector.class.isAssignableFrom(cls)) {
						registerReflector(createReflector((Class<ItemReflector>) cls));
					}
				}
			}
		}
		return this;
	}
	
	public <I,R> R reflect(Object item, Class<R> reflectionType) {
		
		return reflect(item, reflectionType, GeneralScopes.GENERAL);
	}

	public <I,R> R reflect(Object item, Class<R> reflectionType, ReflectionScope scope) {
		
		ItemReflector<I,R> reflector = (ItemReflector<I, R>) this.getItemReflector(item.getClass(), reflectionType);
		
		return reflector.reflect(reflector.getItemType().cast(item));
	}



}
