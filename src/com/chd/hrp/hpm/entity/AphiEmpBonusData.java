package com.chd.hrp.hpm.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public class AphiEmpBonusData implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_id;

	private Long hos_id;

	private String copy_code;

	private String acct_year;

	private String acct_month;

	private String emp_code;

	private double bonus_money;

	private Long dept_id;
	
	private Long dept_no;

	private String dept_kind_code;

	private String formula_code;

	private String method_code;
	
	private String dept_code;

	private String dept_name;
	
	private Long emp_id;
	
	private Long emp_no;

	private String emp_name;
	
	private String duty_name;

	private String bonus_money_dept;

	private String emp_duty_amount;

	private String dept_duty_amount;
	
	private String grant_money;
	
	private String error_type;
	
	private int is_audit;
	
	private int is_grant;
	
	private int is_two_audit;
	
	public int getIs_two_audit() {
		return is_two_audit;
	}

	public void setIs_two_audit(int is_two_audit) {
		this.is_two_audit = is_two_audit;
	}

	private Date grant_date;
	
	private String grant_code;

	public int getIs_grant() {
		return is_grant;
	}

	public void setIs_grant(int is_grant) {
		this.is_grant = is_grant;
	}

	public Date getGrant_date() {
		return grant_date;
	}

	public void setGrant_date(Date grant_date) {
		this.grant_date = grant_date;
	}

	public String getGrant_code() {
		return grant_code;
	}

	public void setGrant_code(String grant_code) {
		this.grant_code = grant_code;
	}

	private String year_month;
	
	private String item_code;
	
	private String item_name;

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

	public String getAcct_month() {
		return acct_month;
	}

	public void setAcct_month(String acct_month) {
		this.acct_month = acct_month;
	}

	public String getEmp_code() {
		return emp_code;
	}

	public void setEmp_code(String emp_code) {
		this.emp_code = emp_code;
	}

	public double getBonus_money() {
		return bonus_money;
	}

	public void setBonus_money(double bonus_money) {
		this.bonus_money = bonus_money;
	}

	public Long getDept_id() {
		return dept_id;
	}

	public void setDept_id(Long dept_id) {
		this.dept_id = dept_id;
	}

	public Long getDept_no() {
		return dept_no;
	}

	public void setDept_no(Long dept_no) {
		this.dept_no = dept_no;
	}

	public String getDept_kind_code() {
		return dept_kind_code;
	}

	public void setDept_kind_code(String dept_kind_code) {
		this.dept_kind_code = dept_kind_code;
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

	public Long getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(Long emp_id) {
		this.emp_id = emp_id;
	}

	public Long getEmp_no() {
		return emp_no;
	}

	public void setEmp_no(Long emp_no) {
		this.emp_no = emp_no;
	}

	public String getEmp_name() {
		return emp_name;
	}

	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}

	public String getDuty_name() {
		return duty_name;
	}

	public void setDuty_name(String duty_name) {
		this.duty_name = duty_name;
	}

	public String getBonus_money_dept() {
		return bonus_money_dept;
	}

	public void setBonus_money_dept(String bonus_money_dept) {
		this.bonus_money_dept = bonus_money_dept;
	}

	public String getEmp_duty_amount() {
		return emp_duty_amount;
	}

	public void setEmp_duty_amount(String emp_duty_amount) {
		this.emp_duty_amount = emp_duty_amount;
	}

	public String getDept_duty_amount() {
		return dept_duty_amount;
	}

	public void setDept_duty_amount(String dept_duty_amount) {
		this.dept_duty_amount = dept_duty_amount;
	}

	public String getGrant_money() {
		return grant_money;
	}

	public void setGrant_money(String grant_money) {
		this.grant_money = grant_money;
	}

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}

	public int getIs_audit() {
		return is_audit;
	}

	public void setIs_audit(int is_audit) {
		this.is_audit = is_audit;
	}

	public String getYear_month() {
		return year_month;
	}

	public void setYear_month(String year_month) {
		this.year_month = year_month;
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
	
	
}