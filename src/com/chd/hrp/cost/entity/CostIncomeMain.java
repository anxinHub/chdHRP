/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 科室收入数据总表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class CostIncomeMain implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String acc_year;
	
	private String acc_month;
	
	private String year_month;
	
	private Long income_id;
	
	private String appl_dept_code;
	
	private String appl_dept_name;
	
	private String exec_dept_code;
	
	private String exec_dept_name;
	
	private String charge_kind_code;
	
	private String charge_kind_name;
	
	private String busi_data_source_code;
	
	private String busi_data_source_name;
	
	private double money;

	public String getAcc_year() {
		return acc_year;
	}

	public String getAcc_month() {
		return acc_month;
	}

	public String getYear_month() {
		return year_month;
	}

	public Long getIncome_id() {
		return income_id;
	}

	public String getAppl_dept_code() {
		return appl_dept_code;
	}

	public String getAppl_dept_name() {
		return appl_dept_name;
	}

	public String getExec_dept_code() {
		return exec_dept_code;
	}

	public String getExec_dept_name() {
		return exec_dept_name;
	}

	public String getCharge_kind_code() {
		return charge_kind_code;
	}

	public String getCharge_kind_name() {
		return charge_kind_name;
	}

	public String getBusi_data_source_code() {
		return busi_data_source_code;
	}

	public String getBusi_data_source_name() {
		return busi_data_source_name;
	}

	public double getMoney() {
		return money;
	}

	public void setAcc_year(String acc_year) {
		this.acc_year = acc_year;
	}

	public void setAcc_month(String acc_month) {
		this.acc_month = acc_month;
	}

	public void setYear_month(String year_month) {
		this.year_month = year_month;
	}

	public void setIncome_id(Long income_id) {
		this.income_id = income_id;
	}

	public void setAppl_dept_code(String appl_dept_code) {
		this.appl_dept_code = appl_dept_code;
	}

	public void setAppl_dept_name(String appl_dept_name) {
		this.appl_dept_name = appl_dept_name;
	}

	public void setExec_dept_code(String exec_dept_code) {
		this.exec_dept_code = exec_dept_code;
	}

	public void setExec_dept_name(String exec_dept_name) {
		this.exec_dept_name = exec_dept_name;
	}

	public void setCharge_kind_code(String charge_kind_code) {
		this.charge_kind_code = charge_kind_code;
	}

	public void setCharge_kind_name(String charge_kind_name) {
		this.charge_kind_name = charge_kind_name;
	}

	public void setBusi_data_source_code(String busi_data_source_code) {
		this.busi_data_source_code = busi_data_source_code;
	}

	public void setBusi_data_source_name(String busi_data_source_name) {
		this.busi_data_source_name = busi_data_source_name;
	}

	public void setMoney(double money) {
		this.money = money;
	}
}