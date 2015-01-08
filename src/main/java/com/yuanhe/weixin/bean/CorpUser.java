package com.yuanhe.weixin.bean;

import java.util.List;

/**
 * Created by dam on 2014/12/18.
 */
public class CorpUser {

    private String userid;
    private String name;
    private String position;
    private String mobile;
    private String email;
    private String weixinid;
    private String avatar;
    private int status;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeixinid() {
        return weixinid;
    }

    public void setWeixinid(String weixinid) {
        this.weixinid = weixinid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    //private List<Department> department;

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

//    public List<Department> getDepartment() {
//        return department;
//    }
//
//    public void setDepartment(List<Department> department) {
//        this.department = department;
//    }
}
