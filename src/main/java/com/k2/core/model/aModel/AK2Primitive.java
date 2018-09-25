package com.k2.core.model.aModel;

import com.k2.Util.classes.ClassUtil;
import com.k2.common.annotation.MetaComponent;
import com.k2.core.model.K2Component;
import com.k2.core.model.K2Primitive;
import com.k2.core.types.ComponentType;

@MetaComponent(id=12)
public abstract class AK2Primitive extends K2Component{

	private static K2Primitive createPrimitive(Long id, String name, String description) {
		K2Primitive p = new K2Primitive(id);
		p.setName(name);
		p.setDescription(description);
		return p;
	}
	
	public static K2Primitive INT = createPrimitive(13L, "int", "Integer primitive values");
	public static K2Primitive LONG = createPrimitive(14L, "long", "Long primitive values");
	public static K2Primitive FLOAT = createPrimitive(15L, "float", "Float primitive values");
	public static K2Primitive DOUBLE = createPrimitive(16L, "double", "Double primitive values");
	public static K2Primitive BOOLEAN = createPrimitive(17L, "boolean", "Boolean primitive values");
	public static K2Primitive SHORT = createPrimitive(18L, "short", "Short primitive values");
	public static K2Primitive BYTE = createPrimitive(19L, "byte", "Byte primitive values");
	public static K2Primitive CHAR = createPrimitive(20L, "char", "Char primitive values");
	public static K2Primitive VOID = createPrimitive(21L, "void", "Void primitive values");
	
	public AK2Primitive(Long id, ComponentType type) {
		super(id, type);
	}
	
	
}
