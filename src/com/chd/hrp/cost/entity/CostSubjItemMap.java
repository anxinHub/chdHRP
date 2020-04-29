package com.chd.hrp.cost.entity;

import java.io.Serializable;

public class CostSubjItemMap implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long group_id;
	
	private long hos_id;
	
	private String copy_code;
	
	private String acc_year;
	
	private String subj_code;
	
	private String subj_name;
	
	private String item_code;
	
	private String item_name;

	public long getGroup_id() {
		return group_id;
	}

	public long getHos_id() {
		return hos_id;
	}

	public String getCopy_code() {
		return copy_code;
	}

	public String getAcc_year() {
		return acc_year;
	}

	public String getSubj_code() {
		return subj_code;
	}

	public String getSubj_name() {
		return subj_name;
	}

	public String getItem_code() {
		return item_code;
	}

	public String getItem_name() {
		return item_name;
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

	public void setAcc_year(String acc_year) {
		this.acc_year = acc_year;
	}

	public void setSubj_code(String subj_code) {
		this.subj_code = subj_code;
	}

	public void setSubj_name(String subj_name) {
		this.subj_name = subj_name;
	}

	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

}

