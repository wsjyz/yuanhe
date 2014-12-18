package com.yuanhe.weixin.bean;

import java.util.List;

/**
 * Created by dam on 2014/12/18.
 */
public class CorpUserResponse extends ResponseBean {

    private List<CorpUser> userlist;

    public List<CorpUser> getUserlist() {
        return userlist;
    }

    public void setUserlist(List<CorpUser> userlist) {
        this.userlist = userlist;
    }
}
