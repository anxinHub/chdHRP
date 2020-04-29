package com.chd.hrp.hr.entity.attendancemanagement.scheduling;

import java.io.Serializable;
import java.util.Date;

public class HrAttendClass implements Serializable {
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
	 * 班次编码
	 */
	private String attend_classcode;
	/**
	 * 考勤项目编码
	 */
	private String attend_code;
	private String attend_name;
	/**
	 * 班次分类
	 */
	private String attend_class_typecode;
	private String attend_class_typename;
	/**
	 * 区域编码
	 */
	private String attend_areacode;
	private String attend_area_name;
	/**
	 * 班次名称
	 */
    private String attend_classname;
	/**
	 * 班次简称
	 */
    private String attend_classsname;
    /**
     * 上班时间1
     */
    private Date attend_begtime1;
    /**
     * 下班时间1
     */
    private Date attend_endtime1;
    /**
     * 上班时间2
     */
    private Date attend_begtime2;
    /**
     * 下班时间2
     */
    private Date attend_endtime2;
    /**
     * 上班时间3
     */
    private Date attend_begtime3;
    /**
     * 下班时间3
     */
    private Date attend_endtime3;
    /**
     * 核算天数
     */
    private Double  attend_days;
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
	public String getAttend_classcode() {
		return attend_classcode;
	}
	public void setAttend_classcode(String attend_classcode) {
		this.attend_classcode = attend_classcode;
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
	public String getAttend_classname() {
		return attend_classname;
	}
	public void setAttend_classname(String attend_classname) {
		this.attend_classname = attend_classname;
	}
	public String getAttend_classsname() {
		return attend_classsname;
	}
	public void setAttend_classsname(String attend_classsname) {
		this.attend_classsname = attend_classsname;
	}
	public Date getAttend_begtime1() {
		return attend_begtime1;
	}
	public void setAttend_begtime1(Date attend_begtime1) {
		this.attend_begtime1 = attend_begtime1;
	}
	public Date getAttend_endtime1() {
		return attend_endtime1;
	}
	public void setAttend_endtime1(Date attend_endtime1) {
		this.attend_endtime1 = attend_endtime1;
	}
	public Date getAttend_begtime2() {
		return attend_begtime2;
	}
	public void setAttend_begtime2(Date attend_begtime2) {
		this.attend_begtime2 = attend_begtime2;
	}
	public Date getAttend_endtime2() {
		return attend_endtime2;
	}
	public void setAttend_endtime2(Date attend_endtime2) {
		this.attend_endtime2 = attend_endtime2;
	}
	public Date getAttend_begtime3() {
		return attend_begtime3;
	}
	public void setAttend_begtime3(Date attend_begtime3) {
		this.attend_begtime3 = attend_begtime3;
	}
	public Date getAttend_endtime3() {
		return attend_endtime3;
	}
	public void setAttend_endtime3(Date attend_endtime3) {
		this.attend_endtime3 = attend_endtime3;
	}

	public Double getAttend_days() {
		return attend_days;
	}
	public void setAttend_days(Double attend_days) {
		this.attend_days = attend_days;
	}
	public String getError_type() {
		return error_type;
	}
	public void setError_type(String error_type) {
		this.error_type = error_type;
	}

	
}
