package com.chd.hrp.mat.entity;

import java.io.Serializable;

public class MatRefCharge implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer group_id;
	private Integer hos_id;
	private String copy_code;
	private Integer inv_id;
	private Integer charge_item_id;
	
	private String inv_code;
	private String inv_name;
	private String inv_model;
	private Long plan_price;
	private Integer is_com;
	private String unit_code;
	private String charge_item_name;
	
	
	public String getInv_code() {
		return inv_code;
	}
	public void setInv_code(String inv_code) {
		this.inv_code = inv_code;
	}
	public String getInv_name() {
		return inv_name;
	}
	public void setInv_name(String inv_name) {
		this.inv_name = inv_name;
	}
	public String getInv_model() {
		return inv_model;
	}
	public void setInv_model(String inv_model) {
		this.inv_model = inv_model;
	}
	public Long getPlan_price() {
		return plan_price;
	}
	public void setPlan_price(Long plan_price) {
		this.plan_price = plan_price;
	}
	public Integer getIs_com() {
		return is_com;
	}
	public void setIs_com(Integer is_com) {
		this.is_com = is_com;
	}
	public String getUnit_code() {
		return unit_code;
	}
	public void setUnit_code(String unit_code) {
		this.unit_code = unit_code;
	}
	public String getCharge_item_name() {
		return charge_item_name;
	}
	public void setCharge_item_name(String charge_item_name) {
		this.charge_item_name = charge_item_name;
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
	public String getCopy_code() {
		return copy_code;
	}
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	public Integer getCharge_item_id() {
		return charge_item_id;
	}
	public void setCharge_item_id(Integer charge_item_id) {
		this.charge_item_id = charge_item_id;
	}
	public Integer getInv_id() {
		return inv_id;
	}
	public void setInv_id(Integer inv_id) {
		this.inv_id = inv_id;
	}
}
