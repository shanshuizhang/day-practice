package com.zss.db.util;

import com.zss.db.constant.DataSourceKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/12/23 14:43
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {

    private Map<Object, Object> targetDataSources;


    public DynamicDataSource(){
        this.targetDataSources = new HashMap(4);
        super.setTargetDataSources(targetDataSources);
    }

    @Override
    protected Object determineCurrentLookupKey() {
      log.info("数据库初始化，数据库：{}",DataSourceHolder.getDataSourceKey());
        return DataSourceHolder.getDataSourceKey();
    }

    public <T extends DataSource> void addDataSource(DataSourceKey key, T dataSource){
        targetDataSources.put(key,dataSource);
    }
}
