package com.k2.core.model;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.k2.common.annotation.MetaComponent;
import com.k2.core.types.ComponentType;

@MetaComponent(id=4)
@Entity
@DiscriminatorValue("INTERFACE")
public class K2Interface extends K2Component{

	public K2Interface(Long id) {
		super(id, ComponentType.INTERFACE);
	}

}
