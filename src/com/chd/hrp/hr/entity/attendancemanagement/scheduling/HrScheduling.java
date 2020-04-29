package com.chd.hrp.hr.entity.attendancemanagement.scheduling;

import java.io.Serializable;
import java.util.Date;

//排班处理
public class HrScheduling implements Serializable {
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
	 * 排班编码
	 */
	private String attend_pbcode;
	/**
	 * 区域编码
	 */
	private String attend_areacode;
	/**
	 * 排班名称
	 */
	private String attend_pbname;
	
	/**
	 * 排班规则
	 */
	private Integer attend_pbrule;
	private String pbrule_name;
	/**
	 * 备注
	 */
	private String attend_pbnote;
	private String [] params;
	private HrSchedulingEmp hrSchedulingEmp;
	/**
	 * 状态
	 */
	private Integer attend_over_is;
	private String attend_over_name;
	
	/**
	 * 审签状态
	 */
	private Integer attend_pbcheck_state;
	private String  pbcheck_state_name;
	/**
	 * 审签人
	 */
	private Integer attend_pbchecker;
	private String pbchecker_name;
	/**
	 * 审签日期
	 */
	private Date attend_pbcheck_date;
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
	public String getAttend_pbcode() {
		return attend_pbcode;
	}
	public void setAttend_pbcode(String attend_pbcode) {
		this.attend_pbcode = attend_pbcode;
	}
	public String getAttend_areacode() {
		return attend_areacode;
	}
	public void setAttend_areacode(String attend_areacode) {
		this.attend_areacode = attend_areacode;
	}
	public String getAttend_pbname() {
		return attend_pbname;
	}
	public void setAttend_pbname(String attend_pbname) {
		this.attend_pbname = attend_pbname;
	}

	public Integer getAttend_pbrule() {
		return attend_pbrule;
	}
	public void setAttend_pbrule(Integer attend_pbrule) {
		this.attend_pbrule = attend_pbrule;
	}
	public String getAttend_pbnote() {
		return attend_pbnote;
	}
	public void setAttend_pbnote(String attend_pbnote) {
		this.attend_pbnote = attend_pbnote;
	}
	public String getError_type() {
		return error_type;
	}
	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	public String[] getParams() {
		return params;
	}
	public void setParams(String[] params) {
		this.params = params;
	}
	public HrSchedulingEmp getHrSchedulingEmp() {
		return hrSchedulingEmp;
	}
	public void setHrSchedulingEmp(HrSchedulingEmp hrSchedulingEmp) {
		this.hrSchedulingEmp = hrSchedulingEmp;
	}
	public String getPbrule_name() {
		return pbrule_name;
	}
	public void setPbrule_name(String pbrule_name) {
		this.pbrule_name = pbrule_name;
	}
	
	public Integer getAttend_over_is() {
		return attend_over_is;
	}
	public void setAttend_over_is(Integer attend_over_is) {
		this.attend_over_is = attend_over_is;
	}
	public String getAttend_over_name() {
		return attend_over_name;
	}
	public void setAttend_over_name(String attend_over_name) {
		this.attend_over_name = attend_over_name;
	}
	public Integer getAttend_pbcheck_state() {
		return attend_pbcheck_state;
	}
	public void setAttend_pbcheck_state(Integer attend_pbcheck_state) {
		this.attend_pbcheck_state = attend_pbcheck_state;
	}
	public String getPbcheck_state_name() {
		return pbcheck_state_name;
	}
	public void setPbcheck_state_name(String pbcheck_state_name) {
		this.pbcheck_state_name = pbcheck_state_name;
	}
	public Integer getAttend_pbchecker() {
		return attend_pbchecker;
	}
	public void setAttend_pbchecker(Integer attend_pbchecker) {
		this.attend_pbchecker = attend_pbchecker;
	}
	public String getPbchecker_name() {
		return pbchecker_name;
	}
	public void setPbchecker_name(String pbchecker_name) {
		this.pbchecker_name = pbchecker_name;
	}
	public Date getAttend_pbcheck_date() {
		return attend_pbcheck_date;
	}
	public void setAttend_pbcheck_date(Date attend_pbcheck_date) {
		this.attend_pbcheck_date = attend_pbcheck_date;
	}
	
}
