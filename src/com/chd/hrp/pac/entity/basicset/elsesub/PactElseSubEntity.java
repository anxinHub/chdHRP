package com.chd.hrp.pac.entity.basicset.elsesub;

import java.io.Serializable;

public class PactElseSubEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9175236788876565422L;

	private Integer group_id;
	private Integer hos_id;
	private String copy_code;
	private Integer sub_id;
	private String sub_code;
	private String sub_name;
	private Integer is_stop;
	private String spell_code;
	private String wbx_code;
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

	public Integer getSub_id() {
		return sub_id;
	}

	public void setSub_id(Integer sub_id) {
		this.sub_id = sub_id;
	}

	public String getSub_code() {
		return sub_code;
	}

	public void setSub_code(String sub_code) {
		this.sub_code = sub_code;
	}

	public String getSub_name() {
		return sub_name;
	}

	public void setSub_name(String sub_name) {
		this.sub_name = sub_name;
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
