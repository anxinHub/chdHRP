package com.chd.hrp.pac.entity.basicset.doc;

import java.io.Serializable;

public class PactDocEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9175236788876565422L;
	private Integer group_id;
	private Integer hos_id;
	private String copy_code;
	private Integer doc_id;
	private String pact_code;
	private String doc_type;
	private String doc_name;
	private Integer dept_id;
	private Integer dept_no;
	private Integer emp_id;
	private String location;
	private String file_path;
	private String pact_state;

	private String state_name;
	private String doc_type_name;
	private String dept_name;
	private String emp_name;

	public String getPact_state() {
		return pact_state;
	}

	public void setPact_state(String pact_state) {
		this.pact_state = pact_state;
	}

	public String getState_name() {
		return state_name;
	}

	public void setState_name(String state_name) {
		this.state_name = state_name;
	}

	public String getDoc_type_name() {
		return doc_type_name;
	}

	public void setDoc_type_name(String doc_type_name) {
		this.doc_type_name = doc_type_name;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getEmp_name() {
		return emp_name;
	}

	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}

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

	public String getCopy_code() {
		return copy_code;
	}

	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}

	public Integer getDoc_id() {
		return doc_id;
	}

	public void setDoc_id(Integer doc_id) {
		this.doc_id = doc_id;
	}

	public String getPact_code() {
		return pact_code;
	}

	public void setPact_code(String pact_code) {
		this.pact_code = pact_code;
	}

	public String getDoc_type() {
		return doc_type;
	}

	public void setDoc_type(String doc_type) {
		this.doc_type = doc_type;
	}

	public String getDoc_name() {
		return doc_name;
	}

	public void setDoc_name(String doc_name) {
		this.doc_name = doc_name;
	}

	public Integer getDept_id() {
		return dept_id;
	}

	public void setDept_id(Integer dept_id) {
		this.dept_id = dept_id;
	}

	public Integer getDept_no() {
		return dept_no;
	}

	public void setDept_no(Integer dept_no) {
		this.dept_no = dept_no;
	}

	public Integer getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(Integer emp_id) {
		this.emp_id = emp_id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
