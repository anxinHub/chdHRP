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
 * 现金存量预算
 * @Table:
 * BUDG_CASH
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgCash implements Serializable {

	
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
	 *  执行表中  预算年度
	 */
	private String year;
	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	/**
	 * 月份
	 */
	private String month;
	
	/**
	 * 期初现金存量
	 */
	private Double cash_begain;
	/**
	 * 执行表 中    期初现金存量
	 */
	private Double cash_begin;
	
	/**
	 * 现金流入
	 */
	private Double cash_in;
	
	/**
	 * 现金流出
	 */
	private Double cash_out;
	
	/**
	 * 现金净增加额
	 */
	private Double cash_add;
	
	/**
	 * 期末现金存量
	 */
	private Double cash_end;
	/**
	 * 结转值
	 */
	private Double cash_carry;
	

  public Double getCash_carry() {
		return cash_carry;
	}

	public void setCash_carry(Double cash_carry) {
		this.cash_carry = cash_carry;
	}
/**
	 * 导入验证信息
	 */
	private String error_type;
	
	
	public Double getCash_begin() {
		return cash_begin;
	}

	public void setCash_begin(Double cash_begin) {
		this.cash_begin = cash_begin;
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
	* 设置 期初现金存量
	* @param value 
	*/
	public void setCash_begain(Double value) {
		this.cash_begain = value;
	}
	
	/**
	* 获取 期初现金存量
	* @return Double
	*/
	public Double getCash_begain() {
		return this.cash_begain;
	}
	/**
	* 设置 现金流入
	* @param value 
	*/
	public void setCash_in(Double value) {
		this.cash_in = value;
	}
	
	/**
	* 获取 现金流入
	* @return Double
	*/
	public Double getCash_in() {
		return this.cash_in;
	}
	/**
	* 设置 现金流出
	* @param value 
	*/
	public void setCash_out(Double value) {
		this.cash_out = value;
	}
	
	/**
	* 获取 现金流出
	* @return Double
	*/
	public Double getCash_out() {
		return this.cash_out;
	}
	/**
	* 设置 现金净增加额
	* @param value 
	*/
	public void setCash_add(Double value) {
		this.cash_add = value;
	}
	
	/**
	* 获取 现金净增加额
	* @return Double
	*/
	public Double getCash_add() {
		return this.cash_add;
	}
	/**
	* 设置 期末现金存量
	* @param value 
	*/
	public void setCash_end(Double value) {
		this.cash_end = value;
	}
	
	/**
	* 获取 期末现金存量
	* @return Double
	*/
	public Double getCash_end() {
		return this.cash_end;
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