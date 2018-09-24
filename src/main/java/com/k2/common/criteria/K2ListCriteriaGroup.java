package com.k2.common.criteria;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;

public abstract class K2ListCriteriaGroup<T> extends K2ListCriteriaNode<T>{
	
	public enum GroupType {
		AND,
		OR,
		NOT
	}
		
	public K2ListCriteriaGroup(K2ListCriteriaBuilder lcb, K2ListCriteria<T> listCriteria, Class<T> javaType, GroupType groupType) {
		super(lcb, listCriteria, javaType, NodeType.GROUP);
		this.groupType = groupType;
	}
	
	public K2ListCriteriaGroup(K2ListCriteriaGroup<T> parentNode, GroupType groupType) {
		super(parentNode, NodeType.GROUP);
		this.groupType = groupType;
	}

	private final GroupType groupType;
	
	private List<K2ListCriteriaNode<T>> childNodes = new ArrayList<K2ListCriteriaNode<T>>();
	
	private K2ListCriteriaNode<T> add(K2ListCriteriaNode<T> childNode) {
		childNodes.add(childNode);
		return childNode;
	}
	
	public List<K2ListCriteriaNode<T>> getChildNodes() {
		return childNodes;
	}
	
	public GroupType getGroupType() {
		return groupType;
	}
	
	public K2ListCriteriaAnd<T> and() {
		return (K2ListCriteriaAnd<T>) add(new K2ListCriteriaAnd(this));
	}
	
	public K2ListCriteriaOr<T> or() {
		return (K2ListCriteriaOr<T>) add(new K2ListCriteriaOr(this));
	}
	
	public K2ListCriteriaNot<T> not() {
		return (K2ListCriteriaNot<T>) add(new K2ListCriteriaNot(this));
	}
	
	public K2ListCriteriaEq<T> eq(K2CriteriaField field, K2CriteriaExpression expression) {
		return (K2ListCriteriaEq<T>) add(new K2ListCriteriaEq(this, field, expression));
	}

	public K2ListCriteriaNotEq<T> notEq(K2CriteriaField field, K2CriteriaExpression expression) {
		return (K2ListCriteriaNotEq<T>) add(new K2ListCriteriaNotEq(this, field, expression));
	}

	public K2ListCriteriaIsNull<T> isNull(K2CriteriaExpression expression) {
		return (K2ListCriteriaIsNull<T>) add(new K2ListCriteriaIsNull(this, expression));
	}

	public K2ListCriteriaIsNotNull<T> isNotNull(K2CriteriaExpression expression) {
		return (K2ListCriteriaIsNotNull<T>) add(new K2ListCriteriaIsNotNull(this, expression));
	}


}
