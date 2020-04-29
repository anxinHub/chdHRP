package com.chd.hrp.hpm.entity;

import java.io.Serializable;

/**
 * alfred
 */

public class AphiItemIncreasePercConf implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_id;

	private Long hos_id;
	
	
	
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

	private String copy_code;

	private String item_code;

	private String item_name;

	private double increase_percent;
	private String error_type;

	public void setCopy_code(String value) {
		this.copy_code = value;
	}

	public String getCopy_code() {
		return this.copy_code;
	}

	public void setItem_code(String value) {
		this.item_code = value;
	}

	public String getItem_code() {
		return this.item_code;
	}

	public void setIncrease_percent(double value) {
		this.increase_percent = value;
	}

	public double getIncrease_percent() {
		return this.increase_percent;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}

	
}