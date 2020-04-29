package com.chd.hrp.htc.entity.task.readydata;

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

public class HtcTitleCostEve implements Serializable {

	
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
	private String title_code;
	private String title_name;
	private long cost_item_no;
	private long cost_item_id;
	private String cost_item_code;
	private String cost_item_name;
	private double time_sum;
	private double cost_min;
	private double cost_avg;
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
	public String getTitle_code() {
		return title_code;
	}
	public String getTitle_name() {
		return title_name;
	}
	public long getCost_item_no() {
		return cost_item_no;
	}
	public long getCost_item_id() {
		return cost_item_id;
	}
	public String getCost_item_code() {
		return cost_item_code;
	}
	public String getCost_item_name() {
		return cost_item_name;
	}
	public double getTime_sum() {
		return time_sum;
	}
	public double getCost_min() {
		return cost_min;
	}
	public double getCost_avg() {
		return cost_avg;
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
	public void setTitle_code(String title_code) {
		this.title_code = title_code;
	}
	public void setTitle_name(String title_name) {
		this.title_name = title_name;
	}
	public void setCost_item_no(long cost_item_no) {
		this.cost_item_no = cost_item_no;
	}
	public void setCost_item_id(long cost_item_id) {
		this.cost_item_id = cost_item_id;
	}
	public void setCost_item_code(String cost_item_code) {
		this.cost_item_code = cost_item_code;
	}
	public void setCost_item_name(String cost_item_name) {
		this.cost_item_name = cost_item_name;
	}
	public void setTime_sum(double time_sum) {
		this.time_sum = time_sum;
	}
	public void setCost_min(double cost_min) {
		this.cost_min = cost_min;
	}
	public void setCost_avg(double cost_avg) {
		this.cost_avg = cost_avg;
	}
	
}