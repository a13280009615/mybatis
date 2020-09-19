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
 * @create 2020-09-19 -22:24
 *
 *
 *   1 获取sqlSessionFactory 对象
 *     解析文件的每一个信息保存在configuration中 并返回包含了configuration 的default SessionFactory
 *     一个MappedStatement  代表了一个增删改查标签的详细信息
 *   2 获取sqlSession 对象
 *      通过defaultSessionFactory 返回一个 包含了Executor和Configuration的 defaultSession 对象
 *   3 获取接口的实现类对象    MapperProxy@385c9627
 *       getMapper  使用MapperProxyFactory 创建一个MapperProxy的代理对象
 *        代理对象里面包含了 DefaultSqlSession(Executor)
 *   4 执行增删改查方法
 *     Executor  ParameterHandler  ResultSetHandler   StatementHandler
 *
 *   总结：
 *     1根据配置文件 初始化 出Configuration
 *     2 创建 uo一个defaultSqlSession对象
 *      它里面包含了Configuration 以及Executor
 *     3 DefaultSqlSession.getMapper() 拿到接口对应的代理对象
 *     4 代理对象里面有DefaultSqlSession
 *     5  执行增删改查
 *           调用DefaultSqlSession 的增删改查
 *           在执行过程中 会创建StatementHandler 同时也会创建出 ParameterHandler ResultSetHandler
 *           调用StatementHandler 的预编译参数 以及设置参数的值
 *           在调用StatementHandler的增删改查方法
 *           使用ResultSetHandler 封装结构
 *     注意：
 *       四大对象每个创建的时候 都有一个 interceptorChain
 *
 *
 */
public class MybatisSourceTest {

    public SqlSessionFactory  getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);

        return new SqlSessionFactoryBuilder().build(inputStream);
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
        }finally {
            sqlSession.close();
        }
    }
}
