/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.entity;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 减员计划明细
 * @Table:
 * BUDG_CUT_EMP_DETAIL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgCutEmpDetail implements Serializable {

	
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
	 * 计划年度
	 */
	private String plan_year;
	
	/**
	 * 计划单号
	 */
	private String plan_code;
	
	/**
	 * 部门ID
	 */
	private Long dept_id;
	
	/**
	 * 职工ID
	 */
	private Long emp_id;
	
	/**
	 * 计划减员日期
	 */
	private String out_month;
	
	/**
	 * 减员原因
	 */
	private String reason;
	
	/**
	 * 工资变动
	 */
	private Double wage_change;
	

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
	* 设置 计划年度
	* @param value 
	*/
	public void setPlan_year(String value) {
		this.plan_year = value;
	}
	
	/**
	* 获取 计划年度
	* @return String
	*/
	public String getPlan_year() {
		return this.plan_year;
	}
	/**
	* 设置 计划单号
	* @param value 
	*/
	public void setPlan_code(String value) {
		this.plan_code = value;
	}
	
	/**
	* 获取 计划单号
	* @return String
	*/
	public String getPlan_code() {
		return this.plan_code;
	}
	/**
	* 设置 部门ID
	* @param value 
	*/
	public void setDept_id(Long value) {
		this.dept_id = value;
	}
	
	/**
	* 获取 部门ID
	* @return Long
	*/
	public Long getDept_id() {
		return this.dept_id;
	}
	/**
	* 设置 职工ID
	* @param value 
	*/
	public void setEmp_id(Long value) {
		this.emp_id = value;
	}
	
	/**
	* 获取 职工ID
	* @return Long
	*/
	public Long getEmp_id() {
		return this.emp_id;
	}
	/**
	* 设置 计划减员日期
	* @param value 
	*/
	public void setOut_month(String value) {
		this.out_month = value;
	}
	
	/**
	* 获取 计划减员日期
	* @return String
	*/
	public String getOut_month() {
		return this.out_month;
	}
	/**
	* 设置 减员原因
	* @param value 
	*/
	public void setReason(String value) {
		this.reason = value;
	}
	
	/**
	* 获取 减员原因
	* @return String
	*/
	public String getReason() {
		return this.reason;
	}
	/**
	* 设置 工资变动
	* @param value 
	*/
	public void setWage_change(Double value) {
		this.wage_change = value;
	}
	
	/**
	* 获取 工资变动
	* @return Double
	*/
	public Double getWage_change() {
		return this.wage_change;
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