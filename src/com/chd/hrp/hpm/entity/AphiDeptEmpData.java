package com.chd.hrp.hpm.entity;

import java.io.Serializable;

public class AphiDeptEmpData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2618556740911006763L;
	private Long group_id;

	private Long hos_id;
	
	
	
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
	private String copy_code;
	private String acct_year;
	private String acct_month;
	private Long dept_no;
	private Long dept_id;
	private String dept_name;
	private String emp_amount;
	private String error_type;
	public String getError_type() {
		return error_type;
	}
	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	
	
	public String getCopy_code() {
		return copy_code;
	}
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	public String getAcct_year() {
		return acct_year;
	}
	public void setAcct_year(String acct_year) {
		this.acct_year = acct_year;
	}
	public String getAcct_month() {
		return acct_month;
	}
	public void setAcct_month(String acct_month) {
		this.acct_month = acct_month;
	}
	
	public Long getDept_no() {
		return dept_no;
	}

	public void setDept_no(Long dept_no) {
		this.dept_no = dept_no;
	}

	public Long getDept_id() {
		return dept_id;
	}

	public void setDept_id(Long dept_id) {
		this.dept_id = dept_id;
	}

	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public String getEmp_amount() {
		return emp_amount;
	}
	public void setEmp_amount(String emp_amount) {
		this.emp_amount = emp_amount;
	}
	
}
