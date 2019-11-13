package com.zss.mini.http.common;

import com.zss.mini.http.model.ControllerRequest;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/11/4 15:42
 */
public interface BaseFilter {

    /**
     * 过滤器之前执行
     *
     * @param controllerRequest
     */
    void beforeFilter(ControllerRequest controllerRequest);

    /**
     * 过滤器之后执行
     *
     * @param controllerRequest
     */
    void afterFilter(ControllerRequest controllerRequest);
}
