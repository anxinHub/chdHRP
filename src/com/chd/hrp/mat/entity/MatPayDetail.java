/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.entity;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 保存一个付款单对应的发票
 * @Table:
 * MAT_PAY_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class MatPayDetail implements Serializable {

	
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
	private Long pay_id;
	
	/**
	 * 付款单据号
	 */
	private Long pay_bill_no;
	/**
	 * 付款单明细ID
	 */
	private Long pay_detail_id;
	
	/**
	 * 发票ID
	 */
	private Long bill_id;
	
	/**
	 * 发票明细ID
	 */
	private Long bill_detail_id;
	
	/**
	 * 发票金额
	 */
	private Double bill_money;
	
	/**
	 * 已付金额
	 */
	private Double payed_money;
	
	/**
	 * 付款金额
	 */
	private Double pay_money;
	private Double dis_money;
	

  public Double getDis_money() {
		return dis_money;
	}

	public void setDis_money(Double dis_money) {
		this.dis_money = dis_money;
	}
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
	public void setPay_id(Long value) {
		this.pay_id = value;
	}
	
	/**
	* 获取 付款单ID
	* @return Long
	*/
	public Long getPay_id() {
		return this.pay_id;
	}
	/**
	* 设置 付款单明细ID
	* @param value 
	*/
	public void setPay_detail_id(Long value) {
		this.pay_detail_id = value;
	}
	
	/**
	* 获取 付款单明细ID
	* @return Long
	*/
	public Long getPay_detail_id() {
		return this.pay_detail_id;
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
	* 设置 发票明细ID
	* @param value 
	*/
	public void setBill_detail_id(Long value) {
		this.bill_detail_id = value;
	}
	
	/**
	* 获取 发票明细ID
	* @return Long
	*/
	public Long getBill_detail_id() {
		return this.bill_detail_id;
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
	
	public Long getPay_bill_no() {
		return pay_bill_no;
	}

	public void setPay_bill_no(Long pay_bill_no) {
		this.pay_bill_no = pay_bill_no;
	}

	public Double getBill_money() {
		return bill_money;
	}

	public void setBill_money(Double bill_money) {
		this.bill_money = bill_money;
	}

	public Double getPayed_money() {
		return payed_money;
	}

	public void setPayed_money(Double payed_money) {
		this.payed_money = payed_money;
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