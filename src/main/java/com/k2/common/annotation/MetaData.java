package com.k2.common.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.k2.common.sequence.K2SequenceFactory;

@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface MetaData {
	
	public String value();

}
