package com.k2.core.model;

import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.k2.common.annotation.MetaComponent;
import com.k2.common.annotation.MetaField;
import com.k2.core.model.aModel.AK2Class;
import com.k2.core.types.ClassType;
import com.k2.core.types.ComponentType;

@MetaComponent(id=3)
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="CLASSTYPE", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("CLASS")
public class K2Class extends AK2Class {
	
	public K2Class(Long id, ClassType classType) {
		super(id, ComponentType.CLASS);
		this.classType = classType;
	}

	@MetaField(id=5)
	protected ClassType classType;
	public ClassType getClassType() { return classType; }
	public void setClassType(ClassType classType) { this.classType = classType; }
	
	@MetaField(id=6)
	protected K2Class extendsClass;
	public K2Class getExtendsClass() { return extendsClass; }
	public void setExtendsClass(K2Class extendsClass) { this.extendsClass = extendsClass; }
	
	@MetaField(id=11)
	protected K2Field discriminatorField;
	public K2Field getDiscriminatorField() { return discriminatorField; }
	public void setDiscriminatorField(K2Field discriminatorField) { this.discriminatorField = discriminatorField; }
	
	@MetaField(id=15)
	protected K2TypeValue discriminatorValue;
	public K2TypeValue getDiscriminatorValue() { return discriminatorValue; }
	public void setDiscriminatorValue(K2TypeValue discriminatorValue) { this.discriminatorValue = discriminatorValue; }
	
	@MetaField(id=16)
	protected List<K2Field> fields;
	public List<K2Field> getFields() { return fields; }
	public void setFields(List<K2Field> fields) { this.fields = fields; }
	
	
}
