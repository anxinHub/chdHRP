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
 * BUDG_ASSET_PUR_DETAIL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgAssetPurDetail implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 资产性质
	 */
	private Integer asset_nature;
	
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
	 * 计划年度
	 */
	private String plan_year;
	
	/**
	 * 计划单号
	 */
	private String plan_id;
	
	/**
	 * 明细序号
	 */
	private Long detail_id;
	
	/**
	 * 资产编码
	 */
	private String asset_code;
	
	/**
	 * 规格
	 */
	private String spec;
	
	/**
	 * 单价
	 */
	private Double price;
	
	/**
	 * 数量
	 */
	private Long num;
	
	/**
	 * 金额
	 */
	private Double amount;
	
	/**
	 * 预购置月
	 */
	private String budg_month;
	
	/**
	 * 申请科室
	 */
	private Long dept_id;
	
	/**
	 * 申请原因
	 */
	private String reson;
	
	/**
	 * 资金来源_财政
	 */
	private Double financial_fund;
	
	/**
	 * 资金来源_科研
	 */
	private Double reserch_fund;
	
	/**
	 * 资金来源_教学
	 */
	private Double education_fund;
	
	/**
	 * 资金来源_自筹
	 */
	private Double self_collect_fund;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 资产性质
	* @param value 
	*/
	public void setAsset_nature(Integer value) {
		this.asset_nature = value;
	}
	
	/**
	* 获取 资产性质
	* @return Integer
	*/
	public Integer getAsset_nature() {
		return this.asset_nature;
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
	* 设置 计划年度
	* @param value 
	*/
	public void setPlan_year(String value) {
		this.plan_year = value;
	}
	
	/**
	* 获取 计划年度
	* @return String
	*/
	public String getPlan_year() {
		return this.plan_year;
	}
	/**
	* 设置 计划单号
	* @param value 
	*/
	public void setPlan_id(String value) {
		this.plan_id = value;
	}
	
	/**
	* 获取 计划单号
	* @return String
	*/
	public String getPlan_id() {
		return this.plan_id;
	}
	/**
	* 设置 明细序号
	* @param value 
	*/
	public void setDetail_id(Long value) {
		this.detail_id = value;
	}
	
	/**
	* 获取 明细序号
	* @return Long
	*/
	public Long getDetail_id() {
		return this.detail_id;
	}
	/**
	* 设置 资产编码
	* @param value 
	*/
	public void setAsset_code(String value) {
		this.asset_code = value;
	}
	
	/**
	* 获取 资产编码
	* @return String
	*/
	public String getAsset_code() {
		return this.asset_code;
	}
	/**
	* 设置 规格
	* @param value 
	*/
	public void setSpec(String value) {
		this.spec = value;
	}
	
	/**
	* 获取 规格
	* @return String
	*/
	public String getSpec() {
		return this.spec;
	}
	/**
	* 设置 单价
	* @param value 
	*/
	public void setPrice(Double value) {
		this.price = value;
	}
	
	/**
	* 获取 单价
	* @return Double
	*/
	public Double getPrice() {
		return this.price;
	}
	/**
	* 设置 数量
	* @param value 
	*/
	public void setNum(Long value) {
		this.num = value;
	}
	
	/**
	* 获取 数量
	* @return Long
	*/
	public Long getNum() {
		return this.num;
	}
	/**
	* 设置 金额
	* @param value 
	*/
	public void setAmount(Double value) {
		this.amount = value;
	}
	
	/**
	* 获取 金额
	* @return Double
	*/
	public Double getAmount() {
		return this.amount;
	}
	/**
	* 设置 预购置月
	* @param value 
	*/
	public void setBudg_month(String value) {
		this.budg_month = value;
	}
	
	/**
	* 获取 预购置月
	* @return String
	*/
	public String getBudg_month() {
		return this.budg_month;
	}
	/**
	* 设置 申请科室
	* @param value 
	*/
	public void setDept_id(Long value) {
		this.dept_id = value;
	}
	
	/**
	* 获取 申请科室
	* @return Long
	*/
	public Long getDept_id() {
		return this.dept_id;
	}
	/**
	* 设置 申请原因
	* @param value 
	*/
	public void setReson(String value) {
		this.reson = value;
	}
	
	/**
	* 获取 申请原因
	* @return String
	*/
	public String getReson() {
		return this.reson;
	}
	/**
	* 设置 资金来源_财政
	* @param value 
	*/
	public void setFinancial_fund(Double value) {
		this.financial_fund = value;
	}
	
	/**
	* 获取 资金来源_财政
	* @return Double
	*/
	public Double getFinancial_fund() {
		return this.financial_fund;
	}
	/**
	* 设置 资金来源_科研
	* @param value 
	*/
	public void setReserch_fund(Double value) {
		this.reserch_fund = value;
	}
	
	/**
	* 获取 资金来源_科研
	* @return Double
	*/
	public Double getReserch_fund() {
		return this.reserch_fund;
	}
	/**
	* 设置 资金来源_教学
	* @param value 
	*/
	public void setEducation_fund(Double value) {
		this.education_fund = value;
	}
	
	/**
	* 获取 资金来源_教学
	* @return Double
	*/
	public Double getEducation_fund() {
		return this.education_fund;
	}
	/**
	* 设置 资金来源_自筹
	* @param value 
	*/
	public void setSelf_collect_fund(Double value) {
		this.self_collect_fund = value;
	}
	
	/**
	* 获取 资金来源_自筹
	* @return Double
	*/
	public Double getSelf_collect_fund() {
		return this.self_collect_fund;
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