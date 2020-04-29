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
 * 医疗支出预算备份
 * @Table:
 * BUDG_MED_COST_COPY
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgMedCostCopy implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 审批单号
	 */
	private String check_code;
	
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
	 * 月
	 */
	private String month;
	
	/**
	 * 部门ID
	 */
	private Long dept_id;
	
	/**
	 * 科目编码
	 */
	private String subj_code;
	
	/**
	 * 支出预算
	 */
	private Double cost_budg;
	
	/**
	 * 上月结转
	 */
	private Double last_month_carried;
	
	/**
	 * 结转下月
	 */
	private Double carried_next_month;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 审批单号
	* @param value 
	*/
	public void setCheck_code(String value) {
		this.check_code = value;
	}
	
	/**
	* 获取 审批单号
	* @return String
	*/
	public String getCheck_code() {
		return this.check_code;
	}
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
	* 设置 月
	* @param value 
	*/
	public void setMonth(String value) {
		this.month = value;
	}
	
	/**
	* 获取 月
	* @return String
	*/
	public String getMonth() {
		return this.month;
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
	* 设置 科目编码
	* @param value 
	*/
	public void setSubj_code(String value) {
		this.subj_code = value;
	}
	
	/**
	* 获取 科目编码
	* @return String
	*/
	public String getSubj_code() {
		return this.subj_code;
	}
	/**
	* 设置 支出预算
	* @param value 
	*/
	public void setCost_budg(Double value) {
		this.cost_budg = value;
	}
	
	/**
	* 获取 支出预算
	* @return Double
	*/
	public Double getCost_budg() {
		return this.cost_budg;
	}
	/**
	* 设置 上月结转
	* @param value 
	*/
	public void setLast_month_carried(Double value) {
		this.last_month_carried = value;
	}
	
	/**
	* 获取 上月结转
	* @return Double
	*/
	public Double getLast_month_carried() {
		return this.last_month_carried;
	}
	/**
	* 设置 结转下月
	* @param value 
	*/
	public void setCarried_next_month(Double value) {
		this.carried_next_month = value;
	}
	
	/**
	* 获取 结转下月
	* @return Double
	*/
	public Double getCarried_next_month() {
		return this.carried_next_month;
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