package com.k2.common.criteria;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;

public class K2ListCriteriaIsNotNull<T> extends K2ListCriteriaConstraint<T>{
	
	
	
	private K2ListCriteriaIsNotNull(K2ListCriteriaBuilder lcb, K2ListCriteria<T> listCriteria, Class<T> javaType, K2CriteriaExpression expression) {
		super(lcb, listCriteria, javaType, ConstraintType.IS_NOT_NULL);
		this.expression = expression;
	}
	
	K2ListCriteriaIsNotNull(K2ListCriteriaGroup<T> parentNode, K2CriteriaExpression expression) {
		super(parentNode, ConstraintType.IS_NOT_NULL);
		this.expression = expression;
	}
	
	private final K2CriteriaExpression expression;
	
	@Override
	public Predicate getPredicate() {
		
		CriteriaBuilder cb = getCriteriaBuilder();

		return cb.isNotNull(expression.getExpression(getListCriteriaBuilder(), getListCritieria()));
	}
	

}
