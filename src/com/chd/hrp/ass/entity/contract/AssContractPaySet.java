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
 * 050502 资产合同分期付款设置
 * @Table:
 * ASS_CONTRACT_PAY_SET
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AssContractPaySet implements Serializable {

	
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
	 * 合同ID
	 */
	private Long contract_id;
	
	/**
	 * 合同号
	 */
	private String contract_no;
	
	/**
	 * 付款期号
	 */
	private Integer payment_id;
	
	/**
	 * 摘要
	 */
	private String summary;
	
	/**
	 * 本币金额
	 */
	private Double payment_money;
	
	/**
	 * 外币金额
	 */
	private Double foreign_money;
	
	/**
	 * 付款金额
	 */
	private Double pay_money;
	
	/**
	 * 付款期间开始
	 */
	private Date start_date;
	
	/**
	 * 付款期间结束
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
	* 设置 付款期号
	* @param value 
	*/
	public void setPayment_id(Integer value) {
		this.payment_id = value;
	}
	
	/**
	* 获取 付款期号
	* @return Integer
	*/
	public Integer getPayment_id() {
		return this.payment_id;
	}
	/**
	* 设置 摘要
	* @param value 
	*/
	public void setSummary(String value) {
		this.summary = value;
	}
	
	/**
	* 获取 摘要
	* @return String
	*/
	public String getSummary() {
		return this.summary;
	}
	/**
	* 设置 本币金额
	* @param value 
	*/
	public void setPayment_money(Double value) {
		this.payment_money = value;
	}
	
	/**
	* 获取 本币金额
	* @return Double
	*/
	public Double getPayment_money() {
		return this.payment_money;
	}
	/**
	* 设置 外币金额
	* @param value 
	*/
	public void setForeign_money(Double value) {
		this.foreign_money = value;
	}
	
	/**
	* 获取 外币金额
	* @return Double
	*/
	public Double getForeign_money() {
		return this.foreign_money;
	}
	/**
	* 设置 付款金额
	* @param value 
	*/
	public void setPay_money(Double value) {
		this.pay_money = value;
	}
	
	/**
	* 获取 付款金额
	* @return Double
	*/
	public Double getPay_money() {
		return this.pay_money;
	}
	/**
	* 设置 付款期间开始
	* @param value 
	*/
	public void setStart_date(Date value) {
		this.start_date = value;
	}
	
	/**
	* 获取 付款期间开始
	* @return Date
	*/
	public Date getStart_date() {
		return this.start_date;
	}
	/**
	* 设置 付款期间结束
	* @param value 
	*/
	public void setEnd_date(Date value) {
		this.end_date = value;
	}
	
	/**
	* 获取 付款期间结束
	* @return Date
	*/
	public Date getEnd_date() {
		return this.end_date;
	}
	/**
	* 设置 实付日期
	* @param value 
	*/
	public void setFact_pay_date(Date value) {
		this.fact_pay_date = value;
	}
	
	/**
	* 获取 实付日期
	* @return Date
	*/
	public Date getFact_pay_date() {
		return this.fact_pay_date;
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
	* 设置 启用状态
	* @param value 
	*/
	public void setIs_state(Integer value) {
		this.is_state = value;
	}
	
	/**
	* 获取 启用状态
	* @return Integer
	*/
	public Integer getIs_state() {
		return this.is_state;
	}
	/**
	* 设置 创建日期
	* @param value 
	*/
	public void setCreate_date(Date value) {
		this.create_date = value;
	}
	
	/**
	* 获取 创建日期
	* @return Date
	*/
	public Date getCreate_date() {
		return this.create_date;
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