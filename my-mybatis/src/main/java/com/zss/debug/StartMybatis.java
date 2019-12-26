package com.zss.debug;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/11/15 17:38
 */
public class StartMybatis {

    public static void main(String[] args) {
        String source = "mybatis-config.xml";

        InputStream inputStream = StartMybatis.class.getClassLoader().getResourceAsStream(source);

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession sql = sqlSessionFactory.openSession();
    }
}
