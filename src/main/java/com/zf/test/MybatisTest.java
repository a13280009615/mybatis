package com.zf.test;

import com.zf.bean.Department;
import com.zf.bean.Employee;
import com.zf.dao.DepartmentMapper;
import com.zf.dao.EmployeeMapper;
import com.zf.dao.EmployeeMapperPlus;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        }
    }


    /**
     *  测试简单的增删改
     *   mybatis 的增删改 允许直接定义 以下返回值 Integer  Long  Boolean void 类型的返回值 自动封装
     *
     * @throws IOException
     */

    @Test
    public  void test03() throws IOException {
       SqlSessionFactory factory = getSqlSessionFactory();
       //默认不会自动提交 如果想自动提交在方法中传一个 true
      SqlSession  session = factory.openSession();

      try{
          EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);

          //测试新增员工
          //  Employee employee = new Employee(null, "kit", "1", "kit@163.com");
          // mapper.addEmp(employee);

          //测试更新员工信息
          //Employee employee = new Employee(2, "jerry", "1", "jerry@163.com");
          //mapper.updateEmp(employee);

          //测试删除
          boolean b = mapper.delEmp(2);
          System.out.println(b);
          session.commit();
      }finally {
          session.close();
      }
    }


    @Test
    public  void test04() throws IOException {
        SqlSessionFactory factory = getSqlSessionFactory();
        //默认不会自动提交 如果想自动提交在方法中传一个 true
        SqlSession  session = factory.openSession();

        try{
            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);

            //测试新增员工
              Employee employee = new Employee(null, "kit", "1", "kit@163.com");
             mapper.addEmp(employee);
            System.out.println(employee.getId());


            session.commit();
        }finally {
            session.close();
        }
    }




    @Test
    public  void test05() throws IOException {

        //Parameter 'id' not found. Available parameters are [0, 1, param1, param2]
        SqlSessionFactory factory = getSqlSessionFactory();

        SqlSession  session = factory.openSession();

        try{
            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
            Employee tom = mapper.getEmpByIdAndLastName(1, "tom");
            System.out.println(tom);
            session.commit();
        }finally {
            session.close();
        }
    }


    /**
     *  通过 map 传值
     * @throws IOException
     */
    @Test
    public  void test06() throws IOException {

        SqlSessionFactory factory = getSqlSessionFactory();

        SqlSession  session = factory.openSession();

        try{
            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
            Map<String,Object> map  =  new HashMap<>();
            map.put("id",1);
            map.put("lastName","tom");
            Employee tom = mapper.getByMap(map);
            System.out.println(tom);
            session.commit();
        }finally {
            session.close();
        }
    }


    /**
     * 返回值是一个集合的测试
     * @throws IOException
     */
    @Test
    public  void test08() throws IOException {

        SqlSessionFactory factory = getSqlSessionFactory();

        SqlSession  session = factory.openSession();

        try{
            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);

            List<Employee> employeeList = mapper.getEmpByLastNameLike("%i%");
            for (Employee e:employeeList) {
                System.out.println(e);
            }
            session.commit();
        }finally {
            session.close();
        }
    }



    @Test
    public  void test09() throws IOException {

        SqlSessionFactory factory = getSqlSessionFactory();

        SqlSession  session = factory.openSession();

        try{
            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);

            Map<String, Object> employee = mapper.getEmployeeById(1);
            System.out.println(employee);

            session.commit();
        }finally {
            session.close();
        }
    }



    @Test
    public  void test10() throws IOException {


        SqlSessionFactory factory = getSqlSessionFactory();

        SqlSession  session = factory.openSession();

        try{
            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);

            Map<Integer, Employee> emps = mapper.getEmpsByLastNameLike("%i%");

            System.out.println(emps);

            session.commit();
        }finally {
            session.close();
        }
    }



    @Test
    public  void test11() throws IOException {


        SqlSessionFactory factory = getSqlSessionFactory();

        SqlSession  session = factory.openSession();

        try{
            EmployeeMapperPlus mapper = session.getMapper(EmployeeMapperPlus.class);

            Employee emp = mapper.getEmpById(1);

            System.out.println(emp);

            session.commit();
        }finally {
            session.close();
        }
    }


    /**
     *  测试设置及联属性 查询
     * @throws IOException
     */
    @Test
    public  void test12() throws IOException {


        SqlSessionFactory factory = getSqlSessionFactory();

        SqlSession  session = factory.openSession();

        try{
            EmployeeMapperPlus mapper = session.getMapper(EmployeeMapperPlus.class);

            Employee employee = mapper.getEmpAndDept(1);
            System.out.println(employee);

            session.commit();
        }finally {
            session.close();
        }
    }



    @Test
    public  void test13() throws IOException {


        SqlSessionFactory factory = getSqlSessionFactory();

        SqlSession  session = factory.openSession();

        try{
            EmployeeMapperPlus mapper = session.getMapper(EmployeeMapperPlus.class);

            Employee employee = mapper.getEmpByIdStep(6);
            System.out.println(employee);

            session.commit();
        }finally {
            session.close();
        }
    }



    @Test
    public  void test14() throws IOException {


        SqlSessionFactory factory = getSqlSessionFactory();

        SqlSession  session = factory.openSession();

        try{
            DepartmentMapper mapper = session.getMapper(DepartmentMapper.class);

            Department plus = mapper.getDeptByIdPlus(2);
            System.out.println(plus);
            System.out.println(plus.getEmps());

            session.commit();
        }finally {
            session.close();
        }
    }



    @Test
    public  void test15() throws IOException {


        SqlSessionFactory factory = getSqlSessionFactory();

        SqlSession  session = factory.openSession();

        try{
            DepartmentMapper mapper = session.getMapper(DepartmentMapper.class);

            Department plus = mapper.getDeptByIdStep(2);
            System.out.println(plus);
            System.out.println(plus.getEmps());

            session.commit();
        }finally {
            session.close();
        }
    }
}
