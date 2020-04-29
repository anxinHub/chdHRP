package com.chd.hrp.hpm.entity;

import java.io.Serializable;

public class AphiTotalBonusControl implements Serializable {

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
	private String dept_code;
	private String dept_name;
	private double bonus_money;
	private double income_money;
	private double income_pro;
	private double cost_money;
	private double cost_pro;
	private double human_cost_money;
	private double human_cost_pro;
	
	
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
	public String getDept_code() {
		return dept_code;
	}
	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public double getBonus_money() {
		return bonus_money;
	}
	public void setBonus_money(double bonus_money) {
		this.bonus_money = bonus_money;
	}
	public double getIncome_money() {
		return income_money;
	}
	public void setIncome_money(double income_money) {
		this.income_money = income_money;
	}
	public double getIncome_pro() {
		return income_pro;
	}
	public void setIncome_pro(double income_pro) {
		this.income_pro = income_pro;
	}
	public double getCost_money() {
		return cost_money;
	}
	public void setCost_money(double cost_money) {
		this.cost_money = cost_money;
	}
	public double getCost_pro() {
		return cost_pro;
	}
	public void setCost_pro(double cost_pro) {
		this.cost_pro = cost_pro;
	}
	public double getHuman_cost_money() {
		return human_cost_money;
	}
	public void setHuman_cost_money(double human_cost_money) {
		this.human_cost_money = human_cost_money;
	}
	public double getHuman_cost_pro() {
		return human_cost_pro;
	}
	public void setHuman_cost_pro(double human_cost_pro) {
		this.human_cost_pro = human_cost_pro;
	}
	

	

}
