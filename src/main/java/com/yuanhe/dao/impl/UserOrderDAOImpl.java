package com.yuanhe.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.yuanhe.dao.BaseDAO;
import com.yuanhe.dao.UserOrderDAO;
import com.yuanhe.domain.UserOrder;

@Service("UserOrderDAO")
public class UserOrderDAOImpl extends BaseDAO implements UserOrderDAO {
	@Override
	public List<UserOrder> getOrderList() {
		StringBuilder sql = new StringBuilder(
				"select * from t_yuanhe_user_order ");
		List<UserOrder> auntInfoList = getJdbcTemplate().query(sql.toString(),
				new String[] {}, new UserOrderRowMapper());
		return auntInfoList;

	}

	public class UserOrderRowMapper implements RowMapper<UserOrder> {

		@Override
		public UserOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserOrder userOrder = new UserOrder();
			userOrder.setOrderId(rs.getString("order_id"));
			userOrder.setPaymentUnionId(rs.getString("payment_union_id"));
			userOrder.setPaymentWeixinNick(rs.getString("payment_weixin_nick"));
			userOrder.setOrderStatus(rs.getString("order_status"));
			userOrder.setBelongsSalesCommission(rs
					.getString("belongs_sales_commission"));
			userOrder.setSalesCommissionMoney(rs
					.getString("sales_commission_money"));
			userOrder.setBelongsMembersCommission(rs
					.getString("belongs_members_commission"));
			userOrder.setMembersCommissionMoney(rs
					.getString("members_commission_money"));
			userOrder.setRealPay(rs.getString("real_pay"));
			userOrder.setPostageMoney(rs.getString("postage_money"));
			userOrder.setOrderMoney(rs.getString("order_money"));
			userOrder.setCommodityName(rs.getString("commodity_name"));
			userOrder.setCommodityPic(rs.getString("commodity_pic"));
			userOrder.setUpdateTime(rs.getString("update_time"));
			return userOrder;
		}
	}


	public class UserOrderByDealRowMapper implements RowMapper<UserOrder> {

		@Override
		public UserOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserOrder userOrder = new UserOrder();
			userOrder.setOrderId(rs.getString("order_id"));
			userOrder.setPaymentUnionId(rs.getString("payment_union_id"));
			userOrder.setPaymentWeixinNick(rs.getString("payment_weixin_nick"));
			userOrder.setOrderStatus(rs.getString("order_status"));
			userOrder.setBelongsSalesCommission(rs
					.getString("belongs_sales_commission"));
			userOrder.setSalesCommissionMoney(rs
					.getString("sales_commission_money"));
			userOrder.setBelongsMembersCommission(rs
					.getString("belongs_members_commission"));
			userOrder.setMembersCommissionMoney(rs
					.getString("members_commission_money"));
			userOrder.setRealPay(rs.getString("real_pay"));
			userOrder.setPostageMoney(rs.getString("postage_money"));
			userOrder.setOrderMoney(rs.getString("order_money"));
			userOrder.setCommodityName(rs.getString("commodity_name"));
			userOrder.setCommodityPic(rs.getString("commodity_pic"));
			userOrder.setUpdateTime(rs.getString("update_time"));
			userOrder.setBelongsMembersCommissionName(rs.getString("belongs_members_name"));
			userOrder.setBelongsSalesCommissionName(rs.getString("belongs_sales_name"));
			return userOrder;
		}
	}
	@Override
	public void saveOrder(final UserOrder userOrder) {
		final String orderId = userOrder.getOrderId();
		StringBuilder sql = new StringBuilder(
				"INSERT INTO t_yuanhe_user_order (order_id, payment_union_id, payment_weixin_nick, order_status, belongs_sales_commission, sales_commission_money,"
						+ "belongs_members_commission, members_commission_money, real_pay, postage_money, order_money,commodity_name,commodity_pic,update_time) ");
		sql.append("VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		getJdbcTemplate().update(sql.toString(), new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, orderId);
				ps.setString(2, userOrder.getPaymentUnionId());
				ps.setString(3, userOrder.getPaymentWeixinNick());
				ps.setString(4, userOrder.getOrderStatus());
				ps.setString(5, userOrder.getBelongsSalesCommission());
				ps.setString(6, userOrder.getSalesCommissionMoney());
				ps.setString(7, userOrder.getBelongsMembersCommission());
				ps.setString(8, userOrder.getMembersCommissionMoney());
				ps.setString(9, userOrder.getRealPay());
				ps.setString(10, userOrder.getPostageMoney());
				ps.setString(11, userOrder.getOrderMoney());
				ps.setString(12, userOrder.getCommodityName());
				ps.setString(13, userOrder.getCommodityPic());
				ps.setString(14, userOrder.getUpdateTime());
			}
		});
	}

	@Override
	public void updateOrder(UserOrder userOrder) {
		StringBuilder sql = new StringBuilder("update t_yuanhe_user_order set ");
		if (StringUtils.isNotBlank(userOrder.getPaymentUnionId())) {
			sql.append("payment_union_id='" + userOrder.getPaymentUnionId()
					+ "',");
		}
		if (StringUtils.isNotBlank(userOrder.getPaymentWeixinNick())) {
			sql.append("payment_weixin_nick='"
					+ userOrder.getPaymentWeixinNick() + "',");
		}
		if (StringUtils.isNotBlank(userOrder.getOrderStatus())) {
			sql.append("order_status='" + userOrder.getOrderStatus() + "',");
		}
		if (StringUtils.isNotBlank(userOrder.getBelongsSalesCommission())) {
			sql.append("belongs_sales_commission='"
					+ userOrder.getBelongsSalesCommission() + "',");
		}
		if (StringUtils.isNotBlank(userOrder.getSalesCommissionMoney())) {
			sql.append("sales_commission_money='"
					+ userOrder.getSalesCommissionMoney() + "',");
		}
		if (StringUtils.isNotEmpty(userOrder.getBelongsMembersCommission())) {
			sql.append("belongs_members_commission='"
					+ userOrder.getBelongsMembersCommission() + "',");

		}
		if (StringUtils.isNotEmpty(userOrder.getMembersCommissionMoney())) {
			sql.append("members_commission_money='"
					+ userOrder.getMembersCommissionMoney() + "',");

		}
		if (StringUtils.isNotEmpty(userOrder.getRealPay())) {
			sql.append("real_pay='" + userOrder.getRealPay() + "',");

		}
		if (StringUtils.isNotEmpty(userOrder.getPostageMoney())) {
			sql.append("postage_money='" + userOrder.getPostageMoney() + "',");

		}
		if (StringUtils.isNotEmpty(userOrder.getOrderMoney())) {
			sql.append("order_money='" + userOrder.getOrderMoney() + "',");
		}
		if (StringUtils.isNotEmpty(userOrder.getUpdateTime())) {
			sql.append("update_time='" + userOrder.getUpdateTime() + "',");
		}
		if (sql.lastIndexOf(",") + 1 == sql.length()) {
			sql.delete(sql.lastIndexOf(","), sql.length());
		}
		sql.append(" where order_id='" + userOrder.getOrderId() + "'");
		getJdbcTemplate().update(sql.toString());
	}

	@Override
	public List<UserOrder> findOrderList(int getiDisplayStart, int i,
			String getsSearch,String startTime,String endTime) {
		StringBuilder sql = new StringBuilder(
				"select uo.*,deal.dealers_name as belongs_sales_name,deal1.dealers_name as belongs_members_name from t_yuanhe_user_order  uo "
				+ "left join t_yuanhe_dealers deal on deal.dealers_id=uo.belongs_sales_commission "
				+ " left join t_yuanhe_dealers deal1 on deal1.dealers_id=uo.belongs_members_commission ");
		sql.append(" where  1=1 ");
		if (StringUtils.isNotEmpty(getsSearch)) {
			sql.append("  and (deal.dealers_name like '%"+getsSearch+"%' or deal1.dealers_name like '%"+getsSearch+"%')");
			
		}
		if (StringUtils.isNotEmpty(startTime)) {
			sql.append(" and uo.update_time>= '"+startTime+"'");
		}
		if (StringUtils.isNotEmpty(endTime)) {
			sql.append(" and  uo.update_time<= '"+endTime+"' ");
		}
		  sql.append(" order by  uo.order_id, uo.update_time desc LIMIT ?,?");
	   return getJdbcTemplate().query(sql.toString(),new Object[]{getiDisplayStart,i},new UserOrderByDealRowMapper());
	}

	@Override
	public int findOrderCount(String getsSearch,String startTime,String endTime) {
		StringBuilder sql = new StringBuilder(
				"select count(*) from t_yuanhe_user_order  uo "
				+ "left join t_yuanhe_dealers deal on deal.dealers_id=uo.belongs_sales_commission "
				+ " left join t_yuanhe_dealers deal1 on deal1.dealers_id=uo.belongs_members_commission ");
		sql.append(" where  1=1 ");
		if (StringUtils.isNotEmpty(getsSearch)) {
			sql.append("  and (deal.dealers_name like '%"+getsSearch+"%' or deal1.dealers_name like '%"+getsSearch+"%')");
			
		}
		if (StringUtils.isNotEmpty(startTime)) {
			sql.append(" and uo.update_time>= '"+startTime+"'");
		}
		if (StringUtils.isNotEmpty(endTime)) {
			sql.append(" and  uo.update_time<= '"+endTime+"' ");
		}
		 return getJdbcTemplate().queryForObject(sql.toString(), Integer.class);
	}

	@Override
	public List<UserOrder> findOrderListByDealId(String dealid,String year,String month) {
		StringBuilder sql = new StringBuilder(
				"select * from t_yuanhe_user_order  where 1=1 ");
		if (StringUtils.isNotEmpty(dealid)) {
			sql.append(" and  (belongs_sales_commission='"+dealid+"' ");
			sql.append(" or  belongs_members_commission='"+dealid+"') ");
		}
		if (StringUtils.isNotEmpty(year)) {
			sql.append(" and update_time like '%"+year+"-%'");
		}
		if (StringUtils.isNotEmpty(month)) {
			sql.append(" and update_time like '%-"+month+"-%'");
		}
		  sql.append(" order by order_id,update_time desc LIMIT 0,100");
		return getJdbcTemplate().query(sql.toString(),new Object[]{},new UserOrderRowMapper());
	}

	@Override
	public List<UserOrder> findOrderListByDealIdAndStartDay(String dealId,
			String startTime, String endTime) {
		StringBuilder sql = new StringBuilder(
				"select uo.*,deal.dealers_name as belongs_sales_name,deal1.dealers_name as belongs_members_name from t_yuanhe_user_order  uo "
				+ "left join t_yuanhe_dealers deal on deal.dealers_id=uo.belongs_sales_commission "
				+ " left join t_yuanhe_dealers deal1 on deal1.dealers_id=uo.belongs_members_commission ");
		sql.append(" where  1=1 ");
		if (StringUtils.isNotEmpty(startTime)) {
			sql.append(" and uo.update_time>= '"+startTime+"'");
		}
		if (StringUtils.isNotEmpty(endTime)) {
			sql.append(" and  uo.update_time<= '"+endTime+"' ");
		}
		  sql.append(" order by  uo.order_id, uo.update_time desc");
	   return getJdbcTemplate().query(sql.toString(),new UserOrderByDealRowMapper());
	}

}
