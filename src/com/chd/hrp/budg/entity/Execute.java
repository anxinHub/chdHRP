package com.chd.hrp.budg.entity;

import java.io.Serializable;
/**
 * 资产采购预算执行
 * @author Administrator
 *
 */
public class Execute implements Serializable{

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
	 * 预算月
	 */
	private String month;
	
	
	/**
	 * 资金来源ID
	 */
	private Long source_id;
	
	/**
	 * 资产分类ID
	 */
	private Long asset_type_id;
	/**
	 * 资产分类编码
	 */
	private String asset_type_code;
	/**
	 * 资产分类名称
	 */
	private String ass_type_name;
	/**
	 * 资产分类名称
	 */
	private String asset_type_name;
	/**
	 * 采购金额
	 */
	private Double pur_amount;
	
	/**
	 * 预算月
	 */
	private String remark;
	
	/**
	 * 资产编码
	 * @return
	 */
	private String source_code;
	/**
	 * 资产名称
	 * @return
	 */
	private String source_name;
	
	
	public Double getPur_amount() {
		return pur_amount;
	}
	public void setPur_amount(Double pur_amount) {
		this.pur_amount = pur_amount;
	}
	public Long getGroup_id() {
		return group_id;
	}
	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}
	public Long getHos_id() {
		return hos_id;
	}
	public void setHos_id(Long hos_id) {
		this.hos_id = hos_id;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	public String getBudg_year() {
		return budg_year;
	}
	public void setBudg_year(String budg_year) {
		this.budg_year = budg_year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public Long getSource_id() {
		return source_id;
	}
	public void setSource_id(Long source_id) {
		this.source_id = source_id;
	}
	
	public Long getAsset_type_id() {
		return asset_type_id;
	}
	public void setAsset_type_id(Long asset_type_id) {
		this.asset_type_id = asset_type_id;
	}
	public String getAsset_type_code() {
		return asset_type_code;
	}
	public void setAsset_type_code(String asset_type_code) {
		this.asset_type_code = asset_type_code;
	}
	public String getAsset_type_name() {
		return asset_type_name;
	}
	public void setAsset_type_name(String asset_type_name) {
		this.asset_type_name = asset_type_name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSource_code() {
		return source_code;
	}
	public void setSource_code(String source_code) {
		this.source_code = source_code;
	}
	public String getSource_name() {
		return source_name;
	}
	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}
	
	
	
}
