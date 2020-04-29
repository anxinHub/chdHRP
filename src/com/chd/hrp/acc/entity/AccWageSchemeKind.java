package com.chd.hrp.acc.entity;

import java.io.Serializable;

public class AccWageSchemeKind implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long detail_id;
	
	private Long scheme_id;
	
	private String kind_code;
	
	private String emp_id;
	
	private String emp_code;
	
	private String emp_name;
	
	private String dept_name;

	public Long getDetail_id() {
		return detail_id;
	}

	public void setDetail_id(Long detail_id) {
		this.detail_id = detail_id;
	}

	public Long getScheme_id() {
		return scheme_id;
	}

	public void setScheme_id(Long scheme_id) {
		this.scheme_id = scheme_id;
	}

	public String getKind_code() {
		return kind_code;
	}

	public void setKind_code(String kind_code) {
		this.kind_code = kind_code;
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

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}
	
	
}
