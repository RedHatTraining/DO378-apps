package com.redhat.training.rest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.quarkus.test.common.QuarkusTestResource;

@QuarkusTestResource(PostgressDBTestResource.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface WithPostgressDB {
    String username() default "";
    String password() default "";
    String name() default "";
}
