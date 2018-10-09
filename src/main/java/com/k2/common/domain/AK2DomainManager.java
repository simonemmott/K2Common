package com.k2.common.domain;

import com.k2.common.dao.K2DaoFactory;
import com.k2.core.model.K2Domain;

public class AK2DomainManager implements K2DomainManager {
	
	protected AK2DomainManager (K2Domain k2Domain, K2DaoFactory daoFactory) {
		this.k2Domain = k2Domain;
		this.daoFactory = daoFactory;
	}

	private final K2Domain k2Domain;
	@Override
	public K2Domain getDomain() { return k2Domain; }
	
	private final K2DaoFactory daoFactory;
	@Override
	public K2DaoFactory getDaoFactory() { return daoFactory; }

}
