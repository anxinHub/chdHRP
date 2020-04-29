package com.chd.hrp.acc.entity.autovouch;

import java.io.Serializable;

public class HisAccMedType implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long group_id;

	private Long hos_id;

	private String copy_code;

	private String med_type_code;

	private String med_type_name;
	
	private String super_code;
	
	private Integer type_level;
	
	private Integer is_last;

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

	public String getMed_type_code() {
		return med_type_code;
	}

	public void setMed_type_code(String med_type_code) {
		this.med_type_code = med_type_code;
	}

	public String getMed_type_name() {
		return med_type_name;
	}

	public void setMed_type_name(String med_type_name) {
		this.med_type_name = med_type_name;
	}

	public String getSuper_code() {
		return super_code;
	}

	public void setSuper_code(String super_code) {
		this.super_code = super_code;
	}

	public Integer getType_level() {
		return type_level;
	}

	public void setType_level(Integer type_level) {
		this.type_level = type_level;
	}

	public Integer getIs_last() {
		return is_last;
	}

	public void setIs_last(Integer is_last) {
		this.is_last = is_last;
	}

	
}
