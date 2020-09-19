package com.zf.bean;

import java.util.List;

/**
 * @author zhengfan
 * @create 2020-09-17 下午2:21
 */
public class Department {

    private  Integer id;

    private  String departmentName;

    private List<Employee> emps;


    public Department() {
    }

    public Department(Integer id) {
        this.id = id;
    }

    public List<Employee> getEmps() {
        return emps;
    }

    public void setEmps(List<Employee> emps) {
        this.emps = emps;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }


    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", departmentName='" + departmentName + '\'' +
                ", emps=" + emps +
                '}';
    }
}
