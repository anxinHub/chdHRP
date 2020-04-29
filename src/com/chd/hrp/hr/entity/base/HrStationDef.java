package com.chd.hrp.hr.entity.base;

import java.io.Serializable;

public class HrStationDef implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_id;
	private Long hos_id;
	private String copy_code;
	private String station_code;
	private String station_name;
	private int is_stop;
	/**
	 * 是否停用
	 */
	private String is_stop_name;
	private int is_last;
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

	
	

	public void setGroup_id(Long value) {
		this.group_id = value;
	}
		
	public Long getGroup_id() {
		return this.group_id;
	}
	public void setHos_id(Long value) {
		this.hos_id = value;
	}
		
	public Long getHos_id() {
		return this.hos_id;
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

	public void setIs_stop(int value) {
		this.is_stop = value;
	}
		
	public int getIs_stop() {
		return this.is_stop;
	}
	
	public String getIs_stop_name() {
		return is_stop_name;
	}

	public void setIs_stop_name(String is_stop_name) {
		this.is_stop_name = is_stop_name;
	}

	public void setIs_last(int value) {
		this.is_last = value;
	}
		
	public int getIs_last() {
		return this.is_last;
	}
	public String getSpell_code() {
		return spell_code;
	}

	public void setSpell_code(String spell_code) {
		this.spell_code = spell_code;
	}

	public void setWbx_code(String value) {
		this.wbx_code = value;
	}
		
	public String getWbx_code() {
		return this.wbx_code;
	}
	public void setNote(String value) {
		this.note = value;
	}
		
	public String getNote() {
		return this.note;
	}

	

}
