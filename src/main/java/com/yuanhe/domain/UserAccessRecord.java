package com.yuanhe.domain;

import java.util.Date;

import com.yuanhe.domain.annotation.Column;
import com.yuanhe.domain.annotation.Table;

@Table(name = "t_yuanhe_user_access_record", comment = "用户访问记录")
public class UserAccessRecord extends BaseDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "record_id", pk = true, comment = "主键ID", length = 32)
	private String recordId;
	@Column(name = "visiter_union_id", comment = "用户访问unionId", length = 32)
	private String visiterUnionId;
	@Column(name = "visiter_dealers_id", comment = "被访问经销商ID", length = 32)
	private String visiterDealersId;
	@Column(name = "visit_time", comment = "访问时间", length = 50)
	private Date visitTime;
	@Column(name = "access_url_title", comment = "访问的链接名称", length = 50)
	private String accessUrlTitle;
	@Column(name = "access_url", comment = "访问的链接地址", length = 50)
	private String accessUrl;

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getVisiterUnionId() {
		return visiterUnionId;
	}

	public void setVisiterUnionId(String visiterUnionId) {
		this.visiterUnionId = visiterUnionId;
	}

	public String getVisiterDealersId() {
		return visiterDealersId;
	}

	public void setVisiterDealersId(String visiterDealersId) {
		this.visiterDealersId = visiterDealersId;
	}

	public Date getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}

	public String getAccessUrlTitle() {
		return accessUrlTitle;
	}

	public void setAccessUrlTitle(String accessUrlTitle) {
		this.accessUrlTitle = accessUrlTitle;
	}

	public String getAccessUrl() {
		return accessUrl;
	}

	public void setAccessUrl(String accessUrl) {
		this.accessUrl = accessUrl;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
