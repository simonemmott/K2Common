package com.k2.common.model;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.k2.common.annotation.MetaComponent;
import com.k2.common.types.ComponentType;
import com.k2.common.model.K2Component;

@MetaComponent(id=5)
@Entity
@DiscriminatorValue("PRIMITIVE")
public class K2Primitive extends K2Component{

	public K2Primitive(Long id) {
		super(id, ComponentType.PRIMITIVE);
	}

}
