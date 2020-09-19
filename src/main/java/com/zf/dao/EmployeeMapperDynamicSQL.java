package com.zf.dao;

import com.zf.bean.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhengfan
 * @create 2020-09-17 下午7:22
 */
public interface EmployeeMapperDynamicSQL {

    public List<Employee> getEmpsByConditionIf(Employee employee);

    public List<Employee> getEmpsByConditionTrim(Employee employee);

    public List<Employee> getEmpsByConditionChoose(Employee employee);

    public  void  updateEmp(Employee employee);

    public  List<Employee>  getEmpsByConditionForEach( List<Integer>  ids);

    public void addEmps(@Param("emps") List<Employee> emps);
}
