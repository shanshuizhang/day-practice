package com.zss.filter;

import com.zss.mini.http.common.BaseFilter;
import com.zss.mini.http.common.annotation.Filter;
import com.zss.mini.http.model.ControllerRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * @author idea
 * @data 2019/4/30
 */
@Filter(order = 1)
@Slf4j
public class MyFilter implements BaseFilter {

    @Override
    public void beforeFilter(ControllerRequest controllerRequest) {
        log.info("before");
    }

    @Override
    public void afterFilter(ControllerRequest controllerRequest) {
        log.info("after");
    }
}
