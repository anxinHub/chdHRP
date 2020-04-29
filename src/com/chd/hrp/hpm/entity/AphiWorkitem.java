package com.chd.hrp.hpm.entity;

import java.io.Serializable;

/**
 * alfred
 */

public class AphiWorkitem implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_id;

	private Long hos_id;
	
	
	
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

	private String copy_code;

	private String work_item_code;

	private String work_item_name;

	private String spell_code;

	private String wbx_code;

	private String data_source;

	private Integer is_stop;
	private String error_type;


	public void setCopy_code(String value) {
		this.copy_code = value;
	}

	public String getCopy_code() {
		return this.copy_code;
	}

	public void setWork_item_code(String value) {
		this.work_item_code = value;
	}

	public String getWork_item_code() {
		return this.work_item_code;
	}

	public void setWork_item_name(String value) {
		this.work_item_name = value;
	}

	public String getWork_item_name() {
		return this.work_item_name;
	}

	public void setSpell_code(String value) {
		this.spell_code = value;
	}

	public String getSpell_code() {
		return this.spell_code;
	}

	public void setWbx_code(String value) {
		this.wbx_code = value;
	}

	public String getWbx_code() {
		return this.wbx_code;
	}

	public void setData_source(String value) {
		this.data_source = value;
	}

	public String getData_source() {
		return this.data_source;
	}

	public void setIs_stop(Integer value) {
		this.is_stop = value;
	}

	public Integer getIs_stop() {
		return this.is_stop;
	}

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	
}