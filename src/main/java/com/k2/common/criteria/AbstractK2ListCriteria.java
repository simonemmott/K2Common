package com.k2.common.criteria;

import java.lang.invoke.MethodHandles;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class AbstractK2ListCriteria<T> implements K2ListCriteria<T> {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	public AbstractK2ListCriteria(K2ListCriteriaBuilder lcb) {
		this.lcb = lcb;
	}

	protected K2ListCriteriaNode<T> rootNode;
	protected final K2ListCriteriaBuilder lcb;
	protected CriteriaQuery<T> qry;
	protected Root<T> root;

	@Override
	public CriteriaQuery<T> getCriteriaQuery() {
		
		if (qry != null) return qry;
		
		logger.debug("Creating new criteria query for {} using criteria builder {}", rootNode.getJaveType().getName(), lcb.getCriteriaBuilder().getClass().getName());
		qry = lcb.getCriteriaBuilder().createQuery(rootNode.getJaveType());
		
		root = qry.from(rootNode.getJaveType());
		
		qry.where(rootNode.getPredicate());
		
		return qry;
	}
	
	@Override
	public Root<T> getRoot() {
		return root;
	}

	@Override
	public K2ListCriteriaNode<T> getRootNode() {
		return rootNode;
	}



}
