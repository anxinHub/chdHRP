package com.chd.hrp.acc.entity.superVouch;

import java.io.Serializable;
import java.util.Date;

public class SysPrintTemplate implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1028546250154700343L;
	private String mod_code;
	private String template_code;
	private String group_id;
	private String hos_id;
	private String copy_code;
	private String content;
	private Long use_id;
	private Date create_date;
	private int print_count;
	public String getMod_code() {
		return mod_code;
	}
	public void setMod_code(String mod_code) {
		this.mod_code = mod_code;
	}
	public String getTemplate_code() {
		return template_code;
	}
	public void setTemplate_code(String template_code) {
		this.template_code = template_code;
	}
	public String getGroup_id() {
		return group_id;
	}
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	public String getHos_id() {
		return hos_id;
	}
	public void setHos_id(String hos_id) {
		this.hos_id = hos_id;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getUse_id() {
		return use_id;
	}
	public void setUse_id(Long use_id) {
		this.use_id = use_id;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public int getPrint_count() {
		return print_count;
	}
	public void setPrint_count(int print_count) {
		this.print_count = print_count;
	}
	
	
}
