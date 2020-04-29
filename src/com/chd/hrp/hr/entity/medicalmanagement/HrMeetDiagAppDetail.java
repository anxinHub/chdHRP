package com.chd.hrp.hr.entity.medicalmanagement;

import java.io.Serializable;
import java.util.Date;

public class HrMeetDiagAppDetail implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;
	
	/**
	 * 集团
	 */
	private Integer group_id;
	
	/**医院
	 * 
	 */
	private Integer hos_id;
	
	/**
	 * 会诊单号
	 */
	private String bill_no;
	/**
	 * 申请日期
	 */
	private Date app_date;
	/**
	 * 科室
	 */
	private Integer dept_id;
	private String dept_name;
	
	/**
	 * 病案号
	 */
	private String case_no;
	/**
	 * 会诊科室
	 */
	private Integer md_dept_id;
	private String md_dept_name;
	
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
	public String getBill_no() {
		return bill_no;
	}
	public void setBill_no(String bill_no) {
		this.bill_no = bill_no;
	}
	public Date getApp_date() {
		return app_date;
	}
	public void setApp_date(Date app_date) {
		this.app_date = app_date;
	}
	public Integer getDept_id() {
		return dept_id;
	}
	public void setDept_id(Integer dept_id) {
		this.dept_id = dept_id;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public String getCase_no() {
		return case_no;
	}
	public void setCase_no(String case_no) {
		this.case_no = case_no;
	}
	
	public Integer getMd_dept_id() {
		return md_dept_id;
	}
	public void setMd_dept_id(Integer md_dept_id) {
		this.md_dept_id = md_dept_id;
	}
	public String getError_type() {
		return error_type;
	}
	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	public String getMd_dept_name() {
		return md_dept_name;
	}
	public void setMd_dept_name(String md_dept_name) {
		this.md_dept_name = md_dept_name;
	}
	
	
	
}
