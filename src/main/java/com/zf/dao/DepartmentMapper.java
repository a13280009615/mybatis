package com.zf.dao;

import com.zf.bean.Department;

/**
 * @author zhengfan
 * @create 2020-09-17 下午5:48
 */
public interface DepartmentMapper {

    public Department getDeptById(Integer id);

    public  Department getDeptByIdPlus(Integer id);

    public Department getDeptByIdStep(Integer id);
}
