package com.chd.hrp.hr.entity.attendancemanagement.leave;

import java.io.Serializable;
import java.util.Date;

/**
 * 职工销假申请表
 * @author Administrator
 *
 */
public class HrTerminateleave implements Serializable {
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
	 * 销假申请编码
	 */
	private String attend_xxjapply_code;
	/**
	 * 休假申请编码
	 */
	private String attend_xjapply_code;
	/**
	 * 职工编码
	 */
	private Integer emp_id;
	private String emp_name;
	private String dept_name;
	/**
	 * 申请回院时间
	 */
	private Date attend_xxj_backtime;
	/**
	 * 实际休假天数
	 */
	private Integer attend_xxjdays;
	/**
	 * 
	 */
	private Integer attend_xxjbegdate_ampm;
	/**
	 * 
	 */
	private Date attend_xxjreg_date	;
	/**
	 * 
	 */
	private Integer attend_xjstate;
	private String attend_xxjstate_name;
	/**
	 * 
	 */
	private Integer attend_xjloginer;
	private String sys_name;
	/**
	 * 
	 */
	private Date attend_xjreg_operdate;
	/**
	 * 
	 */
	private String attend_xxj_note;
	/**
	 * 错误信息
	 */
	private String error_type;
	private HrApplyingLeaves  hrApplyingLeaves ;
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
	public String getAttend_xxjapply_code() {
		return attend_xxjapply_code;
	}
	public void setAttend_xxjapply_code(String attend_xxjapply_code) {
		this.attend_xxjapply_code = attend_xxjapply_code;
	}
	public String getAttend_xjapply_code() {
		return attend_xjapply_code;
	}
	public void setAttend_xjapply_code(String attend_xjapply_code) {
		this.attend_xjapply_code = attend_xjapply_code;
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
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public Date getAttend_xxj_backtime() {
		return attend_xxj_backtime;
	}
	public void setAttend_xxj_backtime(Date attend_xxj_backtime) {
		this.attend_xxj_backtime = attend_xxj_backtime;
	}
	public Integer getAttend_xxjdays() {
		return attend_xxjdays;
	}
	public void setAttend_xxjdays(Integer attend_xxjdays) {
		this.attend_xxjdays = attend_xxjdays;
	}
	public Integer getAttend_xxjbegdate_ampm() {
		return attend_xxjbegdate_ampm;
	}
	public void setAttend_xxjbegdate_ampm(Integer attend_xxjbegdate_ampm) {
		this.attend_xxjbegdate_ampm = attend_xxjbegdate_ampm;
	}
	public Date getAttend_xxjreg_date() {
		return attend_xxjreg_date;
	}
	public void setAttend_xxjreg_date(Date attend_xxjreg_date) {
		this.attend_xxjreg_date = attend_xxjreg_date;
	}
	public Integer getAttend_xjstate() {
		return attend_xjstate;
	}
	public void setAttend_xjstate(Integer attend_xjstate) {
		this.attend_xjstate = attend_xjstate;
	}
	public String getAttend_xxjstate_name() {
		return attend_xxjstate_name;
	}
	public void setAttend_xxjstate_name(String attend_xxjstate_name) {
		this.attend_xxjstate_name = attend_xxjstate_name;
	}
	public Integer getAttend_xjloginer() {
		return attend_xjloginer;
	}
	public void setAttend_xjloginer(Integer attend_xjloginer) {
		this.attend_xjloginer = attend_xjloginer;
	}
	public String getSys_name() {
		return sys_name;
	}
	public void setSys_name(String sys_name) {
		this.sys_name = sys_name;
	}
	public Date getAttend_xjreg_operdate() {
		return attend_xjreg_operdate;
	}
	public void setAttend_xjreg_operdate(Date attend_xjreg_operdate) {
		this.attend_xjreg_operdate = attend_xjreg_operdate;
	}
	public String getAttend_xxj_note() {
		return attend_xxj_note;
	}
	public void setAttend_xxj_note(String attend_xxj_note) {
		this.attend_xxj_note = attend_xxj_note;
	}
	public String getError_type() {
		return error_type;
	}
	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	public HrApplyingLeaves getHrApplyingLeaves() {
		return hrApplyingLeaves;
	}
	public void setHrApplyingLeaves(HrApplyingLeaves hrApplyingLeaves) {
		this.hrApplyingLeaves = hrApplyingLeaves;
	}
	
}
