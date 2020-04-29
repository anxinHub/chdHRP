package com.chd.hrp.hpm.entity;

import java.io.Serializable;

/**
 * alfred
 */

public class AphiDeptLimitConf implements Serializable {

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

	private Long dept_no;
	
	private Long dept_id;

	private Integer is_limit;

	private double lower_money;

	private double upper_money;
	
	private String dept_name;
	private String error_type;

	public void setCopy_code(String value) {
		this.copy_code = value;
	}

	public String getCopy_code() {
		return this.copy_code;
	}

	public Long getDept_no() {
		return dept_no;
	}

	public void setDept_no(Long dept_no) {
		this.dept_no = dept_no;
	}

	public Long getDept_id() {
		return dept_id;
	}

	public void setDept_id(Long dept_id) {
		this.dept_id = dept_id;
	}

	public void setIs_limit(Integer value) {
		this.is_limit = value;
	}

	public Integer getIs_limit() {
		return this.is_limit;
	}

	public void setLower_money(double value) {
		this.lower_money = value;
	}

	public double getLower_money() {
		return this.lower_money;
	}

	public void setUpper_money(double value) {
		this.upper_money = value;
	}

	public double getUpper_money() {
		return this.upper_money;
	}

	
    public String getDept_name() {
    	return dept_name;
    }

	
    public void setDept_name(String dept_name) {
    	this.dept_name = dept_name;
    }

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	
    
}