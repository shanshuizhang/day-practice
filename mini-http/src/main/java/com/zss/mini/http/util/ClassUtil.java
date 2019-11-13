package com.zss.mini.http.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/11/1 11:29
 */
@Slf4j
public class ClassUtil {

    /**
     * 从包package中获取所有的Class
     * @param pkg
     * @return
     */
    public static Set<Class<?>> getClzFromPackage(String pkg){
        log.info("pkg===包名：{}",pkg);
        Set<Class<?>> classes = new LinkedHashSet<>();
        String pkgDirName = pkg.replace(".","/");
        log.info("pkgDirName===包目录名：{}",pkgDirName);
        try {
            Enumeration<URL> urls = ClassUtil.class.getClassLoader().getResources(pkgDirName);
            while(urls.hasMoreElements()){
                URL url = urls.nextElement();
                String protocol = url.getProtocol();
                log.info("protocol===协议名：{}",protocol);
                if("file".equals(protocol)){
                    String filePath = URLDecoder.decode(url.getFile(),"UTF-8");
                    log.info("filePath===文件路径：{}",filePath);
                    findClassesByFile(pkg,filePath,classes);
                }else if("jar".equals(protocol)){
                    JarFile jar = ((JarURLConnection)url.openConnection()).getJarFile();
                    findClassesByJar(pkg,jar,classes);
                }
            }
        } catch (IOException e) {
            log.error("从包package中获取所有的Class失败，异常信息：",e);
        }
        return classes;
    }

    private static void findClassesByFile(String pkgName, String pkgPath,Set<Class<?>> classes){
        File dir = new File(pkgPath);
        if(!dir.exists() || !dir.isDirectory()){
            return;
        }
        File[] dirFiles = dir.listFiles(pathname -> pathname.isDirectory() || pathname.getName().endsWith("class"));
        if(dirFiles == null || dirFiles.length == 0){
            return;
        }

        String className;
        Class clz;
        for(File file :dirFiles){
            if(file.isDirectory()){
                findClassesByFile(pkgName + "."+ file.getName(),pkgPath + "/" + file.getName(),classes);
                continue;
            }
            className = file.getName();
            className = className.substring(0,className.length() - 6);

            clz = loadClass(pkgName + "." + className);
            if(clz != null){
                classes.add(clz);
            }
        }
    }

    private static void findClassesByJar(String pkgName,JarFile jar,Set<Class<?>> classes){
        String pkgDir = pkgName.replace(".", "/");
        Enumeration<JarEntry> entry = jar.entries();

        JarEntry jarEntry;
        String name,className;
        Class<?> clz;
        while (entry.hasMoreElements()){
            jarEntry = entry.nextElement();

            name = jarEntry.getName();
            // 如果是以/开头的
            if (name.charAt(0) == '/') {
                // 获取后面的字符串
                name = name.substring(1);
            }

            if (jarEntry.isDirectory() || !name.startsWith(pkgDir) || !name.endsWith(".class")) {
                continue;
            }

            //如果是一个.class文件 而且不是目录
            // 去掉后面的".class" 获取真正的类名
            className = name.substring(0, name.length() - 6);
            //加载类
            clz = loadClass(className.replace("/", "."));
            // 添加到集合中去
            if (clz != null) {
                classes.add(clz);
            }
        }
    }

    /**
     * 加载类
     * @param fullClzName
     * @return
     */
    private static Class<?> loadClass(String fullClzName){
        try {
            return Thread.currentThread().getContextClassLoader().loadClass(fullClzName);
        } catch (ClassNotFoundException e) {
            log.error("加载类失败，全类名：{}，异常信息：",fullClzName,e);
        }
        return null;
    }
}
