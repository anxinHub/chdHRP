package com.chd.hrp.budg.entity;

import java.io.Serializable;

/**
 * 
 * @Description:
 * 其他收入预算
 * @Table:
 * BUDG_ELSE_INCOME
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
public class BudgElseIncome implements Serializable {

	private static final long serialVersionUID = -2959378126652861308L;
	
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
	 * 年度
	 */
	private String budg_year;
	
	/**
	 * 月份
	 */
	private String month;
	
	/**
	 * 科目编码
	 */
	private String subj_code;
	
	/**
	 * 上年业务量
	 */
	private Double last_income;
	
	/**
	 * 增长比例
	 */
	private Double grow_rate;
	
	/**
	 * 增长额
	 */
	private Double grow_value;
	
	/**
	 * 预算值
	 */
	private Double budg_value;
	
	/**
	 * 上月结转
	 */
	private Double last_month_carried;
	
	/**
	 * 结转下月
	 */
	private Double carried_next_month;
	
	/**
	 * 说明
	 */
	private String remark;
	

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
	* 设置 年度
	* @param value 
	*/
	public void setBudg_year(String value) {
		this.budg_year = value;
	}
	
	/**
	* 获取 年度
	* @return String
	*/
	public String getBudg_year() {
		return this.budg_year;
	}
	/**
	* 设置 月份
	* @param value 
	*/
	public void setMonth(String value) {
		this.month = value;
	}
	
	/**
	* 获取 月份
	* @return String
	*/
	public String getMonth() {
		return this.month;
	}
	/**
	* 设置 指标编码
	* @param value 
	*/
	public void setSubj_code(String value) {
		this.subj_code = value;
	}
	
	/**
	* 获取 指标编码
	* @return String
	*/
	public String getSubj_code() {
		return this.subj_code;
	}
	
	/**
	 * 设置 上年收入
	 * @return
	 */
	public Double getLast_income() {
		return last_income;
	}
	
	/**
	 * 获取 上年收入
	 * @param last_income
	 */
	public void setLast_income(Double last_income) {
		this.last_income = last_income;
	}
	/**
	* 设置 增长比例
	* @param value 
	*/
	public void setGrow_rate(Double value) {
		this.grow_rate = value;
	}
	
	/**
	* 获取 增长比例
	* @return Double
	*/
	public Double getGrow_rate() {
		return this.grow_rate;
	}
	/**
	* 设置 增长额
	* @param value 
	*/
	public void setGrow_value(Double value) {
		this.grow_value = value;
	}
	
	/**
	* 获取 增长额
	* @return Double
	*/
	public Double getGrow_value() {
		return this.grow_value;
	}
	/**
	* 设置 预算值
	* @param value 
	*/
	public void setBudg_value(Double value) {
		this.budg_value = value;
	}
	
	/**
	* 获取 预算值
	* @return Double
	*/
	public Double getBudg_value() {
		return this.budg_value;
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
	* 设置 说明
	* @param value 
	*/
	public void setRemark(String value) {
		this.remark = value;
	}
	
	/**
	* 获取 说明
	* @return String
	*/
	public String getRemark() {
		return this.remark;
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
