package com.yuanhe.dao.impl;

import com.yuanhe.domain.Customer;
import com.yuanhe.domain.Dealers;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.yuanhe.dao.BaseDAO;
import com.yuanhe.dao.DealersDAO;



import com.yuanhe.dao.impl.CustomerDAOImpl.CustomerRowMapper;

import javax.swing.tree.TreePath;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository("DealersDAO")
public class DealersDAOImpl extends BaseDAO implements DealersDAO {

    private final static String TABLE_NAME = "t_yuanhe_dealers";
    @Override
    public List<Dealers> findDealerList(long start, long end) {
        StringBuilder sql = new StringBuilder("SELECT * FROM ");
        sql.append(TABLE_NAME);
        sql.append(" LIMIT ?,?");
        return getJdbcTemplate().query(sql.toString(),new Object[]{start,end},new DealerRowMapper());
    }

    @Override
    public Dealers findDealerByMobileAndName(String mobile,String name) {
        StringBuilder sql = new StringBuilder("SELECT * FROM ");
        sql.append(TABLE_NAME);
        sql.append(" where dealers_mobile = ? and dealers_name = ?");
        List<Dealers> dealersList = getJdbcTemplate().query(sql.toString(),new Object[]{mobile,name},new DealerRowMapper());
        if(dealersList != null && dealersList.size() > 0){
            return dealersList.get(0);
        }else{
            return null;
        }
    }

    @Override
    public void updateDealerUnionId(Dealers dealers) {
        StringBuilder sql = new StringBuilder("UPDATE ");
        sql.append(TABLE_NAME);
        sql.append(" SET union_id = ? where dealers_id = ?");
        getJdbcTemplate().update(sql.toString(),new Object[]{dealers.getUnionId(),dealers.getDealersId()});
    }

    @Override
    public int saveDelearBatch(final List<Dealers> dealersList) {
        //删除原来的
        StringBuilder delSql = new StringBuilder("delete from ");
        delSql.append(TABLE_NAME);
        getJdbcTemplate().execute(delSql.toString());
        StringBuilder topSql = new StringBuilder("insert into ").append(TABLE_NAME)
                .append(" (dealers_id,dealers_name,dealers_mobile,dealers_status,dealers_type," +
                        "dealers_qr_code,dealers_qr_url,opt_time) values(?,?,?,?,?,?,?,?);");
        int[] result = getJdbcTemplate().batchUpdate(topSql.toString(),new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Dealers dealers = dealersList.get(i);
                ps.setString(1,dealers.getDealersId());
                ps.setString(2,dealers.getDealersName());
                ps.setString(3,dealers.getDealersMobile());
                ps.setString(4,dealers.getDealersStatus());
                ps.setString(5,dealers.getDealersType());
                ps.setString(6,dealers.getDealersQrCode());
                ps.setString(7,dealers.getDealersQrUrl());
                ps.setString(8,dealers.getOptTime());
            }

            @Override
            public int getBatchSize() {
                return dealersList.size();
            }
        });

        return result.length;

    }

    @Override
    public int findDealerCount() {
        StringBuilder countSql = new StringBuilder(
                "select count(*) from "+TABLE_NAME);
        return getJdbcTemplate().queryForObject(countSql.toString(), Integer.class);
    }

    private class DealerRowMapper implements RowMapper<Dealers> {

        @Override
        public Dealers mapRow(ResultSet rs, int rowNum) throws SQLException {
            Dealers dealers = new Dealers();
            dealers.setDealersId(rs.getString("dealers_id"));
            dealers.setDealersMobile(rs.getString("dealers_mobile"));
            dealers.setDealersName(rs.getString("dealers_name"));
            dealers.setDealersQrCode(rs.getString("dealers_qr_code"));
            dealers.setDealersQrUrl(rs.getString("dealers_qr_url"));
            dealers.setDealersType(rs.getString("dealers_type"));
            dealers.setOptTime(rs.getString("opt_time"));
            return dealers;
        }
    }

	@Override
	public String getYuanHeDealersId() {
		StringBuilder sql = new StringBuilder(
				"select * from t_yuanhe_dealers where dealers_name ='YUANHE'");
		List<Dealers> dealersList = getJdbcTemplate().query(sql.toString(),
				new String[] {}, new DealerRowMapper());
		if (CollectionUtils.isEmpty(dealersList)) {
			return null;
		}
		return dealersList.get(0).getDealersId();
	}
}
