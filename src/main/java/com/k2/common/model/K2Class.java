package com.k2.common.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.k2.common.annotation.MetaComponent;
import com.k2.common.annotation.MetaField;
import com.k2.common.model.aModel.AK2Class;
import com.k2.common.types.ClassType;
import com.k2.common.types.ComponentType;
import com.k2.common.model.K2Class;
import com.k2.common.model.K2Field;
import com.k2.common.model.K2TypeValue;

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
	@Column(name="CLASSTYPE")
	protected ClassType classType;
	public ClassType getClassType() { return classType; }
	public void setClassType(ClassType classType) { this.classType = classType; }
	
	@MetaField(id=6)
	@Column(name="EXTENDS_CLASS")
	protected K2Class extendsClass;
	public K2Class getExtendsClass() { return extendsClass; }
	public void setExtendsClass(K2Class extendsClass) { this.extendsClass = extendsClass; }
	
	@MetaField(id=11)
	@Column(name="DISCRIMINATOR_FIELD")
	protected K2Field discriminatorField;
	public K2Field getDiscriminatorField() { return discriminatorField; }
	public void setDiscriminatorField(K2Field discriminatorField) { this.discriminatorField = discriminatorField; }
	
	@MetaField(id=16)
	@Column(name="DISCRIMINATOR_VALUE")
	protected K2TypeValue discriminatorValue;
	public K2TypeValue getDiscriminatorValue() { return discriminatorValue; }
	public void setDiscriminatorValue(K2TypeValue discriminatorValue) { this.discriminatorValue = discriminatorValue; }
	
	@MetaField(id=17)
	protected List<K2Field> fields;
	public List<K2Field> getFields() { return fields; }
	public void setFields(List<K2Field> fields) { this.fields = fields; }
	
	
}
