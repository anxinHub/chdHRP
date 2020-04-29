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
 * 报销申请单
 * @Table:
 * BUDG_PAYMENT_APPLY
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgPaymentApply implements Serializable {

	
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
	 * 申请日期
	 */
	private Date apply_date;
	
	/**
	 * 科室ID
	 */
	private Long dept_id;
	
	/**
	 * 科室变更ID
	 */
	private Long dept_no;
	
	/**
	 * 项目ID
	 */
	private Long proj_id;
	
	/**
	 * 项目变更ID
	 */
	private Long proj_no;
	
	/**
	 * 报销人ID
	 */
	private Long emp_id;
	
	/**
	 * 报销人变更ID
	 */
	private Long emp_no;
	
	/**
	 * 申请事由
	 */
	private String remark;
	
	/**
	 * 报销金额
	 */
	private Double payment_amount;
	
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
	private Date check_date;
	
	/**
	 * 支付人
	 */
	private Long payer;
	
	/**
	 * 支付日期
	 */
	private Date pay_date;
	
	/**
	 * 状态
	 */
	private String state;
	
	/**
	 * 支付方式
	 */
	private String pay_way;
	
	/**
	 * 冲抵借款金额
	 */
	private Double offset_amount;
	
	/**
	 * 实际支付金额
	 */
	private Double pay_amount;
	
	/**
	 * 联系电话
	 */
	private String phone;
	
	/**
	 * 银行帐号
	 */
	private String card_no;
	

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
	* 设置 申请日期
	* @param value 
	*/
	public void setApply_date(Date value) {
		this.apply_date = value;
	}
	
	/**
	* 获取 申请日期
	* @return Date
	*/
	public Date getApply_date() {
		return this.apply_date;
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
	* 设置 科室变更ID
	* @param value 
	*/
	public void setDept_no(Long value) {
		this.dept_no = value;
	}
	
	/**
	* 获取 科室变更ID
	* @return Long
	*/
	public Long getDept_no() {
		return this.dept_no;
	}
	/**
	* 设置 项目ID
	* @param value 
	*/
	public void setProj_id(Long value) {
		this.proj_id = value;
	}
	
	/**
	* 获取 项目ID
	* @return Long
	*/
	public Long getProj_id() {
		return this.proj_id;
	}
	/**
	* 设置 项目变更ID
	* @param value 
	*/
	public void setProj_no(Long value) {
		this.proj_no = value;
	}
	
	/**
	* 获取 项目变更ID
	* @return Long
	*/
	public Long getProj_no() {
		return this.proj_no;
	}
	/**
	* 设置 报销人ID
	* @param value 
	*/
	public void setEmp_id(Long value) {
		this.emp_id = value;
	}
	
	/**
	* 获取 报销人ID
	* @return Long
	*/
	public Long getEmp_id() {
		return this.emp_id;
	}
	/**
	* 设置 报销人变更ID
	* @param value 
	*/
	public void setEmp_no(Long value) {
		this.emp_no = value;
	}
	
	/**
	* 获取 报销人变更ID
	* @return Long
	*/
	public Long getEmp_no() {
		return this.emp_no;
	}
	/**
	* 设置 申请事由
	* @param value 
	*/
	public void setRemark(String value) {
		this.remark = value;
	}
	
	/**
	* 获取 申请事由
	* @return String
	*/
	public String getRemark() {
		return this.remark;
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
	* 设置 制单日期
	* @param value 
	*/
	public void setMake_date(Date value) {
		this.make_date = value;
	}
	
	/**
	* 获取 制单日期
	* @return Date
	*/
	public Date getMake_date() {
		return this.make_date;
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
	public void setCheck_date(Date value) {
		this.check_date = value;
	}
	
	/**
	* 获取 审核日期
	* @return Date
	*/
	public Date getCheck_date() {
		return this.check_date;
	}
	/**
	* 设置 支付人
	* @param value 
	*/
	public void setPayer(Long value) {
		this.payer = value;
	}
	
	/**
	* 获取 支付人
	* @return Long
	*/
	public Long getPayer() {
		return this.payer;
	}
	/**
	* 设置 支付日期
	* @param value 
	*/
	public void setPay_date(Date value) {
		this.pay_date = value;
	}
	
	/**
	* 获取 支付日期
	* @return Date
	*/
	public Date getPay_date() {
		return this.pay_date;
	}
	/**
	* 设置 状态
	* @param value 
	*/
	public void setState(String value) {
		this.state = value;
	}
	
	/**
	* 获取 状态
	* @return String
	*/
	public String getState() {
		return this.state;
	}
	/**
	* 设置 支付方式
	* @param value 
	*/
	public void setPay_way(String value) {
		this.pay_way = value;
	}
	
	/**
	* 获取 支付方式
	* @return String
	*/
	public String getPay_way() {
		return this.pay_way;
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
	* 设置 联系电话
	* @param value 
	*/
	public void setPhone(String value) {
		this.phone = value;
	}
	
	/**
	* 获取 联系电话
	* @return String
	*/
	public String getPhone() {
		return this.phone;
	}
	/**
	* 设置 银行帐号
	* @param value 
	*/
	public void setCard_no(String value) {
		this.card_no = value;
	}
	
	/**
	* 获取 银行帐号
	* @return String
	*/
	public String getCard_no() {
		return this.card_no;
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