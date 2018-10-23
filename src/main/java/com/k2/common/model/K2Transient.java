package com.k2.common.model;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.k2.common.annotation.MetaComponent;
import com.k2.common.annotation.MetaField;
import com.k2.common.types.ClassType;
import com.k2.common.model.K2Class;

@MetaComponent(id=11)
@Entity
@DiscriminatorValue("TRANSIENT")
public class K2Transient extends K2Class{

	public K2Transient(Long id) {
		super(id, ClassType.TRANSIENT);
	}

}
