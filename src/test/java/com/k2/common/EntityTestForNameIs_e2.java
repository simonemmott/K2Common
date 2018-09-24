package com.k2.common;

import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.persistence.criteria.CriteriaQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.k2.common.criteria.AbstractK2ListCriteria;
import com.k2.common.criteria.K2Criteria;
import com.k2.common.criteria.K2ListCriteria;
import com.k2.common.criteria.K2ListCriteriaBuilder;
import com.k2.common.criteria.K2ListCriteriaNode;
import com.k2.common.dao.FieldInitialiser;

@K2Criteria(forClass=EntityTest.class, alias="forNameIs_e2")
public class EntityTestForNameIs_e2 extends AbstractK2ListCriteria<EntityTest> implements K2ListCriteria<EntityTest> {
	
	public EntityTestForNameIs_e2(K2ListCriteriaBuilder lcb) {
		super(lcb);
		
		rootNode = lcb.root(EntityTest.class, this)
				.eq(lcb.field("name"), lcb.literal("e2"))
				.root();
		
	}
	
	@Override
	public FieldInitialiser<EntityTest>[] getInitialisers() {
		List<FieldInitialiser<EntityTest>> inits = Lists.newArrayList();
		
		inits.add(FieldInitialiser.fromLiteral(EntityTest.class, "name", "e2"));
		
		return inits.toArray(new FieldInitialiser[inits.size()]);
	}

}
