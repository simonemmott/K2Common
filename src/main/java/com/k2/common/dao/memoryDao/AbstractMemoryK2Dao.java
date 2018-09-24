package com.k2.common.dao.memoryDao;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.k2.EntityMap.EntityMap;
import com.k2.Expressions.criteria.CriteriaQueryImpl;
import com.k2.Expressions.evaluators.GenericEvaluator;
import com.k2.Expressions.evaluators.QueryEvaluator;
import com.k2.Expressions.predicate.K2Predicate;
import com.k2.Util.Identity.IdentityUtil;
import com.k2.common.criteria.K2Criteria;
import com.k2.common.criteria.K2ListCriteria;
import com.k2.common.dao.AbstractInitialisingK2Dao;
import com.k2.common.dao.FieldInitialiser;
import com.k2.common.dao.K2Dao;
import com.k2.common.dao.K2DaoError;
import com.k2.common.dao.ParameterMap;
import com.k2.common.sequence.K2Sequence;
import com.k2.common.sequence.SequenceFieldInitialiser;

public abstract class AbstractMemoryK2Dao<E, K> extends AbstractInitialisingK2Dao<E,K> implements K2Dao<E, K> {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@SafeVarargs
	public AbstractMemoryK2Dao(Class<E> entityClass, Class<K> keyClass, EntityMap<E,K> entityMap, K2Sequence<E> sequence, FieldInitialiser<E> ... initialisers) {
		super(entityClass, keyClass, sequence, initialisers);
		this.entityMap = entityMap;
	}
	
	private final EntityMap<E,K> entityMap;
	
	

	@Override
	public E fetch(K key) {
		logger.debug("fetching instance of {} for key {}", this.getEntityClass().getName(), key.toString());
		E item = entityMap.get(key);
		logger.debug((item==null)?"No match for key '{}'":"Found instance for key '{}'", key.toString());
		return item;
	}

	@Override
	public E fetch(K2ListCriteria<E> criteria) {
		logger.debug("fetching instnace of {} for criteria '{}'", this.getEntityClass().getName(), criteria.getClass().getAnnotation(K2Criteria.class).alias());
		CriteriaQuery<E> query = criteria.getCriteriaQuery();
		if (query instanceof CriteriaQueryImpl) {
			QueryEvaluator<E> eval = new QueryEvaluator<E>((CriteriaQueryImpl<E>)query);

			for (E item : list()) {
				
				logger.debug("Evaluating instance of {} with key '{}' against criteria '{}'", this.getEntityClass(), IdentityUtil.getId(item).toString(), criteria.getClass().getAnnotation(K2Criteria.class).alias());
				
				if (eval.matches(item)) {
					logger.debug("Item mataches criteria");
					return item;
				}
				
			}
			return null;
		} else {
			throw new K2DaoError("The memory DAO can only handle the CriteriaQuery implementation {}", CriteriaQueryImpl.class.getName());
		}
	}

	@Override
	public E fetch(K2ListCriteria<E> criteria, ParameterMap parameters) {

		logger.debug("Fetch instance of {} which match criteria '{}' with parameters {}", this.getEntityClass().getName(), criteria.getClass().getAnnotation(K2Criteria.class).alias(), parameters.toString());
		CriteriaQuery<E> query = criteria.getCriteriaQuery();
		if (query instanceof CriteriaQueryImpl) {
			QueryEvaluator<E> eval = new QueryEvaluator<E>((CriteriaQueryImpl<E>)query);

			for (String parameterName : parameters.getParameterNames()) {
				Object value = parameters.getParameterValue(parameterName);
				eval.setRawParameter(parameterName, value);
			}
			
			for (E item : list()) {
				
				logger.debug("Evaluating instance of {} with key '{}' against criteria '{}'", this.getEntityClass(), IdentityUtil.getId(item).toString(), criteria.getClass().getAnnotation(K2Criteria.class).alias());
				
				if (eval.matches(item)) {
					logger.debug("Item mataches criteria");
					return item;
				}
				
			}
			return null;
		} else {
			throw new K2DaoError("The memory DAO can only handle the CriteriaQuery implementation {}", CriteriaQueryImpl.class.getName());
		}

	}
	
	@Override
	public E insert(E entity) {
		logger.debug("Inserting instance of {} with key {}", entity.getClass().getName(), IdentityUtil.getId(entity));
		if (entityMap.contains(entity))
			throw new K2DaoError("The entity {}[{}] already exists in the Entity Map", entity.getClass().getSimpleName(), IdentityUtil.getId(entity).toString());
		entityMap.put(entity);
		return entity;
	}

	@Override
	public E update(E entity) {
		logger.debug("Updating instance of {} with key {}", entity.getClass().getName(), IdentityUtil.getId(entity));
		if (! entityMap.contains(entity))
			throw new K2DaoError("The entity {}[{}] does not exist in the Entity Map", entity.getClass().getSimpleName(), IdentityUtil.getId(entity).toString());
		entityMap.put(entity);
		return entity;
	}

	@Override
	public void delete(E entity) {
		if (entityMap.contains(entity)) {
			logger.debug("Deleting instance of {} with key {}", entity.getClass().getName(), IdentityUtil.getId(entity));
			entityMap.remove(entity);
		}
		else
			throw new K2DaoError("The entity {}[{}] does not exist in the Entity Map", entity.getClass().getSimpleName(), IdentityUtil.getId(entity).toString());

	}

	@Override
	public List<E> list() {
		logger.debug("Listing all instances of {}", this.getEntityClass().getName());
		return entityMap.list();
	}

	@Override
	public List<E> list(K2ListCriteria<E> criteria) {
		
		logger.debug("Listing instances of {} which match criteria '{}'", this.getEntityClass().getName(), criteria.getClass().getAnnotation(K2Criteria.class).alias());
		CriteriaQuery<E> query = criteria.getCriteriaQuery();
		if (query instanceof CriteriaQueryImpl) {
			QueryEvaluator<E> eval = new QueryEvaluator<E>((CriteriaQueryImpl<E>)query);
			List<E> list = new ArrayList<E>();
			
			for (E item : list()) {
				
				logger.debug("Evaluating instance of {} with key '{}' against criteria '{}'", this.getEntityClass(), IdentityUtil.getId(item).toString(), criteria.getClass().getAnnotation(K2Criteria.class).alias());
				
				if (eval.matches(item)) {
					logger.debug("Item mataches criteria");
					list.add(item);
				}
				
			}
			return list;
		} else {
			throw new K2DaoError("The memory DAO can only handle the CriteriaQuery implementation {}", CriteriaQueryImpl.class.getName());
		}

	}

	@Override
	public List<E> list(K2ListCriteria<E> criteria, ParameterMap parameters) {

		logger.debug("Listing instances of {} which match criteria '{}' with parameters {}", this.getEntityClass().getName(), criteria.getClass().getAnnotation(K2Criteria.class).alias(), parameters.toString());
		CriteriaQuery<E> query = criteria.getCriteriaQuery();
		if (query instanceof CriteriaQueryImpl) {
			QueryEvaluator<E> eval = new QueryEvaluator<E>((CriteriaQueryImpl<E>)query);

			for (String parameterName : parameters.getParameterNames()) {
				Object value = parameters.getParameterValue(parameterName);
				eval.setRawParameter(parameterName, value);
			}
			
			List<E> list = new ArrayList<E>();
			
			for (E item : list()) {
				
				logger.debug("Evaluating instance of {} with key '{}' against criteria '{}'", this.getEntityClass(), IdentityUtil.getId(item).toString(), criteria.getClass().getAnnotation(K2Criteria.class).alias());
				
				if (eval.matches(item)) {
					logger.debug("Item mataches criteria");
					list.add(item);
				}
				
			}
			return list;
		} else {
			throw new K2DaoError("The memory DAO can only handle the CriteriaQuery implementation {}", CriteriaQueryImpl.class.getName());
		}
		
	}

}
