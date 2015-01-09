package com.yuanhe.service;

import com.yuanhe.domain.Dealers;

import java.util.List;

public interface DealersService {

    List<Dealers> findDealerList(long start,long end);

    int saveDelearBatch();

	int findDealerCount();

	String getYuanHeDealersId();

    Dealers findDealerByMobileAndName(String mobile,String name);

    void updateDealerUnionId(Dealers dealers);
    Dealers getDealersByID(String dealersId);

}
