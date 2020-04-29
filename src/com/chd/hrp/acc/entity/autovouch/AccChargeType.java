package com.chd.hrp.acc.entity.autovouch;

import java.io.Serializable;

public class AccChargeType implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long group_id;

	private Long hos_id;

	private String copy_code;

	private String charge_type_code;

	private String charge_type_name;

	private String spell_code;
	
	private String wbx_code;
	
	private Integer is_stop;
	
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

	public String getCharge_type_code() {
		return charge_type_code;
	}

	public void setCharge_type_code(String charge_type_code) {
		this.charge_type_code = charge_type_code;
	}

	public String getCharge_type_name() {
		return charge_type_name;
	}

	public void setCharge_type_name(String charge_type_name) {
		this.charge_type_name = charge_type_name;
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

	public Integer getIs_stop() {
		return is_stop;
	}

	public void setIs_stop(Integer is_stop) {
		this.is_stop = is_stop;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}



}
