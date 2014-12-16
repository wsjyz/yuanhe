package com.yuanhe.dao.impl;

import com.yuanhe.domain.UserOrder;
import org.springframework.stereotype.Service;

import com.yuanhe.dao.BaseDAO;
import com.yuanhe.dao.UserOrderDAO;

import java.util.List;

@Service("UserOrderDAO")
public class UserOrderDAOImpl extends BaseDAO implements UserOrderDAO {

    @Override
    public List<UserOrder> getOrderList() {
        return null;
    }

    @Override
    public void saveOrderList(List<UserOrder> userOrderList) {

    }

    @Override
    public void updateOrder(UserOrder userOrder) {

    }
}
