package com.k2.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.k2.common.domain.K2DomainError;
import com.k2.common.domain.K2DomainManager;

@XmlRootElement
public class K2Config{
	
	@XmlElement
	private String name;
	public String getName() { return name; }
	
    // XmLElementWrapper generates a wrapper element around XML representation
    @XmlElementWrapper(name = "domains")
 	@XmlElement(name="domain")
	private Set<K2DomainConfig> domains;

	private static final long serialVersionUID = 2701606070526820093L;
	
	public void setProperties(Class<? extends K2DomainManager> domainManagerClass, Properties prop) {
		if (domains == null)
			domains = new TreeSet<K2DomainConfig>();
		domains.add(K2DomainConfig.create(domainManagerClass, prop));
	}
	
	public Properties getProperties(Class<? extends K2DomainManager> domainManagerClass) {
		if (domains == null)
			throw new K2DomainError("No properties defined");
		for (K2DomainConfig dConf : domains) {
			if (dConf.getDomainManagerClass().equals(domainManagerClass)) {
				return dConf.getProperties();
			}
		}
		throw new K2DomainError("No properties defined for the domain manager class {}", domainManagerClass.getName());
	}
	
	public List<Class<? extends K2DomainManager>> getDomainManagerClasses() {
		if (domains == null)
			throw new K2DomainError("No properties defined");
		List<Class<? extends K2DomainManager>> list = new ArrayList<Class<? extends K2DomainManager>>(domains.size());
		for (K2DomainConfig dConf : domains) {
			list.add(dConf.getDomainManagerClass());
		}
		return list;
	}
	
	private static JAXBContext context = null;
	private static JAXBContext getContext() throws JAXBException {
        if (context == null)
        	context = JAXBContext.newInstance(K2Config.class);
        return context;

	}

	public void write(File configFile) throws JAXBException {
				
         Marshaller m = getContext().createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(this, configFile);
	}
	
	public static K2Config read(File configFile) throws JAXBException, FileNotFoundException {
        Unmarshaller um = getContext().createUnmarshaller();
        return (K2Config) um.unmarshal(new FileReader(configFile));
 
	}

}
