package com.k2.common.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.k2.common.annotation.MetaComponent;
import com.k2.common.annotation.MetaField;
import com.k2.common.model.aModel.AK2Component;
import com.k2.common.types.ComponentType;

@MetaComponent(id=1)
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="TYPE", discriminatorType = DiscriminatorType.STRING)
public class K2Component extends AK2Component{
	
	public K2Component(Long id, ComponentType type) { 
		this.id = id;
		this.type = type; 
	}

	@Id
	@MetaField(id=1)
	@Column(name="ID")
	protected Long id;
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	
	@MetaField(id=2)
	@Column(name="NAME")
	protected String name;
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	@MetaField(id=3)
	@Column(name="DESCRIPTION")
	protected String description;
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	
	@MetaField(id=4)
	@Column(name="TYPE")
	protected ComponentType type;
	public ComponentType getType() { return type; }
	public void setType(ComponentType type) { this.type = type; }
	
}
