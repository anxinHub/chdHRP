package com.chd.hrp.hpm.entity;

import java.io.Serializable;
/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

public class AphiIncomeitemData implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

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
	private String acct_month;
	private Long dept_no;
	
	private Long dept_id;
	private String dept_code;
	private String dept_name;
	private String income_item_code;
	private String income_item_name;
	private double order_money;
	private double order_ret_money;
	private double perform_money;
	private double perform_ret_money;
	private double income_tot_money;
	private String error_type;
	
	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getIncome_item_name() {
		return income_item_name;
	}

	public void setIncome_item_name(String income_item_name) {
		this.income_item_name = income_item_name;
	}

	public void setCopy_code(String value) {
		this.copy_code = value;
	}
		
	public String getCopy_code() {
		return this.copy_code;
	}
	public void setAcct_year(String value) {
		this.acct_year = value;
	}
		
	public String getAcct_year() {
		return this.acct_year;
	}
	public void setAcct_month(String value) {
		this.acct_month = value;
	}
		
	public String getAcct_month() {
		return this.acct_month;
	}
	
	public Long getDept_no() {
		return dept_no;
	}

	public void setDept_no(Long dept_no) {
		this.dept_no = dept_no;
	}

	public Long getDept_id() {
		return dept_id;
	}

	public void setDept_id(Long dept_id) {
		this.dept_id = dept_id;
	}

	public void setIncome_item_code(String value) {
		this.income_item_code = value;
	}
		
	public String getIncome_item_code() {
		return this.income_item_code;
	}
	public void setOrder_money(double value) {
		this.order_money = value;
	}
		
	public double getOrder_money() {
		return this.order_money;
	}
	public void setOrder_ret_money(double value) {
		this.order_ret_money = value;
	}
		
	public double getOrder_ret_money() {
		return this.order_ret_money;
	}
	public void setPerform_money(double value) {
		this.perform_money = value;
	}
		
	public double getPerform_money() {
		return this.perform_money;
	}
	public void setPerform_ret_money(double value) {
		this.perform_ret_money = value;
	}
		
	public double getPerform_ret_money() {
		return this.perform_ret_money;
	}
	public void setIncome_tot_money(double value) {
		this.income_tot_money = value;
	}
		
	public double getIncome_tot_money() {
		return this.income_tot_money;
	}

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}

	public String getDept_code() {
		return dept_code;
	}

	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}
	
}