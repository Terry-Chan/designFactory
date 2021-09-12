package com.design.method.factory.annotation;



import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface PreCheck {

    String  indexName() default " ";

}
