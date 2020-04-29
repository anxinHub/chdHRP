package com.chd.hrp.hr.entity.scientificresearch;

import java.io.Serializable;

/**
 * 个人学术地位积分
 * @author Administrator
 *
 */
public class HrAcademicStatusIntegral implements Serializable {


	
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
	private String year;
	
	/**
	 * DIC_ACADE_STATUS
	 */
	private String status_code;
	private String status_name;
	
	/**
	 * 
	 */
	private Double score;
	private Integer acade_status;
	
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


public String getStatus_code() {
	return status_code;
}


public void setStatus_code(String status_code) {
	this.status_code = status_code;
}


public String getStatus_name() {
	return status_name;
}


public void setStatus_name(String status_name) {
	this.status_name = status_name;
}




public Double getScore() {
	return score;
}


public void setScore(Double score) {
	this.score = score;
}


public Integer getAcade_status() {
	return acade_status;
}


public void setAcade_status(Integer acade_status) {
	this.acade_status = acade_status;
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
