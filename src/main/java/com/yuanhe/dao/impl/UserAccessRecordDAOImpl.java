package com.yuanhe.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.yuanhe.dao.BaseDAO;
import com.yuanhe.dao.UserAccessRecordDAO;
import com.yuanhe.domain.UserAccessRecord;

@Service("UserAccessRecordDAO")
public class UserAccessRecordDAOImpl extends BaseDAO implements UserAccessRecordDAO {

    @Override
    public List<UserAccessRecord> getListByUserAccessRecord() {
    	StringBuilder sql = new StringBuilder(
				"select * from t_yuanhe_user_access_record ");
		List<UserAccessRecord> userRecordList = getJdbcTemplate().query(sql.toString(),
				new String[] {}, new UserRecordRowMapper());
		if (CollectionUtils.isEmpty(userRecordList)) {
			return null;
		}
		return userRecordList;
    }

	@Override
	public String getDealersIdByUnionId(String unionId) {
		Calendar cal=Calendar.getInstance();
		Date startDate = cal.getTime();
		Date endDate = cal.getTime();
		StringBuilder sql = new StringBuilder(
				"select * from t_yuanhe_user_access_record where visiter_union_id ='"
						+ unionId + "' and visit_time BETWEEN '" + startDate
						+ "' and '" + endDate + "' order by visit_time desc ");
		List<UserAccessRecord> userRecordList = getJdbcTemplate().query(sql.toString(),
				new String[] {}, new UserRecordRowMapper());
		if (CollectionUtils.isEmpty(userRecordList)) {
			return null;
		}
		return userRecordList.get(0).getVisiterDealersId();
	}
	
	public class UserRecordRowMapper implements RowMapper<UserAccessRecord> {
		@Override
		public UserAccessRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserAccessRecord userAccessRecord = new UserAccessRecord();
			userAccessRecord.setRecordId(rs.getString("record_id"));
			userAccessRecord.setVisiterUnionId(rs.getString("visiter_union_id"));
			userAccessRecord.setVisiterDealersId(rs.getString("visiter_dealers_id"));
			userAccessRecord.setVisitTime(rs.getString("visit_time"));
			userAccessRecord.setAccessUrlTitle(rs.getString("access_url_title"));
			userAccessRecord.setAccessUrl(rs.getString("access_url"));
			return userAccessRecord;
		}
	}

}
