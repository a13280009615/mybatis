package com.zf.dao;

import com.zf.bean.Employee;

/**
 * @author zhengfan
 * @create 2020-09-16 -0:11
 */
public interface EmployeeMapper {

    public Employee getEmpById(Integer id);
    public  void  addEmp(Employee employee);
    public  void updateEmp(Employee employee);
    public  boolean delEmp(Integer id);
}
