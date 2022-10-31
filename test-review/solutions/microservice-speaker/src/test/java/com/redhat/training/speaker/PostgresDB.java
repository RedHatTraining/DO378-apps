package com.redhat.training.speaker;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.quarkus.test.common.QuarkusTestResource;

@QuarkusTestResource(value = PostgressDBTestResource.class, restrictToAnnotatedClass = true)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PostgresDB {
    String username() default "";
    String password() default "";
    String name() default "";
}