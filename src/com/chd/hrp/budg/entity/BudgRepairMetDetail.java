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
 * BUDG_REPAIR_MET_DETAIL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgRepairMetDetail implements Serializable {

	
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
	 * 预算年度
	 */
	private String budg_year;
	
	/**
	 * 申请单号
	 */
	private String apply_id;
	
	/**
	 * 明细序号
	 */
	private Long detail_id;
	
	/**
	 * 资产卡片号
	 */
	private String asset_card_code;
	
	/**
	 * 预算金额
	 */
	private Double budg_amount;
	
	/**
	 * 材料编码
	 */
	private String met_code;
	
	/**
	 * 材料名称
	 */
	private String met_name;
	
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
	* 设置 预算年度
	* @param value 
	*/
	public void setBudg_year(String value) {
		this.budg_year = value;
	}
	
	/**
	* 获取 预算年度
	* @return String
	*/
	public String getBudg_year() {
		return this.budg_year;
	}
	/**
	* 设置 申请单号
	* @param value 
	*/
	public void setApply_id(String value) {
		this.apply_id = value;
	}
	
	/**
	* 获取 申请单号
	* @return String
	*/
	public String getApply_id() {
		return this.apply_id;
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
	* 设置 预算金额
	* @param value 
	*/
	public void setBudg_amount(Double value) {
		this.budg_amount = value;
	}
	
	/**
	* 获取 预算金额
	* @return Double
	*/
	public Double getBudg_amount() {
		return this.budg_amount;
	}
	/**
	* 设置 材料编码
	* @param value 
	*/
	public void setMet_code(String value) {
		this.met_code = value;
	}
	
	/**
	* 获取 材料编码
	* @return String
	*/
	public String getMet_code() {
		return this.met_code;
	}
	/**
	* 设置 材料名称
	* @param value 
	*/
	public void setMet_name(String value) {
		this.met_name = value;
	}
	
	/**
	* 获取 材料名称
	* @return String
	*/
	public String getMet_name() {
		return this.met_name;
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