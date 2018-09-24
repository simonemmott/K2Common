package com.k2.common.dao;

import com.k2.common.criteria.K2ListCriteria;
import com.k2.common.sequence.K2Sequence;
import com.k2.common.sequence.SequenceFieldInitialiser;

public abstract class AbstractInitialisingK2Dao<E, K> implements K2Dao<E, K> {
	
	@SafeVarargs
	public AbstractInitialisingK2Dao(Class<E> entityClass, Class<K> keyClass, K2Sequence<E> sequence, FieldInitialiser<E> ... initialisers) {
		this.entityClass = entityClass;
		this.keyClass = keyClass;
		this.sequence = sequence;
		this.initialisers = initialisers;
		for (FieldInitialiser<E> initialiser : this.initialisers) {
			if (initialiser instanceof SequenceFieldInitialiser) {
				((SequenceFieldInitialiser<E>)initialiser).setSequence(sequence);
			}
		}
	}
	
	private final K2Sequence<E> sequence;
	@Override
	public K2Sequence<E> getSequence() {
		return sequence;
	}
	private final FieldInitialiser<E>[] initialisers;
	
	private final Class<E> entityClass;
	@Override
	public Class<E> getEntityClass() { return entityClass; }
	
	private final Class<K> keyClass;
	@Override
	public Class<K> getKeyClass() { return keyClass; }
	
	@SuppressWarnings("unchecked")
	private void initialise(E entity, FieldInitialiser<E> ... inits) {
		
		boolean updated = true;
		while (updated) {
			updated = false;
			for (FieldInitialiser<E> initialiser : inits) {
				if (initialiser.toBeInitialised(entity)) {
					initialiser.initialise(entity);
					updated = true;
				}
			}
		}
	}
	

	@Override
	public E newInstance() {
		
		E entity;
		try {
			entity = entityClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new K2DaoError("Unable to create a new instance of {}. No zero arg constructor?", e, entityClass.getName());
		}
		
		initialise(entity, initialisers);
		
		return entity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E newInstance(FieldInitialiser<E> ... initialisers) {

		E entity;
		try {
			entity = entityClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new K2DaoError("Unable to create a new instance of {}. No zero arg constructor?", e, entityClass.getName());
		}
		
		FieldInitialiser<E>[] inits = new FieldInitialiser[this.initialisers.length+initialisers.length];
		for (int i=0; i<initialisers.length; i++) 
			inits[i] = initialisers[i];
		for (int i=0; i<this.initialisers.length; i++)
			inits[initialisers.length+i] = this.initialisers[i];
		
		initialise(entity, inits);
		
		return entity;
	}

	@Override
	public E newInstance(K2ListCriteria<E> criteria) {
		
		return newInstance(criteria.getInitialisers());
	}

	@Override
	public E newInstance(K2ListCriteria<E> criteria, ParameterMap parameters) {
		
		FieldInitialiser<E>[] inits = criteria.getInitialisers();

		for(FieldInitialiser<E> init : inits) {
			if (init instanceof ParameterFieldInitialiser)
				((ParameterFieldInitialiser<E>)init).setCurrentParameters(parameters);
		}
				
		return newInstance(inits);
	}


}
