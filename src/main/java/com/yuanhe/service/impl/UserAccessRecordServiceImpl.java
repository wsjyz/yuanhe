package com.yuanhe.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanhe.dao.UserAccessRecordDAO;
import com.yuanhe.domain.UserAccessRecord;
import com.yuanhe.service.UserAccessRecordService;

@Service("UserAccessRecordService")
public class UserAccessRecordServiceImpl implements UserAccessRecordService {
	@Autowired
	UserAccessRecordDAO userAccessRecordDao;

	@Override
	public String getDealersIdByUnionId(String unionId) {
		return userAccessRecordDao.getDealersIdByUnionId(unionId);
	}

	@Override
	public List<UserAccessRecord> getListByUserAccessRecord() {
		return userAccessRecordDao.getListByUserAccessRecord();
	}

}
