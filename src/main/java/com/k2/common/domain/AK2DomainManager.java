package com.k2.common.domain;

import java.util.Properties;

import com.k2.app.K2;
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
	
	private K2 k2;
	@Override
	public K2 getK2() { return k2; }
	@Override
	public void setK2(K2 k2) { this.k2 = k2; }
	
	private Properties properties;
	@Override
	public Properties getProperties() { return properties; }
	@Override
	public void setProperties(Properties prop) { this.properties = prop; }
	
	@Override
	public String getProperty(String key) {
		if (properties == null)
			return null;
		return properties.getProperty(key);
	}

}
