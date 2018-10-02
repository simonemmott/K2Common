package com.k2.core.types;

import com.k2.common.annotation.MetaComponent;
import com.k2.common.annotation.MetaTypeValue;

@MetaComponent()
public enum ComponentType {

	@MetaTypeValue(description="Primitive types")
	PRIMITIVE,
	@MetaTypeValue(description="Class types")
	CLASS,
	@MetaTypeValue(description="Interface types")
	INTERFACE,
	@MetaTypeValue(description="Enumerated types")
	TYPE
}
