package com.chd.hrp.hr.entity.nursing;

import java.io.Serializable;

/**
 * 教学能力
 * @author Administrator
 *
 */
public class HrTeachingAbility implements Serializable {


	
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
	private String teach_date;
	
	/**
	 * 
	 */
	private Integer emp_id;
	private String emp_code;
	private String emp_name;
	
	/**
	 * 
	 */
	private String duty_code;
	
	/**
	 * 
	 */
	private String title_code;
	
	/**
	 * 
	 */
	private String level_code;
	
	/**
	 * DIC_TEACH_TYPE
	 */
	private String teach_type;
	private String teach_type_name;
	
	/**
	 * 0：新建 1:提交
	 */
	private Integer state;
	
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


public String getTeach_date() {
	return teach_date;
}


public void setTeach_date(String teach_date) {
	this.teach_date = teach_date;
}


public Integer getEmp_id() {
	return emp_id;
}


public void setEmp_id(Integer emp_id) {
	this.emp_id = emp_id;
}


public String getEmp_code() {
	return emp_code;
}


public void setEmp_code(String emp_code) {
	this.emp_code = emp_code;
}


public String getEmp_name() {
	return emp_name;
}


public void setEmp_name(String emp_name) {
	this.emp_name = emp_name;
}


public String getDuty_code() {
	return duty_code;
}


public void setDuty_code(String duty_code) {
	this.duty_code = duty_code;
}


public String getTitle_code() {
	return title_code;
}


public void setTitle_code(String title_code) {
	this.title_code = title_code;
}


public String getLevel_code() {
	return level_code;
}


public void setLevel_code(String level_code) {
	this.level_code = level_code;
}


public String getTeach_type() {
	return teach_type;
}


public void setTeach_type(String teach_type) {
	this.teach_type = teach_type;
}


public String getTeach_type_name() {
	return teach_type_name;
}


public void setTeach_type_name(String teach_type_name) {
	this.teach_type_name = teach_type_name;
}


public Integer getState() {
	return state;
}


public void setState(Integer state) {
	this.state = state;
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
