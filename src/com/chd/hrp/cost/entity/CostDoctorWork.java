package com.chd.hrp.cost.entity;

import java.io.Serializable;

public class CostDoctorWork implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;
	
	private Long group_id;
	
	private Long hos_id;
	
	private String copy_code;
	
	private String acc_year;
	
	private String acc_month;
	
	private Long dept_id;
	
	private Long dept_no;
	
	private String dept_code;
	
	private String dept_name;
	
    private Long emp_id;
	
	private Long emp_no;
	
	private String emp_code;
	
	private String emp_name;
	
	private Long doctor_num;

	public Long getGroup_id() {
		return group_id;
	}

	public Long getHos_id() {
		return hos_id;
	}

	public String getCopy_code() {
		return copy_code;
	}

	public String getAcc_year() {
		return acc_year;
	}

	public String getAcc_month() {
		return acc_month;
	}

	public Long getDept_id() {
		return dept_id;
	}

	public Long getDept_no() {
		return dept_no;
	}

	public String getDept_code() {
		return dept_code;
	}

	public String getDept_name() {
		return dept_name;
	}

	public Long getEmp_id() {
		return emp_id;
	}

	public Long getEmp_no() {
		return emp_no;
	}

	public String getEmp_code() {
		return emp_code;
	}

	public String getEmp_name() {
		return emp_name;
	}

	public Long getDoctor_num() {
		return doctor_num;
	}

	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}

	public void setHos_id(Long hos_id) {
		this.hos_id = hos_id;
	}

	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}

	public void setAcc_year(String acc_year) {
		this.acc_year = acc_year;
	}

	public void setAcc_month(String acc_month) {
		this.acc_month = acc_month;
	}

	public void setDept_id(Long dept_id) {
		this.dept_id = dept_id;
	}

	public void setDept_no(Long dept_no) {
		this.dept_no = dept_no;
	}

	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public void setEmp_id(Long emp_id) {
		this.emp_id = emp_id;
	}

	public void setEmp_no(Long emp_no) {
		this.emp_no = emp_no;
	}

	public void setEmp_code(String emp_code) {
		this.emp_code = emp_code;
	}

	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}

	public void setDoctor_num(Long doctor_num) {
		this.doctor_num = doctor_num;
	}
	
}
