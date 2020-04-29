package com.chd.hrp.hr.entity.salarymanagement.socialsecuritymanagement;

import java.io.Serializable;

public class HrAreatopay implements Serializable{
	
	private Integer group_id;
	
	private Integer hos_id;

	private String area;
	
	private String insur_type;
	
	private Double unit_rate;
	
	private Double individual_rate;
	
	private Double upper_limit;
	
	private Double lower_limit;
	
	private String remark;

	public HrAreatopay() {
		super();
	}

	public Integer getGroup_id() {
		return group_id;
	}

	public void setGroup_id(Integer group_id) {
		this.group_id = group_id;
	}

	public Integer getHos_id() {
		return hos_id;
	}

	public void setHos_id(Integer hos_id) {
		this.hos_id = hos_id;
	}

	public String getAREA() {
		return area;
	}

	public void setAREA(String aREA) {
		area = aREA;
	}

	public String getInsur_type() {
		return insur_type;
	}

	public void setInsur_type(String insur_type) {
		this.insur_type = insur_type;
	}

	public Double getUnit_rate() {
		return unit_rate;
	}

	public void setUnit_rate(Double unit_rate) {
		this.unit_rate = unit_rate;
	}

	public Double getIndividual_rate() {
		return individual_rate;
	}

	public void setIndividual_rate(Double individual_rate) {
		this.individual_rate = individual_rate;
	}

	public Double getUpper_limit() {
		return upper_limit;
	}

	public void setUpper_limit(Double upper_limit) {
		this.upper_limit = upper_limit;
	}

	public Double getLower_limit() {
		return lower_limit;
	}

	public void setLower_limit(Double lower_limit) {
		this.lower_limit = lower_limit;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
