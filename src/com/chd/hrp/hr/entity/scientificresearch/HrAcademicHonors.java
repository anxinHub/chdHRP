package com.chd.hrp.hr.entity.scientificresearch;

import java.io.Serializable;
import java.util.Date;

/**
 * 个人学术荣誉申请
 * @author Administrator
 *
 */
public class HrAcademicHonors implements Serializable {


	
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
	private Integer emp_id;
	private String emp_code;
	private String emp_name;
	
	/**
	 * 
	 */
	private String apply_no;
	
	/**
	 * DIC_ACADE_HONOR
	 */
	private String honor_code;
	private String honor_name;
	
	/**
	 * 
	 */
	private Date get_date;
	
	/**
	 * 
	 */
	private String accessory;
	
	/**
	 * 
	 */
	private Date apply_date;
	
	/**
	 * USER_ID
	 */
	private Long audit_id;
	private String audit_name;
	
	/**
	 * 
	 */
	private Date audit_date;
	
	/**
	 * 0:新建 1:提交 2:审核
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


public String getApply_no() {
	return apply_no;
}


public void setApply_no(String apply_no) {
	this.apply_no = apply_no;
}


public String getHonor_code() {
	return honor_code;
}


public void setHonor_code(String honor_code) {
	this.honor_code = honor_code;
}


public String getHonor_name() {
	return honor_name;
}


public void setHonor_name(String honor_name) {
	this.honor_name = honor_name;
}


public Date getGet_date() {
	return get_date;
}


public void setGet_date(Date get_date) {
	this.get_date = get_date;
}


public String getAccessory() {
	return accessory;
}


public void setAccessory(String accessory) {
	this.accessory = accessory;
}


public Date getApply_date() {
	return apply_date;
}


public void setApply_date(Date apply_date) {
	this.apply_date = apply_date;
}


public Long getAudit_id() {
	return audit_id;
}


public void setAudit_id(Long audit_id) {
	this.audit_id = audit_id;
}


public String getAudit_name() {
	return audit_name;
}


public void setAudit_name(String audit_name) {
	this.audit_name = audit_name;
}


public Date getAudit_date() {
	return audit_date;
}


public void setAudit_date(Date audit_date) {
	this.audit_date = audit_date;
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
