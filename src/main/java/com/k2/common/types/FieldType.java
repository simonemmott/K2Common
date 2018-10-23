package com.k2.common.types;

import com.k2.common.annotation.MetaComponent;
import com.k2.common.annotation.MetaTypeValue;

@MetaComponent(id=35)
public enum FieldType {

	@MetaTypeValue(description="Primitive fields")
	PRIMITIVE,
	@MetaTypeValue(description="Native fields")
	NATIVE,
	@MetaTypeValue(description="Type fields")
	TYPE,
	@MetaTypeValue(description="Linked fields")
	LINKED,
	@MetaTypeValue(description="Collection fields")
	COLLECTION
}
