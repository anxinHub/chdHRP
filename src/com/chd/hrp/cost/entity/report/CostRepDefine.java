package com.chd.hrp.cost.entity.report;

import java.io.Serializable;
import java.util.Date;

/**
 * 报表定义
 * @author ADMINISTRATOR
 *
 */
public class CostRepDefine implements Serializable{

		
	/**
	 * 
	 */
	private static final long serialVersionUID = 421451042717617380L;
	private String report_code;
	private Long group_id;
	private Long hos_id;
	private String copy_code;
	private String mod_code;
	private String report_name;
	private String report_group;
	private String report_type;
	private String report_attr;
	private String report_note;
	private String content;
	private Long user_id;
	private Date create_date;
	private Long sort_code;
	private int is_perm;
	private int is_sys;
	private int is_stop;
	private int is_write;
	public String getReport_code() {
		return report_code;
	}
	public void setReport_code(String report_code) {
		this.report_code = report_code;
	}
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
	public String getReport_name() {
		return report_name;
	}
	public void setReport_name(String report_name) {
		this.report_name = report_name;
	}
	public String getReport_type() {
		return report_type;
	}
	public void setReport_type(String report_type) {
		this.report_type = report_type;
	}
	public String getReport_attr() {
		return report_attr;
	}
	public void setReport_attr(String report_attr) {
		this.report_attr = report_attr;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public Long getSort_code() {
		return sort_code;
	}
	public void setSort_code(Long sort_code) {
		this.sort_code = sort_code;
	}
	public int getIs_perm() {
		return is_perm;
	}
	public void setIs_perm(int is_perm) {
		this.is_perm = is_perm;
	}	
	public int getIs_sys() {
		return is_sys;
	}
	public void setIs_sys(int is_sys) {
		this.is_sys = is_sys;
	}
	public int getIs_stop() {
		return is_stop;
	}
	public void setIs_stop(int is_stop) {
		this.is_stop = is_stop;
	}
	public String getReport_group() {
		return report_group;
	}
	public void setReport_group(String report_group) {
		this.report_group = report_group;
	}
	public String getReport_note() {
		return report_note;
	}
	public void setReport_note(String report_note) {
		this.report_note = report_note;
	}
	public int getIs_write() {
		return is_write;
	}
	public void setIs_write(int is_write) {
		this.is_write = is_write;
	}
	
	
}
