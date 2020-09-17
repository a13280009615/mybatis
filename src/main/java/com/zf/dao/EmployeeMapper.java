package com.zf.dao;

import com.zf.bean.Employee;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author zhengfan
 * @create 2020-09-16 -0:11
 */
public interface EmployeeMapper {


    // @MapKey  告诉 mybatis  封装这个map的时候作为map 的key
    @MapKey("id")
    public  Map<Integer,Employee> getEmpsByLastNameLike(String lastName);

    public  Map<String,Object>  getEmployeeById(Integer id);

    public List<Employee> getEmpByLastNameLike(String lastName);

    public  Employee getByMap(Map<String,Object> map);
    public Employee getEmpById(Integer id);
    public  void  addEmp(Employee employee);
    public  void updateEmp(Employee employee);
    public  boolean delEmp(Integer id);
    Employee  getEmpByIdAndLastName(@Param("id") Integer id,@Param("lastName") String lastName);
}
