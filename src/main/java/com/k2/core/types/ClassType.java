package com.k2.core.types;

import com.k2.common.annotation.MetaComponent;
import com.k2.common.annotation.MetaTypeValue;

@MetaComponent(id=36)
public enum ClassType {
	
	@MetaTypeValue(name="Native", description="Native classes")
	NATIVE,
	@MetaTypeValue(description="Entity classes")
	ENTITY,
	@MetaTypeValue(description="Transient classes")
	TRANSIENT
	
}
