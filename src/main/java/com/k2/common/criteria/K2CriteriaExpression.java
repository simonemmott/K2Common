package com.k2.common.criteria;

import javax.persistence.criteria.Expression;

public abstract class K2CriteriaExpression {
	
	public K2CriteriaExpression(K2CriteriaExpressionType type) {
		this.type = type;
	}
	private final K2CriteriaExpressionType type;
	public K2CriteriaExpressionType type() {
		return type;
	}
	
	public abstract <T> Expression<?> getExpression(K2ListCriteriaBuilder lcb, K2ListCriteria<T> listCritieria);


}
