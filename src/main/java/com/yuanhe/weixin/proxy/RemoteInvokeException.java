package com.yuanhe.weixin.proxy;

import java.io.IOException;

/**
 * Created by dam on 2014/6/30.
 */
public class RemoteInvokeException extends IOException {

    public RemoteInvokeException(){

    }

    public RemoteInvokeException(String message){
        super(message);
    }
}
