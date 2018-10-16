package com.k2.common.domain;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.k2.Util.StringUtil;
import com.k2.Util.classes.ClassUtil;
import com.k2.app.K2;
import com.k2.common.annotation.DomainManager;
import com.k2.common.annotation.DomainManagerAware;
import com.k2.common.annotation.MetaDomain;
import com.k2.common.dao.K2Dao;
import com.k2.common.dao.K2DaoFactory;
import com.k2.common.reflector.K2Reflector;
import com.k2.core.model.K2Domain;

public interface K2DomainManager {
	

	public static K2DomainManager start(Class<? extends K2DomainManager> domainManagerClass) {
		
		if (! domainManagerClass.isAnnotationPresent(MetaDomain.class))
			throw new K2DomainError("The class domamin manager class {} is not annotated with @MetaDomain");
		
		MetaDomain mDomain = domainManagerClass.getAnnotation(MetaDomain.class);

		K2DaoFactory daoFactory;
		if (domainManagerClass.getName().equals("com.k2.core.K2CoreDomainManager"))
			daoFactory = K2DaoFactory.reflect(mDomain.sequencesClass(), mDomain.packages());
		else {
			//TODO - Create DaoFactory for non K2Core domains
			throw new K2DomainError("No implementation of K2DaoFactory available for domain manager class {}", domainManagerClass.getName());
		}
		
		K2Domain domain = K2Reflector.create(mDomain.sequencesClass()).reflect(domainManagerClass, K2Domain.class);
		
		K2DomainManager domainManager = K2DomainManagerUtil.createDomainManager(domainManagerClass, domain, daoFactory);
		
		K2DomainManagerUtil.setDomainAwareClasses(domainManager, mDomain.packages());
		
		return domainManager;
	}
		
	public static K2DomainManager reflect(String ... packageNames) {
		
		Class<? extends K2DomainManager> domainManagerClass = null;
		boolean found = false;
		for (String packageName : packageNames) {
			for (Class<?> cls : ClassUtil.getClasses(packageName, MetaDomain.class)) {
				domainManagerClass = (Class<? extends K2DomainManager>) cls;
				found = true;
				break;
			}
			if (found)
				break;
		}
		
		if (domainManagerClass == null)
			throw new K2DomainError("The packages {} do not contain an implementation of K2DomainManager annotated with @MetaDomain", 
					StringUtil.braceConcatenate("[", ", ", "]", (Object[])packageNames));

		K2DaoFactory daoFactory;
		if (domainManagerClass.getName().equals("com.k2.core.K2CoreDomainManager"))
			daoFactory = K2DaoFactory.reflect(packageNames);
		else {
			//TODO - Create DaoFactory for non K2Core domains
			throw new K2DomainError("No implementation of K2DaoFactory available for domain manager class {}", domainManagerClass.getName());
		}		
				
		K2Domain domain = K2Reflector.create().reflect(domainManagerClass, K2Domain.class);
		
		K2DomainManager domainManager = K2DomainManagerUtil.createDomainManager(domainManagerClass, domain, daoFactory);

		K2DomainManagerUtil.setDomainAwareClasses(domainManager, packageNames);
		
		return domainManager;
	}
	
	
	public K2Domain getDomain();
	public K2DaoFactory getDaoFactory();
	public K2 getK2();
	public void setK2(K2 k2);
	public Properties getProperties();
	public void setProperties(Properties prop);
	public String getProperty(String key);
	

}

