/**
* @Copyright: Copyright (c) 2015-3-15 
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
public class CostBalanceAnalysis implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3424117691042164780L;
	private String group_id;
	private String hos_id;
	private String copy_code;
	private String acc_year;
	private String acc_month;
	private String dept_id;
	private String dept_no;
	private String dept_name;
	private String dept_code;
	private String dept_income;
	private String dept_cost;
	private String dept_profit;
	
	public String getDept_code() {
		return dept_code;
	}
	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
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
	public String getDept_income() {
		return dept_income;
	}
	public void setDept_income(String dept_income) {
		this.dept_income = dept_income;
	}
	public String getDept_cost() {
		return dept_cost;
	}
	public void setDept_cost(String dept_cost) {
		this.dept_cost = dept_cost;
	}
	public String getDept_profit() {
		return dept_profit;
	}
	public void setDept_profit(String dept_profit) {
		this.dept_profit = dept_profit;
	}
	
}
