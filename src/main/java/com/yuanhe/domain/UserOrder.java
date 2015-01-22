package com.yuanhe.domain;

import com.yuanhe.domain.annotation.Column;
import com.yuanhe.domain.annotation.Table;

@Table(name = "t_yuanhe_user_order", comment = "订单表")
public class UserOrder extends BaseDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "order_id", pk = true, comment = "订单ID", length = 32)
	private String orderId;
	@Column(name = "payment_union_id", comment = "付款人union_id", length = 32)
	private String paymentUnionId;
	@Column(name = "payment_weixin_nick", comment = "付款人微信昵称", length = 50)
	private String paymentWeixinNick;
	@Column(name = "order_status", comment = "订单状态(正常/退款)", length = 20)
	private String orderStatus;
	@Column(name = "belongs_sales_commission", comment = "销售佣金所属", length = 32)
	private String belongsSalesCommission;
	@Column(name = "sales_commission_money", comment = "销售佣金金额", length = 10)
	private String salesCommissionMoney;
	@Column(name = "belongs_members_commission", comment = "会员佣金所属", length = 32)
	private String belongsMembersCommission;
	@Column(name = "members_commission_money", comment = "会员佣金金额", length = 10)
	private String membersCommissionMoney;
	@Column(name = "real_pay", comment = "实付金额", length = 10)
	private String realPay;
	@Column(name = "postage_money", comment = "邮费", length = 10)
	private String postageMoney;
	@Column(name = "order_money", comment = "计算金额 = 实付金额 - 邮费 - 税金", length = 10)
	private String orderMoney;
	@Column(name = "commodity_name", comment = "商品名称", length = 50)
	private String commodityName;
	@Column(name = "commodity_pic", comment = "商品图片", length = 200)
	private String commodityPic;
	@Column(name = "update_time", comment = "交易更新时间", length = 50)
	private String updateTime;
	private String belongsSalesCommissionName;
	private String belongsMembersCommissionName;

	public String getBelongsSalesCommissionName() {
		return belongsSalesCommissionName;
	}

	public void setBelongsSalesCommissionName(String belongsSalesCommissionName) {
		this.belongsSalesCommissionName = belongsSalesCommissionName;
	}

	public String getBelongsMembersCommissionName() {
		return belongsMembersCommissionName;
	}

	public void setBelongsMembersCommissionName(
			String belongsMembersCommissionName) {
		this.belongsMembersCommissionName = belongsMembersCommissionName;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	private String weixin_user_id;
	private String weixin_openid;

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public String getCommodityPic() {
		return commodityPic;
	}

	public void setCommodityPic(String commodityPic) {
		this.commodityPic = commodityPic;
	}

	public void setWeixin_openid(String weixin_openid) {
		this.weixin_openid = weixin_openid;
	}

	public String getWeixin_openid() {
		return weixin_openid;
	}

	public void setWeixin_user_id(String weixin_user_id) {
		this.weixin_user_id = weixin_user_id;
	}

	public String getWeixin_user_id() {
		return weixin_user_id;
	}

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
