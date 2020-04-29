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
 * 期初货币资金
 * @Table:
 * BUDG_CASH_BEGIN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgCashBegin implements Serializable {

	
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
	 * 年初货币资金
	 */
	private Double cash_begin_year;
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
	 * 期初货币资金
	 */
	private Double cash_begin;
	/**
	 * 状态
	 */
	private String state;
	 

	public Double getCash_begin_year() {
		return cash_begin_year;
	}

	public void setCash_begin_year(Double cash_begin_year) {
		this.cash_begin_year = cash_begin_year;
	}

	public Double getCash_in() {
		return cash_in;
	}

	public void setCash_in(Double cash_in) {
		this.cash_in = cash_in;
	}

	public Double getCash_out() {
		return cash_out;
	}

	public void setCash_out(Double cash_out) {
		this.cash_out = cash_out;
	}

	public Double getCash_add() {
		return cash_add;
	}

	public void setCash_add(Double cash_add) {
		this.cash_add = cash_add;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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
	* 设置 期初货币资金
	* @param value 
	*/
	public void setCash_begin(Double value) {
		this.cash_begin = value;
	}
	
	/**
	* 获取 期初货币资金
	* @return Double
	*/
	public Double getCash_begin() {
		return this.cash_begin;
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