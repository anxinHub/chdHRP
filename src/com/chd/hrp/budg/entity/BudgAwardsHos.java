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
 * 全院奖金预算
 * @Table:
 * BUDG_AWARDS_HOS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgAwardsHos implements Serializable {

	
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
	 * 年度
	 */
	private String year;
	
	/**
	 * 月份
	 */
	private String month;
	
	/**
	 * 奖金项目编码
	 */
	private String awards_item_code;
	
	/**
	 * 上年同期数
	 */
	private Double last_value;
	
	/**
	 * 增长比例
	 */
	private Double grow_rate;
	
	/**
	 * 计算值
	 */
	private Double count_value;
	
	/**
	 * 支出预算
	 */
	private Double cost_budg;
	
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
	public void setYear(String value) {
		this.year = value;
	}
	
	/**
	* 获取 年度
	* @return String
	*/
	public String getYear() {
		return this.year;
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
	* 设置 奖金项目编码
	* @param value 
	*/
	public void setAwards_item_code(String value) {
		this.awards_item_code = value;
	}
	
	/**
	* 获取 奖金项目编码
	* @return String
	*/
	public String getAwards_item_code() {
		return this.awards_item_code;
	}
	/**
	* 设置 上年同期数
	* @param value 
	*/
	public void setLast_value(Double value) {
		this.last_value = value;
	}
	
	/**
	* 获取 上年同期数
	* @return Double
	*/
	public Double getLast_value() {
		return this.last_value;
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
	* 设置 计算值
	* @param value 
	*/
	public void setCount_value(Double value) {
		this.count_value = value;
	}
	
	/**
	* 获取 计算值
	* @return Double
	*/
	public Double getCount_value() {
		return this.count_value;
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