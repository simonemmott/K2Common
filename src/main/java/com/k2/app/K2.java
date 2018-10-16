package com.k2.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import com.google.common.collect.Lists;
import com.k2.common.domain.K2DomainError;
import com.k2.common.domain.K2DomainManager;
import com.k2.core.K2CoreDomainManager;

public class K2 {
	
	public static K2 start(File config) throws FileNotFoundException {
		return new K2(config);
	}

	private final Map<String, K2DomainManager> domains = new HashMap<String, K2DomainManager>();
	private final K2Config config;
	
	private K2(File configFile) throws FileNotFoundException {
		try {
			this.config = K2Config.read(configFile);
		} catch (JAXBException e) {
			throw new K2DomainError("Unable to parse the configuration file {}", e, configFile.getAbsolutePath());
		}
		for (Class<? extends K2DomainManager> domainManagerClass : config.getDomainManagerClasses()) {
			K2DomainManager domainManager = K2DomainManager.start(domainManagerClass);
			domainManager.setK2(this);
			domainManager.setProperties(config.getProperties(domainManagerClass));
			domains.put(domainManager.getDomain().getName(), domainManager);
		}
	}
	
	public K2DomainManager getDomainManager(String domainName) {
		K2DomainManager dm = domains.get(domainName);
		if (dm == null)
			throw new K2DomainError("This K2 application does not manage a domain named {}", domainName);
		return dm;
	}
	
	public List<String> getDomainNames() {
		List<String> list = Lists.newArrayList(domains.keySet());
		Collections.sort(list);
		return list;
	}
}
