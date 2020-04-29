package com.chd.hrp.hr.entity.orangnize;

import java.io.Serializable;

public class HosDuty implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_id;
	private Long hos_id;
	private String copy_code;
	private String duty_code;
	private String duty_name;
	private String kind_code;
	private String kind_name;
	private String duty_level_code;
	private String duty_level_name;
	private int is_stop;
	private String is_stop_name;
	private String spell_code;
	private String wbx_code;
	private String note;
	private String error_type;
	
	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}

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

	public String getDuty_code() {
		return duty_code;
	}

	public void setDuty_code(String duty_code) {
		this.duty_code = duty_code;
	}

	public String getDuty_name() {
		return duty_name;
	}

	public void setDuty_name(String duty_name) {
		this.duty_name = duty_name;
	}

	public String getKind_code() {
		return kind_code;
	}

	public void setKind_code(String kind_code) {
		this.kind_code = kind_code;
	}

	public String getKind_name() {
		return kind_name;
	}

	public void setKind_name(String kind_name) {
		this.kind_name = kind_name;
	}

	public String getDuty_level_code() {
		return duty_level_code;
	}

	public void setDuty_level_code(String duty_level_code) {
		this.duty_level_code = duty_level_code;
	}

	public String getDuty_level_name() {
		return duty_level_name;
	}

	public void setDuty_level_name(String duty_level_name) {
		this.duty_level_name = duty_level_name;
	}

	public int getIs_stop() {
		return is_stop;
	}

	public void setIs_stop(int is_stop) {
		this.is_stop = is_stop;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getIs_stop_name() {
		return is_stop_name;
	}

	public void setIs_stop_name(String is_stop_name) {
		this.is_stop_name = is_stop_name;
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
