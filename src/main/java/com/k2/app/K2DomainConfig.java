package com.k2.app;

import java.util.List;
import java.util.Map.Entry;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;

import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import com.k2.common.domain.K2DomainError;
import com.k2.common.domain.K2DomainManager;

public class K2DomainConfig implements Comparable<K2DomainConfig>{
	
	public static K2DomainConfig create(Class<? extends K2DomainManager> domainManagerClass, Properties prop) {
		return new K2DomainConfig(domainManagerClass, prop);
	}
	
	public K2DomainConfig() {}
	private K2DomainConfig(Class<? extends K2DomainManager> domainManagerClass, Properties prop) {
		this.className = domainManagerClass.getName();
		this.setProperties(prop);
	}

	@XmlElement
	private String className;
	public Class<? extends K2DomainManager> getDomainManagerClass() {
		try {
			return (Class<? extends K2DomainManager>) Class.forName(className);
		} catch (ClassNotFoundException | ClassCastException e) {
			throw new K2DomainError("The className {} does not define a K2DomainManager class", className);  
		}
	}
	
    @XmlElementWrapper(name = "properties")
 	@XmlElement(name="property")
	private Set<K2Property> properties;
    @XmlTransient
	public Properties getProperties() {
		Properties prop = new Properties();
		for (K2Property property : properties) {
			prop.setProperty(property.getKey(), property.getValue());
		}
		return prop;
	}
	public void setProperties(Properties prop) {
		properties = new TreeSet<K2Property>();
		for (Entry<Object, Object> p : prop.entrySet()) {
			properties.add(K2Property.create((String)p.getKey(), (String)p.getValue()));
		}
	}
	public String getProperty(String key) {
		return getProperties().getProperty(key);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((className == null) ? 0 : className.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		K2DomainConfig other = (K2DomainConfig) obj;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		return true;
	}

	@Override
	public int compareTo(K2DomainConfig o) {
		return className.compareTo(o.className);
	}

}
