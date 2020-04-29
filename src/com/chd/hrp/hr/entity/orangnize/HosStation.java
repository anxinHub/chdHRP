package com.chd.hrp.hr.entity.orangnize;

import java.io.Serializable;

public class HosStation implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_id;
	private Long hos_id;
	private String copy_code;
	private String station_code;
	private String station_name;
	private Long dept_id;
	private Long dept_no;
	private String dept_code;
	private String dept_name;
	private String duty_code;
	private String duty_name;
	private String station_level_code;
	private String station_level_name;
	private String spell_code;
	private String wbx_code;
	
	private int is_stop;
	private String is_stop_name;
	private String note;
	
	private String error_type;

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	
	public Long getDept_id() {
		return dept_id;
	}

	public void setDept_id(Long dept_id) {
		this.dept_id = dept_id;
	}
	public Long getDept_no() {
		return dept_no;
	}

	public void setDept_no(Long dept_no) {
		this.dept_no = dept_no;
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

	public String getStation_code() {
		return station_code;
	}

	public void setStation_code(String station_code) {
		this.station_code = station_code;
	}

	public String getStation_name() {
		return station_name;
	}

	public void setStation_name(String station_name) {
		this.station_name = station_name;
	}

	public String getDept_code() {
		return dept_code;
	}

	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
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

	public String getStation_level_code() {
		return station_level_code;
	}

	public void setStation_level_code(String station_level_code) {
		this.station_level_code = station_level_code;
	}

	public String getStation_level_name() {
		return station_level_name;
	}

	public void setStation_level_name(String station_level_name) {
		this.station_level_name = station_level_name;
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
