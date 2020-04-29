package com.chd.hrp.htcg.entity.info;

import java.io.Serializable;

public class HtcgChargeItemDicts implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long group_id;
	private Long hos_id;
	private String copy_code;
	private String charge_item_code;
	private String charge_item_name;
	private String charge_kind_code;
	private String charge_kind_name;
	private double price;
	private Integer is_stop;
	public Long getGroup_id() {
		return group_id;
	}
	public Long getHos_id() {
		return hos_id;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public String getCharge_item_code() {
		return charge_item_code;
	}
	public String getCharge_item_name() {
		return charge_item_name;
	}
	public String getCharge_kind_code() {
		return charge_kind_code;
	}
	public String getCharge_kind_name() {
		return charge_kind_name;
	}
	public double getPrice() {
		return price;
	}
	public Integer getIs_stop() {
		return is_stop;
	}
	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}
	public void setHos_id(Long hos_id) {
		this.hos_id = hos_id;
	}
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	public void setCharge_item_code(String charge_item_code) {
		this.charge_item_code = charge_item_code;
	}
	public void setCharge_item_name(String charge_item_name) {
		this.charge_item_name = charge_item_name;
	}
	public void setCharge_kind_code(String charge_kind_code) {
		this.charge_kind_code = charge_kind_code;
	}
	public void setCharge_kind_name(String charge_kind_name) {
		this.charge_kind_name = charge_kind_name;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public void setIs_stop(Integer is_stop) {
		this.is_stop = is_stop;
	}
}
