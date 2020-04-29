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
 * 借款和报销月结表（科室）
 * @Table:
 * BUDG_BOR_PAY_DEPT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgBorPayDept implements Serializable {

	
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
	 * 科室ID
	 */
	private Long dept_id;
	
	/**
	 * 职工ID
	 */
	private Long emp_id;
	
	/**
	 * 期初借款余额
	 */
	private Double b_remain_amount;
	
	/**
	 * 本期借款金额
	 */
	private Double borrow_amount;
	
	/**
	 * 本期报销金额
	 */
	private Double payment_amount;
	
	/**
	 * 本期实际支付金额
	 */
	private Double pay_amount;
	
	/**
	 * 本期报销冲抵金额
	 */
	private Double offset_amount;
	
	/**
	 * 本期退还金额
	 */
	private Double return_amount;
	
	/**
	 * 期末借款余额
	 */
	private Double e_remain_amount;
	
	/**
	 * 本年累计借款金额
	 */
	private Double year_borrow_amount;
	
	/**
	 * 本年累计报销金额
	 */
	private Double year_payment_amount;
	
	/**
	 * 本年累计实际支付金额
	 */
	private Double year_pay_amount;
	
	/**
	 * 本年累计报销冲抵金额
	 */
	private Double year_offset_amount;
	
	/**
	 * 本年累计退还金额
	 */
	private Double year_return_amount;
	

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
	* 设置 科室ID
	* @param value 
	*/
	public void setDept_id(Long value) {
		this.dept_id = value;
	}
	
	/**
	* 获取 科室ID
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
	* 设置 期初借款余额
	* @param value 
	*/
	public void setB_remain_amount(Double value) {
		this.b_remain_amount = value;
	}
	
	/**
	* 获取 期初借款余额
	* @return Double
	*/
	public Double getB_remain_amount() {
		return this.b_remain_amount;
	}
	/**
	* 设置 本期借款金额
	* @param value 
	*/
	public void setBorrow_amount(Double value) {
		this.borrow_amount = value;
	}
	
	/**
	* 获取 本期借款金额
	* @return Double
	*/
	public Double getBorrow_amount() {
		return this.borrow_amount;
	}
	/**
	* 设置 本期报销金额
	* @param value 
	*/
	public void setPayment_amount(Double value) {
		this.payment_amount = value;
	}
	
	/**
	* 获取 本期报销金额
	* @return Double
	*/
	public Double getPayment_amount() {
		return this.payment_amount;
	}
	/**
	* 设置 本期实际支付金额
	* @param value 
	*/
	public void setPay_amount(Double value) {
		this.pay_amount = value;
	}
	
	/**
	* 获取 本期实际支付金额
	* @return Double
	*/
	public Double getPay_amount() {
		return this.pay_amount;
	}
	/**
	* 设置 本期报销冲抵金额
	* @param value 
	*/
	public void setOffset_amount(Double value) {
		this.offset_amount = value;
	}
	
	/**
	* 获取 本期报销冲抵金额
	* @return Double
	*/
	public Double getOffset_amount() {
		return this.offset_amount;
	}
	/**
	* 设置 本期退还金额
	* @param value 
	*/
	public void setReturn_amount(Double value) {
		this.return_amount = value;
	}
	
	/**
	* 获取 本期退还金额
	* @return Double
	*/
	public Double getReturn_amount() {
		return this.return_amount;
	}
	/**
	* 设置 期末借款余额
	* @param value 
	*/
	public void setE_remain_amount(Double value) {
		this.e_remain_amount = value;
	}
	
	/**
	* 获取 期末借款余额
	* @return Double
	*/
	public Double getE_remain_amount() {
		return this.e_remain_amount;
	}
	/**
	* 设置 本年累计借款金额
	* @param value 
	*/
	public void setYear_borrow_amount(Double value) {
		this.year_borrow_amount = value;
	}
	
	/**
	* 获取 本年累计借款金额
	* @return Double
	*/
	public Double getYear_borrow_amount() {
		return this.year_borrow_amount;
	}
	/**
	* 设置 本年累计报销金额
	* @param value 
	*/
	public void setYear_payment_amount(Double value) {
		this.year_payment_amount = value;
	}
	
	/**
	* 获取 本年累计报销金额
	* @return Double
	*/
	public Double getYear_payment_amount() {
		return this.year_payment_amount;
	}
	/**
	* 设置 本年累计实际支付金额
	* @param value 
	*/
	public void setYear_pay_amount(Double value) {
		this.year_pay_amount = value;
	}
	
	/**
	* 获取 本年累计实际支付金额
	* @return Double
	*/
	public Double getYear_pay_amount() {
		return this.year_pay_amount;
	}
	/**
	* 设置 本年累计报销冲抵金额
	* @param value 
	*/
	public void setYear_offset_amount(Double value) {
		this.year_offset_amount = value;
	}
	
	/**
	* 获取 本年累计报销冲抵金额
	* @return Double
	*/
	public Double getYear_offset_amount() {
		return this.year_offset_amount;
	}
	/**
	* 设置 本年累计退还金额
	* @param value 
	*/
	public void setYear_return_amount(Double value) {
		this.year_return_amount = value;
	}
	
	/**
	* 获取 本年累计退还金额
	* @return Double
	*/
	public Double getYear_return_amount() {
		return this.year_return_amount;
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