package com.k2.core.model;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.k2.common.annotation.MetaComponent;
import com.k2.core.types.ComponentType;

@MetaComponent(id=5)
@Entity
@DiscriminatorValue("PRIMITIVE")
public class K2Primitive extends K2Component{

	public K2Primitive(Long id) {
		super(id, ComponentType.PRIMITIVE);
	}

}
