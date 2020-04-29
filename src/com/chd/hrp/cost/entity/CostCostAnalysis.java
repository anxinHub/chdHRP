/**
* @Copyright: Copyright (c) 2015-3-16 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.cost.entity;

import java.io.Serializable;

/**
* @Title. @Description.
* 结余分析<BR>
* @Author: XueWanLi
* @email: bell@s-chd.com
* @Version: 1.0
*/
public class CostCostAnalysis implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -547026498930739846L;
	private String group_id;
	private String hos_id;
	private String copy_code;
	private String year_month;
	private String dept_id;
	private String dept_no;
	private String dept_name;
	private String dept_code;
	private String clinic_num;
	private String bed_use_day_num;
	private String income_total;
	private String cost_total;
	private String profit_total;
	private String unit_income;
	private String unit_cost;
	private String unit_profit;
	
	
	public String getBed_use_day_num() {
		return bed_use_day_num;
	}
	public void setBed_use_day_num(String bed_use_day_num) {
		this.bed_use_day_num = bed_use_day_num;
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
	public String getYear_month() {
		return year_month;
	}
	public void setYear_month(String year_month) {
		this.year_month = year_month;
	}
	public String getDept_id() {
		return dept_id;
	}
	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}
	public String getDept_no() {
		return dept_no;
	}
	public void setDept_no(String dept_no) {
		this.dept_no = dept_no;
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
	public String getClinic_num() {
		return clinic_num;
	}
	public void setClinic_num(String clinic_num) {
		this.clinic_num = clinic_num;
	}
	public String getIncome_total() {
		return income_total;
	}
	public void setIncome_total(String income_total) {
		this.income_total = income_total;
	}
	public String getCost_total() {
		return cost_total;
	}
	public void setCost_total(String cost_total) {
		this.cost_total = cost_total;
	}
	public String getProfit_total() {
		return profit_total;
	}
	public void setProfit_total(String profit_total) {
		this.profit_total = profit_total;
	}
	public String getUnit_income() {
		return unit_income;
	}
	public void setUnit_income(String unit_income) {
		this.unit_income = unit_income;
	}
	public String getUnit_cost() {
		return unit_cost;
	}
	public void setUnit_cost(String unit_cost) {
		this.unit_cost = unit_cost;
	}
	public String getUnit_profit() {
		return unit_profit;
	}
	public void setUnit_profit(String unit_profit) {
		this.unit_profit = unit_profit;
	}
	
}
