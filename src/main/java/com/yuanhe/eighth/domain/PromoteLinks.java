package com.yuanhe.eighth.domain;

import com.yuanhe.eighth.domain.annotation.BaseDomain;
import com.yuanhe.eighth.domain.annotation.Column;
import com.yuanhe.eighth.domain.annotation.Table;

@Table(name = "t_yuanhe_promoteLinks", comment = "推广链接表")
public class PromoteLinks extends BaseDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "link_id", comment = "链接ID", length = 32)
	private String linkId;
	@Column(name = "link_title", comment = "链接名称", length = 32)
	private String linkTitle;
	@Column(name = "link_url", comment = "链接URL", length = 32)
	private String linkUrl;

	public String getLinkId() {
		return linkId;
	}

	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}

	public String getLinkTitle() {
		return linkTitle;
	}

	public void setLinkTitle(String linkTitle) {
		this.linkTitle = linkTitle;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

}
