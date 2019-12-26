package com.zss.login.service;

import com.zss.db.annotation.DataSource;
import com.zss.login.mapper.SysLogMapper;
import com.zss.login.mapper.po.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/12/23 18:31
 */
@Service
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    @DataSource(name="log")
    @Override
    public void insertSysLog(){
        SysLog sysLog = SysLog.builder()
                .module("测试模块")
                .params("")
                .remark("备注")
                .username("zss")
                .flag(true)
                .createTime(new Date())
                .build();
        sysLogMapper.insert(sysLog);
    }
}
