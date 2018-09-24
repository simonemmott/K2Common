package com.k2.common.criteria;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;

import com.k2.common.criteria.K2ListCriteriaGroup.GroupType;
import com.k2.common.dao.K2DaoError;

public class K2ListCriteriaOr<T> extends K2ListCriteriaGroup<T>{
	
	public K2ListCriteriaOr(K2ListCriteriaBuilder lcb, K2ListCriteria<T> listCriteria, Class<T> javaType) {
		super(lcb, listCriteria, javaType, GroupType.OR);
	}

	public K2ListCriteriaOr(K2ListCriteriaGroup<T> parentNode) {
		super(parentNode, GroupType.OR);
	}
	
	@Override
	public Predicate getPredicate() {
		CriteriaBuilder cb = getCriteriaBuilder();
		List<K2ListCriteriaNode<T>> childNodes = this.getChildNodes();
		
		if (childNodes.size() == 0) {
			throw new K2DaoError("Group predicates must have at least 1 child node");
		} else {
			
			Predicate[] restrictions = new Predicate[childNodes.size()];
			
			for (int i=0; i<childNodes.size(); i++) {
				K2ListCriteriaNode<T> childNode = childNodes.get(i);
				restrictions[i] = childNode.getPredicate();
			}
			
			return cb.or(restrictions);
			
		}
		
	}

	

}
