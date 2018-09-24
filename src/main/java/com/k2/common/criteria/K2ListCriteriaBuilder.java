package com.k2.common.criteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public interface K2ListCriteriaBuilder {

	public <T> K2ListCriteriaAnd<T> root(Class<T> forClass, K2ListCriteria<T> listCriteria);

	public <T> K2CriteriaParameter<T> parameter(Class<T> parameterType, String alias);

	public K2CriteriaField field(String alias);

	public K2CriteriaLiteral literal(Object value);

	public CriteriaBuilder getCriteriaBuilder();

}
