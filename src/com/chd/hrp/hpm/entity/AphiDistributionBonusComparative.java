package com.chd.hrp.hpm.entity;

import java.io.Serializable;

public class AphiDistributionBonusComparative implements Serializable {

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
	private String dept_kind_code;
	private String dept_kind_name;
	private double bonus_money;
	private double pro;
	private double emp_num;
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
	public String getDept_kind_code() {
		return dept_kind_code;
	}
	public void setDept_kind_code(String dept_kind_code) {
		this.dept_kind_code = dept_kind_code;
	}
	public String getDept_kind_name() {
		return dept_kind_name;
	}
	public void setDept_kind_name(String dept_kind_name) {
		this.dept_kind_name = dept_kind_name;
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

	public double getAv() {
		return av;
	}
	public void setAv(double av) {
		this.av = av;
	}
	public double getEmp_num() {
		return emp_num;
	}
	public void setEmp_num(double emp_num) {
		this.emp_num = emp_num;
	}
	

	

}
