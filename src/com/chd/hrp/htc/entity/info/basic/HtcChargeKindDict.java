package com.chd.hrp.htc.entity.info.basic;

import java.io.Serializable;
/** 
* 2015-3-17 
* author:alfred
*/ 

public class HtcChargeKindDict implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private long group_id;
	
	private long hos_id;
	
	private String copy_code;
	
	private long income_type_id;
	
	private String income_type_code;
	
	private String income_type_name;

	private long income_item_id_in;

	private String income_item_code_in;
	
	private String income_item_name_in;
	
	private long income_item_id_out;

	private String income_item_code_out;
	
	private String income_item_name_out;
	
	private long charge_kind_id;

	private String charge_kind_code;
	
	private String charge_kind_name;
	
	private String spell_code;
	
	private String wbx_code;

	public long getGroup_id() {
		return group_id;
	}

	public long getHos_id() {
		return hos_id;
	}

	public String getCopy_code() {
		return copy_code;
	}

	public long getIncome_type_id() {
		return income_type_id;
	}

	public String getIncome_type_code() {
		return income_type_code;
	}

	public String getIncome_type_name() {
		return income_type_name;
	}

	public long getIncome_item_id_in() {
		return income_item_id_in;
	}

	public String getIncome_item_code_in() {
		return income_item_code_in;
	}

	public String getIncome_item_name_in() {
		return income_item_name_in;
	}

	public long getIncome_item_id_out() {
		return income_item_id_out;
	}

	public String getIncome_item_code_out() {
		return income_item_code_out;
	}

	public String getIncome_item_name_out() {
		return income_item_name_out;
	}

	public long getCharge_kind_id() {
		return charge_kind_id;
	}

	public String getCharge_kind_code() {
		return charge_kind_code;
	}

	public String getCharge_kind_name() {
		return charge_kind_name;
	}

	public String getSpell_code() {
		return spell_code;
	}

	public String getWbx_code() {
		return wbx_code;
	}

	public void setGroup_id(long group_id) {
		this.group_id = group_id;
	}

	public void setHos_id(long hos_id) {
		this.hos_id = hos_id;
	}

	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}

	public void setIncome_type_id(long income_type_id) {
		this.income_type_id = income_type_id;
	}

	public void setIncome_type_code(String income_type_code) {
		this.income_type_code = income_type_code;
	}

	public void setIncome_type_name(String income_type_name) {
		this.income_type_name = income_type_name;
	}

	public void setIncome_item_id_in(long income_item_id_in) {
		this.income_item_id_in = income_item_id_in;
	}

	public void setIncome_item_code_in(String income_item_code_in) {
		this.income_item_code_in = income_item_code_in;
	}

	public void setIncome_item_name_in(String income_item_name_in) {
		this.income_item_name_in = income_item_name_in;
	}

	public void setIncome_item_id_out(long income_item_id_out) {
		this.income_item_id_out = income_item_id_out;
	}

	public void setIncome_item_code_out(String income_item_code_out) {
		this.income_item_code_out = income_item_code_out;
	}

	public void setIncome_item_name_out(String income_item_name_out) {
		this.income_item_name_out = income_item_name_out;
	}

	public void setCharge_kind_id(long charge_kind_id) {
		this.charge_kind_id = charge_kind_id;
	}

	public void setCharge_kind_code(String charge_kind_code) {
		this.charge_kind_code = charge_kind_code;
	}

	public void setCharge_kind_name(String charge_kind_name) {
		this.charge_kind_name = charge_kind_name;
	}

	public void setSpell_code(String spell_code) {
		this.spell_code = spell_code;
	}

	public void setWbx_code(String wbx_code) {
		this.wbx_code = wbx_code;
	}
	
	
}