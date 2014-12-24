package com.yuanhe.weixin.bean;

/**
 * Created by dam on 2014/12/23.
 */
public class QrcodeResponse extends ResponseBean {

    private String ticket;
    private String url;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
