package com.zss.hot.classloader;

import com.zss.hot.classloader.watchfile.Test;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/11/8 19:03
 */
@Data
@AllArgsConstructor
public class FileDefine {

    public static String WATCH_PACKAGE = Test.class.getPackage().getName().replace(".","/");

    private String fileName;

    private Long lastDefine;
}
