package com.yuanhe.dao.impl;

import com.yuanhe.domain.UserAccessRecord;
import org.springframework.stereotype.Service;

import com.yuanhe.dao.BaseDAO;
import com.yuanhe.dao.UserAccessRecordDAO;

import java.util.List;

@Service("UserAccessRecordDAO")
public class UserAccessRecordDAOImpl extends BaseDAO implements UserAccessRecordDAO {

    @Override
    public List<UserAccessRecord> getListByUserAccessRecord() {
        return null;
    }
}
