package com.k2.common.criteria;

import javax.persistence.criteria.Expression;

public class K2CriteriaField extends K2CriteriaExpression {
	
	public K2CriteriaField(String alias) {
		super(K2CriteriaExpressionType.FIELD);
		this.alias = alias;
	}
	
	private final String alias;
	public String alias() {
		return alias;
	}
	@Override
	public <T> Expression<?> getExpression(K2ListCriteriaBuilder lcb, K2ListCriteria<T> listCriteria) {
		
		return listCriteria.getRoot().get(alias);
	}
}
