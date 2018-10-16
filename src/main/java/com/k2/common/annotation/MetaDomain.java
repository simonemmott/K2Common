package com.k2.common.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.k2.common.sequence.K2SequenceFactory;

@Retention(RUNTIME)
@Target(TYPE)
public @interface MetaDomain {
	
	public long id() default 0;
	public String name() default "";
	public String description() default "";
	public String[] packages();
	public Class<? extends K2SequenceFactory> sequencesClass();
	

}
