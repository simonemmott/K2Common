package com.k2.core.model.aModel;

import com.k2.Util.classes.ClassUtil;
import com.k2.common.annotation.MetaComponent;

@MetaComponent(id=2)
public abstract class AK2Component {

	public abstract String getName();
	
	public String getPackageName() {
		return ClassUtil.getPackageNameFromCanonicalName(getName());
	}
	
	public String getSimpleName() {
		return ClassUtil.getBasenameFromCanonicalName(getName());
	}
	
}
