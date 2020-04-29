package com.chd.hrp.acc.entity.autovouch;

import java.io.Serializable;

public class AccBusiTemplate implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long group_id;

	private Long hos_id;

	private String copy_code;

	private String acc_year;

	private String mod_code;

	private String busi_type_code;

	private String template_code;

	private String template_name;

	private String vouch_type_code;
	
	private String vouch_type_name;

	private String summary;

	private Integer is_detail_summary;

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

	public String getAcc_year() {
		return acc_year;
	}

	public void setAcc_year(String acc_year) {
		this.acc_year = acc_year;
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

	public String getTemplate_code() {
		return template_code;
	}

	public void setTemplate_code(String template_code) {
		this.template_code = template_code;
	}

	public String getTemplate_name() {
		return template_name;
	}

	public void setTemplate_name(String template_name) {
		this.template_name = template_name;
	}

	public String getVouch_type_code() {
		return vouch_type_code;
	}

	public void setVouch_type_code(String vouch_type_code) {
		this.vouch_type_code = vouch_type_code;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Integer getIs_detail_summary() {
		return is_detail_summary;
	}

	public void setIs_detail_summary(Integer is_detail_summary) {
		this.is_detail_summary = is_detail_summary;
	}

	public String getVouch_type_name() {
		return vouch_type_name;
	}

	public void setVouch_type_name(String vouch_type_name) {
		this.vouch_type_name = vouch_type_name;
	}
	
	

}
