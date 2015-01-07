package com.yuanhe.weixin.corp;

import com.yuanhe.weixin.bean.ResponseBean;

/**
 * Created by dam on 2015/1/4.
 */
public class UserInfoResponse extends ResponseBean {

    private String UserId;
    private String DeviceId;

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
