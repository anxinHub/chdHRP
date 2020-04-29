/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.allot.in;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 050901 资产无偿调拨入库明细(专用设备)
 * @Table:
 * ASS_ALLOT_IN_DETAIL_SPECIAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class AssAllotInDetailSpecial implements Serializable {

	
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
	 * 调拨单号
	 */
	private String allot_in_no;
	
	/**
	 * 资产ID
	 */
	private String ass_id;
	
	/**
	 * 资产变更NO
	 */
	private Long ass_no;
	
	
	private String ass_code;
	
	private String ass_name;
	
	/**
	 * 原始卡片编号
	 */
	private String ass_ori_card_no;
	
	/**
	 * 品牌
	 */
	private String ass_brand;
	
	/**
	 * 规格
	 */
	private String ass_spec;
	
	/**
	 * 型号
	 */
	private String ass_model;
	
	/**
	 * 生产厂家ID
	 */
	private String fac_id;
	
	/**
	 * 生产厂家NO
	 */
	private Long fac_no;
	
	private String fac_code;
	
	private String fac_name;
	
	/**
	 * 资产原值
	 */
	private Double price;
	
	/**
	 * 累计折旧
	 */
	private Double add_depre;
	
	/**
	 * 累计折旧月份
	 */
	private Integer add_depre_month;
	
	/**
	 * 资产净值
	 */
	private Double cur_money;
	
	/**
	 * 预留残值
	 */
	private Double fore_money;
	
	/**
	 * 备注
	 */
	private String note;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	
	
	public String getAss_code() {
		return ass_code;
	}
	
	public void setAss_code(String ass_code) {
		this.ass_code = ass_code;
	}
	
	public String getAss_name() {
		return ass_name;
	}
	
	public void setAss_name(String ass_name) {
		this.ass_name = ass_name;
	}
	
	public String getFac_code() {
		return fac_code;
	}
	
	public void setFac_code(String fac_code) {
		this.fac_code = fac_code;
	}
	
	public String getFac_name() {
		return fac_name;
	}
	
	public void setFac_name(String fac_name) {
		this.fac_name = fac_name;
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
	* 设置 调拨单号
	* @param value 
	*/
	public void setAllot_in_no(String value) {
		this.allot_in_no = value;
	}
	
	/**
	* 获取 调拨单号
	* @return String
	*/
	public String getAllot_in_no() {
		return this.allot_in_no;
	}
	/**
	* 设置 资产ID
	* @param value 
	*/
	public void setAss_id(String value) {
		this.ass_id = value;
	}
	
	/**
	* 获取 资产ID
	* @return Long
	*/
	public String getAss_id() {
		return this.ass_id;
	}
	/**
	* 设置 资产变更NO
	* @param value 
	*/
	public void setAss_no(Long value) {
		this.ass_no = value;
	}
	
	/**
	* 获取 资产变更NO
	* @return Long
	*/
	public Long getAss_no() {
		return this.ass_no;
	}
	/**
	* 设置 原始卡片编号
	* @param value 
	*/
	public void setAss_ori_card_no(String value) {
		this.ass_ori_card_no = value;
	}
	
	/**
	* 获取 原始卡片编号
	* @return String
	*/
	public String getAss_ori_card_no() {
		return this.ass_ori_card_no;
	}
	/**
	* 设置 品牌
	* @param value 
	*/
	public void setAss_brand(String value) {
		this.ass_brand = value;
	}
	
	/**
	* 获取 品牌
	* @return String
	*/
	public String getAss_brand() {
		return this.ass_brand;
	}
	/**
	* 设置 规格
	* @param value 
	*/
	public void setAss_spec(String value) {
		this.ass_spec = value;
	}
	
	/**
	* 获取 规格
	* @return String
	*/
	public String getAss_spec() {
		return this.ass_spec;
	}
	/**
	* 设置 型号
	* @param value 
	*/
	public void setAss_model(String value) {
		this.ass_model = value;
	}
	
	/**
	* 获取 型号
	* @return String
	*/
	public String getAss_model() {
		return this.ass_model;
	}
	/**
	* 设置 生产厂家ID
	* @param value 
	*/
	public void setFac_id(String value) {
		this.fac_id = value;
	}
	
	/**
	* 获取 生产厂家ID
	* @return Long
	*/
	public String getFac_id() {
		return this.fac_id;
	}
	/**
	* 设置 生产厂家NO
	* @param value 
	*/
	public void setFac_no(Long value) {
		this.fac_no = value;
	}
	
	/**
	* 获取 生产厂家NO
	* @return Long
	*/
	public Long getFac_no() {
		return this.fac_no;
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
	public void setAdd_depre(Double value) {
		this.add_depre = value;
	}
	
	/**
	* 获取 累计折旧
	* @return Double
	*/
	public Double getAdd_depre() {
		return this.add_depre;
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