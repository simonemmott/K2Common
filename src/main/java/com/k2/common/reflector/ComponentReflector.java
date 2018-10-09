package com.k2.common.reflector;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.k2.Util.classes.ClassUtil;
import com.k2.Util.entity.EntityUtil;
import com.k2.common.K2MetaDataError;
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

@Reflector
public class ComponentReflector extends AItemReflector<Class, K2Component> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	public ComponentReflector(K2Reflector reflector) {
		super(Class.class, K2Component.class, reflector);
	}

	@Override
	public K2Component reflect(Class item) {
		logger.trace("Reflecting class {}", item.getName());
		K2Component k2Comp;
		if (item == int.class) 				{ k2Comp = AK2Primitive.INT; 		getReflector().getEntityMap().put(k2Comp); return k2Comp; } 
		else if (item == long.class) 		{ k2Comp = AK2Primitive.LONG; 		getReflector().getEntityMap().put(k2Comp); return k2Comp; }
		else if (item == float.class) 		{ k2Comp = AK2Primitive.FLOAT; 		getReflector().getEntityMap().put(k2Comp); return k2Comp; }
		else if (item == double.class) 		{ k2Comp = AK2Primitive.DOUBLE; 	getReflector().getEntityMap().put(k2Comp); return k2Comp; }
		else if (item == boolean.class) 	{ k2Comp = AK2Primitive.BOOLEAN; 	getReflector().getEntityMap().put(k2Comp); return k2Comp; }
		else if (item == byte.class) 		{ k2Comp = AK2Primitive.BYTE; 		getReflector().getEntityMap().put(k2Comp); return k2Comp; }
		else if (item == short.class) 		{ k2Comp = AK2Primitive.SHORT; 		getReflector().getEntityMap().put(k2Comp); return k2Comp; }
		else if (item == char.class) 		{ k2Comp = AK2Primitive.CHAR; 		getReflector().getEntityMap().put(k2Comp); return k2Comp; }
		else if (item == void.class) 		{ k2Comp = AK2Primitive.VOID; 		getReflector().getEntityMap().put(k2Comp); return k2Comp; }
		else if (item == Integer.class) 	{ k2Comp = AK2Native.INTEGER; 		getReflector().getEntityMap().put(k2Comp); return k2Comp; }
		else if (item == Long.class) 		{ k2Comp = AK2Native.LONG; 			getReflector().getEntityMap().put(k2Comp); return k2Comp; }
		else if (item == Float.class) 		{ k2Comp = AK2Native.FLOAT; 		getReflector().getEntityMap().put(k2Comp); return k2Comp; }
		else if (item == Double.class) 		{ k2Comp = AK2Native.DOUBLE; 		getReflector().getEntityMap().put(k2Comp); return k2Comp; }
		else if (item == Boolean.class) 	{ k2Comp = AK2Native.BOOLEAN; 		getReflector().getEntityMap().put(k2Comp); return k2Comp; }
		else if (item == Byte.class) 		{ k2Comp = AK2Native.BYTE; 			getReflector().getEntityMap().put(k2Comp); return k2Comp; }
		else if (item == Short.class) 		{ k2Comp = AK2Native.SHORT; 		getReflector().getEntityMap().put(k2Comp); return k2Comp; }
		else if (item == Character.class)	{ k2Comp = AK2Native.CHARACTER; 	getReflector().getEntityMap().put(k2Comp); return k2Comp; }
		else if (item == String.class) 		{ k2Comp = AK2Native.STRING; 		getReflector().getEntityMap().put(k2Comp); return k2Comp; }
		else if (item == Date.class) 		{ k2Comp = AK2Native.DATE; 			getReflector().getEntityMap().put(k2Comp); return k2Comp; }
		else if (item.isAnnotationPresent(MetaComponent.class)){
			
			MetaComponent mComp = (MetaComponent) item.getAnnotation(MetaComponent.class);
			
			Long id = getReflector().idOrSequence(K2Component.class, mComp.id());
			k2Comp = getReflector().getEntityMap().get(K2Component.class, id);
			if (k2Comp != null) {
				if ( ! k2Comp.getName().equals(item.getName()))
					throw new K2MetaDataError("Duplicate component id {} detected on {} and {}", id, item.getName(), k2Comp.getName());
				logger.trace("Found reflection prior reflection of class {}", item.getName());
				return k2Comp;
			}
			for (K2Component c : getReflector().getEntityMap().list(K2Component.class)) {
				if (c.getName().equals(item.getName())) {
					logger.trace("Found reflection prior reflection of class {}", item.getName());
					return c;
				}
			}
			
			k2Comp = newComponentInstance(item, id);

			getReflector().getEntityMap().put(k2Comp);
			
			populate(item, k2Comp);
			
			logger.trace("Reflected {}", item.getName());
			
			return k2Comp;
			
		} else {
			throw new K2ReflectorError("Unable to reflect the class {}. It is not annotated with @MetaComponent or @MetaDomain", item.getName());
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
//			values.add(getReflector().reflectTypeValue(f));
			values.add(getReflector().reflect(f, K2TypeValue.class));
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
			fields.add(getReflector().reflect(field, K2Field.class));	
		
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
			instance.setDiscriminatorField(getReflector().reflect(discriminatorField, K2Field.class));
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



}
