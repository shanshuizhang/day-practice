package com.zss.db.util;

import com.zss.db.constant.DataSourceKey;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/12/23 14:48
 */
public class DataSourceHolder {

    private final static ThreadLocal<DataSourceKey> DATA_SOURCE_KEY = new ThreadLocal();

    public static DataSourceKey getDataSourceKey(){
        return DATA_SOURCE_KEY.get();
    }

    public static void setDataSourceKey(DataSourceKey type){
        DATA_SOURCE_KEY.set(type);
    }

    public static void cleanDataSourceKey(){
        DATA_SOURCE_KEY.remove();
    }
}
