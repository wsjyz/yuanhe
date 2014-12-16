package com.yuanhe.dao;

import com.yuanhe.domain.Dealers;

import java.util.List;

public interface DealersDAO {

    List<Dealers> findDealerList(long start,long end);

    int saveDelearBatch(List<Dealers> dealersList);

    int findDealerCount();
}
