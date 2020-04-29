package com.chd.hrp.sys.entity;

import java.io.Serializable;

public class BusiQuery implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mod_code;
	private String busi_type_code;
	private String main_table;
	private String main_key_field;
	private String business_no;
	private String detail_table;
	private String detail_key_field;
	private Integer is_unfold;
	private String source_detail_table;
	private String source_key_field;
	private String head_name;
	private String log_name;
	private String group_by;
	private String where_sql;
	private String business_url;
	
	
	public String getBusiness_url() {
		return business_url;
	}
	public void setBusiness_url(String business_url) {
		this.business_url = business_url;
	}
	public String getMod_code() {
		return mod_code;
	}
	public void setMod_code(String mod_code) {
		this.mod_code = mod_code;
	}
	public String getBusi_type_code() {
		return busi_type_code;
	}
	public void setBusi_type_code(String busi_type_code) {
		this.busi_type_code = busi_type_code;
	}
	public String getMain_table() {
		return main_table;
	}
	public void setMain_table(String main_table) {
		this.main_table = main_table;
	}
	public String getMain_key_field() {
		return main_key_field;
	}
	public void setMain_key_field(String main_key_field) {
		this.main_key_field = main_key_field;
	}
	public String getBusiness_no() {
		return business_no;
	}
	public void setBusiness_no(String business_no) {
		this.business_no = business_no;
	}
	public String getDetail_table() {
		return detail_table;
	}
	public void setDetail_table(String detail_table) {
		this.detail_table = detail_table;
	}
	public String getDetail_key_field() {
		return detail_key_field;
	}
	public void setDetail_key_field(String detail_key_field) {
		this.detail_key_field = detail_key_field;
	}
	public Integer getIs_unfold() {
		return is_unfold;
	}
	public void setIs_unfold(Integer is_unfold) {
		this.is_unfold = is_unfold;
	}
	public String getSource_detail_table() {
		return source_detail_table;
	}
	public void setSource_detail_table(String source_detail_table) {
		this.source_detail_table = source_detail_table;
	}
	public String getSource_key_field() {
		return source_key_field;
	}
	public void setSource_key_field(String source_key_field) {
		this.source_key_field = source_key_field;
	}
	public String getHead_name() {
		return head_name;
	}
	public void setHead_name(String head_name) {
		this.head_name = head_name;
	}
	public String getLog_name() {
		return log_name;
	}
	public void setLog_name(String log_name) {
		this.log_name = log_name;
	}
	public String getGroup_by() {
		return group_by;
	}
	public void setGroup_by(String group_by) {
		this.group_by = group_by;
	}
	public String getWhere_sql() {
		return where_sql;
	}
	public void setWhere_sql(String where_sql) {
		this.where_sql = where_sql;
	}
	
}
