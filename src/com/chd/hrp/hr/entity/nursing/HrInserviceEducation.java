package com.chd.hrp.hr.entity.nursing;

import java.io.Serializable;
import java.util.Date;

/**
 * 年度在职教育培训
 * @author Administrator
 *
 */
public class HrInserviceEducation implements Serializable {


	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 
	 */
	private Long group_id;
	
	/**
	 * 
	 */
	private Long hos_id;
	
	/**
	 * 
	 */
	private Date edu_date;
	
	/**
	 * 
	 */
	private String classs_name;
	
	/**
	 * 
	 */
	private String teacher;
	
	private String teacher_name;
	
	/**
	 * 
	 */
	private Double hours;
	
	/**
	 * 
	 */
	private String place;
	
	/**
	 * 
	 */
	private Double student_num;
	
	/**
	 * 0：新建 1:提交
	 */
	private Integer state;
	private String state_name;
	
	/**
	 * 
	 */
	private String note;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;


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


public Date getEdu_date() {
	return edu_date;
}


public void setEdu_date(Date edu_date) {
	this.edu_date = edu_date;
}


public String getClasss_name() {
	return classs_name;
}


public void setClasss_name(String classs_name) {
	this.classs_name = classs_name;
}


public String getTeacher() {
	return teacher;
}


public void setTeacher(String teacher) {
	this.teacher = teacher;
}


public String getTeacher_name() {
	return teacher_name;
}


public void setTeacher_name(String teacher_name) {
	this.teacher_name = teacher_name;
}


public Double getHours() {
	return hours;
}


public void setHours(Double hours) {
	this.hours = hours;
}


public String getPlace() {
	return place;
}


public void setPlace(String place) {
	this.place = place;
}


public Double getStudent_num() {
	return student_num;
}


public void setStudent_num(Double student_num) {
	this.student_num = student_num;
}


public Integer getState() {
	return state;
}


public void setState(Integer state) {
	this.state = state;
}


public String getState_name() {
	return state_name;
}


public void setState_name(String state_name) {
	this.state_name = state_name;
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
