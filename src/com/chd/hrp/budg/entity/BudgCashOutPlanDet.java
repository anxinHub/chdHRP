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
 * 现金流出计划明细
 * @Table:
 * BUDG_CASH_OUT_PLAN_DET
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgCashOutPlanDet implements Serializable {

	
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
	 * 现金流出计划单号
	 */
	private String plan_code;
	
	/**
	 * 现金流量项目
	 */
	private String cash_item_code;
	
	/**
	 * 1月
	 */
	private Double january;
	
	/**
	 * 2月
	 */
	private Double february;
	
	/**
	 * 3月
	 */
	private Double march;
	
	/**
	 * 4月
	 */
	private Double april;
	
	/**
	 * 5月
	 */
	private Double may;
	
	/**
	 * 6月
	 */
	private Double june;
	
	/**
	 * 7月
	 */
	private Double july;
	
	/**
	 * 8月
	 */
	private Double august;
	
	/**
	 * 9月
	 */
	private Double september;
	
	/**
	 * 10月
	 */
	private Double october;
	
	/**
	 * 11月
	 */
	private Double november;
	
	/**
	 * 12月
	 */
	private Double december;
	

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
	* 设置 现金流出计划单号
	* @param value 
	*/
	public void setPlan_code(String value) {
		this.plan_code = value;
	}
	
	/**
	* 获取 现金流出计划单号
	* @return String
	*/
	public String getPlan_code() {
		return this.plan_code;
	}
	/**
	* 设置 现金流量项目
	* @param value 
	*/
	public void setCash_item_code(String value) {
		this.cash_item_code = value;
	}
	
	/**
	* 获取 现金流量项目
	* @return String
	*/
	public String getCash_item_code() {
		return this.cash_item_code;
	}
	/**
	* 设置 1月
	* @param value 
	*/
	public void setJanuary(Double value) {
		this.january = value;
	}
	
	/**
	* 获取 1月
	* @return Double
	*/
	public Double getJanuary() {
		return this.january;
	}
	/**
	* 设置 2月
	* @param value 
	*/
	public void setFebruary(Double value) {
		this.february = value;
	}
	
	/**
	* 获取 2月
	* @return Double
	*/
	public Double getFebruary() {
		return this.february;
	}
	/**
	* 设置 3月
	* @param value 
	*/
	public void setMarch(Double value) {
		this.march = value;
	}
	
	/**
	* 获取 3月
	* @return Double
	*/
	public Double getMarch() {
		return this.march;
	}
	/**
	* 设置 4月
	* @param value 
	*/
	public void setApril(Double value) {
		this.april = value;
	}
	
	/**
	* 获取 4月
	* @return Double
	*/
	public Double getApril() {
		return this.april;
	}
	/**
	* 设置 5月
	* @param value 
	*/
	public void setMay(Double value) {
		this.may = value;
	}
	
	/**
	* 获取 5月
	* @return Double
	*/
	public Double getMay() {
		return this.may;
	}
	/**
	* 设置 6月
	* @param value 
	*/
	public void setJune(Double value) {
		this.june = value;
	}
	
	/**
	* 获取 6月
	* @return Double
	*/
	public Double getJune() {
		return this.june;
	}
	/**
	* 设置 7月
	* @param value 
	*/
	public void setJuly(Double value) {
		this.july = value;
	}
	
	/**
	* 获取 7月
	* @return Double
	*/
	public Double getJuly() {
		return this.july;
	}
	/**
	* 设置 8月
	* @param value 
	*/
	public void setAugust(Double value) {
		this.august = value;
	}
	
	/**
	* 获取 8月
	* @return Double
	*/
	public Double getAugust() {
		return this.august;
	}
	/**
	* 设置 9月
	* @param value 
	*/
	public void setSeptember(Double value) {
		this.september = value;
	}
	
	/**
	* 获取 9月
	* @return Double
	*/
	public Double getSeptember() {
		return this.september;
	}
	/**
	* 设置 10月
	* @param value 
	*/
	public void setOctober(Double value) {
		this.october = value;
	}
	
	/**
	* 获取 10月
	* @return Double
	*/
	public Double getOctober() {
		return this.october;
	}
	/**
	* 设置 11月
	* @param value 
	*/
	public void setNovember(Double value) {
		this.november = value;
	}
	
	/**
	* 获取 11月
	* @return Double
	*/
	public Double getNovember() {
		return this.november;
	}
	/**
	* 设置 12月
	* @param value 
	*/
	public void setDecember(Double value) {
		this.december = value;
	}
	
	/**
	* 获取 12月
	* @return Double
	*/
	public Double getDecember() {
		return this.december;
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