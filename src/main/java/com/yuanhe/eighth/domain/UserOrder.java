package com.yuanhe.eighth.domain;

import com.yuanhe.eighth.domain.annotation.BaseDomain;
import com.yuanhe.eighth.domain.annotation.Column;
import com.yuanhe.eighth.domain.annotation.Table;

@Table(name = "t_yuanhe_user_order", comment = "订单表")
public class UserOrder extends BaseDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "order_id", comment = "订单ID", length = 32)
	private String orderId;
	@Column(name = "payment_union_id", comment = "付款人union_id", length = 3)
	private String paymentUnionId;
	@Column(name = "payment_weixin_nick", comment = "付款人微信昵称", length = 3)
	private String paymentWeixinNick;
	@Column(name = "order_status", comment = "订单状态(正常/退款)", length = 3)
	private String orderStatus;
	@Column(name = "belongs_sales_commission", comment = "销售佣金所属", length = 3)
	private String belongsSalesCommission;
	@Column(name = "sales_commission_money", comment = "销售佣金金额", length = 3)
	private String salesCommissionMoney;
	@Column(name = "belongs_members_commission", comment = "会员佣金所属", length = 3)
	private String belongsMembersCommission;
	@Column(name = "members_commission_money", comment = "会员佣金金额", length = 3)
	private String membersCommissionMoney;
	@Column(name = "real_pay", comment = "实付金额", length = 3)
	private String realPay;
	@Column(name = "postage_money", comment = "邮费", length = 3)
	private String postageMoney;
	@Column(name = "order_money", comment = "计算金额 = 实付金额 - 邮费 - 税金", length = 3)
	private String orderMoney;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPaymentUnionId() {
		return paymentUnionId;
	}

	public void setPaymentUnionId(String paymentUnionId) {
		this.paymentUnionId = paymentUnionId;
	}

	public String getPaymentWeixinNick() {
		return paymentWeixinNick;
	}

	public void setPaymentWeixinNick(String paymentWeixinNick) {
		this.paymentWeixinNick = paymentWeixinNick;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getBelongsSalesCommission() {
		return belongsSalesCommission;
	}

	public void setBelongsSalesCommission(String belongsSalesCommission) {
		this.belongsSalesCommission = belongsSalesCommission;
	}

	public String getSalesCommissionMoney() {
		return salesCommissionMoney;
	}

	public void setSalesCommissionMoney(String salesCommissionMoney) {
		this.salesCommissionMoney = salesCommissionMoney;
	}

	public String getBelongsMembersCommission() {
		return belongsMembersCommission;
	}

	public void setBelongsMembersCommission(String belongsMembersCommission) {
		this.belongsMembersCommission = belongsMembersCommission;
	}

	public String getMembersCommissionMoney() {
		return membersCommissionMoney;
	}

	public void setMembersCommissionMoney(String membersCommissionMoney) {
		this.membersCommissionMoney = membersCommissionMoney;
	}

	public String getRealPay() {
		return realPay;
	}

	public void setRealPay(String realPay) {
		this.realPay = realPay;
	}

	public String getPostageMoney() {
		return postageMoney;
	}

	public void setPostageMoney(String postageMoney) {
		this.postageMoney = postageMoney;
	}

	public String getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(String orderMoney) {
		this.orderMoney = orderMoney;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
