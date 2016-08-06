package com.dhy.annotation;

import java.lang.annotation.*;

/**
 * Created by Dai on 2016/8/6.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FruitName {
    String value() default "";
}
