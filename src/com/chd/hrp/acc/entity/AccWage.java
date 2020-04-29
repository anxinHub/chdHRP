package com.chd.hrp.acc.entity;

import java.io.Serializable;

public class AccWage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 集团ID
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
	/**
	 * 工资编码
	 */
	private String wage_code;
	/**
	 * 工资名称
	 */
	private String wage_name;
	/**
	 * 备注
	 */
	private String note;

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

	public String getWage_code() {
		return wage_code;
	}

	public void setWage_code(String wage_code) {
		this.wage_code = wage_code;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getWage_name() {
		return wage_name;
	}

	public void setWage_name(String wage_name) {
		this.wage_name = wage_name;
	}
	
	

}
