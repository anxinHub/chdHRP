package com.chd.hrp.acc.entity;

import java.io.Serializable;

public class AccWageTaxCal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 公式ID
	 */
	private Long cal_id;
	/**
	 * 集团ID
	 */
	private Long group_id;
	/**
	 * 医院ID
	 */
	private Long hos_id;
	/**
	 * 账套编码
	 */
	private String copy_code;
	/**
	 * 会计年度
	 */
	private String acc_year;
	/**
	 * 工资套编码
	 */
	private String wage_code;
	/**
	 * 工资套名称
	 */
	private String wage_name;
	/**
	 * 项目ID
	 */
	private Long item_id;
	/**
	 * 职工分类
	 */
	private String kind_code;
	
	private String kind_name;
	/**
	 * 公式名称
	 */
	private String cal_name;
	/**
	 * 公式转译
	 */
	private String cal_eng;
	/**
	 * 备注
	 */
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

	public Long getItem_id() {
		return item_id;
	}

	public void setItem_id(Long item_id) {
		this.item_id = item_id;
	}

	public String getKind_code() {
		return kind_code;
	}

	public void setKind_code(String kind_code) {
		this.kind_code = kind_code;
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

	public String getWage_name() {
		return wage_name;
	}

	public void setWage_name(String wage_name) {
		this.wage_name = wage_name;
	}

	public String getKind_name() {
		return kind_name;
	}

	public void setKind_name(String kind_name) {
		this.kind_name = kind_name;
	}
	
}
