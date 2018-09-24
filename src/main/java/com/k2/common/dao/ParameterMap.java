package com.k2.common.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ParameterMap {

	private Map<String, Object> map = new HashMap<String,Object>();
	
	private ParameterMap() {}
	
	public static ParameterMap create(String alias, Object value) {
		return new ParameterMap().map(alias, value);
	}
	
	public ParameterMap map(String alias, Object value) {
		map.put(alias, value);
		return this;
	}
	
	public Set<String> getParameterNames() {
		return map.keySet();
	}
	
	public Object getParameterValue(String key) {
		return map.get(key);
	}
	
}
