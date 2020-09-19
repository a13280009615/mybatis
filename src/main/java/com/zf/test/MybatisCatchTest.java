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
 * @create 2020-09-19 下午1:50
 *
 * mybatis  有 2级缓存
 *
 * 1级缓存  本地缓存  sqlSession级别的缓存 是一直开启的
 *
 *  于数据库同一次会话期间查询到的数据会放到本地缓存中
 *  以后如果获取相同的数据 直接从缓存中拿 没必要再去查询数据库了
 *
 *   一级缓存失效的情况 如果没有使用到当前一级缓存的情况 还需要向数据库发出查询
 *       1 session 不同
 *       2 session相同 但是查询条件不同
 *       3 session 相同  查询条件相同 (2次查询之前执行了增删改) 因为这次增删改可能会对当前数据有影响
 *       4 session 相同  手动清除了一级缓存   session.clearCatch();
 *
 *  2级缓存  全局缓存  基于 namespace 级别  一个namespace 对应一个二级缓存
 *
 *   机制：  1一个会话 查询一条数据 这个数据就会 被放在会话的以及缓存中
 *          2  如果会话关闭了 以及缓存中的数据会被保存在 二级缓存中 新的会话查询信息 就可以参照
 *           二级缓存 去取数据
 *          3 sqlSession  因为 二级缓存是namespace级别的 不同的namespace查出的数据会放在自己的
 *          namespace缓存中(map 中)
 *
 *          数据会从二级缓存中获取数据  查询的数据都会被默认先放在一级缓存中
 *          只有会话提交或者关闭之后 一级缓存中的数据才会转移到二级缓存中
 *
 *
 *    使用 ：
 *    1全局配置文件中 开启 二级缓存
 *      <setting name="cacheEnabled" value="true"/>
 *    2 在mapper中  <cache></cache>
 *    3 我们的pojo需要实现序列化接口
 *
 *
 *    和缓存有关的设置 和属性
 *      1） cacheEnabled =true   关闭的是二级缓存
 *      2）每个select 标签 都有 useCache   false 不使用二级缓存   一级缓存也不影响
 *      3） 每一个增删改 标签都有 一个flushCache 属性  默认是 true  一级缓存和二级缓存都会被清空
 *          增删改执行完成之后就会清楚缓存
 *      4） 每个select 标签 也有 一个flushCache  默认是 false 如果改为true 每次查询完毕都会清理缓存
 *      5） session.clearCache(); 该方法只是清除 session 的一级缓存
 *      6)  localCacheScope  本地缓存的作用域(一级缓存session ) 当前会话的所有数据保存在会话缓存中
 *          设置到statement 可以禁用到一级缓存
 *
 *
 *          第三方缓存整合
 *          1 导入第三方缓存包
 *          2  导入与第三方缓存整合的适配包
 *          3  mapper.xml 中使用自定以缓存
 *          <cache type="org.mybatis.caches.ehcache.EhcacheCache"></cache>
 *           其他 mapper.xml 也想用
 *           <cache-ref namespace="com.zf.dao.EmployeeMapper"/>
 *
 */
public class MybatisCatchTest {

    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);

        return new SqlSessionFactoryBuilder().build(inputStream);
    }


    @Test
    public  void test() throws IOException {
        SqlSessionFactory sessionFactory = getSqlSessionFactory();
        SqlSession session = sessionFactory.openSession();

        try{
            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
            Employee emp = mapper.getEmpById(1);
            System.out.println(emp);


            Employee emp2 = mapper.getEmpById(1);
            System.out.println(emp == emp2);
        }finally {
            session.close();
        }

    }


    /***
     *  测试 sqlsession不同  级别的一级缓存
     * @throws IOException
     */
    @Test
    public  void test2() throws IOException {
        SqlSessionFactory sessionFactory = getSqlSessionFactory();
        SqlSession session = sessionFactory.openSession();
        SqlSession session1 = sessionFactory.openSession();

        try{
            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
            Employee emp = mapper.getEmpById(1);


            EmployeeMapper mapper2 = session1.getMapper(EmployeeMapper.class);
            Employee emp2 = mapper2.getEmpById(1);

            System.out.println(emp == emp2);  //false   sqlsession不同
        }finally {
            session.close();
            session1.close();
        }

    }



    /***
     *  测试   sqlsession相同  条件不同
     * @throws IOException
     */
    @Test
    public  void test3() throws IOException {
        SqlSessionFactory sessionFactory = getSqlSessionFactory();
        SqlSession session = sessionFactory.openSession();


        try{
            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
            Employee emp = mapper.getEmpById(1);

            Employee emp1 = mapper.getEmpById(2);


            System.out.println(emp == emp1);  //false   sqlsession相同 条件不同 当前一级缓存还没有数据
        }finally {
            session.close();

        }

    }



    /***
     *  测试   sqlsession相同  条件相同  在2次查询期间  有增删改
     * @throws IOException
     */
    @Test
    public  void test4() throws IOException {
        SqlSessionFactory sessionFactory = getSqlSessionFactory();
        SqlSession session = sessionFactory.openSession();

        try{
            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
            Employee emp = mapper.getEmpById(1);


            mapper.addEmp(new Employee(null,"小张三","1","zs@163.com"));

            Employee emp1 = mapper.getEmpById(1);


            System.out.println(emp == emp1);  //false   sqlsession相同 条件不同 当前一级缓存还没有数据
        }finally {
            session.close();

        }

    }



    /***
     *  测试   sqlsession相同  条件相同  在2次查询期间  手动清理缓存
     * @throws IOException
     */
    @Test
    public  void test5() throws IOException {
        SqlSessionFactory sessionFactory = getSqlSessionFactory();
        SqlSession session = sessionFactory.openSession();

        try{
            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
            Employee emp = mapper.getEmpById(1);

           session.clearCache();

            Employee emp1 = mapper.getEmpById(1);


            System.out.println(emp == emp1);  //false   sqlsession相同 条件不同 当前一级缓存还没有数据
        }finally {
            session.close();

        }

    }
}
