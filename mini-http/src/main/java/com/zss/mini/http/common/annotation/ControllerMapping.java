package com.zss.mini.http.common.annotation;

import java.lang.annotation.*;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/11/4 14:34
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface ControllerMapping {

    String url() default "";
}
