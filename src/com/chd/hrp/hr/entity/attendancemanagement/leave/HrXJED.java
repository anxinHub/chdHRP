package com.chd.hrp.hr.entity.attendancemanagement.leave;

import java.io.Serializable;

public class HrXJED  implements Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	// 集团ID
	private Integer group_id;
	// 医院ID
	private Integer hos_id;
	//年份
	private String attend_year;
	//人员编码
	private Integer emp_id;
	private String emp_name;
	//考勤项目编码
	private String attend_code;
	private String attend_name;
	//额度（天）
	private Double attend_ed;

	
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


	public String getAttend_year() {
		return attend_year;
	}


	public void setAttend_year(String attend_year) {
		this.attend_year = attend_year;
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


	public String getAttend_code() {
		return attend_code;
	}


	public void setAttend_code(String attend_code) {
		this.attend_code = attend_code;
	}


	public String getAttend_name() {
		return attend_name;
	}


	public void setAttend_name(String attend_name) {
		this.attend_name = attend_name;
	}


	public Double getAttend_ed() {
		return attend_ed;
	}


	public void setAttend_ed(Double attend_ed) {
		this.attend_ed = attend_ed;
	}


	public String getError_type() {
		return error_type;
	}


	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	
	
	
	
}
