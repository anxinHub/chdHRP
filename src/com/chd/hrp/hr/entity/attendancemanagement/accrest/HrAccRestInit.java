package com.chd.hrp.hr.entity.attendancemanagement.accrest;

import java.io.Serializable;

public class HrAccRestInit implements Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	// 集团ID
	private Integer group_id;
	// 医院ID
	private Integer hos_id;
	private Integer emp_id;
	private String emp_code;
	private String emp_name;
	private Integer dept_id;
	private Double attend_accdays;
	private String attend_accnote;
	// 错误信息
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
	public String getEmp_name() {
		return emp_name;
	}
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
	public Integer getDept_id() {
		return dept_id;
	}
	public void setDept_id(Integer dept_id) {
		this.dept_id = dept_id;
	}

	public Double getAttend_accdays() {
		return attend_accdays;
	}
	public void setAttend_accdays(Double attend_accdays) {
		this.attend_accdays = attend_accdays;
	}
	public String getAttend_accnote() {
		return attend_accnote;
	}
	public void setAttend_accnote(String attend_accnote) {
		this.attend_accnote = attend_accnote;
	}
	public String getError_type() {
		return error_type;
	}
	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	public void setEmp_code(String emp_code) {
		this.emp_code = emp_code;
	}
	public String getEmp_code() {
		return emp_code;
	}
	
}
