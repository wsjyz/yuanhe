package com.yuanhe.service.impl;

import com.yuanhe.dao.DealersDAO;
import com.yuanhe.domain.Dealers;
import com.yuanhe.weixin.DepartmentService;
import com.yuanhe.weixin.bean.Department;
import com.yuanhe.weixin.bean.DepartmentResponse;
import com.yuanhe.weixin.proxy.RemoteProxy;
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
        DepartmentService departmentService = new RemoteProxy<DepartmentService>(DepartmentService.class).getProxy();
        DepartmentResponse departmentResponse = departmentService.list();
        for(Department department:departmentResponse.getDepartment()){
            Dealers dealers = new Dealers();
            dealers.setDealersId(department.getId()+"");
            dealers.setDealersName(department.getName());
            dealers.setParentId(department.getParentid()+"");
            dealersList.add(dealers);
        }
        return dealersDAO.saveDelearBatch(dealersList);
    }

    @Override
    public int findDealerCount() {

        return dealersDAO.findDealerCount();
    }
}
