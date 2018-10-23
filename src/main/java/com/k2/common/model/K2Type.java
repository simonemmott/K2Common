package com.k2.common.model;

import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.k2.common.annotation.MetaComponent;
import com.k2.common.annotation.MetaField;
import com.k2.common.types.ComponentType;
import com.k2.common.model.K2Component;
import com.k2.common.model.K2TypeValue;

@MetaComponent(id=6)
@Entity
public class K2Type extends K2Component{

	public K2Type(Long id) {
		super(id, ComponentType.TYPE);
	}
	
	@MetaField(id=18)
	protected List<K2TypeValue> values;
	public List<K2TypeValue> getValues() { return values; }
	public void setValues(List<K2TypeValue> values) { this.values = values; }
	

}
