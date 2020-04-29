package com.chd.hrp.acc.entity.autovouch;

import java.io.Serializable;

public class AccBusiType implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long group_id;

	private Long hos_id;

	private String copy_code;

	private String mod_code;

	private String busi_type_code;

	private String busi_type_name;

	private String super_code;

	private Integer busi_type_level;

	private Integer is_last;

	private String log_name;

	private Integer is_vouch;

	private Long sort_code;

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

	public String getBusi_type_name() {
		return busi_type_name;
	}

	public void setBusi_type_name(String busi_type_name) {
		this.busi_type_name = busi_type_name;
	}

	public String getSuper_code() {
		return super_code;
	}

	public void setSuper_code(String super_code) {
		this.super_code = super_code;
	}

	public Integer getBusi_type_level() {
		return busi_type_level;
	}

	public void setBusi_type_level(Integer busi_type_level) {
		this.busi_type_level = busi_type_level;
	}

	public Integer getIs_last() {
		return is_last;
	}

	public void setIs_last(Integer is_last) {
		this.is_last = is_last;
	}

	public String getLog_name() {
		return log_name;
	}

	public void setLog_name(String log_name) {
		this.log_name = log_name;
	}

	public Integer getIs_vouch() {
		return is_vouch;
	}

	public void setIs_vouch(Integer is_vouch) {
		this.is_vouch = is_vouch;
	}

	public Long getSort_code() {
		return sort_code;
	}

	public void setSort_code(Long sort_code) {
		this.sort_code = sort_code;
	}

}
