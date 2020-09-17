package com.zf.dao;

import com.zf.bean.Employee;

/**
 * @author zhengfan
 * @create 2020-09-17 下午2:08
 */
public interface EmployeeMapperPlus {

    public Employee getEmpById(Integer id);

    public  Employee getEmpAndDept(Integer id);

}
