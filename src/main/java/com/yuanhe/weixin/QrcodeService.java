package com.yuanhe.weixin;

import com.yuanhe.weixin.bean.QrcodeParams;
import com.yuanhe.weixin.bean.QrcodeResponse;
import com.yuanhe.weixin.proxy.RemoteMethod;

/**
 * Created by dam on 2014/12/23.
 */
public interface QrcodeService {

    QrcodeResponse create(QrcodeParams qrcodeParams);

}
