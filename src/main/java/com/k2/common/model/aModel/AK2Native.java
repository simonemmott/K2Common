package com.k2.common.model.aModel;

import com.k2.Util.classes.ClassUtil;
import com.k2.common.annotation.MetaComponent;
import com.k2.common.model.K2Class;
import com.k2.common.model.K2Component;
import com.k2.common.model.K2Native;
import com.k2.common.model.K2Primitive;
import com.k2.common.types.ClassType;
import com.k2.common.types.ComponentType;

@MetaComponent(id=22)
public abstract class AK2Native extends K2Class{

	private static K2Native createNative(Long id, String name, String description) {
		K2Native n = new K2Native(id);
		n.setName(name);
		n.setDescription(description);
		return n;
	}
	
	public static K2Native INTEGER = createNative(23L, "java.lang.Integer", "Integer native values");
	public static K2Native LONG = createNative(24L, "java.lang.Long", "Long native values");
	public static K2Native FLOAT = createNative(25L, "java.lang.Float", "Float native values");
	public static K2Native DOUBLE = createNative(26L, "java.lang.Double", "Double native values");
	public static K2Native BOOLEAN = createNative(27L, "java.lang.Boolean", "Boolean native values");
	public static K2Native SHORT = createNative(28L, "java.lang.Short", "Short native values");
	public static K2Native BYTE = createNative(29L, "java.lang.Byte", "Byte native values");
	public static K2Native CHARACTER = createNative(30L, "java.lang.Character", "Character native values");
	public static K2Native STRING = createNative(31L, "java.lang.String", "String values");
	public static K2Native DATE = createNative(32L, "java.util.Date", "Date values");
	
	public AK2Native(Long id, ClassType classType) {
		super(id, classType);
		type = ComponentType.CLASS;
	}
	
	
}
