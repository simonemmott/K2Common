package com.k2.common;

import java.util.Arrays;
import java.util.List;

import javax.persistence.criteria.CriteriaQuery;

import com.google.common.collect.Lists;
import com.k2.common.criteria.AbstractK2ListCriteria;
import com.k2.common.criteria.K2Criteria;
import com.k2.common.criteria.K2ListCriteria;
import com.k2.common.criteria.K2ListCriteriaBuilder;
import com.k2.common.criteria.K2ListCriteriaNode;
import com.k2.common.dao.FieldInitialiser;

@K2Criteria(forClass=EntityTest.class, alias="forName")
public class EntityTestForName extends AbstractK2ListCriteria<EntityTest> implements K2ListCriteria<EntityTest> {
	
	public EntityTestForName(K2ListCriteriaBuilder lcb) {
		super(lcb);
		
		rootNode = lcb.root(EntityTest.class, this)
				.eq(lcb.field("name"), lcb.parameter(String.class, "name"))
				.root();
		
	}
	
	@Override
	public FieldInitialiser<EntityTest>[] getInitialisers() {
		List<FieldInitialiser<EntityTest>> inits = Lists.newArrayList();
		
		inits.add(FieldInitialiser.fromParameter(EntityTest.class, "name", "name"));
		
		return inits.toArray(new FieldInitialiser[inits.size()]);
	}

}
