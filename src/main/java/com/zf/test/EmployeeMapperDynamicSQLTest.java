package com.zf.test;

import com.zf.bean.Department;
import com.zf.bean.Employee;
import com.zf.dao.EmployeeMapperDynamicSQL;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhengfan
 * @create 2020-09-19 上午10:56
 */
public class EmployeeMapperDynamicSQLTest {


    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);

        return new SqlSessionFactoryBuilder().build(inputStream);
    }


    @Test
    public   void  test() throws IOException {

        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();

            try{

                EmployeeMapperDynamicSQL mapper = sqlSession.getMapper(EmployeeMapperDynamicSQL.class);
                Employee employee = new Employee(null, "%i%", null, "kit@163.com");
                List<Employee> emps = mapper.getEmpsByConditionTrim(employee);
                emps.forEach((item)->{
                    System.out.println(item);
                });
//SQL: select * from tbl_employee      where  and last_name like ? and email= ?
            }finally {
                sqlSession.close();
            }

        }



        /**
         *  测试 choose
         * */
    @Test
    public   void  test2() throws IOException {

        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{

            EmployeeMapperDynamicSQL mapper = sqlSession.getMapper(EmployeeMapperDynamicSQL.class);
            Employee employee = new Employee(null, "i", null, null);
            List<Employee> emps = mapper.getEmpsByConditionChoose(employee);
            emps.forEach((item)->{
                System.out.println(item);
            });
//SQL: select * from tbl_employee      where  and last_name like ? and email= ?
        }finally {
            sqlSession.close();
        }

    }


    /***
     *  测试 forEach
     * @throws IOException
     */

    @Test
    public   void  test3() throws IOException {

        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{

            EmployeeMapperDynamicSQL mapper = sqlSession.getMapper(EmployeeMapperDynamicSQL.class);

            List<Employee> emps = mapper.getEmpsByConditionForEach(Arrays.asList(1, 2, 3, 6));

            emps.forEach((item)->{
                System.out.println(item);
            });

        }finally {
            sqlSession.close();
        }

    }


    /***
     *  测试批量保存
     * @throws IOException
     */
    @Test
    public   void  test4() throws IOException {

        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{

            EmployeeMapperDynamicSQL mapper = sqlSession.getMapper(EmployeeMapperDynamicSQL.class);

            List<Employee> employees  = new ArrayList<>();
            employees.add(new Employee(null,"switch","1","switch@163.com",new Department(1)));
            employees.add(new Employee(null,"switch2","1","switch2@163.com",new Department(2)));
            employees.add(new Employee(null,"switch3","1","switch3@163.com",new Department(1)));
            employees.add(new Employee(null,"switch4","1","switch4@163.com",new Department(2)));
            employees.add(new Employee(null,"switch5","1","switch5@163.com",new Department(1)));
            mapper.addEmps(employees);

           sqlSession.commit();
        }finally {
            sqlSession.close();
        }

    }

}
