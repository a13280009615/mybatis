package com.zf.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author zhengfan
 * @create 2020-09-19 下午1:50
 *
 * mybatis  有 2级缓存
 *
 * 1级缓存  本地缓存
 *
 *  于数据库同一次会话期间查询到的数据会放到本地缓存中
 *  以后如果获取相同的数据 直接从缓存中拿 没必要再去查询数据库了
 *
 *  2级缓存  全局缓存
 */
public class MybatisCatchTest {

    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);

        return new SqlSessionFactoryBuilder().build(inputStream);
    }
}
