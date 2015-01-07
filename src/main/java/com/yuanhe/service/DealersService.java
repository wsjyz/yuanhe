package com.yuanhe.service;

import com.yuanhe.domain.Dealers;

import java.util.List;

public interface DealersService {

    List<Dealers> findDealerList(long start,long end);

    int saveDelearBatch();

    Dealers findDealerByMobileAndName(String mobile,String name);

    void updateDealerUnionId(Dealers dealers);

    int findDealerCount();
}
