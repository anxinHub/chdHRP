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

public class AphiIncomeitemIncreaseData implements Serializable {

	
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
	private String income_item_code;
	private String income_item_name;
	private double income_money;
	private double income_ret_money;
	private String error_type;

	
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
	public void setIncome_item_code(String value) {
		this.income_item_code = value;
	}
		
	public String getIncome_item_code() {
		return this.income_item_code;
	}
	public void setIncome_money(double value) {
		this.income_money = value;
	}
		
	public double getIncome_money() {
		return this.income_money;
	}
	public void setIncome_ret_money(double value) {
		this.income_ret_money = value;
	}
		
	public double getIncome_ret_money() {
		return this.income_ret_money;
	}

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	
	
	
}