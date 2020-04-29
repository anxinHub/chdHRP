package com.chd.hrp.htc.entity.task.business;

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

public class HtcSrouceTest implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private long group_id;
	private long hos_id;
	private String acc_year;
	private String copy_code;
	private String plan_code;
	private String plan_name;
	private long proj_dept_id;
	private String proj_dept_code;
	private String proj_dept_name;
	private long cost_item_id;
	private String cost_item_code;
	private String cost_item_name;
	private double total_cost;
	private double charge_direct_cost;
	private double mate_charge_cost;
	private double work_cost;
	private long source_id;
	private String source_code;
	private String source_name;
	public long getGroup_id() {
		return group_id;
	}
	public long getHos_id() {
		return hos_id;
	}
	public String getAcc_year() {
		return acc_year;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public String getPlan_code() {
		return plan_code;
	}
	public String getPlan_name() {
		return plan_name;
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
	public long getCost_item_id() {
		return cost_item_id;
	}
	public String getCost_item_code() {
		return cost_item_code;
	}
	public String getCost_item_name() {
		return cost_item_name;
	}
	public double getTotal_cost() {
		return total_cost;
	}
	public double getCharge_direct_cost() {
		return charge_direct_cost;
	}
	public double getMate_charge_cost() {
		return mate_charge_cost;
	}
	public double getWork_cost() {
		return work_cost;
	}
	public long getSource_id() {
		return source_id;
	}
	public String getSource_code() {
		return source_code;
	}
	public String getSource_name() {
		return source_name;
	}
	public void setGroup_id(long group_id) {
		this.group_id = group_id;
	}
	public void setHos_id(long hos_id) {
		this.hos_id = hos_id;
	}
	public void setAcc_year(String acc_year) {
		this.acc_year = acc_year;
	}
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	public void setPlan_code(String plan_code) {
		this.plan_code = plan_code;
	}
	public void setPlan_name(String plan_name) {
		this.plan_name = plan_name;
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
	public void setCost_item_id(long cost_item_id) {
		this.cost_item_id = cost_item_id;
	}
	public void setCost_item_code(String cost_item_code) {
		this.cost_item_code = cost_item_code;
	}
	public void setCost_item_name(String cost_item_name) {
		this.cost_item_name = cost_item_name;
	}
	public void setTotal_cost(double total_cost) {
		this.total_cost = total_cost;
	}
	public void setCharge_direct_cost(double charge_direct_cost) {
		this.charge_direct_cost = charge_direct_cost;
	}
	public void setMate_charge_cost(double mate_charge_cost) {
		this.mate_charge_cost = mate_charge_cost;
	}
	public void setWork_cost(double work_cost) {
		this.work_cost = work_cost;
	}
	public void setSource_id(long source_id) {
		this.source_id = source_id;
	}
	public void setSource_code(String source_code) {
		this.source_code = source_code;
	}
	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}
}