package com.yuanhe.domain;

import com.yuanhe.domain.annotation.Column;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dam on 2014/6/30.
 */
public class BaseDomain implements java.io.Serializable {

    @Column(name="opt_time",length = 20,comment = "操作时间yyyy-MM-dd HH:mm:ss")
    String optTime;//操作时间

    public String getOptTime() {
        if(optTime == null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            optTime = sdf.format(new Date());
        }
        return optTime;
    }

    public void setOptTime(String optTime) {
        this.optTime = optTime;
    }
}
