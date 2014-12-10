package com.yuanhe.eighth.dao.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yuanhe.eighth.dao.BaseDAO;
import com.yuanhe.eighth.dao.UserAccessRecordDAO;
import com.yuanhe.eighth.domain.UserAccessRecord;
@Service("UserAccessRecordDAO")
public class UserAccessRecordDAOImpl extends BaseDAO implements UserAccessRecordDAO {

	@Override
	public List<UserAccessRecord> getListByUserAccessRecord() {
		return null;
	}

}
