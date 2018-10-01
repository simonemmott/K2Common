package com.k2.core.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.k2.common.annotation.MetaComponent;
import com.k2.common.annotation.MetaField;
import com.k2.core.types.ComponentType;

@MetaComponent(id=33)
@Entity
public class K2TypeValue {

	public K2TypeValue(Long id) {
		this.id = id;
	}

	@Id
	@MetaField(id=12)
	@Column(name="ID")
	protected Long id;
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	@MetaField(id=13)
	@Column(name="DEFINING_TYPE")
	protected K2Type definingType;
	public K2Type getDefiningType() { return definingType; }
	public void setDefiningType(K2Type definingType) { this.definingType = definingType; }
	
	@MetaField(id=14)
	@Column(name="ALIAS")
	protected String alias;
	public String getAlias() { return alias; }
	public void setAlias(String alias) { this.alias = alias; }
	
	@MetaField(id=15)
	@Column(name="NAME")
	protected String name;
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	@MetaField()
	@Column(name="DESCRIPTION")
	protected String description;
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	
	

}
