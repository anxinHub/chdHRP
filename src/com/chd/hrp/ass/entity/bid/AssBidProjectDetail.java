package com.chd.hrp.ass.entity.bid;

import java.util.Date;

public class AssBidProjectDetail {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 集体ID
	 */
	private Long group_id;
	
	/**
	 * 医院ID
	 */
	private Long hos_id;
	
	/**
	 * 账套编码
	 */
	private String copy_code;
	
	private Long project_id;
	
	private String project_no;
	
	private Date bid_date;
	
	private Long is_group;
	
	
	/**
	 * 招标明细ID
	 */
	private Long project_detail_id;
	
	/**
	 * 招标编号
	 */
	private String bid_no;
	
	/**
	 * 资产ID
	 */
	private Long ass_id;
	
	/**
	 * 资产变更NO
	 */
	private Long ass_no;
	
	private String ass_code;
	
	private String ass_name;
	
	/**
	 * 规格
	 */
	private String ass_spec;
	
	/**
	 * 型号
	 */
	private String ass_model;
	
	/**
	 * 数量
	 */
	private Integer ass_num;
	
	/**
	 * 价格
	 */
	private Double ass_price;
	
	/**
	 * 金额
	 */
	private Double ass_money;
	
	/**
	 * 生产厂家ID
	 */
	private Long fac_id;
	
	/**
	 * 生产厂家NO
	 */
	private Long fac_no;
	
	private String fac_code;
	
	private String fac_name;
	
	/**
	 * 备注
	 */
	private String bid_note;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	private String ass_nature;
	



	public Long getProject_detail_id() {
		return project_detail_id;
	}

	public void setProject_detail_id(Long project_detail_id) {
		this.project_detail_id = project_detail_id;
	}

	public String getBid_no() {
		return bid_no;
	}

	public void setBid_no(String bid_no) {
		this.bid_no = bid_no;
	}

	public Long getAss_id() {
		return ass_id;
	}

	public void setAss_id(Long ass_id) {
		this.ass_id = ass_id;
	}

	public Long getAss_no() {
		return ass_no;
	}

	public void setAss_no(Long ass_no) {
		this.ass_no = ass_no;
	}

	public String getAss_code() {
		return ass_code;
	}

	public void setAss_code(String ass_code) {
		this.ass_code = ass_code;
	}

	public String getAss_name() {
		return ass_name;
	}

	public void setAss_name(String ass_name) {
		this.ass_name = ass_name;
	}

	public String getAss_spec() {
		return ass_spec;
	}

	public void setAss_spec(String ass_spec) {
		this.ass_spec = ass_spec;
	}

	public String getAss_model() {
		return ass_model;
	}

	public void setAss_model(String ass_model) {
		this.ass_model = ass_model;
	}

	public Integer getAss_num() {
		return ass_num;
	}

	public void setAss_num(Integer ass_num) {
		this.ass_num = ass_num;
	}

	public Double getAss_price() {
		return ass_price;
	}

	public void setAss_price(Double ass_price) {
		this.ass_price = ass_price;
	}

	public Double getAss_money() {
		return ass_money;
	}

	public void setAss_money(Double ass_money) {
		this.ass_money = ass_money;
	}

	public Long getFac_id() {
		return fac_id;
	}

	public void setFac_id(Long fac_id) {
		this.fac_id = fac_id;
	}

	public Long getFac_no() {
		return fac_no;
	}

	public void setFac_no(Long fac_no) {
		this.fac_no = fac_no;
	}

	public String getFac_code() {
		return fac_code;
	}

	public void setFac_code(String fac_code) {
		this.fac_code = fac_code;
	}

	public String getFac_name() {
		return fac_name;
	}

	public void setFac_name(String fac_name) {
		this.fac_name = fac_name;
	}

	public String getBid_note() {
		return bid_note;
	}

	public void setBid_note(String bid_note) {
		this.bid_note = bid_note;
	}

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}

	public String getAss_nature() {
		return ass_nature;
	}

	public void setAss_nature(String ass_nature) {
		this.ass_nature = ass_nature;
	}

	public Long getGroup_id() {
		return group_id;
	}

	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}

	public Long getHos_id() {
		return hos_id;
	}

	public void setHos_id(Long hos_id) {
		this.hos_id = hos_id;
	}

	public String getCopy_code() {
		return copy_code;
	}

	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}

	public Long getProject_id() {
		return project_id;
	}

	public void setProject_id(Long project_id) {
		this.project_id = project_id;
	}

	public String getProject_no() {
		return project_no;
	}

	public void setProject_no(String project_no) {
		this.project_no = project_no;
	}

	public Date getBid_date() {
		return bid_date;
	}

	public void setBid_date(Date bid_date) {
		this.bid_date = bid_date;
	}

	public Long getIs_group() {
		return is_group;
	}

	public void setIs_group(Long is_group) {
		this.is_group = is_group;
	}
	
}
