package com.k2.core;

import com.k2.common.annotation.MetaDomain;
import com.k2.common.dao.K2DaoFactory;
import com.k2.common.domain.AK2DomainManager;
import com.k2.common.domain.K2DomainManager;
import com.k2.core.model.K2Domain;

@MetaDomain(name="K2Core", description="This is the K2 core development domain")
public class K2CoreDomainManager extends AK2DomainManager implements K2DomainManager {

	public K2CoreDomainManager(K2Domain k2Domain, K2DaoFactory daoFactory) {
		super(k2Domain, daoFactory);
	}

	public void generateSource(String repoPath) {
		// TODO Auto-generated method stub
		
	}

}
