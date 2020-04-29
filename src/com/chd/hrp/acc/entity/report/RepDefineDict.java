package com.chd.hrp.acc.entity.report;

import java.io.Serializable;

/**
 * 报表字典
 * @author ADMINISTRATOR
 *
 */
public class RepDefineDict implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4440696568244391618L;
	private Long group_id;
	private Long hos_id;
	private String copy_code;
	private String mod_code;
	private String mod_name;
	private String dict_code;
	private String dict_name;
	private String dict_sql;
	private String dict_check_sql;
	private int is_sys;
	private int is_stop;
	private String is_sys_name;
	private String is_stop_name;
	private Long sort_code;
	private String note;
	
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
	public String getDict_code() {
		return dict_code;
	}
	public void setDict_code(String dict_code) {
		this.dict_code = dict_code;
	}
	public String getDict_name() {
		return dict_name;
	}
	public void setDict_name(String dict_name) {
		this.dict_name = dict_name;
	}
	public String getDict_sql() {
		return dict_sql;
	}
	public void setDict_sql(String dict_sql) {
		this.dict_sql = dict_sql;
	}
	public int getIs_sys() {
		return is_sys;
	}
	public void setIs_sys(int is_sys) {
		this.is_sys = is_sys;
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
	public String getIs_sys_name() {
		return is_sys_name;
	}
	public void setIs_sys_name(String is_sys_name) {
		this.is_sys_name = is_sys_name;
	}
	public String getIs_stop_name() {
		return is_stop_name;
	}
	public void setIs_stop_name(String is_stop_name) {
		this.is_stop_name = is_stop_name;
	}
	public String getDict_check_sql() {
		return dict_check_sql;
	}
	public void setDict_check_sql(String dict_check_sql) {
		this.dict_check_sql = dict_check_sql;
	}
	
	
}
