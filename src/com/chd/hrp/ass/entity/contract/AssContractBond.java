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
 * 050502 资产合同保证金
 * @Table:
 * ASS_CONTRACT_BOND
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AssContractBond implements Serializable {

	
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
	 * 保证金单号
	 */
	private String bond_no;
	
	/**
	 * 合同ID
	 */
	private Long contract_id;
	
	/**
	 * 供应商ID
	 */
	private Integer ven_id;
	
	/**
	 * 供应商变更ID
	 */
	private String ven_no;
	
	/**
	 * 登记日期
	 */
	private String create_date;
	
	/**
	 * 保证金类型
	 */
	private String bond_flag;
	
	/**
	 * 付款方式
	 */
	private String pay_flag;
	
	/**
	 * 金额
	 */
	private Double pay_money;
	
	/**
	 * 状态
	 */
	private Integer state;
	
	/**
	 * 发票号
	 */
	private String bill_no;
	
	/**
	 * 支票号
	 */
	private String check_no;
	
	/**
	 * 原因
	 */
	private String reason;
	

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
	* 设置 保证金单号
	* @param value 
	*/
	public void setBond_no(String value) {
		this.bond_no = value;
	}
	
	/**
	* 获取 保证金单号
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
	* 设置 供应商ID
	* @param value 
	*/
	public void setVen_id(Integer value) {
		this.ven_id = value;
	}
	
	/**
	* 获取 供应商ID
	* @return Integer
	*/
	public Integer getVen_id() {
		return this.ven_id;
	}
	/**
	* 设置 供应商变更ID
	* @param value 
	*/
	public void setVen_no(String value) {
		this.ven_no = value;
	}
	
	/**
	* 获取 供应商变更ID
	* @return String
	*/
	public String getVen_no() {
		return this.ven_no;
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
	* 设置 保证金类型
	* @param value 
	*/
	public void setBond_flag(String value) {
		this.bond_flag = value;
	}
	
	/**
	* 获取 保证金类型
	* @return String
	*/
	public String getBond_flag() {
		return this.bond_flag;
	}
	/**
	* 设置 付款方式
	* @param value 
	*/
	public void setPay_flag(String value) {
		this.pay_flag = value;
	}
	
	/**
	* 获取 付款方式
	* @return String
	*/
	public String getPay_flag() {
		return this.pay_flag;
	}
	/**
	* 设置 金额
	* @param value 
	*/
	public void setPay_money(Double value) {
		this.pay_money = value;
	}
	
	/**
	* 获取 金额
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
	* 设置 发票号
	* @param value 
	*/
	public void setBill_no(String value) {
		this.bill_no = value;
	}
	
	/**
	* 获取 发票号
	* @return String
	*/
	public String getBill_no() {
		return this.bill_no;
	}
	/**
	* 设置 支票号
	* @param value 
	*/
	public void setCheck_no(String value) {
		this.check_no = value;
	}
	
	/**
	* 获取 支票号
	* @return String
	*/
	public String getCheck_no() {
		return this.check_no;
	}
	/**
	* 设置 原因
	* @param value 
	*/
	public void setReason(String value) {
		this.reason = value;
	}
	
	/**
	* 获取 原因
	* @return String
	*/
	public String getReason() {
		return this.reason;
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