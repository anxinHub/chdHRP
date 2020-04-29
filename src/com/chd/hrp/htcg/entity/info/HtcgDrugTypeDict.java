/*
 *
 */package com.chd.hrp.htcg.entity.info;

import java.io.Serializable;

public class HtcgDrugTypeDict implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private long group_id;
	private long hos_id;
	private String copy_code;
	private String drug_type_code;
	private String drug_type_name;
	private String spell_code;
	private String wbx_code;
	private Integer is_stop;
	public long getGroup_id() {
		return group_id;
	}
	public long getHos_id() {
		return hos_id;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public String getDrug_type_code() {
		return drug_type_code;
	}
	public String getDrug_type_name() {
		return drug_type_name;
	}
	public String getSpell_code() {
		return spell_code;
	}
	public String getWbx_code() {
		return wbx_code;
	}
	public Integer getIs_stop() {
		return is_stop;
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
	public void setDrug_type_code(String drug_type_code) {
		this.drug_type_code = drug_type_code;
	}
	public void setDrug_type_name(String drug_type_name) {
		this.drug_type_name = drug_type_name;
	}
	public void setSpell_code(String spell_code) {
		this.spell_code = spell_code;
	}
	public void setWbx_code(String wbx_code) {
		this.wbx_code = wbx_code;
	}
	public void setIs_stop(Integer is_stop) {
		this.is_stop = is_stop;
	}

	
	

}