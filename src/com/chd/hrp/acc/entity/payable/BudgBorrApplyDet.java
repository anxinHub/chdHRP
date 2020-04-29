/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.acc.entity.payable;

import java.io.Serializable;
/**
 * 
 * @Description:
 * 借款明细
 * @Table:
 * 
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class BudgBorrApplyDet implements Serializable {

	
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
	 * 期初借款单号
	 */
	private String apply_code;
	
	/**
	 * 资金来源ID
	 */
	private Long source_id;
	private String source_name;
	private String source_id_no;
	/**
	 * 支出项目ID
	 */
	private Long payment_item_id;
	
	/**
	 * 支出项目变更ID
	 */
	private Long payment_item_no;
	
	private String payment_item_name;
	private String payment_item_id_no;
	/**
	 * 借款余额
	 */
	private Double borrow_amount;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	
	private String remark;
	
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSource_id_no() {
		return source_id_no;
	}
	
	public void setSource_id_no(String source_id_no) {
		this.source_id_no = source_id_no;
	}
	
	public String getPayment_item_id_no() {
		return payment_item_id_no;
	}
	
	public void setPayment_item_id_no(String payment_item_id_no) {
		this.payment_item_id_no = payment_item_id_no;
	}

	public String getSource_name() {
		return source_name;
	}
	
	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}
	
	public String getPayment_item_name() {
		return payment_item_name;
	}
	
	public void setPayment_item_name(String payment_item_name) {
		this.payment_item_name = payment_item_name;
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
	* 设置 期初借款单号
	* @param value 
	*/
	public void setApply_code(String value) {
		this.apply_code = value;
	}
	
	/**
	* 获取 期初借款单号
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
	* 设置 借款余额
	* @param value 
	*/
	public void setBorrow_amount(Double value) {
		this.borrow_amount = value;
	}
	
	/**
	* 获取 借款余额
	* @return Double
	*/
	public Double getBorrow_amount() {
		return this.borrow_amount;
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