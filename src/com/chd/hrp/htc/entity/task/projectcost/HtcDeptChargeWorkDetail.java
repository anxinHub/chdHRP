package com.chd.hrp.htc.entity.task.projectcost;

import java.io.Serializable;
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

public class HtcDeptChargeWorkDetail implements Serializable {

	
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
	private long charge_item_id;
	private String charge_item_code;
	private String charge_item_name;
	private double price;
	private String work_code;
	private String work_name;
	private int ratio;
	private String title_code;
	private String title_name;
	private double person_num;
	private double oper_time;
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
	public long getCharge_item_id() {
		return charge_item_id;
	}
	public String getCharge_item_code() {
		return charge_item_code;
	}
	public String getCharge_item_name() {
		return charge_item_name;
	}
	public String getWork_code() {
		return work_code;
	}
	public String getWork_name() {
		return work_name;
	}
	public String getTitle_code() {
		return title_code;
	}
	public String getTitle_name() {
		return title_name;
	}
	public double getPerson_num() {
		return person_num;
	}
	public double getOper_time() {
		return oper_time;
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
	public void setCharge_item_id(long charge_item_id) {
		this.charge_item_id = charge_item_id;
	}
	public void setCharge_item_code(String charge_item_code) {
		this.charge_item_code = charge_item_code;
	}
	public void setCharge_item_name(String charge_item_name) {
		this.charge_item_name = charge_item_name;
	}
	public void setWork_code(String work_code) {
		this.work_code = work_code;
	}
	public void setWork_name(String work_name) {
		this.work_name = work_name;
	}
	public void setTitle_code(String title_code) {
		this.title_code = title_code;
	}
	public void setTitle_name(String title_name) {
		this.title_name = title_name;
	}
	public void setPerson_num(double person_num) {
		this.person_num = person_num;
	}
	public void setOper_time(double oper_time) {
		this.oper_time = oper_time;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getRatio() {
		return ratio;
	}
	public void setRatio(int ratio) {
		this.ratio = ratio;
	}
}