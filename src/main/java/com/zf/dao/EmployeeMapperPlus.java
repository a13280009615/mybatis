package com.zf.dao;

import com.zf.bean.Employee;

import java.util.List;

/**
 * @author zhengfan
 * @create 2020-09-17 下午2:08
 */
public interface EmployeeMapperPlus {

    public Employee getEmpById(Integer id);

    public  Employee getEmpAndDept(Integer id);

    public Employee getEmpByIdStep(Integer id);

    public List<Employee> getEmpByDeptId(Integer id);

}
