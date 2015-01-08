package com.yuanhe.domain;

import com.yuanhe.domain.annotation.Column;
import com.yuanhe.domain.annotation.Table;

@Table(name = "t_yuanhe_customer", comment = "顾客表")
public class Customer extends BaseDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "customer_union_id", pk = true, comment = "顾客union_id", length = 32)
	private String customerUnionId;
	@Column(name = "customer_nick", comment = "顾客昵称", length = 50)
	private String customerNick;
	@Column(name = "customer_pic", comment = "顾客头像", length = 200)
	private String customerPic;
	@Column(name = "customer_provice", comment = "省份", length = 50)
	private String customerProvice;
	@Column(name = "customer_dealers", comment = "顾客所属会员", length = 32)
	private String customerDealers;
	@Column(name = "open_id", comment = "商城openID", length = 32)
	private String openId;
	@Column(name = "ouath_open_id", comment = "OuathOpenID", length = 32)
	private String ouathOpenId;
	@Column(name = "create_time", comment = "create_time", length = 32)
	private String cteateTime;

	public void setCteateTime(String cteateTime) {
		this.cteateTime = cteateTime;
	}

	public String getCteateTime() {
		return cteateTime;
	}

	public String getCustomerUnionId() {
		return customerUnionId;
	}

	public void setCustomerUnionId(String customerUnionId) {
		this.customerUnionId = customerUnionId;
	}

	public String getCustomerNick() {
		return customerNick;
	}

	public void setCustomerNick(String customerNick) {
		this.customerNick = customerNick;
	}

	public String getCustomerPic() {
		return customerPic;
	}

	public void setCustomerPic(String customerPic) {
		this.customerPic = customerPic;
	}

	public String getCustomerProvice() {
		return customerProvice;
	}

	public void setCustomerProvice(String customerProvice) {
		this.customerProvice = customerProvice;
	}

	public String getCustomerDealers() {
		return customerDealers;
	}

	public void setCustomerDealers(String customerDealers) {
		this.customerDealers = customerDealers;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getOuathOpenId() {
		return ouathOpenId;
	}

	public void setOuathOpenId(String ouathOpenId) {
		this.ouathOpenId = ouathOpenId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
