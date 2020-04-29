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
 * 报销申请单明细
 * @Table:
 * BUDG_PAYMENT_APPLY_DET
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgPaymentApplyDet implements Serializable {

	
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
	 * 申请单号
	 */
	private String apply_code;
	
	/**
	 * 资金来源ID
	 */
	private Long source_id;
	
	/**
	 * 支出项目ID
	 */
	private Long payment_item_id;
	
	/**
	 * 支出项目变更ID
	 */
	private Long payment_item_no;
	
	/**
	 * 报销金额
	 */
	private Double payment_amount;
	
	/**
	 * 冲抵借款金额
	 */
	private Double offset_amount;
	
	/**
	 * 实际支付金额
	 */
	private Double pay_amount;
	

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
	* 设置 申请单号
	* @param value 
	*/
	public void setApply_code(String value) {
		this.apply_code = value;
	}
	
	/**
	* 获取 申请单号
	* @return String
	*/
	public String getApply_code() {
		return this.apply_code;
	}
	/**
	* 设置 资金来源ID
	* @param value 
	*/
	public void setSource_id(Long value) {
		this.source_id = value;
	}
	
	/**
	* 获取 资金来源ID
	* @return Long
	*/
	public Long getSource_id() {
		return this.source_id;
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
	* 设置 报销金额
	* @param value 
	*/
	public void setPayment_amount(Double value) {
		this.payment_amount = value;
	}
	
	/**
	* 获取 报销金额
	* @return Double
	*/
	public Double getPayment_amount() {
		return this.payment_amount;
	}
	/**
	* 设置 冲抵借款金额
	* @param value 
	*/
	public void setOffset_amount(Double value) {
		this.offset_amount = value;
	}
	
	/**
	* 获取 冲抵借款金额
	* @return Double
	*/
	public Double getOffset_amount() {
		return this.offset_amount;
	}
	/**
	* 设置 实际支付金额
	* @param value 
	*/
	public void setPay_amount(Double value) {
		this.pay_amount = value;
	}
	
	/**
	* 获取 实际支付金额
	* @return Double
	*/
	public Double getPay_amount() {
		return this.pay_amount;
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