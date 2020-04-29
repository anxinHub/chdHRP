package com.chd.hrp.hr.entity.scientificresearch;

import java.io.Serializable;

/**
 * 个人学术荣誉积分
 * @author Administrator
 *
 */
public class HrPersonalAcademicCredit implements Serializable {
	/*
	 * 集团ID
	 */
	private Integer group_id;
	/*
	 * 医院ID
	 */
	private Integer hos_id;
	/*
	 * 年度
	 */
	private String year;
	/*
	 * 学术地位编码
	 */
	private String honor_code;
	/*
	 * 学术地位积分
	 */
	private Double score;
	/*
	 * 备注
	 */
	private String note;
	
	private String field_col_code;
	private String field_col_name;
	private Integer acade_honor;
	
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
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getHonor_code() {
		return honor_code;
	}
	public void setHonor_code(String honor_code) {
		this.honor_code = honor_code;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getField_col_code() {
		return field_col_code;
	}
	public void setField_col_code(String field_col_code) {
		this.field_col_code = field_col_code;
	}
	public String getField_col_name() {
		return field_col_name;
	}
	public void setField_col_name(String field_col_name) {
		this.field_col_name = field_col_name;
	}
	public Integer getAcade_honor() {
		return acade_honor;
	}
	public void setAcade_honor(Integer acade_honor) {
		this.acade_honor = acade_honor;
	}
	
}
