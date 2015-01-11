package com.yuanhe.domain;

import com.yuanhe.domain.annotation.Column;
import com.yuanhe.domain.annotation.Table;

@Table(name = "t_yuanhe_dealers", comment = "经销商表")
public class Dealers extends BaseDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "dealers_id", pk = true, comment = "经销商ID", length = 32)
	private String dealersId;
	@Column(name = "dealers_name", comment = "经销商名称", length = 50)
	private String dealersName;
	@Column(name = "dealers_mobile", comment = "经销商电话", length = 11)
	private String dealersMobile;
	@Column(name = "dealers_status", comment = "经销商状态", length = 10)
	private String dealersStatus;
	@Column(name = "dealers_type", comment = "经销商类型（实体经销商、网络经销商）", length = 10)
	private String dealersType;
	@Column(name = "dealers_qr_code", comment = "经销商二维码scene_idID", length = 10)
	private String dealersQrCode;
	@Column(name = "dealers_qr_url", comment = "经销商二维码URL", length = 50)
	private String dealersQrUrl;
	@Column(name = "parent_id", comment = "父级id", length = 32)
	private String parentId;
	@Column(name = "union_id", comment = "unionid", length = 50)
	private String unionId;
	@Column(name = "wei_xinId", comment = "weixinId", length = 50)
	private String weixinId;

	public String getWeixinId() {
		return weixinId;
	}

	public void setWeixinId(String weixinId) {
		this.weixinId = weixinId;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public String getDealersId() {
		return dealersId;
	}

	public void setDealersId(String dealersId) {
		this.dealersId = dealersId;
	}

	public String getDealersName() {
		return dealersName;
	}

	public void setDealersName(String dealersName) {
		this.dealersName = dealersName;
	}

	public String getDealersMobile() {
		return dealersMobile;
	}

	public void setDealersMobile(String dealersMobile) {
		this.dealersMobile = dealersMobile;
	}

	public String getDealersStatus() {
		return dealersStatus;
	}

	public void setDealersStatus(String dealersStatus) {
		this.dealersStatus = dealersStatus;
	}

	public String getDealersType() {
		return dealersType;
	}

	public void setDealersType(String dealersType) {
		this.dealersType = dealersType;
	}

	public String getDealersQrCode() {
		return dealersQrCode;
	}

	public void setDealersQrCode(String dealersQrCode) {
		this.dealersQrCode = dealersQrCode;
	}

	public String getDealersQrUrl() {
		return dealersQrUrl;
	}

	public void setDealersQrUrl(String dealersQrUrl) {
		this.dealersQrUrl = dealersQrUrl;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
}
