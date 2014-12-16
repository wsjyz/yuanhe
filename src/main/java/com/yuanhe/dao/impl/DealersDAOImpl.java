package com.yuanhe.dao.impl;

import com.yuanhe.domain.Dealers;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.yuanhe.dao.BaseDAO;
import com.yuanhe.dao.DealersDAO;


import javax.swing.tree.TreePath;
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
    public int saveDelearBatch(List<Dealers> dealersList) {
        List<String> sqls = new ArrayList<String>();
        for(Dealers dealers:dealersList){
            StringBuilder topSql = new StringBuilder("insert into ").append(TABLE_NAME)
                    .append(" (dealers_id,dealers_name,dealers_mobile,dealers_status,dealers_type," +
                            "dealers_qr_code,dealers_qr_url,opt_time) values('")
                    .append(dealers.getDealersId() + "','").append(dealers.getDealersName() + "',")
                    .append(dealers.getDealersMobile() + "','").append(dealers.getDealersStatus() + "',")
                    .append(dealers.getDealersQrCode() + "','")
                    .append(dealers.getDealersQrUrl() + ",'").append(dealers.getOptTime() + "')");
            System.out.println(topSql.toString());
            sqls.add(topSql.toString());
        }

        int[] results =  getJdbcTemplate().batchUpdate(sqls.toArray(new String[sqls.size()]));
        return results.length;
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
}
