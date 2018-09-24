package com.k2.common.criteria;

import java.util.List;

public abstract class K2ListCriteriaConstraint<T> extends K2ListCriteriaNode<T>{
	
	public enum ConstraintType {
		EQUALS,
		NOT_EQUALS,
		IS_NULL,
		IS_NOT_NULL
	}
	
	protected K2ListCriteriaConstraint(K2ListCriteriaBuilder lcb, K2ListCriteria<T> listCriteria, Class<T> javaType, ConstraintType constraintType) {
		super(lcb, listCriteria, javaType, NodeType.CONSTRAINT);
		this.constraintType = constraintType;
	}
	
	protected K2ListCriteriaConstraint(K2ListCriteriaGroup<T> parentNode, ConstraintType constraintType) {
		super(parentNode, NodeType.CONSTRAINT);
		this.constraintType = constraintType;
	}
	
	private final ConstraintType constraintType;
	
	public ConstraintType getConstraintType() {
		return constraintType;
	}
	

}
