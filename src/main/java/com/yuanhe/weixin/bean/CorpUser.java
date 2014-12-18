package com.yuanhe.weixin.bean;

import java.util.List;

/**
 * Created by dam on 2014/12/18.
 */
public class CorpUser {

    private String userid;
    private String name;
    private List<Department> department;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Department> getDepartment() {
        return department;
    }

    public void setDepartment(List<Department> department) {
        this.department = department;
    }
}
