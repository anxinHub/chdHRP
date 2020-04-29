package com.chd.hrp.htc.entity.task.basic;

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

public class HtcFassetDict implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private long group_id;
	private long hos_id;
	private String copy_code;
	private String asset_code;
	private String asset_name;
	private String asset_type_code;
	private String asset_type_name;
	private String asset_model;
	private double prim_value;
	private String start_date;
	private String end_date;
	private Integer dep_year;
	private long dept_no;
	private long dept_id;
	private String dept_code;
	private String dept_name;
	private String spell_code;
	private String wbx_code;
	public long getGroup_id() {
		return group_id;
	}
	public long getHos_id() {
		return hos_id;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public String getAsset_code() {
		return asset_code;
	}
	public String getAsset_name() {
		return asset_name;
	}
	public String getAsset_type_code() {
		return asset_type_code;
	}
	public String getAsset_type_name() {
		return asset_type_name;
	}
	public String getAsset_model() {
		return asset_model;
	}
	public double getPrim_value() {
		return prim_value;
	}
	public String getStart_date() {
		return start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public Integer getDep_year() {
		return dep_year;
	}
	public long getDept_no() {
		return dept_no;
	}
	public long getDept_id() {
		return dept_id;
	}
	public String getDept_code() {
		return dept_code;
	}
	public String getDept_name() {
		return dept_name;
	}
	public String getSpell_code() {
		return spell_code;
	}
	public String getWbx_code() {
		return wbx_code;
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
	public void setAsset_code(String asset_code) {
		this.asset_code = asset_code;
	}
	public void setAsset_name(String asset_name) {
		this.asset_name = asset_name;
	}
	public void setAsset_type_code(String asset_type_code) {
		this.asset_type_code = asset_type_code;
	}
	public void setAsset_type_name(String asset_type_name) {
		this.asset_type_name = asset_type_name;
	}
	public void setAsset_model(String asset_model) {
		this.asset_model = asset_model;
	}
	public void setPrim_value(double prim_value) {
		this.prim_value = prim_value;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public void setDep_year(Integer dep_year) {
		this.dep_year = dep_year;
	}
	public void setDept_no(long dept_no) {
		this.dept_no = dept_no;
	}
	public void setDept_id(long dept_id) {
		this.dept_id = dept_id;
	}
	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public void setSpell_code(String spell_code) {
		this.spell_code = spell_code;
	}
	public void setWbx_code(String wbx_code) {
		this.wbx_code = wbx_code;
	}
	
}