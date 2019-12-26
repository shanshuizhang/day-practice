package com.zss.db.annotation;

import java.lang.annotation.*;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/12/23 14:40
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    String name();
}
