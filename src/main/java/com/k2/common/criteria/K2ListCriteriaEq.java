package com.k2.common.criteria;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;

public class K2ListCriteriaEq<T> extends K2ListCriteriaConstraint<T>{
	
	
	
	private K2ListCriteriaEq(K2ListCriteriaBuilder lcb, K2ListCriteria<T> listCriteria, Class<T> javaType, K2CriteriaField field, K2CriteriaExpression expression) {
		super(lcb, listCriteria, javaType, ConstraintType.EQUALS);
		this.field = field;
		this.expression = expression;
	}
	
	K2ListCriteriaEq(K2ListCriteriaGroup<T> parentNode, K2CriteriaField field, K2CriteriaExpression expression) {
		super(parentNode, ConstraintType.EQUALS);
		this.field = field;
		this.expression = expression;
	}
	
	private final K2CriteriaField field;
	private final K2CriteriaExpression expression;
	
	@Override
	public Predicate getPredicate() {
		
		CriteriaBuilder cb = getCriteriaBuilder();

		return cb.equal(field.getExpression(getListCriteriaBuilder(), getListCritieria()), expression.getExpression(getListCriteriaBuilder(), getListCritieria()));
	}
	

}
