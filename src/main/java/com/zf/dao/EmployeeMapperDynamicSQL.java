package com.zf.dao;

import com.zf.bean.Employee;

import java.util.List;

/**
 * @author zhengfan
 * @create 2020-09-17 下午7:22
 */
public interface EmployeeMapperDynamicSQL {

    public List<Employee> getEmpsByConditionIf(Employee employee);
}
