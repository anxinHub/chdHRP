package com.chd.hrp.cost.entity;

import java.io.Serializable;

public class CostInhosWorkContrast implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 部门编码
	 */
	private String dept_code;
	/**
	 * 部门名称
	 */
	private String dept_name;
	/**
	 * 金额1
	 */
	private double money1;
	/**
	 * 金额2
	 */
	private double money2;
	/**
	 * 金额3
	 */
	private double money3;
	/**
	 * 成本1
	 */
	private double amount1;
	/**
	 * 成本2
	 */
	private double amount2;
	/**
	 * 成本3
	 */
	private double amount3;
	/**
	 * 门诊人次1
	 */
	private int clinic_num1;
	/**
	 * 门诊人次2
	 */
	private int clinic_num2;
	/**
	 * 门诊人次3
	 */
	private int clinic_num3;
	
	
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
	public double getMoney1() {
		return money1;
	}
	public void setMoney1(double money1) {
		this.money1 = money1;
	}
	public double getMoney2() {
		return money2;
	}
	public void setMoney2(double money2) {
		this.money2 = money2;
	}
	public double getMoney3() {
		return money3;
	}
	public void setMoney3(double money3) {
		this.money3 = money3;
	}
	public double getAmount1() {
		return amount1;
	}
	public void setAmount1(double amount1) {
		this.amount1 = amount1;
	}
	public double getAmount2() {
		return amount2;
	}
	public void setAmount2(double amount2) {
		this.amount2 = amount2;
	}
	public double getAmount3() {
		return amount3;
	}
	public void setAmount3(double amount3) {
		this.amount3 = amount3;
	}
	public int getClinic_num1() {
		return clinic_num1;
	}
	public void setClinic_num1(int clinic_num1) {
		this.clinic_num1 = clinic_num1;
	}
	public int getClinic_num2() {
		return clinic_num2;
	}
	public void setClinic_num2(int clinic_num2) {
		this.clinic_num2 = clinic_num2;
	}
	public int getClinic_num3() {
		return clinic_num3;
	}
	public void setClinic_num3(int clinic_num3) {
		this.clinic_num3 = clinic_num3;
	}

}
