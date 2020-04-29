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
 * 

 * @Table:
 * BUDG_ASS_CARD
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgAssCard implements Serializable {

	
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
	 * 卡片编号
	 */
	private String ass_card_no;
	
	/**
	 * 资产ID
	 */
	private Long ass_id;
	
	/**
	 * 资产性质
	 */
	private String naturs_code;
	
	/**
	 * 管理部门ID
	 */
	private Long dept_id;
	
	/**
	 * 使用状态
	 */
	private Long use_state;
	
	/**
	 * 1：在用 0：在库
	 */
	private Integer is_dept;
	
	/**
	 * 仓库ID
	 */
	private Long store_id;
	
	/**
	 * 是否投放
	 */
	private Integer is_throw;
	
	/**
	 * 入库日期
	 */
	private Date in_date;
	
	/**
	 * 备注
	 */
	private String note;
	
	/**
	 * 是否折旧
	 */
	private Integer is_depr;
	
	/**
	 * 折旧方法
	 */
	private String depr_method;
	
	/**
	 * 计提年数
	 */
	private Integer acc_depre_amount;
	
	/**
	 * 资产原值
	 */
	private Double price;
	
	/**
	 * 累计折旧
	 */
	private Double depre_money;
	
	/**
	 * 资产净值
	 */
	private Double cur_money;
	
	/**
	 * 预留残值
	 */
	private Double fore_money;
	
	/**
	 * 累计折旧月份
	 */
	private Integer add_depre_month;
	
	/**
	 * 上次折旧年
	 */
	private String last_depr_year;
	
	/**
	 * 上次折旧月
	 */
	private String last_depr_month;
	

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
	* 设置 卡片编号
	* @param value 
	*/
	public void setAss_card_no(String value) {
		this.ass_card_no = value;
	}
	
	/**
	* 获取 卡片编号
	* @return String
	*/
	public String getAss_card_no() {
		return this.ass_card_no;
	}
	/**
	* 设置 资产ID
	* @param value 
	*/
	public void setAss_id(Long value) {
		this.ass_id = value;
	}
	
	/**
	* 获取 资产ID
	* @return Long
	*/
	public Long getAss_id() {
		return this.ass_id;
	}
	/**
	* 设置 资产性质
	* @param value 
	*/
	public void setNaturs_code(String value) {
		this.naturs_code = value;
	}
	
	/**
	* 获取 资产性质
	* @return String
	*/
	public String getNaturs_code() {
		return this.naturs_code;
	}
	/**
	* 设置 管理部门ID
	* @param value 
	*/
	public void setDept_id(Long value) {
		this.dept_id = value;
	}
	
	/**
	* 获取 管理部门ID
	* @return Long
	*/
	public Long getDept_id() {
		return this.dept_id;
	}
	/**
	* 设置 使用状态
	* @param value 
	*/
	public void setUse_state(Long value) {
		this.use_state = value;
	}
	
	/**
	* 获取 使用状态
	* @return Long
	*/
	public Long getUse_state() {
		return this.use_state;
	}
	/**
	* 设置 1：在用 0：在库
	* @param value 
	*/
	public void setIs_dept(Integer value) {
		this.is_dept = value;
	}
	
	/**
	* 获取 1：在用 0：在库
	* @return Integer
	*/
	public Integer getIs_dept() {
		return this.is_dept;
	}
	/**
	* 设置 仓库ID
	* @param value 
	*/
	public void setStore_id(Long value) {
		this.store_id = value;
	}
	
	/**
	* 获取 仓库ID
	* @return Long
	*/
	public Long getStore_id() {
		return this.store_id;
	}
	/**
	* 设置 是否投放
	* @param value 
	*/
	public void setIs_throw(Integer value) {
		this.is_throw = value;
	}
	
	/**
	* 获取 是否投放
	* @return Integer
	*/
	public Integer getIs_throw() {
		return this.is_throw;
	}
	/**
	* 设置 入库日期
	* @param value 
	*/
	public void setIn_date(Date value) {
		this.in_date = value;
	}
	
	/**
	* 获取 入库日期
	* @return Date
	*/
	public Date getIn_date() {
		return this.in_date;
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
	* 设置 是否折旧
	* @param value 
	*/
	public void setIs_depr(Integer value) {
		this.is_depr = value;
	}
	
	/**
	* 获取 是否折旧
	* @return Integer
	*/
	public Integer getIs_depr() {
		return this.is_depr;
	}
	/**
	* 设置 折旧方法
	* @param value 
	*/
	public void setDepr_method(String value) {
		this.depr_method = value;
	}
	
	/**
	* 获取 折旧方法
	* @return String
	*/
	public String getDepr_method() {
		return this.depr_method;
	}
	/**
	* 设置 计提年数
	* @param value 
	*/
	public void setAcc_depre_amount(Integer value) {
		this.acc_depre_amount = value;
	}
	
	/**
	* 获取 计提年数
	* @return Integer
	*/
	public Integer getAcc_depre_amount() {
		return this.acc_depre_amount;
	}
	/**
	* 设置 资产原值
	* @param value 
	*/
	public void setPrice(Double value) {
		this.price = value;
	}
	
	/**
	* 获取 资产原值
	* @return Double
	*/
	public Double getPrice() {
		return this.price;
	}
	/**
	* 设置 累计折旧
	* @param value 
	*/
	public void setDepre_money(Double value) {
		this.depre_money = value;
	}
	
	/**
	* 获取 累计折旧
	* @return Double
	*/
	public Double getDepre_money() {
		return this.depre_money;
	}
	/**
	* 设置 资产净值
	* @param value 
	*/
	public void setCur_money(Double value) {
		this.cur_money = value;
	}
	
	/**
	* 获取 资产净值
	* @return Double
	*/
	public Double getCur_money() {
		return this.cur_money;
	}
	/**
	* 设置 预留残值
	* @param value 
	*/
	public void setFore_money(Double value) {
		this.fore_money = value;
	}
	
	/**
	* 获取 预留残值
	* @return Double
	*/
	public Double getFore_money() {
		return this.fore_money;
	}
	/**
	* 设置 累计折旧月份
	* @param value 
	*/
	public void setAdd_depre_month(Integer value) {
		this.add_depre_month = value;
	}
	
	/**
	* 获取 累计折旧月份
	* @return Integer
	*/
	public Integer getAdd_depre_month() {
		return this.add_depre_month;
	}
	/**
	* 设置 上次折旧年
	* @param value 
	*/
	public void setLast_depr_year(String value) {
		this.last_depr_year = value;
	}
	
	/**
	* 获取 上次折旧年
	* @return String
	*/
	public String getLast_depr_year() {
		return this.last_depr_year;
	}
	/**
	* 设置 上次折旧月
	* @param value 
	*/
	public void setLast_depr_month(String value) {
		this.last_depr_month = value;
	}
	
	/**
	* 获取 上次折旧月
	* @return String
	*/
	public String getLast_depr_month() {
		return this.last_depr_month;
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