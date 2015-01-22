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
import java.util.Arrays;
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
    public Dealers findDealerByUnionId(String unionId) {
        StringBuilder sql = new StringBuilder("SELECT * FROM ");
        sql.append(TABLE_NAME);
        sql.append(" where union_id = ?");
        List<Dealers> dealersList = getJdbcTemplate().query(sql.toString(),new Object[]{unionId},new DealerRowMapper());
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
//        StringBuilder delSql = new StringBuilder("delete from ");
//        delSql.append(TABLE_NAME);
//        getJdbcTemplate().execute(delSql.toString());
        List<String> sqlList = new ArrayList<String>();
        for(Dealers dealers:dealersList){
            if(dealers != null){
                StringBuilder sql = new StringBuilder("SELECT * FROM ");
                sql.append(TABLE_NAME);
                sql.append(" where dealers_id = ? ");
                List<Dealers> list = getJdbcTemplate().query(sql.toString(),new Object[]{dealers.getDealersId()},new DealerRowMapper());
                if(list != null && list.size() > 0){
                    String batchSql = "update "+TABLE_NAME+" set dealers_name = '"+dealers.getDealersName()+"'," +
                            "dealers_mobile='"+dealers.getDealersMobile()+"',dealers_status='"+dealers.getDealersStatus()+"'," +
                            "opt_time='"+dealers.getOptTime()+"',wei_xinId='"+dealers.getWeixinId()+"' where dealers_id='"+dealers.getDealersId()+"'";
                    sqlList.add(batchSql);

                }else{
                    String batchSql = "insert into "+TABLE_NAME+" (dealers_id,dealers_name,dealers_mobile,dealers_status,dealers_type," +
                            "dealers_qr_code,dealers_qr_url,opt_time,wei_xinId) values('"+dealers.getDealersId()+"'" +
                            ",'"+dealers.getDealersName()+"','"+dealers.getDealersMobile()+"'," +
                            "'"+dealers.getDealersStatus()+"','"+dealers.getDealersType()+"','"+dealers.getDealersQrCode()+"'" +
                            ",'"+dealers.getDealersQrUrl()+"','"+dealers.getOptTime()+"','"+dealers.getWeixinId()+"');";
                    sqlList.add(batchSql);
                }
            }
        }
        int[] result = getJdbcTemplate().batchUpdate((String[])sqlList.toArray(new String[sqlList.size()]));


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
            dealers.setWeixinId(rs.getString("wei_xinId"));
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

	@Override
	public Dealers getDealersById(String dealersId) {
		StringBuilder sql = new StringBuilder(
				"select * from t_yuanhe_dealers where dealers_id ='"+dealersId+"'");
		List<Dealers> dealersList = getJdbcTemplate().query(sql.toString(),
				new String[] {}, new DealerRowMapper());
		if (CollectionUtils.isEmpty(dealersList)) {
			return null;
		}
		return dealersList.get(0);
	}
}
