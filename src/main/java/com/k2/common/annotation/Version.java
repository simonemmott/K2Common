package com.k2.common.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(TYPE)
public @interface Version {
	public int major() default 0;
	public int minor() default 0;
	public int point() default 0;
	public int build() default 0;
}
