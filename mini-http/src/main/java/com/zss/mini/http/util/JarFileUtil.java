package com.zss.mini.http.util;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/11/4 11:23
 */
public class JarFileUtil {

    public String readJarFileIndex(String filePathInJar) throws IOException {
        InputStream inputStream = ClassLoader.getSystemResourceAsStream(filePathInJar);
        return readResources(inputStream);
    }

    /**
     * 配合readJarFileIndex使用
     *
     * @param path
     * @return
     * @throws IOException
     */
    private String readResources(String path) throws IOException {
        InputStream is = this.getClass().getResourceAsStream(path);
        return readResources(is);
    }

    public static String readResources(InputStream inputStream) throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream,"UTF-8");
        for(;;){
            int rsz = in.read(buffer,0,buffer.length);
            if(rsz < 0){
                break;
            }
            out.append(buffer,0,rsz);
        }
        inputStream.close();
        return out.toString();
    }
}
