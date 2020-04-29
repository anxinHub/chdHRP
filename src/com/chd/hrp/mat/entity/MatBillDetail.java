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
 * 保存一个发票对应的入库单，及金额
 * @Table:
 * MAT_BILL_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class MatBillDetail implements Serializable {

	
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
	 * 发票明细ID
	 */
	private Long bill_detail_id;
	
	/**
	 * 入库单ID
	 */
	private String in_id;
	
	/**
	 * 入库明细ID
	 */
	private Long in_detail_id;
	
	/**
	 * 发票金额
	 */
	private Long bill_money;
	
	/**
	 * 优惠金额
	 */
	private Double fav_money;
	
	private Double in_money;
	

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
	* 设置 入库单ID
	* @param value 
	*/
	public void setIn_id(String value) {
		this.in_id = value;
	}
	
	/**
	* 获取 入库单ID
	* @return String
	*/
	public String getIn_id() {
		return this.in_id;
	}
	/**
	* 设置 入库明细ID
	* @param value 
	*/
	public void setIn_detail_id(Long value) {
		this.in_detail_id = value;
	}
	
	/**
	* 获取 入库明细ID
	* @return Long
	*/
	public Long getIn_detail_id() {
		return this.in_detail_id;
	}
	/**
	* 设置 发票金额
	* @param value 
	*/
	public void setBill_money(Long value) {
		this.bill_money = value;
	}
	
	/**
	* 获取 发票金额
	* @return Long
	*/
	public Long getBill_money() {
		return this.bill_money;
	}
	/**
	* 设置 优惠金额
	* @param value 
	*/
	public void setFav_money(Double value) {
		this.fav_money = value;
	}
	
	/**
	* 获取 优惠金额
	* @return Double
	*/
	public Double getFav_money() {
		return this.fav_money;
	}
	
	public Double getIn_money() {
		return in_money;
	}

	public void setIn_money(Double in_money) {
		this.in_money = in_money;
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