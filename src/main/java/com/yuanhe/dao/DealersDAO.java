package com.yuanhe.dao;

import com.yuanhe.domain.Dealers;

import java.util.List;

public interface DealersDAO {

    List<Dealers> findDealerList(long start,long end);

    int saveDelearBatch(List<Dealers> dealersList);

    Dealers findDealerByMobileAndName(String mobile,String name);

    void updateDealerUnionId(Dealers dealers);

    Dealers findDealerByUnionId(String unionId);

    int findDealerCount();
    String getYuanHeDealersId();

	Dealers getDealersById(String dealersId);
}
