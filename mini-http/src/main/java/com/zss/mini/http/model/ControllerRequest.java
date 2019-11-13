package com.zss.mini.http.model;

import lombok.Setter;

import java.util.Map;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/11/4 15:28
 */
@Setter
public class ControllerRequest {

    private Map<String,Object> params;

    private Map<String,String> header;


    public Object getParameter(String name){
        return params.get(name);
    }

    public Object getHeader(String name){
        return header.get(name);
    }
}
