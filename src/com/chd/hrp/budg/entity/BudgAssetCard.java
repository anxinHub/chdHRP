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
 * BUDG_ASSET_CARD
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgAssetCard implements Serializable {

	
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
	 * 资产卡片号
	 */
	private String asset_card_code;
	
	/**
	 * 资产编码
	 */
	private String asset_code;
	
	/**
	 * 使用科室
	 */
	private Long use_dept_id;
	
	/**
	 * 原值
	 */
	private Double amount;
	
	/**
	 * 累计折旧
	 */
	private Double acc_deprec;
	
	/**
	 * 购置日期
	 */
	private Date date;
	
	/**
	 * 折旧年限
	 */
	private Long depre_year;
	
	/**
	 * 上次折旧日期
	 */
	private Date last_dep_date;
	
	/**
	 * 备注
	 */
	private String remark;
	
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
	* 设置 资产卡片号
	* @param value 
	*/
	public void setAsset_card_code(String value) {
		this.asset_card_code = value;
	}
	
	/**
	* 获取 资产卡片号
	* @return String
	*/
	public String getAsset_card_code() {
		return this.asset_card_code;
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
	* 设置 使用科室
	* @param value 
	*/
	public void setUse_dept_id(Long value) {
		this.use_dept_id = value;
	}
	
	/**
	* 获取 使用科室
	* @return Long
	*/
	public Long getUse_dept_id() {
		return this.use_dept_id;
	}
	/**
	* 设置 原值
	* @param value 
	*/
	public void setAmount(Double value) {
		this.amount = value;
	}
	
	/**
	* 获取 原值
	* @return Double
	*/
	public Double getAmount() {
		return this.amount;
	}
	/**
	* 设置 累计折旧
	* @param value 
	*/
	public void setAcc_deprec(Double value) {
		this.acc_deprec = value;
	}
	
	/**
	* 获取 累计折旧
	* @return Double
	*/
	public Double getAcc_deprec() {
		return this.acc_deprec;
	}
	/**
	* 设置 购置日期
	* @param value 
	*/
	public void setDate(Date value) {
		this.date = value;
	}
	
	/**
	* 获取 购置日期
	* @return Date
	*/
	public Date getDate() {
		return this.date;
	}
	/**
	* 设置 折旧年限
	* @param value 
	*/
	public void setDepre_year(Long value) {
		this.depre_year = value;
	}
	
	/**
	* 获取 折旧年限
	* @return Long
	*/
	public Long getDepre_year() {
		return this.depre_year;
	}
	/**
	* 设置 上次折旧日期
	* @param value 
	*/
	public void setLast_dep_date(Date value) {
		this.last_dep_date = value;
	}
	
	/**
	* 获取 上次折旧日期
	* @return Date
	*/
	public Date getLast_dep_date() {
		return this.last_dep_date;
	}
	/**
	* 设置 备注
	* @param value 
	*/
	public void setRemark(String value) {
		this.remark = value;
	}
	
	/**
	* 获取 备注
	* @return String
	*/
	public String getRemark() {
		return this.remark;
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