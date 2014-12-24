package com.yuanhe.weixin.bean;

/**
 * Created by dam on 2014/12/24.
 */
public class TextMessage extends BaseMessage {
    // 消息内容
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}