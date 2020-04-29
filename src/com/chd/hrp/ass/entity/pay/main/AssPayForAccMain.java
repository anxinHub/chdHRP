/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
  
package com.chd.hrp.ass.entity.pay.main;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * state
1:未审核；2审核；3:记帐
PAY_BILL_TYPE: 0付款 1 退款
 * @Table:
 * MAT_PAY_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AssPayForAccMain implements Serializable {

	
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
	 * 付款单ID
	 */
	private Long pay_no;
	
	/**
	 * 付款日期
	 */
	private Date pay_date;
	
	/**
	 * 单据类型
	 */
	private String pay_bill_type;
	
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
	 * 付款方式
	 */
	private String pay_type_code;
	
	/**
	 * 付款账号
	 */
	private String acct_code;
	
	/**
	 * 票据号码
	 */
	private String cheq_no;
	/**
	 * 应付金额
	 */
	private Double payable_money;
	/**
	 * 已付金额
	 */
	private Double payed_money;
	
	/**
	 * 付款金额
	 */
	private Double pay_money;
	
	/**
	 * 制单人
	 */
	private Long maker;
	
	/**
	 * 制单日期
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
	 * 期初标志
	 */
	private Integer is_init;
	
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
	* 设置 付款单ID
	* @param value 
	*/
	public void setPay_no(Long value) {
		this.pay_no = value;
	}
	
	/**
	* 获取 付款单ID
	* @return Long
	*/
	public Long getPay_no() {
		return this.pay_no;
	}
	
	/**
	* 设置 付款日期
	* @param value 
	*/
	public void setPay_date(Date value) {
		this.pay_date = value;
	}
	
	/**
	* 获取 付款日期
	* @return Date
	*/
	public Date getPay_date() {
		return this.pay_date;
	}
	/**
	* 设置 单据类型
	* @param value 
	*/
	public void setPay_bill_type(String value) {
		this.pay_bill_type = value;
	}
	
	/**
	* 获取 单据类型
	* @return String
	*/
	public String getPay_bill_type() {
		return this.pay_bill_type;
	}
	/**
	* 设置 付款方式
	* @param value 
	*/
	public void setPay_type_code(String value) {
		this.pay_type_code = value;
	}
	
	/**
	* 获取 付款方式
	* @return String
	*/
	public String getPay_type_code() {
		return this.pay_type_code;
	}
	/**
	* 设置 票据号码
	* @param value 
	*/
	public void setCheq_no(String value) {
		this.cheq_no = value;
	}
	
	/**
	* 获取 票据号码
	* @return String
	*/
	public String getCheq_no() {
		return this.cheq_no;
	}
	/**
	* 设置 付款账号
	* @param value 
	*/
	public void setAcct_code(String value) {
		this.acct_code = value;
	}
	
	/**
	* 获取 付款账号
	* @return String
	*/
	public String getAcct_code() {
		return this.acct_code;
	}
	public Double getPayable_money() {
		return payable_money;
	}

	public void setPayable_money(Double payable_money) {
		this.payable_money = payable_money;
	}

	public Double getPayed_money() {
		return payed_money;
	}

	public void setPayed_money(Double payed_money) {
		this.payed_money = payed_money;
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
	
	public Date getMake_date() {
		return make_date;
	}

	public void setMake_date(Date make_date) {
		this.make_date = make_date;
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