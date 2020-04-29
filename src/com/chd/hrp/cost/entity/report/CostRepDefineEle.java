package com.chd.hrp.cost.entity.report;

import java.io.Serializable;

/**
 * 报表元素
 * @author ADMINISTRATOR
 *
 */
public class CostRepDefineEle implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4262565105498141259L;
	private Long group_id;
	private Long hos_id;
	private String copy_code;
	private String mod_code;
	private String mod_name;
	private String ele_code;//指字段名、函数名、存储过程名
	private String ele_name;
	private String ele_group;
	private int ele_type;//1函数、2存储过程、3视图、4自定义SQL
	private Long sort_code;
	private int is_stop;
	private int is_sys;
	private String note;
	private String ele_sql;
	
	public Long getGroup_id() {
		return group_id;
	}
	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}
	public Long getHos_id() {
		return hos_id;
	}
	public void setHos_id(Long hos_id) {
		this.hos_id = hos_id;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	public String getMod_code() {
		return mod_code;
	}
	public void setMod_code(String mod_code) {
		this.mod_code = mod_code;
	}
	public String getEle_code() {
		return ele_code;
	}
	public void setEle_code(String ele_code) {
		this.ele_code = ele_code;
	}
	public String getEle_name() {
		return ele_name;
	}
	public void setEle_name(String ele_name) {
		this.ele_name = ele_name;
	}
	public String getEle_group() {
		return ele_group;
	}
	public void setEle_group(String ele_group) {
		this.ele_group = ele_group;
	}
	public Long getSort_code() {
		return sort_code;
	}
	public void setSort_code(Long sort_code) {
		this.sort_code = sort_code;
	}
	public int getIs_stop() {
		return is_stop;
	}
	public void setIs_stop(int is_stop) {
		this.is_stop = is_stop;
	}
	public String getMod_name() {
		return mod_name;
	}
	public void setMod_name(String mod_name) {
		this.mod_name = mod_name;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getIs_sys() {
		return is_sys;
	}
	public void setIs_sys(int is_sys) {
		this.is_sys = is_sys;
	}
	public int getEle_type() {
		return ele_type;
	}
	public void setEle_type(int ele_type) {
		this.ele_type = ele_type;
	}
	public String getEle_sql() {
		return ele_sql;
	}
	public void setEle_sql(String ele_sql) {
		this.ele_sql = ele_sql;
	}
	
	
}
