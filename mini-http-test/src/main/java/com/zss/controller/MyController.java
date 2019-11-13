package com.zss.controller;


import com.zss.mini.http.common.BaseController;
import com.zss.mini.http.common.annotation.ControllerMapping;
import com.zss.mini.http.core.handle.response.BaseResponse;
import com.zss.mini.http.model.ControllerRequest;

/**
 * @author idea
 * @data 2019/4/30
 */
@ControllerMapping(url = "/myController")
public class MyController implements BaseController {

    @Override
    public BaseResponse doGet(ControllerRequest controllerRequest) {
        String username= (String) controllerRequest.getParameter("username");
        System.out.println(username);
        return new BaseResponse(1,username,"");
    }

    @Override
    public BaseResponse doPost(ControllerRequest controllerRequest) {
        return null;
    }
}
