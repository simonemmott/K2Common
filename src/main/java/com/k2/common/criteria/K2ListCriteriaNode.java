package com.k2.common.criteria;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import com.k2.Expressions.predicate.PredicateBuilder;

public abstract class K2ListCriteriaNode<T> {
	
	public enum NodeType {
		GROUP,
		CONSTRAINT
	}

	
	private final K2ListCriteriaNode<T> parentNode;
	private final K2ListCriteriaNode<T> rootNode;
	private final Class<T> javaType;
	private final NodeType nodeType;
	private final K2ListCriteriaBuilder lcb;
	private final K2ListCriteria<T> listCriteria;
	
	public K2ListCriteriaNode(K2ListCriteriaBuilder lcb, K2ListCriteria<T> listCriteria, Class<T> forClass, NodeType nodeType) {
		this.parentNode = null;
		this.rootNode = this;
		this.javaType = forClass;
		this.nodeType = nodeType;
		this.lcb = lcb;
		this.listCriteria = listCriteria;
	}

	public K2ListCriteriaNode(K2ListCriteriaNode<T> parentNode, NodeType nodeType) {
		this.parentNode = parentNode;
		this.rootNode = parentNode.root();
		this.javaType = parentNode.getJaveType();
		this.nodeType = nodeType;
		this.lcb = parentNode.getListCriteriaBuilder();
		this.listCriteria = parentNode.getListCritieria();
	}

	public K2ListCriteria<T> getListCritieria() {
		return listCriteria;
	}
	
	public K2ListCriteriaNode<T> up() {
		return (parentNode==null)?this:parentNode;
	}

	public K2ListCriteriaNode<T> root() {
		return rootNode;
	}
	
	public Class<T> getJaveType() {
		return javaType;
	}
	
	public NodeType getNodeType() {
		return nodeType;
	}
	public K2ListCriteriaBuilder getListCriteriaBuilder() {
		return lcb;
	}
	public CriteriaBuilder getCriteriaBuilder() {
		return lcb.getCriteriaBuilder();
	}


	public abstract Predicate getPredicate();

}
