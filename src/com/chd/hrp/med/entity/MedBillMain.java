/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.entity;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 保存采购发票的主要信息
 * @Table:
 * MED_BILL_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class MedBillMain implements Serializable {

	
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
	 * 发票ID
	 */
	private Long bill_id;
	
	/**
	 * 发票号
	 */
	private String bill_no;
	
	private String bill_code;
	/**
	 * 原始发票号
	 */
	private String ori_no;
	
	private String bill_type;
	/**
	 * 采购类型
	 */
	private String stock_type_code;
	
	/**
	 * 开票日期
	 */
	private Date bill_date;
	
	/**
	 * 供应商ID
	 */
	private Long sup_id;
	
	/**
	 * 供应商变更ID
	 */
	private Long sup_no;
	
	/**
	 * 付款条件
	 */
	private String pay_code;
	
	/**
	 * 期初标志
	 */
	private Integer is_init;
	
	/**
	 * 单据总金额
	 */
	private Double payable_money;
	
	/**
	 * 发票金额
	 */
	private Double bill_money;
	
	/**
	 * 制单人
	 */
	private Long maker;
	
	/**
	 * 结算日期
	 */
	private Date make_date;
	
	/**
	 * 审核人
	 */
	private Long checker;
	
	/**
	 * 审核日期
	 */
	private Date chk_date;

	/**
	 * 状态
	 */
	private Integer state;
	
	/**
	 * 备注
	 */
	private String note;
	
	

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
	* 设置 发票ID
	* @param value 
	*/
	public void setBill_id(Long value) {
		this.bill_id = value;
	}
	
	/**
	* 获取 发票ID
	* @return Long
	*/
	public Long getBill_id() {
		return this.bill_id;
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
	* 设置 原始发票号
	* @param value 
	*/
	public void setOri_no(String value) {
		this.ori_no = value;
	}
	
	/**
	* 获取 原始发票号
	* @return String
	*/
	public String getOri_no() {
		return this.ori_no;
	}
	/**
	* 设置 采购类型
	* @param value 
	*/
	public void setStock_type_code(String value) {
		this.stock_type_code = value;
	}
	
	/**
	* 获取 采购类型
	* @return String
	*/
	public String getStock_type_code() {
		return this.stock_type_code;
	}
	/**
	* 设置 开票日期
	* @param value 
	*/
	public void setBill_date(Date value) {
		this.bill_date = value;
	}
	
	/**
	* 获取 开票日期
	* @return Date
	*/
	public Date getBill_date() {
		return this.bill_date;
	}
	/**
	* 设置 供应商ID
	* @param value 
	*/
	public void setSup_id(Long value) {
		this.sup_id = value;
	}
	
	/**
	* 获取 供应商ID
	* @return Long
	*/
	public Long getSup_id() {
		return this.sup_id;
	}
	/**
	* 设置 供应商变更ID
	* @param value 
	*/
	public void setSup_no(Long value) {
		this.sup_no = value;
	}
	
	/**
	* 获取 供应商变更ID
	* @return Long
	*/
	public Long getSup_no() {
		return this.sup_no;
	}
	/**
	* 设置 付款条件
	* @param value 
	*/
	public void setPay_code(String value) {
		this.pay_code = value;
	}
	
	/**
	* 获取 付款条件
	* @return String
	*/
	public String getPay_code() {
		return this.pay_code;
	}
	/**
	* 设置 备注
	* @param value 
	*/
	public void setNote(String value) {
		this.note = value;
	}
	
	/**
	* 获取 备注
	* @return String
	*/
	public String getNote() {
		return this.note;
	}
	
	public Date getMake_date() {
		return make_date;
	}

	public void setMake_date(Date make_date) {
		this.make_date = make_date;
	}

	/**
	* 设置 制单人
	* @param value 
	*/
	public void setMaker(Long value) {
		this.maker = value;
	}
	
	/**
	* 获取 制单人
	* @return Long
	*/
	public Long getMaker() {
		return this.maker;
	}
	/**
	* 设置 审核人
	* @param value 
	*/
	public void setChecker(Long value) {
		this.checker = value;
	}
	
	/**
	* 获取 审核人
	* @return Long
	*/
	public Long getChecker() {
		return this.checker;
	}
	/**
	* 设置 审核日期
	* @param value 
	*/
	public void setChk_date(Date value) {
		this.chk_date = value;
	}
	
	/**
	* 获取 审核日期
	* @return Date
	*/
	public Date getChk_date() {
		return this.chk_date;
	}
	/**
	* 设置 期初标志
	* @param value 
	*/
	public void setIs_init(Integer value) {
		this.is_init = value;
	}
	
	/**
	* 获取 期初标志
	* @return Integer
	*/
	public Integer getIs_init() {
		return this.is_init;
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
	* 设置 发票金额
	* @param value 
	*/
	public void setBill_money(Double value) {
		this.bill_money = value;
	}
	
	/**
	* 获取 发票金额
	* @return Double
	*/
	public Double getBill_money() {
		return this.bill_money;
	}
	
	public String getBill_code() {
		return bill_code;
	}

	public void setBill_code(String bill_code) {
		this.bill_code = bill_code;
	}

	public String getBill_type() {
		return bill_type;
	}

	public void setBill_type(String bill_type) {
		this.bill_type = bill_type;
	}
	
	public Double getPayable_money() {
		return payable_money;
	}

	public void setPayable_money(Double payable_money) {
		this.payable_money = payable_money;
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