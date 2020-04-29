/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.entity.medicalmanagement;

import java.io.Serializable;
import java.util.Date;
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
 
public class HrMeetMdt implements Serializable{
	/**
	 * 
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
	 * 记录人
	 */
	private String recorder;
	/**
	 * 主持人
	 */
	private String compere;
	/**
	 * 会议内容
	 */
	private String content;
	/**
	 * 地点
	 */
	private String site;
	/**
	 * 是否提交
	 */
	private Integer is_commit;
	
	/**
	 * 提交状态
	 */
	private String state_name;
	/**
	 * 主持人姓名
	 */
	private String emp_name;
	
	public String getEmp_name() {
		return emp_name;
	}
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
	public String getState_name() {
		return state_name;
	}
	public void setState_name(String state_name) {
		this.state_name = state_name;
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
	public String getRecorder() {
		return recorder;
	}
	public void setRecorder(String recorder) {
		this.recorder = recorder;
	}
	public String getCompere() {
		return compere;
	}
	public void setCompere(String compere) {
		this.compere = compere;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public Integer getIs_commit() {
		return is_commit;
	}
	public void setIs_commit(Integer is_commit) {
		this.is_commit = is_commit;
	}

}
