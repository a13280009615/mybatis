package com.zf.test;

import com.zf.bean.Employee;
import com.zf.dao.EmployeeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author zhengfan
 * @create 2020-09-19 -17:38
 */
public class MybatisCache2Test {


    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);

        return new SqlSessionFactoryBuilder().build(inputStream);
    }


    /***
     *  测试二级缓存
     */
    @Test
    public  void test() throws IOException{
        SqlSessionFactory factory = getSqlSessionFactory();
        SqlSession sqlSession = factory.openSession();
        SqlSession session = factory.openSession();

        try{
            EmployeeMapper mapper1 = sqlSession.getMapper(EmployeeMapper.class);
            EmployeeMapper mapper2 = session.getMapper(EmployeeMapper.class);
            Employee empById = mapper1.getEmpById(1);

            Employee empById1 = mapper2.getEmpById(1);
            sqlSession.close();
            session.close();

        }
        finally {


        }
    }

}
