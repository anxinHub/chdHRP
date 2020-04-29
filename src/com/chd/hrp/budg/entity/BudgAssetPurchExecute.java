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
 * 标识（FLAG）：01固定资产，02无形资产

 * @Table:
 * BUDG_ASSET_PURCH_EXECUTE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgAssetPurchExecute implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 标识
	 */
	private Integer flag;
	
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
	private Object copy_code;
	
	/**
	 * 购置年度
	 */
	private Object purchase_year;
	
	/**
	 * 计划单号
	 */
	private Long plan_id;
	
	/**
	 * 入库单号
	 */
	private Long in_id;
	
	/**
	 * 入库月份
	 */
	private Object in_month;
	
	/**
	 * 资产编码
	 */
	private Object asset_code;
	
	/**
	 * 规格
	 */
	private Object spec;
	
	/**
	 * 资产类别
	 */
	private Object asset_type;
	
	/**
	 * 单价
	 */
	private String price;
	
	/**
	 * 数量
	 */
	private String num;
	
	/**
	 * 金额
	 */
	private Double budg_value;
	
	/**
	 * 资金来源_财政
	 */
	private Object finance_value;
	
	/**
	 * 资金来源_科研
	 */
	private Double reserch_value;
	
	/**
	 * 资金来源_教学
	 */
	private Double education_value;
	
	/**
	 * 资金来源_自筹
	 */
	private Double self_collect_value;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 标识
	* @param value 
	*/
	public void setFlag(Integer value) {
		this.flag = value;
	}
	
	/**
	* 获取 标识
	* @return Integer
	*/
	public Integer getFlag() {
		return this.flag;
	}
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
	public void setCopy_code(Object value) {
		this.copy_code = value;
	}
	
	/**
	* 获取 账套编码
	* @return Object
	*/
	public Object getCopy_code() {
		return this.copy_code;
	}
	/**
	* 设置 购置年度
	* @param value 
	*/
	public void setPurchase_year(Object value) {
		this.purchase_year = value;
	}
	
	/**
	* 获取 购置年度
	* @return Object
	*/
	public Object getPurchase_year() {
		return this.purchase_year;
	}
	/**
	* 设置 计划单号
	* @param value 
	*/
	public void setPlan_id(Long value) {
		this.plan_id = value;
	}
	
	/**
	* 获取 计划单号
	* @return Long
	*/
	public Long getPlan_id() {
		return this.plan_id;
	}
	/**
	* 设置 入库单号
	* @param value 
	*/
	public void setIn_id(Long value) {
		this.in_id = value;
	}
	
	/**
	* 获取 入库单号
	* @return Long
	*/
	public Long getIn_id() {
		return this.in_id;
	}
	/**
	* 设置 入库月份
	* @param value 
	*/
	public void setIn_month(Object value) {
		this.in_month = value;
	}
	
	/**
	* 获取 入库月份
	* @return Object
	*/
	public Object getIn_month() {
		return this.in_month;
	}
	/**
	* 设置 资产编码
	* @param value 
	*/
	public void setAsset_code(Object value) {
		this.asset_code = value;
	}
	
	/**
	* 获取 资产编码
	* @return Object
	*/
	public Object getAsset_code() {
		return this.asset_code;
	}
	/**
	* 设置 规格
	* @param value 
	*/
	public void setSpec(Object value) {
		this.spec = value;
	}
	
	/**
	* 获取 规格
	* @return Object
	*/
	public Object getSpec() {
		return this.spec;
	}
	/**
	* 设置 资产类别
	* @param value 
	*/
	public void setAsset_type(Object value) {
		this.asset_type = value;
	}
	
	/**
	* 获取 资产类别
	* @return Object
	*/
	public Object getAsset_type() {
		return this.asset_type;
	}
	/**
	* 设置 单价
	* @param value 
	*/
	public void setPrice(String value) {
		this.price = value;
	}
	
	/**
	* 获取 单价
	* @return String
	*/
	public String getPrice() {
		return this.price;
	}
	/**
	* 设置 数量
	* @param value 
	*/
	public void setNum(String value) {
		this.num = value;
	}
	
	/**
	* 获取 数量
	* @return String
	*/
	public String getNum() {
		return this.num;
	}
	/**
	* 设置 金额
	* @param value 
	*/
	public void setBudg_value(Double value) {
		this.budg_value = value;
	}
	
	/**
	* 获取 金额
	* @return Double
	*/
	public Double getBudg_value() {
		return this.budg_value;
	}
	/**
	* 设置 资金来源_财政
	* @param value 
	*/
	public void setFinance_value(Object value) {
		this.finance_value = value;
	}
	
	/**
	* 获取 资金来源_财政
	* @return Object
	*/
	public Object getFinance_value() {
		return this.finance_value;
	}
	/**
	* 设置 资金来源_科研
	* @param value 
	*/
	public void setReserch_value(Double value) {
		this.reserch_value = value;
	}
	
	/**
	* 获取 资金来源_科研
	* @return Double
	*/
	public Double getReserch_value() {
		return this.reserch_value;
	}
	/**
	* 设置 资金来源_教学
	* @param value 
	*/
	public void setEducation_value(Double value) {
		this.education_value = value;
	}
	
	/**
	* 获取 资金来源_教学
	* @return Double
	*/
	public Double getEducation_value() {
		return this.education_value;
	}
	/**
	* 设置 资金来源_自筹
	* @param value 
	*/
	public void setSelf_collect_value(Double value) {
		this.self_collect_value = value;
	}
	
	/**
	* 获取 资金来源_自筹
	* @return Double
	*/
	public Double getSelf_collect_value() {
		return this.self_collect_value;
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