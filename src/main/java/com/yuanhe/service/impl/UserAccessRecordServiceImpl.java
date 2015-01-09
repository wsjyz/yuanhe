package com.yuanhe.service.impl;

import java.sql.Date;
import java.util.List;

import com.yuanhe.domain.PromoteLinks;
import com.yuanhe.utils.StringUtil;

import org.apache.commons.lang3.StringUtils;
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

    public UserAccessRecord saveAccessRecord(UserAccessRecord userAccessRecord){
        if(StringUtils.isBlank(userAccessRecord.getRecordId())){
            userAccessRecord.setRecordId(StringUtil.genUUID());
            userAccessRecord.setVisitTime(Date.valueOf(userAccessRecord.getOptTime()));
        }
        return userAccessRecordDao.saveAccessRecord(userAccessRecord);
    }

}
