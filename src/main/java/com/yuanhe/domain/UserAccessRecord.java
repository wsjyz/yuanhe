package com.yuanhe.domain;

import com.yuanhe.domain.annotation.Column;
import com.yuanhe.domain.annotation.Table;

@Table(name = "t_yuanhe_user_ access_record", comment = "用户访问记录")
public class UserAccessRecord extends BaseDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "record_id", comment = "主键ID", length = 32)
	private String recordId;
	@Column(name = "visiter_union_id", comment = "用户访问unionId", length = 3)
	private String visiterUnionId;
	@Column(name = "visiter_dealers_id", comment = "被访问经销商ID", length = 3)
	private String visiterDealersId;
	@Column(name = "visit_time", comment = "访问时间", length = 3)
	private String visitTime;
	@Column(name = "access_url_title", comment = "访问的链接名称", length = 3)
	private String accessUrlTitle;
	@Column(name = "access_url", comment = "访问的链接地址", length = 3)
	private String accessUrl;

}
