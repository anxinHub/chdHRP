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
 * 期初现金流量累计
 * @Table:
 * BUDG_CASH_FLOW_BEGIN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgCashFlowBegin implements Serializable {

	
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
	 * 现金流量项目
	 */
	private String cash_item_id;
	
	/**
	 * 金额
	 */
	private Double amount;
	
	/**
	 * 备注
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
	* 设置 说明
	* @param value 
	*/
	public void setremark(String value) {
		this.remark = value;
	}
	
	/**
	* 获取说明
	* @return String
	*/
	public String getremark() {
		return this.remark;
	}
	/**
	* 设置 现金流量项目
	* @param value 
	*/
	public void setcash_item_id(String value) {
		this.cash_item_id = value;
	}
	
	/**
	* 获取 现金流量项目
	* @return String
	*/
	public String getcash_item_id() {
		return this.cash_item_id;
	}
	
	/**
	* 设置 金额
	* @param value 
	*/
	public void setAmount(Double value) {
		this.amount = value;
	}
	
	/**
	* 获取 金额
	* @return Double
	*/
	public Double getAmount() {
		return this.amount;
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