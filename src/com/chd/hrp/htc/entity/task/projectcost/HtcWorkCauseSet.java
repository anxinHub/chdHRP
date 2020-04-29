package com.chd.hrp.htc.entity.task.projectcost;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

public class HtcWorkCauseSet implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private long group_id;
	private long hos_id;
	private String copy_code;
	private String acc_year;
	private String plan_code;
	private String plan_name;
	private long proj_dept_no;
	private long proj_dept_id;
	private String proj_dept_code;
	private String proj_dept_name;
	private long cost_type_id;
	private String cost_type_code;
	private String cost_type_name;
	private String work_code;
	private String work_name;
	private String dir_work_code;
	private String dir_work_name;
	private String pub_work_code;
	private String pub_work_name;
	private String man_work_code;
	private String man_work_name;
	private String ass_work_code;
	private String ass_work_name;
	public long getGroup_id() {
		return group_id;
	}
	public long getHos_id() {
		return hos_id;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public String getAcc_year() {
		return acc_year;
	}
	public String getPlan_code() {
		return plan_code;
	}
	public String getPlan_name() {
		return plan_name;
	}
	public long getProj_dept_no() {
		return proj_dept_no;
	}
	public long getProj_dept_id() {
		return proj_dept_id;
	}
	public String getProj_dept_code() {
		return proj_dept_code;
	}
	public String getProj_dept_name() {
		return proj_dept_name;
	}
	public long getCost_type_id() {
		return cost_type_id;
	}
	public String getCost_type_code() {
		return cost_type_code;
	}
	public String getCost_type_name() {
		return cost_type_name;
	}
	public String getWork_code() {
		return work_code;
	}
	public String getWork_name() {
		return work_name;
	}
	public String getDir_work_code() {
		return dir_work_code;
	}
	public String getDir_work_name() {
		return dir_work_name;
	}
	public String getPub_work_code() {
		return pub_work_code;
	}
	public String getPub_work_name() {
		return pub_work_name;
	}
	public String getMan_work_code() {
		return man_work_code;
	}
	public String getMan_work_name() {
		return man_work_name;
	}
	public String getAss_work_code() {
		return ass_work_code;
	}
	public String getAss_work_name() {
		return ass_work_name;
	}
	public void setGroup_id(long group_id) {
		this.group_id = group_id;
	}
	public void setHos_id(long hos_id) {
		this.hos_id = hos_id;
	}
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	public void setAcc_year(String acc_year) {
		this.acc_year = acc_year;
	}
	public void setPlan_code(String plan_code) {
		this.plan_code = plan_code;
	}
	public void setPlan_name(String plan_name) {
		this.plan_name = plan_name;
	}
	public void setProj_dept_no(long proj_dept_no) {
		this.proj_dept_no = proj_dept_no;
	}
	public void setProj_dept_id(long proj_dept_id) {
		this.proj_dept_id = proj_dept_id;
	}
	public void setProj_dept_code(String proj_dept_code) {
		this.proj_dept_code = proj_dept_code;
	}
	public void setProj_dept_name(String proj_dept_name) {
		this.proj_dept_name = proj_dept_name;
	}
	public void setCost_type_id(long cost_type_id) {
		this.cost_type_id = cost_type_id;
	}
	public void setCost_type_code(String cost_type_code) {
		this.cost_type_code = cost_type_code;
	}
	public void setCost_type_name(String cost_type_name) {
		this.cost_type_name = cost_type_name;
	}
	public void setWork_code(String work_code) {
		this.work_code = work_code;
	}
	public void setWork_name(String work_name) {
		this.work_name = work_name;
	}
	public void setDir_work_code(String dir_work_code) {
		this.dir_work_code = dir_work_code;
	}
	public void setDir_work_name(String dir_work_name) {
		this.dir_work_name = dir_work_name;
	}
	public void setPub_work_code(String pub_work_code) {
		this.pub_work_code = pub_work_code;
	}
	public void setPub_work_name(String pub_work_name) {
		this.pub_work_name = pub_work_name;
	}
	public void setMan_work_code(String man_work_code) {
		this.man_work_code = man_work_code;
	}
	public void setMan_work_name(String man_work_name) {
		this.man_work_name = man_work_name;
	}
	public void setAss_work_code(String ass_work_code) {
		this.ass_work_code = ass_work_code;
	}
	public void setAss_work_name(String ass_work_name) {
		this.ass_work_name = ass_work_name;
	}
}