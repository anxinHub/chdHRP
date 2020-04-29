/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.entity;

import java.io.Serializable;
import java.util.*;

import com.chd.base.util.DateUtil;
/**
 * 
 * @Description:
 * 

 * @Table:
 * BUDG_EXPENSE_APPLY
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgExpenseApply implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 集团ID
	 */
	private Long group_id;
	
	/**
	 * 医院ID
	 */
	private Long hos_id;
	
	/**
	 * 账套编码
	 */
	private String copy_code;
	
	/**
	 * 预算年度
	 */
	private String budg_year;
	
	/**
	 * 申报单号
	 */
	private String apply_id;
	
	/**
	 * 支出项目ID
	 */
	private Long payment_item_id;
	
	/**
	 * 支出项目变更ID
	 */
	private Long payment_item_no;
	
	/**
	 * 预算金额
	 */
	private Double budg_amount;
	
	/**
	 * 申报科室
	 */
	private Long apply_dept;
	
	private Long apply_dept_no;
	
	/**
	 * 申报人员
	 */
	private Long apply_emp;
	
	private Long apply_emp_no;
	
	private String apply_emp_name;
	
	/**
	 * 申报日期
	 */
	private Date make_date;
	
	/**
	 * 审核人
	 */
	private Long checker;
	
	private String checker_emp_name;
	
	/**
	 * 审核日期
	 */
	private Date check_date;
	
	/**
	 * 状态
	 */
	private String state;
	
	private String state_name;
	
	private String remark;
	private Long affi_emp;
	private String affi_emp_name;
	private Date affi_emp_date;
	

  public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getAffi_emp() {
		return affi_emp;
	}

	public void setAffi_emp(Long affi_emp) {
		this.affi_emp = affi_emp;
	}

	public String getAffi_emp_name() {
		return affi_emp_name;
	}

	public void setAffi_emp_name(String affi_emp_name) {
		this.affi_emp_name = affi_emp_name;
	}

	public Date getAffi_emp_date() {
		return affi_emp_date;
	}

	public void setAffi_emp_date(Date affi_emp_date) {
		this.affi_emp_date = affi_emp_date;
	}
/**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 集团ID
	* @param value 
	*/
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	
	/**
	* 获取 集团ID
	* @return Long
	*/
	public Long getGroup_id() {
		return this.group_id;
	}
	/**
	* 设置 医院ID
	* @param value 
	*/
	public void setHos_id(Long value) {
		this.hos_id = value;
	}
	
	/**
	* 获取 医院ID
	* @return Long
	*/
	public Long getHos_id() {
		return this.hos_id;
	}
	/**
	* 设置 账套编码
	* @param value 
	*/
	public void setCopy_code(String value) {
		this.copy_code = value;
	}
	
	/**
	* 获取 账套编码
	* @return String
	*/
	public String getCopy_code() {
		return this.copy_code;
	}
	/**
	* 设置 预算年度
	* @param value 
	*/
	public void setBudg_year(String value) {
		this.budg_year = value;
	}
	
	/**
	* 获取 预算年度
	* @return String
	*/
	public String getBudg_year() {
		return this.budg_year;
	}
	/**
	* 设置 申报单号
	* @param value 
	*/
	public void setApply_id(String value) {
		this.apply_id = value;
	}
	
	/**
	* 获取 申报单号
	* @return String
	*/
	public String getApply_id() {
		return this.apply_id;
	}
	/**
	* 设置 支出项目ID
	* @param value 
	*/
	public void setPayment_item_id(Long value) {
		this.payment_item_id = value;
	}
	
	/**
	* 获取 支出项目ID
	* @return Long
	*/
	public Long getPayment_item_id() {
		return this.payment_item_id;
	}
	/**
	* 设置 支出项目变更ID
	* @param value 
	*/
	public void setPayment_item_no(Long value) {
		this.payment_item_no = value;
	}
	
	/**
	* 获取 支出项目变更ID
	* @return Long
	*/
	public Long getPayment_item_no() {
		return this.payment_item_no;
	}
	/**
	* 设置 预算金额
	* @param value 
	*/
	public void setBudg_amount(Double value) {
		this.budg_amount = value;
	}
	
	/**
	* 获取 预算金额
	* @return Double
	*/
	public Double getBudg_amount() {
		return this.budg_amount;
	}
	/**
	* 设置 申报科室
	* @param value 
	*/
	public void setApply_dept(Long value) {
		this.apply_dept = value;
	}
	
	/**
	* 获取 申报科室
	* @return Long
	*/
	public Long getApply_dept() {
		return this.apply_dept;
	}
	/**
	* 设置 申报人员
	* @param value 
	*/
	public void setApply_emp(Long value) {
		this.apply_emp = value;
	}
	
	/**
	* 获取 申报人员
	* @return Long
	*/
	public Long getApply_emp() {
		return this.apply_emp;
	}
	/**
	* 设置 申报日期
	* @param value 
	*/
	public void setMake_date(Date value) {
		this.make_date = value;
	}
	
	/**
	* 获取 申报日期
	* @return Date
	*/
	public Date getMake_date() {
		return this.make_date;
	}
	
	public String getMake_date_str(){
		return DateUtil.dateToString(this.make_date);
	}
	
	/**
	* 设置 审核人
	* @param value 
	*/
	public void setChecker(Long value) {
		this.checker = value;
	}
	
	/**
	* 获取 审核人
	* @return Long
	*/
	public Long getChecker() {
		return this.checker;
	}
	/**
	* 设置 审核日期
	* @param value 
	*/
	public void setCheck_date(Date value) {
		this.check_date = value;
	}
	
	/**
	* 获取 审核日期
	* @return Date
	*/
	public Date getCheck_date() {
		return this.check_date;
	}
	
	public String getCheck_date_str(){
		return DateUtil.dateToString(this.check_date);
	}
	/**
	* 设置 状态
	* @param value 
	*/
	public void setState(String value) {
		this.state = value;
	}
	
	/**
	* 获取 状态
	* @return String
	*/
	public String getState() {
		return this.state;
	}
	
	public Long getApply_dept_no() {
		return apply_dept_no;
	}

	public void setApply_dept_no(Long apply_dept_no) {
		this.apply_dept_no = apply_dept_no;
	}

	public Long getApply_emp_no() {
		return apply_emp_no;
	}

	public void setApply_emp_no(Long apply_emp_no) {
		this.apply_emp_no = apply_emp_no;
	}

	
	public String getApply_emp_name() {
		return apply_emp_name;
	}

	public void setApply_emp_name(String apply_emp_name) {
		this.apply_emp_name = apply_emp_name;
	}

	public String getChecker_emp_name() {
		return checker_emp_name;
	}

	public void setChecker_emp_name(String checker_emp_name) {
		this.checker_emp_name = checker_emp_name;
	}

	public String getState_name() {
		return state_name;
	}

	public void setState_name(String state_name) {
		this.state_name = state_name;
	}

	/**
	 * 设置 导入验证信息
	 */
	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	/**
	 * 获取 导入验证信息
	 */
	public String getError_type() {
		return error_type;
	}
}