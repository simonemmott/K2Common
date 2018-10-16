package com.k2.app;

import javax.xml.bind.annotation.XmlElement;

public class K2Property implements Comparable<K2Property>{
	
	public static K2Property create(String key, String value) {
		return new K2Property(key, value);
	}
	
	public K2Property() {}
	public K2Property(String key, String value) {
		this.key = key;
		this.value = value;
	}

	@XmlElement
	private String key;
	public String getKey() { return key; }
	
	@XmlElement
	private String value;
	public String getValue() { return value; }
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
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
		K2Property other = (K2Property) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}

	@Override
	public int compareTo(K2Property o) {
		return key.compareTo(o.key);
	}
	
}
