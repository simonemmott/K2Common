package com.k2.common.domain;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.k2.Util.classes.ClassUtil;
import com.k2.common.annotation.DomainManager;
import com.k2.common.annotation.DomainManagerAware;
import com.k2.common.dao.K2DaoFactory;
import com.k2.core.model.K2Domain;

public class K2DomainManagerUtil {

	static K2DomainManager createDomainManager(Class<? extends K2DomainManager> domainManagerClass, K2Domain domain, K2DaoFactory daoFactory) {

		Constructor<? extends K2DomainManager> constructor;
		try {
			constructor = domainManagerClass.getConstructor(K2Domain.class, K2DaoFactory.class);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new K2DomainError("No constructor available in class {} for the parameters K2Domain and K2DaoFactorty", e, domainManagerClass.getName());
		}
		
		K2DomainManager domainManager;
		
		try {
			domainManager = constructor.newInstance(domain, daoFactory);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new K2DomainError("Unable to construst Domain Manager instance from class {} with parameters K2Domain and K2DaoFactory", e, domainManagerClass.getName());
		}
		
		return domainManager;
	}
	
	static void setDomainAwareClasses(K2DomainManager domainManager, String ... packageNames) {

		for (String packageName : packageNames) {
			for (Class<?> domainAwareClass : ClassUtil.getClasses(packageName, DomainManagerAware.class)) {
				
				for (Field f : domainAwareClass.getDeclaredFields()) {
					if (f.isAnnotationPresent(DomainManager.class)) {
						f.setAccessible(true);
						try {
							f.set(null, domainManager);
						} catch (IllegalArgumentException | IllegalAccessException e) {
							throw new K2DomainError("Unable to set domain manager in field {}.{}", domainAwareClass.getName(), f.getName());
						}
					}
				}
				
				for (Method m : domainAwareClass.getDeclaredMethods()) {
					if (m.isAnnotationPresent(DomainManager.class)) {
						m.setAccessible(true);
						try {
							if (m.getParameterCount() == 0) {
								m.invoke(null);
							} else if (m.getParameterCount() == 1) {
								if (K2DomainManager.class.isAssignableFrom(m.getParameterTypes()[0]) ) {
									m.invoke(null, domainManager);
								} else {
									throw new K2DomainError("The method {}.{} to initialise the domain manager aware class with its domain manager recieves a parameter other than a K2DomainManager",
											domainAwareClass.getName(),
											m.getName());
								}
							} else {
								throw new K2DomainError("Too many arguments to method {}.{} to initialise domain manager aware class",
										domainAwareClass.getName(),
										m.getName());
							}
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							throw new K2DomainError("Unable to execute the method {}.{} to initialise the domain manager aware class with its domain manager", e,
									domainAwareClass.getName(),
									m.getName());
						} finally {
							
						}
					}
				}
			}
		}

	}
}
