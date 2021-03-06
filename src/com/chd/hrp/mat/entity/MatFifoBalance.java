﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.entity;

import java.io.Serializable;
/**
 * 
 * @Description:
 * 货位暂时不做管理内容，默认为 0
 * @Table:
 * MAT_FIFO_BALANCE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class MatFifoBalance implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 
	 */
	private Long group_id;
	
	/**
	 * 
	 */
	private Long hos_id;
	
	/**
	 * 
	 */
	private Object copy_code;
	
	/**
	 * 
	 */
	private Long store_id;
	
	/**
	 * 
	 */
	private Long inv_id;
	
	/**
	 * 
	 */
	private Long batch_sn;
	
	/**
	 * 
	 */
	private Object batch_no;
	
	/**
	 * 
	 */
	private Object bar_code;
	
	/**
	 * 
	 */
	private Double price;
	
	/**
	 * 
	 */
	private Double sale_price;
	
	/**
	 * 
	 */
	private Double in_amount;
	
	/**
	 * 
	 */
	private Double in_money;
	
	/**
	 * 
	 */
	private Double in_sale_money;
	
	/**
	 * 
	 */
	private Double out_amount;
	
	/**
	 * 
	 */
	private Double out_money;
	
	/**
	 * 
	 */
	private Double out_sale_money;
	
	/**
	 * 
	 */
	private Double left_amount;
	
	/**
	 * 
	 */
	private Double left_money;
	
	/**
	 * 
	 */
	private Double left_sale_money;
	
	/**
	 * 
	 */
	private Double remove_zero_error;
	
	/**
	 * 
	 */
	private Double sale_zero_error;
	
	/**
	 * 
	 */
	private Long location_id;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 
	* @param value 
	*/
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getGroup_id() {
		return this.group_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setHos_id(Long value) {
		this.hos_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getHos_id() {
		return this.hos_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setCopy_code(Object value) {
		this.copy_code = value;
	}
	
	/**
	* 获取 
	* @return Object
	*/
	public Object getCopy_code() {
		return this.copy_code;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setStore_id(Long value) {
		this.store_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getStore_id() {
		return this.store_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setInv_id(Long value) {
		this.inv_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getInv_id() {
		return this.inv_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setBatch_sn(Long value) {
		this.batch_sn = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getBatch_sn() {
		return this.batch_sn;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setBatch_no(Object value) {
		this.batch_no = value;
	}
	
	/**
	* 获取 
	* @return Object
	*/
	public Object getBatch_no() {
		return this.batch_no;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setBar_code(Object value) {
		this.bar_code = value;
	}
	
	/**
	* 获取 
	* @return Object
	*/
	public Object getBar_code() {
		return this.bar_code;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setPrice(Double value) {
		this.price = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getPrice() {
		return this.price;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setSale_price(Double value) {
		this.sale_price = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getSale_price() {
		return this.sale_price;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setIn_amount(Double value) {
		this.in_amount = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getIn_amount() {
		return this.in_amount;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setIn_money(Double value) {
		this.in_money = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getIn_money() {
		return this.in_money;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setIn_sale_money(Double value) {
		this.in_sale_money = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getIn_sale_money() {
		return this.in_sale_money;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setOut_amount(Double value) {
		this.out_amount = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getOut_amount() {
		return this.out_amount;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setOut_money(Double value) {
		this.out_money = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getOut_money() {
		return this.out_money;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setOut_sale_money(Double value) {
		this.out_sale_money = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getOut_sale_money() {
		return this.out_sale_money;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setLeft_amount(Double value) {
		this.left_amount = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getLeft_amount() {
		return this.left_amount;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setLeft_money(Double value) {
		this.left_money = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getLeft_money() {
		return this.left_money;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setLeft_sale_money(Double value) {
		this.left_sale_money = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getLeft_sale_money() {
		return this.left_sale_money;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setRemove_zero_error(Double value) {
		this.remove_zero_error = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getRemove_zero_error() {
		return this.remove_zero_error;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setSale_zero_error(Double value) {
		this.sale_zero_error = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getSale_zero_error() {
		return this.sale_zero_error;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setLocation_id(Long value) {
		this.location_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getLocation_id() {
		return this.location_id;
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