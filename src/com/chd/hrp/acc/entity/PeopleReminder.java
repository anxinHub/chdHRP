package com.chd.hrp.acc.entity;

import java.util.Date;

//人往来催款单
public class PeopleReminder {
	private Long dept_id;
	private String dept_name;
	private String dept_code;
	private Long emp_id;
	private String emp_code;
	private String emp_name;
	private Date occur_date;
	private String vouch_code;
	private String summary;
	private Long debit;
	private Long veri_money;
	private int veri;
	public Long getDept_id() {
		return dept_id;
	}
	public void setDept_id(Long dept_id) {
		this.dept_id = dept_id;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public String getDept_code() {
		return dept_code;
	}
	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}
	public Long getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(Long emp_id) {
		this.emp_id = emp_id;
	}
	public String getEmp_code() {
		return emp_code;
	}
	public void setEmp_code(String emp_code) {
		this.emp_code = emp_code;
	}
	public String getEmp_name() {
		return emp_name;
	}
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
	public Date getOccur_date() {
		return occur_date;
	}
	public void setOccur_date(Date occur_date) {
		this.occur_date = occur_date;
	}
	public String getVouch_code() {
		return vouch_code;
	}
	public void setVouch_code(String vouch_code) {
		this.vouch_code = vouch_code;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Long getDebit() {
		return debit;
	}
	public void setDebit(Long debit) {
		this.debit = debit;
	}
	public Long getVeri_money() {
		return veri_money;
	}
	public void setVeri_money(Long veri_money) {
		this.veri_money = veri_money;
	}
	public int getVeri() {
		return veri;
	}
	public void setVeri(int veri) {
		this.veri = veri;
	}
	
	
}
