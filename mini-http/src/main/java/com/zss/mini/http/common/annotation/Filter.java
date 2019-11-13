package com.zss.mini.http.common.annotation;

import java.lang.annotation.*;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/11/4 14:44
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Filter {

    String name() default "";

    int order() default 0;
}
