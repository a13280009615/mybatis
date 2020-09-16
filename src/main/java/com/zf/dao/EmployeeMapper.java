package com.zf.dao;

import com.zf.bean.Employee;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhengfan
 * @create 2020-09-16 -0:11
 */
public interface EmployeeMapper {

    public Employee getEmpById(Integer id);
    public  void  addEmp(Employee employee);
    public  void updateEmp(Employee employee);
    public  boolean delEmp(Integer id);
    Employee  getEmpByIdAndLastName(@Param("id") Integer id,@Param("lastName") String lastName);
}
