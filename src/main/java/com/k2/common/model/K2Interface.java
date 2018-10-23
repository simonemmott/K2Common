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

@MetaComponent(id=4)
@Entity
@DiscriminatorValue("INTERFACE")
public class K2Interface extends K2Component{

	public K2Interface(Long id) {
		super(id, ComponentType.INTERFACE);
	}

}
