package com.k2.common.criteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

public class K2CriteriaParameter<T> extends K2CriteriaExpression {
	
	public K2CriteriaParameter(Class<T> parameterType, String alias) {
		super(K2CriteriaExpressionType.PARAMETER);
		this.alias = alias;
		this.parameterType = parameterType;
	}
	
	private final String alias;
	public String alias() {
		return alias;
	}
	
	private final Class<T> parameterType;
	public Class<T> parameterType() {
		return parameterType;
	}
	
	@Override
	public <T> Expression<?> getExpression(K2ListCriteriaBuilder lcb, K2ListCriteria<T> listCritieria) {
		CriteriaBuilder cb = lcb.getCriteriaBuilder();
		
		return cb.parameter(parameterType, alias);
	}

}
