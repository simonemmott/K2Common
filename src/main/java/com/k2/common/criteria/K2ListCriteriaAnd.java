package com.k2.common.criteria;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import com.k2.common.dao.K2DaoError;
import com.k2.common.dao.memoryDao.MemoryK2ListCriteriaBuilder;

public class K2ListCriteriaAnd<T> extends K2ListCriteriaGroup<T>{
	
	public K2ListCriteriaAnd(K2ListCriteriaBuilder lcb, K2ListCriteria<T> listCriteria, Class<T> javaType) {
		super(lcb, listCriteria, javaType, GroupType.AND);
	}

	public K2ListCriteriaAnd(K2ListCriteriaGroup<T> parentNode) {
		super(parentNode, GroupType.AND);
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
			
			return cb.and(restrictions);
			
		}
		
	}

	
	

}
