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
 * 050502 资产合同银行保函
 * @Table:
 * ASS_CONTRACT_BANK
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AssContractBank implements Serializable {

	
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
	
	/**
	 * 年度
	 */
	private String acct_year;
	
	/**
	 * 保函单号
	 */
	private String bond_no;
	
	/**
	 * 合同ID
	 */
	private Long contract_id;
	
	/**
	 * 登记日期
	 */
	private String create_date;
	
	/**
	 * 担保金额
	 */
	private Double pay_money;
	
	/**
	 * 状态
	 */
	private Integer state;
	
	/**
	 * 保函内容
	 */
	private String reason;
	
	/**
	 * 银行信息
	 */
	private String bank_info;
	
	/**
	 * 银行负责人
	 */
	private String bank_emp;
	
	/**
	 * 银行联系电话
	 */
	private String bank_tel;
	
	/**
	 * 银行帐号
	 */
	private String bank_account;
	
	/**
	 * 委托方
	 */
	private String delegate;
	
	/**
	 * 委托方负责人
	 */
	private String delegate_emp;
	
	/**
	 * 委托人联系电话
	 */
	private String delegate_tel;
	
	/**
	 * 医院信息
	 */
	private String hos_info;
	

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
	* 设置 年度
	* @param value 
	*/
	public void setAcct_year(String value) {
		this.acct_year = value;
	}
	
	/**
	* 获取 年度
	* @return String
	*/
	public String getAcct_year() {
		return this.acct_year;
	}
	/**
	* 设置 保函单号
	* @param value 
	*/
	public void setBond_no(String value) {
		this.bond_no = value;
	}
	
	/**
	* 获取 保函单号
	* @return String
	*/
	public String getBond_no() {
		return this.bond_no;
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
	* 设置 登记日期
	* @param value 
	*/
	public void setCreate_date(String value) {
		this.create_date = value;
	}
	
	/**
	* 获取 登记日期
	* @return Date
	*/
	public String getCreate_date() {
		return this.create_date;
	}
	/**
	* 设置 担保金额
	* @param value 
	*/
	public void setPay_money(Double value) {
		this.pay_money = value;
	}
	
	/**
	* 获取 担保金额
	* @return Double
	*/
	public Double getPay_money() {
		return this.pay_money;
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
	* 设置 保函内容
	* @param value 
	*/
	public void setReason(String value) {
		this.reason = value;
	}
	
	/**
	* 获取 保函内容
	* @return String
	*/
	public String getReason() {
		return this.reason;
	}
	/**
	* 设置 银行信息
	* @param value 
	*/
	public void setBank_info(String value) {
		this.bank_info = value;
	}
	
	/**
	* 获取 银行信息
	* @return String
	*/
	public String getBank_info() {
		return this.bank_info;
	}
	/**
	* 设置 银行负责人
	* @param value 
	*/
	public void setBank_emp(String value) {
		this.bank_emp = value;
	}
	
	/**
	* 获取 银行负责人
	* @return String
	*/
	public String getBank_emp() {
		return this.bank_emp;
	}
	/**
	* 设置 银行联系电话
	* @param value 
	*/
	public void setBank_tel(String value) {
		this.bank_tel = value;
	}
	
	/**
	* 获取 银行联系电话
	* @return String
	*/
	public String getBank_tel() {
		return this.bank_tel;
	}
	/**
	* 设置 银行帐号
	* @param value 
	*/
	public void setBank_account(String value) {
		this.bank_account = value;
	}
	
	/**
	* 获取 银行帐号
	* @return String
	*/
	public String getBank_account() {
		return this.bank_account;
	}
	/**
	* 设置 委托方
	* @param value 
	*/
	public void setDelegate(String value) {
		this.delegate = value;
	}
	
	/**
	* 获取 委托方
	* @return String
	*/
	public String getDelegate() {
		return this.delegate;
	}
	/**
	* 设置 委托方负责人
	* @param value 
	*/
	public void setDelegate_emp(String value) {
		this.delegate_emp = value;
	}
	
	/**
	* 获取 委托方负责人
	* @return String
	*/
	public String getDelegate_emp() {
		return this.delegate_emp;
	}
	/**
	* 设置 委托人联系电话
	* @param value 
	*/
	public void setDelegate_tel(String value) {
		this.delegate_tel = value;
	}
	
	/**
	* 获取 委托人联系电话
	* @return String
	*/
	public String getDelegate_tel() {
		return this.delegate_tel;
	}
	/**
	* 设置 医院信息
	* @param value 
	*/
	public void setHos_info(String value) {
		this.hos_info = value;
	}
	
	/**
	* 获取 医院信息
	* @return String
	*/
	public String getHos_info() {
		return this.hos_info;
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