package com.chd.hrp.acc.entity;

import java.io.Serializable;
import java.util.Date;

public class AccMatchIncome implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7637041008412908510L;
	private Long income_id;
	private Long group_id;
	private Long hos_id;
	private String copy_code;
	private String acc_year;
	private String business_no;
	private Long proj_id;
	private Long proj_no;
	private String proj_code;
	private String proj_name;
	private String reply_no;
	private Date reply_date;
	private String reply_money;
	private String note;
	private String emp_name;
	
	
	public String getReply_no() {
		return reply_no;
	}
	public void setReply_no(String reply_no) {
		this.reply_no = reply_no;
	}
	public String getEmp_name() {
		return emp_name;
	}
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
	public String getProj_code() {
		return proj_code;
	}
	public void setProj_code(String proj_code) {
		this.proj_code = proj_code;
	}
	public String getProj_name() {
		return proj_name;
	}
	public void setProj_name(String proj_name) {
		this.proj_name = proj_name;
	}
	public Long getIncome_id() {
		return income_id;
	}
	public void setIncome_id(Long income_id) {
		this.income_id = income_id;
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
	public String getAcc_year() {
		return acc_year;
	}
	public void setAcc_year(String acc_year) {
		this.acc_year = acc_year;
	}
	public String getBusiness_no() {
		return business_no;
	}
	public void setBusiness_no(String business_no) {
		this.business_no = business_no;
	}
	public Long getProj_id() {
		return proj_id;
	}
	public void setProj_id(Long proj_id) {
		this.proj_id = proj_id;
	}
	public Long getProj_no() {
		return proj_no;
	}
	public void setProj_no(Long proj_no) {
		this.proj_no = proj_no;
	}

	public Date getReply_date() {
		return reply_date;
	}
	public void setReply_date(Date reply_date) {
		this.reply_date = reply_date;
	}
	public String getReply_money() {
		return reply_money;
	}
	public void setReply_money(String reply_money) {
		this.reply_money = reply_money;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
}
