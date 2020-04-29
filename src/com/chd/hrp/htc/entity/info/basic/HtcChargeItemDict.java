package com.chd.hrp.htc.entity.info.basic;

import java.io.Serializable;
import java.util.*;
/** 
* 2015-3-17 
* author:alfred
*/ 


public class HtcChargeItemDict implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

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
	 * 收费类别编码
	 */
	private Long charge_kind_id;
	
	private String charge_kind_code;
	
	private String charge_kind_name;
	
	private Long charge_item_id;
	/**
	 * 收费项目编码
	 */
	private String charge_item_code;
	/**
	 * 收费项目名称
	 */
	private String charge_item_name;
	/**
	 * 单价
	 */
	private double price;
	/**
	 * 停用标志
	 */
	private Integer is_stop;
	/**
	 * 拼音码
	 */
	private String spell_code;
	/**
	 * 五笔码
	 */
	private String wbx_code;
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
	public Long getCharge_kind_id() {
		return charge_kind_id;
	}
	public void setCharge_kind_id(Long charge_kind_id) {
		this.charge_kind_id = charge_kind_id;
	}
	public String getCharge_kind_code() {
		return charge_kind_code;
	}
	public void setCharge_kind_code(String charge_kind_code) {
		this.charge_kind_code = charge_kind_code;
	}
	public String getCharge_kind_name() {
		return charge_kind_name;
	}
	public void setCharge_kind_name(String charge_kind_name) {
		this.charge_kind_name = charge_kind_name;
	}
	public Long getCharge_item_id() {
		return charge_item_id;
	}
	public void setCharge_item_id(Long charge_item_id) {
		this.charge_item_id = charge_item_id;
	}
	public String getCharge_item_code() {
		return charge_item_code;
	}
	public void setCharge_item_code(String charge_item_code) {
		this.charge_item_code = charge_item_code;
	}
	public String getCharge_item_name() {
		return charge_item_name;
	}
	public void setCharge_item_name(String charge_item_name) {
		this.charge_item_name = charge_item_name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Integer getIs_stop() {
		return is_stop;
	}
	public void setIs_stop(Integer is_stop) {
		this.is_stop = is_stop;
	}
	public String getSpell_code() {
		return spell_code;
	}
	public void setSpell_code(String spell_code) {
		this.spell_code = spell_code;
	}
	public String getWbx_code() {
		return wbx_code;
	}
	public void setWbx_code(String wbx_code) {
		this.wbx_code = wbx_code;
	}
}