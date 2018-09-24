package com.k2.common.dao.memoryDao;

import java.lang.invoke.MethodHandles;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.Metamodel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.k2.Expressions.criteria.CriteriaBuilderImpl;
import com.k2.Expressions.metamodel.MetamodelImpl;
import com.k2.common.criteria.K2CriteriaField;
import com.k2.common.criteria.K2CriteriaLiteral;
import com.k2.common.criteria.K2CriteriaParameter;
import com.k2.common.criteria.K2ListCriteria;
import com.k2.common.criteria.K2ListCriteriaAnd;
import com.k2.common.criteria.K2ListCriteriaBuilder;
import com.k2.common.criteria.K2ListCriteriaNode;

public class MemoryK2ListCriteriaBuilder implements K2ListCriteriaBuilder {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	public static MemoryK2ListCriteriaBuilder create(Class<?> ... classes) {
		return new MemoryK2ListCriteriaBuilder(classes);
	}
	
	private MemoryK2ListCriteriaBuilder(Class<?> ... classes) {
		for (Class<?> cls : classes) 
			logger.debug("Creating {} for class {}", this.getClass().getName(), cls.getName());
		
		metamodel = new MetamodelImpl(classes);
	}

	@Override
	public <T> K2ListCriteriaAnd<T> root(Class<T> forClass, K2ListCriteria<T> listCriteria) {
		return new K2ListCriteriaAnd<T>(this, listCriteria, forClass);
	}

	@Override
	public <T> K2CriteriaParameter<T> parameter(Class<T> parameterType, String alias) {
		return new K2CriteriaParameter<T>(parameterType, alias);
	}

	@Override
	public K2CriteriaField field(String alias) {
		return new K2CriteriaField(alias);
	}

	@Override
	public K2CriteriaLiteral literal(Object value) {
		return new K2CriteriaLiteral(value);
	}

	private CriteriaBuilder cb;
	private MetamodelImpl metamodel;
	
	@Override
	public CriteriaBuilder getCriteriaBuilder() {
		if (cb == null) 
			cb = new CriteriaBuilderImpl(metamodel);
		return cb;
	}

}
