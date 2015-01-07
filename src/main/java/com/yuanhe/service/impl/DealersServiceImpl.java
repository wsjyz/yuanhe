package com.yuanhe.service.impl;

import com.yuanhe.dao.DealersDAO;
import com.yuanhe.domain.Dealers;
import com.yuanhe.weixin.corp.UserService;
import com.yuanhe.weixin.bean.CorpUser;
import com.yuanhe.weixin.bean.CorpUserResponse;
import com.yuanhe.weixin.proxy.WeixinCorpRemoteProxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanhe.service.DealersService;

import java.util.ArrayList;
import java.util.List;

@Service("DealersService")
public class DealersServiceImpl implements DealersService {

    @Autowired
    private DealersDAO dealersDAO;

    @Override
    public List<Dealers> findDealerList(long start, long end) {
        return dealersDAO.findDealerList(start,end);
    }

    @Override
    public int saveDelearBatch() {
        List<Dealers> dealersList = new ArrayList<Dealers>();
        UserService userService = new WeixinCorpRemoteProxy<UserService>(UserService.class).getProxy();
        CorpUserResponse corpUserResponse = userService.simplelist(1,1,0);
        for(CorpUser corpUser:corpUserResponse.getUserlist()){
            Dealers dealers = new Dealers();
            dealers.setDealersId(corpUser.getUserid()+"");
            dealers.setDealersName(corpUser.getName());
            dealersList.add(dealers);
        }
        return dealersDAO.saveDelearBatch(dealersList);
    }

    @Override
    public Dealers findDealerByMobileAndName(String mobile, String name) {
        return dealersDAO.findDealerByMobileAndName(mobile,name);
    }

    @Override
    public void updateDealerUnionId(Dealers dealers) {
        dealersDAO.updateDealerUnionId(dealers);
    }

    @Override
    public int findDealerCount() {

        return dealersDAO.findDealerCount();
    }

	@Override
	public String getYuanHeDealersId() {
		return dealersDAO.getYuanHeDealersId();
	}


}
