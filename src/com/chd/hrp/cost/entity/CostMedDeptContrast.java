package com.chd.hrp.cost.entity;

import java.io.Serializable;

public class CostMedDeptContrast implements Serializable {

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
	 * 总人数1
	 */
	private double people_num1;
	/**
	 * 总人数2
	 */
	private double people_num2;
	/**
	 * 总人数3
	 */
	private double people_num3;
	
	
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
	public double getPeople_num1() {
		return people_num1;
	}
	public void setPeople_num1(double people_num1) {
		this.people_num1 = people_num1;
	}
	public double getPeople_num2() {
		return people_num2;
	}
	public void setPeople_num2(double people_num2) {
		this.people_num2 = people_num2;
	}
	public double getPeople_num3() {
		return people_num3;
	}
	public void setPeople_num3(double people_num3) {
		this.people_num3 = people_num3;
	}
	

}
