package com.zss.controller;


import com.zss.mini.http.common.BaseController;
import com.zss.mini.http.common.annotation.ControllerMapping;
import com.zss.mini.http.core.handle.response.BaseResponse;
import com.zss.mini.http.model.ControllerRequest;

/**
 * @author idea
 * @data 2019/4/30
 */
@ControllerMapping(url = "/seconde-controller")
public class SecondController implements BaseController {

    @Override
    public BaseResponse doGet(ControllerRequest controllerRequest) {
        String pwd= (String) controllerRequest.getParameter("password");
        System.out.println(pwd);
        return new BaseResponse(2,pwd,"");
    }

    @Override
    public BaseResponse doPost(ControllerRequest controllerRequest) {
        return null;
    }
}
