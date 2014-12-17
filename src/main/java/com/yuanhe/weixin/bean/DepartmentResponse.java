package com.yuanhe.weixin.bean;

import java.util.List;

/**
 * Created by dam on 2014/12/17.
 */
public class DepartmentResponse extends ResponseBean{

    private List<Department> department;

    public List<Department> getDepartment() {
        return department;
    }

    public void setDepartment(List<Department> department) {
        this.department = department;
    }
}
