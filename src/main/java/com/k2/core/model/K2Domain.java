package com.k2.core.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.k2.common.annotation.MetaComponent;
import com.k2.common.annotation.MetaField;
import com.k2.core.model.aModel.AK2Component;
import com.k2.core.model.aModel.AK2Domain;
import com.k2.core.types.ComponentType;

@MetaComponent()
@Entity
public class K2Domain extends AK2Domain{
	
	public K2Domain(Long id) { 
		this.id = id;
	}

	@Id
	@MetaField()
	@Column(name="ID")
	protected Long id;
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	
	@MetaField()
	@Column(name="NAME")
	protected String name;
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	@MetaField()
	@Column(name="DESCRIPTION")
	protected String description;
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	
	@MetaField()
	protected List<K2Component> domainComponents;
	@Override
	public List<K2Component> getDomainComponents() { return domainComponents; }
	public void setDomainComponents(List<K2Component> domainComponents) { this.domainComponents = domainComponents; }
	
	@MetaField()
	@Column(name="DOMAIN_CLASS_NAME")
	private String domainClassName;
	public String getDomainClassName() { return domainClassName; }
	public void setDomainClassName(String domainClassName) { this.domainClassName = domainClassName; }
	
}
