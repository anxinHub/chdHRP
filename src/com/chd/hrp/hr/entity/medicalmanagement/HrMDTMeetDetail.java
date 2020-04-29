/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.hr.entity.medicalmanagement;

import java.io.Serializable;
import java.util.Date;

public class HrMDTMeetDetail implements Serializable{
	/**
	 * 
	 * @Description:
	 * 
	 * @Table:
	 * HR_MEET_MDT
	 * @Author: ade
	 * @email:  ade@e-tonggroup.com
	 * @Version: 1.0
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 集团ID
	 */
	private Double group_id;
	/**
	 * 医院ID
	 */
	private Double hos_id;
	/**
	 * 记录单号
	 */
	private String rc_no;
	/**
	 * 会议日期
	 */
	private Date rc_date;
	/**
	 * 团队名称
	 */
	private String team_name;
	/**
	 * 讨论主题
	 */
	private String title;
	/**
	 * 科室ID
	 */
	private Integer dept_id;
	/**
	 * 职工ID
	 */
	private Integer emp_id;
	
	/**
	 * 科室名称
	 */
	private String dept_name;
	
	/**
	 * 参会人员名称
	 */
	private String emp_name;
	
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public String getEmp_name() {
		return emp_name;
	}
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
	public Double getGroup_id() {
		return group_id;
	}
	public void setGroup_id(Double group_id) {
		this.group_id = group_id;
	}
	public Double getHos_id() {
		return hos_id;
	}
	public void setHos_id(Double hos_id) {
		this.hos_id = hos_id;
	}
	public String getRc_no() {
		return rc_no;
	}
	public void setRc_no(String rc_no) {
		this.rc_no = rc_no;
	}
	public Date getRc_date() {
		return rc_date;
	}
	public void setRc_date(Date rc_date) {
		this.rc_date = rc_date;
	}
	public String getTeam_name() {
		return team_name;
	}
	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getDept_id() {
		return dept_id;
	}
	public void setDept_id(Integer dept_id) {
		this.dept_id = dept_id;
	}
	public Integer getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(Integer emp_id) {
		this.emp_id = emp_id;
	}
	
}
