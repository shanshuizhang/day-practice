package com.zss.hot.classloader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/11/8 18:13
 */
public class CustomClassLoader extends ClassLoader {

    private static final String CLASS_FILE_SUFFIX = ".class";

    private ClassLoader extClassLoader;

    public CustomClassLoader(){
        ClassLoader ext = String.class.getClassLoader();
        if(ext == null){
            ext = getSystemClassLoader();
            while (ext.getParent() != null){
                ext = ext.getParent();
            }
        }
        this.extClassLoader = ext;
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve){
        Class cls = null;
        cls = findLoadedClass(name);
        if(cls != null){
            return cls;
        }

        ClassLoader extClassLoader = getExtClassLoader();
        try {
            cls = extClassLoader.loadClass(name);
            if(cls != null){
                return cls;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        cls = findClass(name);
        return cls;
    }

    @Override
    public Class<?> findClass(String name) {
        byte[] bt = loadClassData(name);
        return defineClass(name, bt, 0, bt.length);
    }

    private byte[] loadClassData(String className) {
        // 读取Class文件呢
        InputStream is = getClass().getClassLoader().getResourceAsStream(className.replace(".", "/")+CLASS_FILE_SUFFIX);
        ByteArrayOutputStream byteSt = new ByteArrayOutputStream();
        // 写入byteStream
        int len =0;
        try {
            while((len=is.read())!=-1){
                byteSt.write(len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 转换为数组
        return byteSt.toByteArray();

    }

    public ClassLoader getExtClassLoader(){
        return extClassLoader;
    }
}
