package com.zss;

import com.zss.mini.http.MyFrameApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/11/5 9:38
 */
public class WebApplication {
    private static final Logger logger = LoggerFactory.getLogger(WebApplication.class);

    public static void main(String[] args) throws IllegalAccessException,InstantiationException{
        MyFrameApplication.start(WebApplication.class);
    }
}
