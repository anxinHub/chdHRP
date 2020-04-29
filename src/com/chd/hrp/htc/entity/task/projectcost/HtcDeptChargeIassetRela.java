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

public class HtcDeptChargeIassetRela implements Serializable {

	
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
	private String server_dept_no;
	private String server_dept_id;
	private String server_dept_code;
	private String server_dept_name;
	private String asset_code;
	private String asset_name;
	private double prim_value;
	private String start_date;
	private Integer opte_time;
	
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
	public double getPrice() {
		return price;
	}
	public String getServer_dept_no() {
		return server_dept_no;
	}
	public String getServer_dept_id() {
		return server_dept_id;
	}
	public String getServer_dept_code() {
		return server_dept_code;
	}
	public String getServer_dept_name() {
		return server_dept_name;
	}
	public String getAsset_code() {
		return asset_code;
	}
	public String getAsset_name() {
		return asset_name;
	}
	public double getPrim_value() {
		return prim_value;
	}
	public String getStart_date() {
		return start_date;
	}
	public Integer getOpte_time() {
		return opte_time;
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
	public void setPrice(double price) {
		this.price = price;
	}
	public void setServer_dept_no(String server_dept_no) {
		this.server_dept_no = server_dept_no;
	}
	public void setServer_dept_id(String server_dept_id) {
		this.server_dept_id = server_dept_id;
	}
	public void setServer_dept_code(String server_dept_code) {
		this.server_dept_code = server_dept_code;
	}
	public void setServer_dept_name(String server_dept_name) {
		this.server_dept_name = server_dept_name;
	}
	public void setAsset_code(String asset_code) {
		this.asset_code = asset_code;
	}
	public void setAsset_name(String asset_name) {
		this.asset_name = asset_name;
	}
	public void setPrim_value(double prim_value) {
		this.prim_value = prim_value;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public void setOpte_time(Integer opte_time) {
		this.opte_time = opte_time;
	}
	
}