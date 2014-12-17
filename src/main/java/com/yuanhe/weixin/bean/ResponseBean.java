package com.yuanhe.weixin.bean;

/**
 * Created by dam on 2014/12/16.
 */
public class ResponseBean {

    private int errorcode;
    private String errmsg;

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
