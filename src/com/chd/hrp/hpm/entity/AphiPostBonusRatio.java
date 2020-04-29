package com.chd.hrp.hpm.entity;

import java.io.Serializable;

public class AphiPostBonusRatio implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long group_id;

	private Long hos_id;
	
	
	
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
	private String copy_code;
	private String acct_year;
	private String duty_code;
	private String duty_name;
	private double bonus_money;
	private double pro;
	private String emp_num;
	private double av;
	
	
	public String getCopy_code() {
		return copy_code;
	}
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	public String getAcct_year() {
		return acct_year;
	}
	public void setAcct_year(String acct_year) {
		this.acct_year = acct_year;
	}
	public String getDuty_code() {
		return duty_code;
	}
	public void setDuty_code(String duty_code) {
		this.duty_code = duty_code;
	}
	public String getDuty_name() {
		return duty_name;
	}
	public void setDuty_name(String duty_name) {
		this.duty_name = duty_name;
	}
	public double getBonus_money() {
		return bonus_money;
	}
	public void setBonus_money(double bonus_money) {
		this.bonus_money = bonus_money;
	}
	public double getPro() {
		return pro;
	}
	public void setPro(double pro) {
		this.pro = pro;
	}
	public String getEmp_num() {
		return emp_num;
	}
	public void setEmp_num(String emp_num) {
		this.emp_num = emp_num;
	}
	public double getAv() {
		return av;
	}
	public void setAv(double av) {
		this.av = av;
	}

	

}
