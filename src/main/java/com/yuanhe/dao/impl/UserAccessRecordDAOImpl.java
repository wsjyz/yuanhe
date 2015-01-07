package com.yuanhe.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.yuanhe.domain.PromoteLinks;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.yuanhe.dao.BaseDAO;
import com.yuanhe.dao.UserAccessRecordDAO;
import com.yuanhe.domain.UserAccessRecord;

@Service("UserAccessRecordDAO")
public class UserAccessRecordDAOImpl extends BaseDAO implements UserAccessRecordDAO {

    private final static String TABLE_NAME = "t_yuanhe_user_access_record";

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

    @Override
    public UserAccessRecord saveAccessRecord(final UserAccessRecord userAccessRecord) {
        StringBuilder insertSql = new StringBuilder("insert into ").append(TABLE_NAME)
                .append(" (record_id,visiter_union_id,visiter_dealers_id," +
                        "visit_time,access_url_title,access_url,opt_time) values(?,?,?,?,?,?,?);");
        getJdbcTemplate().update(insertSql.toString(),new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1,userAccessRecord.getRecordId());
                ps.setString(2,userAccessRecord.getVisiterUnionId());
                ps.setString(3,userAccessRecord.getVisiterDealersId());
                ps.setString(4,userAccessRecord.getVisitTime());
                ps.setString(5,userAccessRecord.getAccessUrlTitle());
                ps.setString(6,userAccessRecord.getAccessUrl());
                ps.setString(7,userAccessRecord.getOptTime());
            }
        });
        return userAccessRecord;
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
