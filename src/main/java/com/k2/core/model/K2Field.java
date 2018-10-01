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
import com.k2.core.model.aModel.AK2Field;
import com.k2.core.types.ComponentType;

@MetaComponent(id=8)
@Entity
public class K2Field extends AK2Field {
	
	public K2Field(Long id) {
		this.id = id;
	}

	@Id
	@MetaField(id=7)
	@Column(name="ID")
	protected Long id;
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	@MetaField(id=8)
	@Column(name="DECLARING_CLASS")
	protected K2Class declaringClass;
	public K2Class getDeclaringClass() { return declaringClass; }
	public void setDeclaringClass(K2Class declaringClass) { this.declaringClass = declaringClass; }

	@MetaField(id=9)
	@Column(name="ALIAS")
	protected String alias;
	public String getAlias() { return alias; }
	public void setAlias(String alias) { this.alias = alias; }
	
	@MetaField(id=10)
	@Column(name="DATA_TYPE")
	protected K2Component dataType;
	public K2Component getDataType() { return dataType; }
	public void setDataType(K2Component dataType) { this.dataType = dataType; }
}
