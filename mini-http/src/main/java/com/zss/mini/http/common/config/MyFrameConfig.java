package com.zss.mini.http.common.config;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/11/4 14:57
 */
@Slf4j
public class MyFrameConfig {

    public static String INDEX_PAGE = "page/index.html";

    public static boolean INDEX_CHANGE=false;

    public static String NOT_FOUND_PAGE = "page/404_page.html";

    public static boolean NOT_FOUND_CHANGE=false;

    public static String UNKNOW_EXCEPTION_PAGE = "page/404_page.html";

    public static boolean UNKNOW_EXCEPTION_CHANGE=false;

    public static int PORT = 8080;

    private static final String DEFAULT_LOCALTION = "/config/myframe-config.properties";

    private static final String INDEX_PAGE_KEY = "index.page";

    private static final String NOT_FOUND_PAGE_KEY = "not.found.page";

    private static final String UNKOWN_EXCEPTION_PAGE_KEY = "unkown.exception.page";

    private static final String PORT_KEY = "server.port";

    public static Class APPLICATION_CLASS;

    public static void init(){
        Properties properties = new Properties();
        try{
            InputStream in = APPLICATION_CLASS.getResourceAsStream(DEFAULT_LOCALTION);
            if(in != null){
                properties.load(in);
                if(properties.getProperty(INDEX_PAGE_KEY) != null){
                    INDEX_PAGE = properties.getProperty(INDEX_PAGE_KEY);
                    INDEX_CHANGE = true;
                }
                if(properties.getProperty(NOT_FOUND_PAGE_KEY) != null){
                    NOT_FOUND_PAGE = properties.getProperty(NOT_FOUND_PAGE_KEY);
                    NOT_FOUND_CHANGE = true;
                }
                if(properties.getProperty(UNKOWN_EXCEPTION_PAGE_KEY) != null){
                    UNKNOW_EXCEPTION_PAGE = properties.getProperty(UNKOWN_EXCEPTION_PAGE_KEY);
                    UNKNOW_EXCEPTION_CHANGE = true;
                }
                if(properties.getProperty(PORT_KEY) != null){
                    PORT = Integer.parseInt(properties.getProperty(PORT_KEY));
                }
            }
            log.info("[MyFrameConfig] server初始化静态资源文件");
            log.info("[MyFrameConfig] PORT:{},index_page:{},error_page:{}",PORT,INDEX_PAGE,UNKNOW_EXCEPTION_PAGE);
        }catch (IOException e){
            log.error("[MyFrameConfig]异常为{}", e);
        }
    }
}
