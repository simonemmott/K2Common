package com.k2.common.reflector;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.k2.Util.StringUtil;
import com.k2.Util.classes.ClassUtil;
import com.k2.Util.entity.EntityUtil;
import com.k2.common.K2MetaDataError;
import com.k2.common.annotation.MetaComponent;
import com.k2.common.annotation.MetaDomain;
import com.k2.common.annotation.MetaField;
import com.k2.common.annotation.MetaTypeValue;
import com.k2.core.model.K2Class;
import com.k2.core.model.K2Component;
import com.k2.core.model.K2Domain;
import com.k2.core.model.K2Entity;
import com.k2.core.model.K2Field;
import com.k2.core.model.K2Transient;
import com.k2.core.model.K2Type;
import com.k2.core.model.K2TypeValue;
import com.k2.core.model.aModel.AK2Native;
import com.k2.core.model.aModel.AK2Primitive;
import com.k2.core.types.FieldType;

@Reflector
public class DomainReflector extends AItemReflector<Class, K2Domain> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	public DomainReflector(K2Reflector reflector) {
		super(Class.class, K2Domain.class, reflector);
	}

	@Override
	public K2Domain reflect(Class item) {
		logger.trace("Reflecting dfomain {}", item.getName());

		MetaDomain mDomain = (MetaDomain) item.getAnnotation(MetaDomain.class);
		Long id = getReflector().idOrSequence(K2Domain.class, mDomain.id());
		
		K2Domain domain = getReflector().getEntityMap().get(K2Domain.class, id);
		if (domain != null)
			throw new K2MetaDataError("Duplicate domain id {} detected on {} and {}", id, item.getName(), domain.getDomainClassName());
		
		domain = new K2Domain(id);
		
		getReflector().getEntityMap().put(domain);
		
		populate(item, domain);
		
		return domain;

	}

	private void populate(Class<?> item, K2Domain instance) {
		MetaDomain mDomain = item.getAnnotation(MetaDomain.class);
		
		instance.setName((mDomain.name().equals("")?item.getSimpleName():mDomain.name()));
		instance.setDescription(mDomain.description());
		instance.setDomainClassName(item.getName());
		instance.setRootPackageName(ClassUtil.getPackageNameFromCanonicalName(item.getName()));
		
	}


}
