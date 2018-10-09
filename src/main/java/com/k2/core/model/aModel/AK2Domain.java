package com.k2.core.model.aModel;

import java.util.List;

import com.k2.Util.classes.ClassUtil;
import com.k2.common.annotation.DomainManager;
import com.k2.common.annotation.DomainManagerAware;
import com.k2.common.annotation.MetaComponent;
import com.k2.common.domain.K2DomainManager;
import com.k2.core.K2CoreDomainManager;
import com.k2.core.model.K2Component;

@DomainManagerAware
@MetaComponent()
public abstract class AK2Domain {

	public abstract String getName();
	
	public abstract List<K2Component> getDomainComponents();
	
	public void generateSource(String repoPath) {	
		if (domainManager instanceof K2CoreDomainManager)
			((K2CoreDomainManager)domainManager).generateSource(repoPath);	
	}
	
	@DomainManager
	private static K2DomainManager domainManager;
	
	public static void setDomainManager(K2DomainManager dm) {
		domainManager = dm;
	}
	
	public K2DomainManager getDomainManager() { return domainManager; }
	
	
	
}
