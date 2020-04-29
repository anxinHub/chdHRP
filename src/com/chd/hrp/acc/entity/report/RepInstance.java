package com.chd.hrp.acc.entity.report;

import java.io.Serializable;
import java.util.Date;

/**
 * 报表实例
 * @author ADMINISTRATOR
 *
 */
public class RepInstance implements Serializable{

		
	/**
	 * 
	 */
	private static final long serialVersionUID = 421451042717617380L;
	private String report_code;
	private Long group_id;
	private Long hos_id;
	private String copy_code;
	private String report_name;
	private String mod_code;
	private String acc_year;
	private String acc_month;
	private String content;
	private Long user_id;
	private String user_name;
	private Date create_date;
	private int report_type;
	private String acc_month_name;
	private String report_type_name;
	
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAcc_year() {
		return acc_year;
	}
	public void setAcc_year(String acc_year) {
		this.acc_year = acc_year;
	}
	public String getAcc_month() {
		return acc_month;
	}
	public void setAcc_month(String acc_month) {
		this.acc_month = acc_month;
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
	public String getAcc_month_name() {
		return acc_month_name;
	}
	public void setAcc_month_name(String acc_month_name) {
		this.acc_month_name = acc_month_name;
	}
	public String getReport_type_name() {
		return report_type_name;
	}
	public void setReport_type_name(String report_type_name) {
		this.report_type_name = report_type_name;
	}
	public String getReport_name() {
		return report_name;
	}
	public void setReport_name(String report_name) {
		this.report_name = report_name;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public int getReport_type() {
		return report_type;
	}
	public void setReport_type(int report_type) {
		this.report_type = report_type;
	}
	
}
