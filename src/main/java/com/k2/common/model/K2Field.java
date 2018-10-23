package com.k2.common.model;

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
import com.k2.common.model.aModel.AK2Field;
import com.k2.common.types.ComponentType;
import com.k2.common.types.FieldType;
import com.k2.common.model.K2Class;
import com.k2.common.model.K2Component;

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
	
	@MetaField()
	@Column(name="FIELD_TYPE")
	protected FieldType fieldType;
	public FieldType getFieldType() { return fieldType; }
	public void setFieldType(FieldType fieldType) { this.fieldType = fieldType; }
	
	@MetaField()
	@Column(name="COLUMN_NAME")
	protected String columnName;
	public String getColumnName() { return columnName; }
	public void setColumnName(String columnName) { this.columnName = columnName; }
	
	
	@MetaField()
	@Column(name="IS_PRIMARY_KEY")
	protected Boolean isPrimaryKey;
	public Boolean getIsPrimaryKey() { return isPrimaryKey; }
	public void setIsPrimaryKey(Boolean isPrimaryKey) { this.isPrimaryKey = isPrimaryKey; }

	@MetaField()
	@Column(name="SORT_ORDER")
	protected Integer sortOrder;
	public Integer getSortOrder() { return sortOrder; }
	public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
	
	
}
