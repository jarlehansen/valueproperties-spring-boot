package com.github.jarlehansen.valueproperties;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(PropertiesLocatorConfiguration.class)
public @interface EnableValueProperties {
    String[] profiles() default {};
}
