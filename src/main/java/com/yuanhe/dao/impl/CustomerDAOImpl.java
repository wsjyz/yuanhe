package com.yuanhe.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.yuanhe.dao.BaseDAO;
import com.yuanhe.dao.CustomerDAO;
import com.yuanhe.domain.Customer;

@Service("CustomerDAO")
public class CustomerDAOImpl extends BaseDAO implements CustomerDAO {

	@Override
	public Customer getCustomerById(String userUnionId) {
		StringBuilder sql = new StringBuilder(
				"select * from t_yuanhe_customer where customer_union_id ='"
						+ userUnionId + "'");
		List<Customer> customerList = getJdbcTemplate().query(sql.toString(),
				new String[] {}, new CustomerRowMapper());
		if (CollectionUtils.isEmpty(customerList)) {
			return null;
		}
		return customerList.get(0);
	}

	public class CustomerRowMapper implements RowMapper<Customer> {

		@Override
		public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
			Customer customer = new Customer();
			customer.setCustomerUnionId(rs.getString("customer_union_id"));
			customer.setCustomerNick(rs.getString("customer_nick"));
			customer.setCustomerPic(rs.getString("customer_pic"));
			customer.setCustomerProvice(rs.getString("customer_provice"));
			customer.setCustomerDealers(rs.getString("customer_dealers"));
			customer.setOpenId(rs.getString("open_id"));
			customer.setOuathOpenId(rs.getString("ouath_open_id"));
			customer.setCteateTime(rs.getString("create_time"));
			return customer;
		}
	}

	@Override
	public void saveCustomer(final Customer customer) {
		StringBuilder sql = new StringBuilder(
				"INSERT INTO t_yuanhe_customer (customer_union_id, customer_nick, customer_pic, customer_provice, customer_dealers, open_id,ouath_open_id,create_time) ");
		sql.append("VALUES (?,?,?,?,?,?,?,?)");
		getJdbcTemplate().update(sql.toString(), new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, customer.getCustomerUnionId());
				ps.setString(2, customer.getCustomerNick());
				ps.setString(3, customer.getCustomerPic());
				ps.setString(4, customer.getCustomerProvice());
				ps.setString(5, customer.getCustomerDealers());
				ps.setString(6, customer.getOpenId());
				ps.setString(7, customer.getOuathOpenId());
				ps.setString(8, customer.getCteateTime());

			}
		});
	}

	@Override
	public void updateCustomer(Customer customer) {

		StringBuilder sql = new StringBuilder("update t_yuanhe_customer set ");
		if (StringUtils.isNotBlank(customer.getCustomerNick())) {
			sql.append("customer_nick='" + customer.getCustomerNick() + "',");
		}
		if (StringUtils.isNotBlank(customer.getCustomerPic())) {
			sql.append("customer_pic='" + customer.getCustomerPic() + "',");
		}
		if (StringUtils.isNotBlank(customer.getCustomerProvice())) {
			sql.append("customer_provice='" + customer.getCustomerProvice()
					+ "',");
		}
		if (StringUtils.isNotBlank(customer.getCustomerDealers())) {
			sql.append("customer_dealers='" + customer.getCustomerDealers()
					+ "',");
		}
		if (StringUtils.isNotBlank(customer.getOpenId())) {
			sql.append("open_id='" + customer.getOpenId() + "',");
		}
		if (StringUtils.isNotEmpty(customer.getOuathOpenId())) {
			sql.append("ouath_open_id='" + customer.getOuathOpenId() + "',");

		}
		if (sql.lastIndexOf(",") + 1 == sql.length()) {
			sql.delete(sql.lastIndexOf(","), sql.length());
		}
		sql.append(" where customer_union_id='" + customer.getCustomerUnionId()
				+ "'");
		getJdbcTemplate().update(sql.toString());

	}

	@Override
	public int findCustomerCount(String dealersName) {
		StringBuilder countSql = new StringBuilder(
				"select count(*) from t_yuanhe_customer  cu left join t_yuanhe_dealers dealer "
						+ "on cu.customer_dealers=dealer.dealers_id where 1=1 ");
		if (StringUtils.isNotEmpty(dealersName)) {
			countSql.append(" and dealer.dealers_name ='" + dealersName + "'");
		}
		return getJdbcTemplate().queryForObject(countSql.toString(),
				Integer.class);
	}

	@Override
	public List<Customer> findCustomerList(int getiDisplayStart, int end,
			String dealersName) {
		StringBuilder sql = new StringBuilder(
				"select cu.* from t_yuanhe_customer  cu left join t_yuanhe_dealers dealer "
						+ "on cu.customer_dealers=dealer.dealers_id where 1=1 ");
		if (StringUtils.isNotEmpty(dealersName)) {
			sql.append(" and dealer.dealers_name ='" + dealersName + "'");
		}
		sql.append(" LIMIT ?,?");
		return getJdbcTemplate()
				.query(sql.toString(), new Object[] { getiDisplayStart, end },
						new CustomerRowMapper());
	}

}
