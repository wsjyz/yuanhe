package com.yuanhe.dao.impl;

import com.yuanhe.domain.PromoteLinks;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.yuanhe.dao.BaseDAO;
import com.yuanhe.dao.PromoteLinksDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("PromoteLinksDAO")
public class PromoteLinksDAOImpl extends BaseDAO implements PromoteLinksDAO {

    private final static String TABLE_NAME = "t_yuanhe_promoteLinks";

    @Override
    public PromoteLinks addPromoteLink(final PromoteLinks promoteLink) {
        StringBuilder insertSql = new StringBuilder("insert into ").append(TABLE_NAME)
                .append(" (link_id,link_title,link_url,opt_time) values(?,?,?,?);");
        getJdbcTemplate().update(insertSql.toString(),new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1,promoteLink.getLinkId());
                ps.setString(2,promoteLink.getLinkTitle());
                ps.setString(3,promoteLink.getLinkUrl());
                ps.setString(4,promoteLink.getOptTime());
            }
        });
        return promoteLink;
    }

    @Override
    public List<PromoteLinks> findPromoteLinkList(long start, long end) {
        StringBuilder sql = new StringBuilder("SELECT * FROM ");
        sql.append(TABLE_NAME);
        sql.append(" LIMIT ?,?");
        return getJdbcTemplate().query(sql.toString(),new Object[]{start,end},new PromoteLinkRowMapper());
    }

    @Override
    public int findPromoteLinkCount() {
        StringBuilder countSql = new StringBuilder(
                "select count(*) from "+TABLE_NAME);
        return getJdbcTemplate().queryForObject(countSql.toString(), Integer.class);
    }

    @Override
    public void deletePromoteLink(String promoteLinkId) {
        StringBuilder delSql = new StringBuilder("delete from ");
        delSql.append(TABLE_NAME)
        .append(" where link_id=?");
        getJdbcTemplate().update(delSql.toString(),new Object[]{promoteLinkId});
    }
    private class PromoteLinkRowMapper implements RowMapper<PromoteLinks> {

        @Override
        public PromoteLinks mapRow(ResultSet rs, int rowNum) throws SQLException {
            PromoteLinks promoteLinks = new PromoteLinks();
            promoteLinks.setLinkId(rs.getString("link_id"));
            promoteLinks.setLinkTitle(rs.getString("link_title"));
            promoteLinks.setLinkUrl(rs.getString("link_url"));
            promoteLinks.setOptTime(rs.getString("opt_time"));
            return promoteLinks;
        }
    }
}
