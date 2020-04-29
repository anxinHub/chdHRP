/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.contract;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 付款信息050501 资产合同主表
 * @Table:
 * ASS_CONTRACT_PAY_SET
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AssContractManage implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 集体ID
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
	private Long id;
	/**
	 * 合同ID
	 */
	private Long contract_id;
	
	/**
	 * 合同号
	 */
	private String contract_no;
	
	/**
	 * 年度
	 */
	private String acct_year;
	
	
	private Long payment_id;
	
	/**
	 * 摘要
	 */
	private String summary;
	
	/**
	 * 币种编码
	 */
	private String cur_code;
	
	/**
	 * 金额
	 */
	private Double pay_money;

	/**
	 * 开始日期
	 */
	private Date start_date;
	
	/**
	 * 结束日期
	 */
	private Date end_date;
	
	/**
	 * 实付日期
	 */
	private Date fact_pay_date;
	
	/**
	 * 状态
	 */
	private Integer state;
	
	/**
	 * 启用状态
	 */
	private Integer is_state;
	/**
	 * 创建日期
	 */
	private Date create_date;

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 集体ID
	* @param value 
	*/
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	
	/**
	* 获取 集体ID
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
	* 设置 合同ID
	* @param value 
	*/
	public void setContract_id(Long value) {
		this.contract_id = value;
	}
	
	/**
	* 获取 合同ID
	* @return Long
	*/
	public Long getContract_id() {
		return this.contract_id;
	}
	/**
	* 设置 合同号
	* @param value 
	*/
	public void setContract_no(String value) {
		this.contract_no = value;
	}
	
	/**
	* 获取 合同号
	* @return String
	*/
	public String getContract_no() {
		return this.contract_no;
	}
	
	/**
	* 设置 开始日期
	* @param value 
	*/
	public void setStart_date(Date value) {
		this.start_date = value;
	}
	
	/**
	* 获取 开始日期
	* @return Date
	*/
	public Date getStart_date() {
		return this.start_date;
	}
	/**
	* 设置 结束日期
	* @param value 
	*/
	public void setEnd_date(Date value) {
		this.end_date = value;
	}
	
	/**
	* 获取 结束日期
	* @return Date
	*/
	public Date getEnd_date() {
		return this.end_date;
	}
	
	/**
	* 设置 制单日期
	* @param value 
	*/
	public void setCreate_date(Date value) {
		this.create_date = value;
	}
	
	/**
	* 获取 制单日期
	* @return Date
	*/
	public Date getCreate_date() {
		return this.create_date;
	}
	
	/**
	* 设置 状态
	* @param value 
	*/
	public void setState(Integer value) {
		this.state = value;
	}
	
	/**
	* 获取 状态
	* @return Integer
	*/
	public Integer getState() {
		return this.state;
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

	/**
	 * @return the acct_year
	 */
	public String getAcct_year() {
		return acct_year;
	}

	/**
	 * @param acct_year the acct_year to set
	 */
	public void setAcct_year(String acct_year) {
		this.acct_year = acct_year;
	}

	/**
	 * @return the payment_id
	 */
	public Long getPayment_id() {
		return payment_id;
	}

	/**
	 * @param payment_id the payment_id to set
	 */
	public void setPayment_id(Long payment_id) {
		this.payment_id = payment_id;
	}

	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @param summary the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * @return the cur_code
	 */
	public String getCur_code() {
		return cur_code;
	}

	/**
	 * @param cur_code the cur_code to set
	 */
	public void setCur_code(String cur_code) {
		this.cur_code = cur_code;
	}

	/**
	 * @return the pay_money
	 */
	public Double getPay_money() {
		return pay_money;
	}

	/**
	 * @param pay_money the pay_money to set
	 */
	public void setPay_money(Double pay_money) {
		this.pay_money = pay_money;
	}

	/**
	 * @return the fact_pay_date
	 */
	public Date getFact_pay_date() {
		return fact_pay_date;
	}

	/**
	 * @param fact_pay_date the fact_pay_date to set
	 */
	public void setFact_pay_date(Date fact_pay_date) {
		this.fact_pay_date = fact_pay_date;
	}

	/**
	 * @return the is_state
	 */
	public Integer getIs_state() {
		return is_state;
	}

	/**
	 * @param is_state the is_state to set
	 */
	public void setIs_state(Integer is_state) {
		this.is_state = is_state;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	
}