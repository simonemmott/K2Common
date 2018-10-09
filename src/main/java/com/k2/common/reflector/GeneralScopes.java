package com.k2.common.reflector;

public enum GeneralScopes implements ReflectionScope {

	GENERAL,
	CLASS,
	ENTITY,
	TRANSIENT,
	TYPE,
	FIELD,
	CRITERIA, 
	TYPEVALUE;

	@Override
	public String getScope() {
		return this.name();
	}
}
