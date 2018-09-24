package com.k2.common.criteria;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.k2.common.dao.FieldInitialiser;

public interface K2ListCriteria<T> {

	public CriteriaQuery<T> getCriteriaQuery();
	
	public FieldInitialiser<T>[] getInitialisers();

	public Root<T> getRoot();

	public K2ListCriteriaNode<T> getRootNode();
	
}
