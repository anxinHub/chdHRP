package com.chd.hrp.hr.entity.nursing;

import java.io.Serializable;

/**
 * 重症护理能力
 * @author Administrator
 *
 */
public class HrIntensiveCare implements Serializable {
	


	
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
	 * 
	 */
	private Integer dept_id;
	private String dept_name;
	/**
	 * 
	 */
	private Integer dept_no;
	
	/**
	 * 
	 */
	private String in_hos_no;
	
	/**
	 * 
	 */
	private String patient;
	
	/**
	 * 
	 */
	private String diagnose;
	
	/**
	 * 0：不符合，1:符合
	 */
	private Integer is_same;
	private String same_name;
	
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


public String getYear() {
	return year;
}


public void setYear(String year) {
	this.year = year;
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


public Integer getDept_no() {
	return dept_no;
}


public void setDept_no(Integer dept_no) {
	this.dept_no = dept_no;
}


public String getIn_hos_no() {
	return in_hos_no;
}


public void setIn_hos_no(String in_hos_no) {
	this.in_hos_no = in_hos_no;
}


public String getPatient() {
	return patient;
}


public void setPatient(String patient) {
	this.patient = patient;
}


public String getDiagnose() {
	return diagnose;
}


public void setDiagnose(String diagnose) {
	this.diagnose = diagnose;
}


public Integer getIs_same() {
	return is_same;
}


public void setIs_same(Integer is_same) {
	this.is_same = is_same;
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


public String getSame_name() {
	return same_name;
}


public void setSame_name(String same_name) {
	this.same_name = same_name;
}
	
	

}
