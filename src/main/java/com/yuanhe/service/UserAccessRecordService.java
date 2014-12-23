package com.yuanhe.service;

import java.util.List;

import com.yuanhe.domain.UserAccessRecord;

public interface UserAccessRecordService {
	List<UserAccessRecord> getListByUserAccessRecord();

	/**
	 * 根据用户UnionId获取经销商ID
	 */
	String getDealersIdByUnionId(String unionId);
}
