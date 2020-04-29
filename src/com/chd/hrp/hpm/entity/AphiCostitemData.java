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

public class AphiCostitemData implements Serializable {

	
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
	private String cost_item_code;
	private String dept_code;
	private String dept_name;
	private String cost_item_name;
	private double prim_cost;
	private double prim_cost_ret;
	private double calc_cost;
	private double calc_cost_ret;
	private double cost_tot_ret;
	private String cost_iitem_name;
	private String error_type;

	public String getCost_iitem_name() {
		return cost_iitem_name;
	}

	public void setCost_iitem_name(String cost_iitem_name) {
		this.cost_iitem_name = cost_iitem_name;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getCost_item_name() {
		return cost_item_name;
	}

	public void setCost_item_name(String cost_item_name) {
		this.cost_item_name = cost_item_name;
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

	public void setCost_item_code(String value) {
		this.cost_item_code = value;
	}
		
	public String getCost_item_code() {
		return this.cost_item_code;
	}
	public void setPrim_cost(double value) {
		this.prim_cost = value;
	}
		
	public double getPrim_cost() {
		return this.prim_cost;
	}
	public void setPrim_cost_ret(double value) {
		this.prim_cost_ret = value;
	}
		
	public double getPrim_cost_ret() {
		return this.prim_cost_ret;
	}
	public void setCalc_cost(double value) {
		this.calc_cost = value;
	}
		
	public double getCalc_cost() {
		return this.calc_cost;
	}
	public void setCalc_cost_ret(double value) {
		this.calc_cost_ret = value;
	}
		
	public double getCalc_cost_ret() {
		return this.calc_cost_ret;
	}
	public void setCost_tot_ret(double value) {
		this.cost_tot_ret = value;
	}
		
	public double getCost_tot_ret() {
		return this.cost_tot_ret;
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