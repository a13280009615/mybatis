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
 * @create 2020-09-15 23:34
 *
 *  核心对象 sqlSession 代表和数据库的一次会话  用完必须关闭
 *  sqlSession和 connection 一样都是非线程安全 每次使用都应该获取新的对象
 *  mapper接口没有实现类 mybatis 会给这个接口生成一个代理对象 前提是接口和xml绑定
 *
 *
 *
 */
public class MybatisTest {

    public SqlSessionFactory  getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);

         return new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public  void test() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        /**
         * 根据xml配置文件 创建selSessionFactory 对象 有数据源等一些运行环境信息
         * */
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            /**
             * selectOne()
             *   第一个参数是 sql语句的唯一标识
             *   第二个参数是 object 类型的参数
             *   sql映射文件 配置了每一个sql 以及sql 的封装规则
             *    mybatis 之前的规则 现在 使用 接口的方式
             * */
            Employee employee = sqlSession.selectOne("com.zf.mybatis.EmployeeMapper.selectEmp", 1);
            System.out.println(employee);
        }finally {
            sqlSession.close();
        }

    }


    @Test
    public  void  test02() throws IOException {
        // 获取 工厂对象
        SqlSessionFactory factory = getSqlSessionFactory();
        //获取 session 对象
        SqlSession sqlSession = factory.openSession();
        //获取接口的实现类对象
        try{


        EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
        Employee emp = employeeMapper.getEmpById(1);
            System.out.println(employeeMapper);
            // 类型 是 proxy 代理对象
            System.out.println(employeeMapper.getClass());
            System.out.println(emp);
        }finally {
            sqlSession.close();
            System.out.println("sout");
        }
    }
}
