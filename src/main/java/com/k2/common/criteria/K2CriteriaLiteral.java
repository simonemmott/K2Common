package com.k2.common.criteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

public class K2CriteriaLiteral extends K2CriteriaExpression {
	
	public K2CriteriaLiteral(Object value) {
		super(K2CriteriaExpressionType.LITERAL);
		this.value = value;
	}
	
	private Object value;
	public Object value() {
		return value;
	}
	
	@Override
	public <T> Expression<?> getExpression(K2ListCriteriaBuilder lcb, K2ListCriteria<T> listCritieria) {
		CriteriaBuilder cb = lcb.getCriteriaBuilder();
		return cb.literal(value);
	}
}
