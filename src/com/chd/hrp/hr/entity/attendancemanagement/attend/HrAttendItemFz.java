package com.chd.hrp.hr.entity.attendancemanagement.attend;

import java.io.Serializable;

public class HrAttendItemFz implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;
	// 集团ID
	private Integer group_id;
	// 医院ID
	private Integer hos_id;
	
	// 项目编码
	private String attend_code_fz;
	// 项目名称
	private String attend_name_fz;
	//是否在考勤项表显示
	private Integer attend_result_is_fz;
	private String attend_result_is_fzname;
	
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

	public String getAttend_code_fz() {
		return attend_code_fz;
	}

	public void setAttend_code_fz(String attend_code_fz) {
		this.attend_code_fz = attend_code_fz;
	}

	public String getAttend_name_fz() {
		return attend_name_fz;
	}

	public void setAttend_name_fz(String attend_name_fz) {
		this.attend_name_fz = attend_name_fz;
	}

	public Integer getAttend_result_is_fz() {
		return attend_result_is_fz;
	}

	public void setAttend_result_is_fz(Integer attend_result_is_fz) {
		this.attend_result_is_fz = attend_result_is_fz;
	}

	public String getAttend_result_is_fzname() {
		return attend_result_is_fzname;
	}

	public void setAttend_result_is_fzname(String attend_result_is_fzname) {
		this.attend_result_is_fzname = attend_result_is_fzname;
	}

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	
}
