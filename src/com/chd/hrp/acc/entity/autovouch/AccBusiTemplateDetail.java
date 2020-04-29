package com.chd.hrp.acc.entity.autovouch;

import java.io.Serializable;

public class AccBusiTemplateDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long group_id;

	private Long hos_id;

	private String copy_code;

	private String acc_year;

	private String mod_code;

	private String busi_type_code;

	private String template_code;

	private Long template_detail_id;

	private String summary;

	private Long vouch_row;

	private String meta_code;

	private Integer direction;
	
	private String meta_name;
	
	private String sort_code;
	
	private String cal;
	
	private String kind_code;
	
	

	public String getSort_code() {
		return sort_code;
	}

	public void setSort_code(String sort_code) {
		this.sort_code = sort_code;
	}

	public String getCal() {
		return cal;
	}

	public void setCal(String cal) {
		this.cal = cal;
	}

	public String getKind_code() {
		return kind_code;
	}

	public void setKind_code(String kind_code) {
		this.kind_code = kind_code;
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

	public Long getTemplate_detail_id() {
		return template_detail_id;
	}

	public void setTemplate_detail_id(Long template_detail_id) {
		this.template_detail_id = template_detail_id;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Long getVouch_row() {
		return vouch_row;
	}

	public void setVouch_row(Long vouch_row) {
		this.vouch_row = vouch_row;
	}

	public String getMeta_code() {
		return meta_code;
	}

	public void setMeta_code(String meta_code) {
		this.meta_code = meta_code;
	}

	public Integer getDirection() {
		return direction;
	}

	public void setDirection(Integer direction) {
		this.direction = direction;
	}

	public String getMeta_name() {
		return meta_name;
	}

	public void setMeta_name(String meta_name) {
		this.meta_name = meta_name;
	}

}
