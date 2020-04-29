package com.chd.hrp.htc.entity.task.basic;

import java.io.Serializable;
import java.util.*;
/** 
* 2015-3-17 
* author:alfred
*/ 

public class HtcIassetDict implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private long group_id;
	private long hos_id;
	private String copy_code;
	private String asset_code;
	private String asset_name;
	private String asset_type_code;
	private String asset_type_name;
	private double prim_value;
	private String start_date;
	private String end_date;
	private Integer dep_year;
	private String spell_code;
	private String wbx_code;
	
	public long getGroup_id() {
		return group_id;
	}

	public void setGroup_id(long group_id) {
		this.group_id = group_id;
	}

	public long getHos_id() {
		return hos_id;
	}

	public void setHos_id(long hos_id) {
		this.hos_id = hos_id;
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
	
	public String getAsset_type_name() {
		return asset_type_name;
	}

	public void setAsset_type_name(String asset_type_name) {
		this.asset_type_name = asset_type_name;
	}

	public void setCopy_code(String value) {
		this.copy_code = value;
	}
		
	public String getCopy_code() {
		return this.copy_code;
	}
	public void setAsset_code(String value) {
		this.asset_code = value;
	}
		
	public String getAsset_code() {
		return this.asset_code;
	}
	public void setAsset_name(String value) {
		this.asset_name = value;
	}
		
	public String getAsset_name() {
		return this.asset_name;
	}
	public void setAsset_type_code(String value) {
		this.asset_type_code = value;
	}
		
	public String getAsset_type_code() {
		return this.asset_type_code;
	}
	public void setPrim_value(double value) {
		this.prim_value = value;
	}
		
	public double getPrim_value() {
		return this.prim_value;
	}
	public void setStart_date(String value) {
		this.start_date = value;
	}
		
	public String getStart_date() {
		return this.start_date;
	}
	public void setEnd_date(String value) {
		this.end_date = value;
	}
		
	public String getEnd_date() {
		return this.end_date;
	}
	public void setDep_year(Integer value) {
		this.dep_year = value;
	}
		
	public Integer getDep_year() {
		return this.dep_year;
	}

}