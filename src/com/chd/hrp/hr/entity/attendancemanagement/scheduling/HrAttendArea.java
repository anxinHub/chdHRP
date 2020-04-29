package com.chd.hrp.hr.entity.attendancemanagement.scheduling;

import java.io.Serializable;

public class HrAttendArea implements Serializable {
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
	 * 区域编码
	 */
	private String attend_areacode;
	/**
	 * 区域名称
	 */
	private String attend_area_name;
	/**
	 * 班次分类
	 */
	private String attend_class_typecode;
	private String attend_class_typename;
	/**
	 * 排班规则
	 */
	private Integer pb_gz;
	private String pb_gz_name;
	/**
	 * 倒班规则
	 */
	private Integer db_gz;
	private String db_gz_name;
	/**
	 * 医护属性
	 */
	private String yh_code;
	private String yh_code_name;
	/**
	 * 区域科室编码
	 */
	private String dept_id;
	private String dept_name;
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
	public String getAttend_areacode() {
		return attend_areacode;
	}
	public void setAttend_areacode(String attend_areacode) {
		this.attend_areacode = attend_areacode;
	}
	public String getAttend_area_name() {
		return attend_area_name;
	}
	public void setAttend_area_name(String attend_area_name) {
		this.attend_area_name = attend_area_name;
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
	public String getDept_id() {
		return dept_id;
	}
	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}
	
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public String getError_type() {
		return error_type;
	}
	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	
	public Integer getPb_gz() {
		return pb_gz;
	}
	public void setPb_gz(Integer pb_gz) {
		this.pb_gz = pb_gz;
	}
	public String getPb_gz_name() {
		return pb_gz_name;
	}
	public void setPb_gz_name(String pb_gz_name) {
		this.pb_gz_name = pb_gz_name;
	}
	public Integer getDb_gz() {
		return db_gz;
	}
	public void setDb_gz(Integer db_gz) {
		this.db_gz = db_gz;
	}
	public String getDb_gz_name() {
		return db_gz_name;
	}
	public void setDb_gz_name(String db_gz_name) {
		this.db_gz_name = db_gz_name;
	}
	public String getYh_code() {
		return yh_code;
	}
	public void setYh_code(String yh_code) {
		this.yh_code = yh_code;
	}
	public String getYh_code_name() {
		return yh_code_name;
	}
	public void setYh_code_name(String yh_code_name) {
		this.yh_code_name = yh_code_name;
	}
	
	
	
}
