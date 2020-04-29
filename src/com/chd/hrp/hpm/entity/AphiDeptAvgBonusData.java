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

public class AphiDeptAvgBonusData implements Serializable {

	
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
	private String dept_code;
	private String dept_name;
	private double dept_bonus;
	private double emp_count;
	private double dept_avg_bonus;
	private double clinc_avg_bonus;
	
	private Integer scheme_seq_no;
	private String item_code;
	private String item_name;
	private String formula_code;
	private String method_code;

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
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
	public void setDept_code(String value) {
		this.dept_code = value;
	}
		
	public String getDept_code() {
		return this.dept_code;
	}
	public void setDept_bonus(double value) {
		this.dept_bonus = value;
	}
		
	public double getDept_bonus() {
		return this.dept_bonus;
	}
	public void setEmp_count(double value) {
		this.emp_count = value;
	}
		
	public double getEmp_count() {
		return this.emp_count;
	}
	public void setDept_avg_bonus(double value) {
		this.dept_avg_bonus = value;
	}
		
	public double getDept_avg_bonus() {
		return this.dept_avg_bonus;
	}
	public void setClinc_avg_bonus(double value) {
		this.clinc_avg_bonus = value;
	}
		
	public double getClinc_avg_bonus() {
		return this.clinc_avg_bonus;
	}

	public Integer getScheme_seq_no() {
		return scheme_seq_no;
	}

	public void setScheme_seq_no(Integer scheme_seq_no) {
		this.scheme_seq_no = scheme_seq_no;
	}

	public String getItem_code() {
		return item_code;
	}

	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getFormula_code() {
		return formula_code;
	}

	public void setFormula_code(String formula_code) {
		this.formula_code = formula_code;
	}

	public String getMethod_code() {
		return method_code;
	}

	public void setMethod_code(String method_code) {
		this.method_code = method_code;
	}

	
}