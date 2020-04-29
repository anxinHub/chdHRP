package com.chd.hrp.hr.entity.scientificresearch;

import java.io.Serializable;

/**
 * 科研项目与成果积分
 * @author Administrator
 *
 */
public class HrIntegrationProjectsResults implements Serializable {
	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 
	 */
	private Integer group_id;
	
	/**
	 * 
	 */
	private Integer hos_id;
	
	/**
	 * 
	 */
	private String  year;
	
	/**
	 * DIC_PROJ_LEVEL
	 */
	private String proj_level;
	private String proj_level_name;
	
	/**
	 * 
	 */
	private Double score;
	private Integer proj;
	
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


public String getYear() {
	return year;
}


public void setYear(String year) {
	this.year = year;
}


public String getProj_level() {
	return proj_level;
}


public void setProj_level(String proj_level) {
	this.proj_level = proj_level;
}




public String getProj_level_name() {
	return proj_level_name;
}


public void setProj_level_name(String proj_level_name) {
	this.proj_level_name = proj_level_name;
}


public Double getScore() {
	return score;
}


public void setScore(Double score) {
	this.score = score;
}


public Integer getProj() {
	return proj;
}


public void setProj(Integer proj) {
	this.proj = proj;
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

	
}
