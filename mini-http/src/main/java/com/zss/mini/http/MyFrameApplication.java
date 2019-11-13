package com.zss.mini.http;

import com.zss.mini.http.common.config.MyFrameConfig;
import com.zss.mini.http.core.NettyHttpServer;
import com.zss.mini.http.core.handle.ControllerMapping;
import com.zss.mini.http.core.handle.ControllerReactor;
import com.zss.mini.http.core.handle.FilterReactor;
import com.zss.mini.http.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/11/4 15:43
 */
@Slf4j
public class MyFrameApplication {

    public static void start(Class clazz) throws IllegalAccessException,InstantiationException{
        log.info("类的包路径：{}",clazz.getPackage().getName());
        MyFrameConfig.APPLICATION_CLASS = clazz;
        MyFrameConfig.init();
        Map<String, String> map = CommonUtil.scanController(clazz.getPackage().getName());
        map.forEach((k,v)->{
            ControllerReactor.CONTROLLER_LIST.add(new ControllerMapping(k,v));
            log.info("url:{},-> controller:{}",k,v);
        });
        FilterReactor.FILTER_LIST = CommonUtil.scanFilter(clazz.getPackage().getName());
        NettyHttpServer server = new NettyHttpServer(MyFrameConfig.PORT);
        try {
            server.init();
        } catch (Exception e) {
            log.error("server服务初始化异常，异常信息：{}",e);
        }
        log.info("server初始化成功。");
    }

    public static void main(String[] args) throws IllegalAccessException,InstantiationException{
        MyFrameApplication.start(MyFrameApplication.class);
    }
}
