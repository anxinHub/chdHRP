package com.chd.hrp.hr.entity.attendancemanagement.scheduling;

import java.io.Serializable;

public class HrAttendClassType implements Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/**
	 * 集团ID
	 */
	private Integer group_id;
	/**
	 * 医院ID
	 */
	private Integer hos_id;

	/**
	 * 班次分类编码
	 */
	private String attend_class_typecode;

	/**
	 * 班次分类名称
	 */
	private String attend_class_typename;
	/**
	 * 错误信息
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

	public String getAttend_class_typecode() {
		return attend_class_typecode;
	}
	public void setAttend_class_typecode(String attend_class_typecode) {
		this.attend_class_typecode = attend_class_typecode;
	}
	public String getAttend_class_typename() {
		return attend_class_typename;
	}
	public void setAttend_class_typename(String attend_class_typename) {
		this.attend_class_typename = attend_class_typename;
	}
	public String getError_type() {
		return error_type;
	}
	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	
}
