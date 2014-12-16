package com.yuanhe.service.impl;

import com.yuanhe.dao.DealersDAO;
import com.yuanhe.domain.Dealers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanhe.service.DealersService;

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
    public int saveDelearBatch(List<Dealers> dealersList) {
        return dealersDAO.saveDelearBatch(dealersList);
    }

    @Override
    public int findDealerCount() {
        return 0;
    }
}
