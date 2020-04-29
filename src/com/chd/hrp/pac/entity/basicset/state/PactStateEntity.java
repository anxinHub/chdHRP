package com.chd.hrp.pac.entity.basicset.state;

import java.io.Serializable;

public class PactStateEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9175236788876565422L;
	private Integer group_id;
	private Integer hos_id;
	private String copy_code;
	private String state_code;
	private String state_name;
	private Integer is_init;
	private Integer is_stop;
	private String note;

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

	public String getState_code() {
		return state_code;
	}

	public void setState_code(String state_code) {
		this.state_code = state_code;
	}

	public String getState_name() {
		return state_name;
	}

	public void setState_name(String state_name) {
		this.state_name = state_name;
	}

	public Integer getIs_init() {
		return is_init;
	}

	public void setIs_init(Integer is_init) {
		this.is_init = is_init;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
