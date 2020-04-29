package com.chd.hrp.hr.entity.scientificresearch;

import java.io.Serializable;

/**
 * 学术论文积分
 * @author Administrator
 *
 */
public class HrAcademicPaperIntegration implements Serializable {


	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 
	 */
	private Integer group_id;
	
	/**
	 * 
	 */
	private Integer hos_id;
	
	private String year;
	/**
	 * DIC_PAPER_TYPE
	 */
	private String paper_type_code;
	private String paper_type_name;
	
	/**
	 * 
	 */
	private Integer affect_para;
	private String affect_name;
	
	/**
	 * 
	 */
	private Double score;
	
	private Integer paper;
	
	/**
	 * 
	 */
	private String note;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;


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


public String getPaper_type_code() {
	return paper_type_code;
}


public void setPaper_type_code(String paper_type_code) {
	this.paper_type_code = paper_type_code;
}


public String getPaper_type_name() {
	return paper_type_name;
}


public void setPaper_type_name(String paper_type_name) {
	this.paper_type_name = paper_type_name;
}


public Integer getAffect_para() {
	return affect_para;
}


public void setAffect_para(Integer affect_para) {
	this.affect_para = affect_para;
}


public String getAffect_name() {
	return affect_name;
}


public void setAffect_name(String affect_name) {
	this.affect_name = affect_name;
}




public Double getScore() {
	return score;
}


public void setScore(Double score) {
	this.score = score;
}


public Integer getPaper() {
	return paper;
}


public void setPaper(Integer paper) {
	this.paper = paper;
}


public String getNote() {
	return note;
}


public void setNote(String note) {
	this.note = note;
}


public String getError_type() {
	return error_type;
}


public void setError_type(String error_type) {
	this.error_type = error_type;
}


public String getYear() {
	return year;
}


public void setYear(String year) {
	this.year = year;
}
	
	
    
}
