package com.chd.hrp.acc.entity;

import java.io.Serializable;

public class AccWageItemCal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long cal_id;
	
	private Long group_id;
	
	private Long hos_id;
	
	private String copy_code;
	
	private String acc_year;
	
	private String wage_code;
	
	private String wage_name;
	
	private Long item_id;
	
	private String item_code;
	
	private String item_name;
	
	private String kind_code;
	
	private String kind_name;
	
	private String cal_name;
	
	private String cal_eng;
	
	private String note;

	public Long getCal_id() {
		return cal_id;
	}

	public void setCal_id(Long cal_id) {
		this.cal_id = cal_id;
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

	public String getAcc_year() {
		return acc_year;
	}

	public void setAcc_year(String acc_year) {
		this.acc_year = acc_year;
	}

	public String getWage_code() {
		return wage_code;
	}

	public void setWage_code(String wage_code) {
		this.wage_code = wage_code;
	}

	public String getWage_name() {
		return wage_name;
	}

	public void setWage_name(String wage_name) {
		this.wage_name = wage_name;
	}

	public Long getItem_id() {
		return item_id;
	}

	public void setItem_id(Long item_id) {
		this.item_id = item_id;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
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

	public String getCal_name() {
		return cal_name;
	}

	public void setCal_name(String cal_name) {
		this.cal_name = cal_name;
	}

	public String getCal_eng() {
		return cal_eng;
	}

	public void setCal_eng(String cal_eng) {
		this.cal_eng = cal_eng;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getItem_code() {
		return item_code;
	}

	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}
	
}
