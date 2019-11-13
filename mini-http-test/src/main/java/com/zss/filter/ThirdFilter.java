package com.zss.filter;


import com.zss.mini.http.common.BaseFilter;
import com.zss.mini.http.common.annotation.Filter;
import com.zss.mini.http.model.ControllerRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * @author idea
 * @data 2019/5/1
 */
@Filter(order = 3)
@Slf4j
public class ThirdFilter implements BaseFilter {

    @Override
    public void beforeFilter(ControllerRequest controllerRequest) {
        log.info("this is 3 before");
    }

    @Override
    public void afterFilter(ControllerRequest controllerRequest) {
        log.info("this is 3 after");
    }
}
