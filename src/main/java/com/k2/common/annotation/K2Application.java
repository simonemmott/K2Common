package com.k2.common.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.k2.common.domain.K2DomainManager;

@Retention(RUNTIME)
@Target(TYPE)
public @interface K2Application {
	
	public String name();
	public String description() default "";
	public Class<? extends K2DomainManager>[] domainManagers() default {};
	public Version version() default @Version();

}
