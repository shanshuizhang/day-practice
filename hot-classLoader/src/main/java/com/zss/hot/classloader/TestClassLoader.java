package com.zss.hot.classloader;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/11/8 19:09
 */
@Slf4j
public class TestClassLoader {

    public static Map<String,FileDefine> fileDefineMap = new HashMap<String, FileDefine>();

    public static void main(String[] args){
        initMap();
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        scheduledThreadPool.scheduleAtFixedRate(new WatchDog(fileDefineMap), 0,500, TimeUnit.MICROSECONDS);

    }


    public static void initMap(){
        log.info("路径：{}",FileDefine.WATCH_PACKAGE);
        FileDefine.WATCH_PACKAGE = "F:\\IdeaProjects\\my-day-practice\\hot-classLoader\\src\\main\\java\\com\\zss\\hot\\classloader\\watchfile";
        File file = new File(FileDefine.WATCH_PACKAGE);
        File[] files = file.listFiles();
        for (File watchFile : files){
            long l = watchFile.lastModified();
            String name = watchFile.getName();
            FileDefine fileDefine = new FileDefine(name,l);
            fileDefineMap.put(name,fileDefine);
        }
    }
}
