package com.zss.mini.http.common;

import com.zss.mini.http.core.handle.response.BaseResponse;
import com.zss.mini.http.model.ControllerRequest;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/11/4 15:41
 */
public interface BaseController {

    /**
     * get请求
     *
     * @param controllerRequest
     * @return
     */
    BaseResponse doGet(ControllerRequest controllerRequest);

    /**
     * post请求
     *
     * @param controllerRequest
     * @return
     */
    BaseResponse doPost(ControllerRequest controllerRequest);
}
