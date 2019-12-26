package com.zss.db.aop;

import com.zss.db.annotation.DataSource;
import com.zss.db.constant.DataSourceKey;
import com.zss.db.util.DataSourceHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/12/23 17:57
 */
@Aspect
@Slf4j
@Order(-1)
public class DataSourceAOP {

    @Before("@annotation(ds)")
    public void changeDataSource(JoinPoint join,DataSource ds){
        String dsId = ds.name();
        try{
            DataSourceKey key = DataSourceKey.valueOf(dsId);
            DataSourceHolder.setDataSourceKey(key);
            log.info("数据源切换成功,{}",ds.name());
        }catch(Exception e){
            log.error("数据源[{}]不存在，使用默认数据源 > {}", ds.name(), join.getSignature());
        }

    }

    @After("@annotation(ds)")
    public void restoreDataSource(JoinPoint point,DataSource ds){
        log.info("Revert DataSource : {transIdo} > {}", ds.name());
        log.debug("Revert DataSource : {transIdo} > {}", ds.name(), point.getSignature());
        DataSourceHolder.cleanDataSourceKey();
    }
}
