/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.plan;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 050301 资产购置计划明细表
 * @Table:
 * ASS_PLAN_DEPT_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AssPlanDeptDetail implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 集体ID
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
	 * 计划ID
	 */
	private Long plan_id;
	
	/**
	 * 明细序号
	 */
	private Long plan_detail_id;
	
	/**
	 * 购置计划号
	 */
	private String plan_no;
	
	/**
	 * 资产ID
	 */
	private Long ass_id;
	
	private String ass_code;
	private String ass_name;
	/**
	 * 资产变更NO
	 */
	private Long ass_no;
	
	/**
	 * 规格
	 */
	private String ass_spec;
	
	/**
	 * 型号
	 */
	private String ass_model;
	
	/**
	 * 品牌
	 */
	private String ass_brand;
	
	/**
	 * 建议单价
	 */
	private Double advice_price;
	
	/**
	 * 生产厂家ID
	 */
	private Long fac_id;
	private String fac_name;
	private String fac_code;
	/**
	 * 生产厂家NO
	 */
	private Long fac_no;
	
	/**
	 * 计划数量
	 */
	private Integer plan_amount;
	
	/**
	 * 希望到货日期
	 */
	private Date need_date;
	
	/**
	 * 用途编码
	 */
	private String ass_usage_code;
	private String ass_usage_name;
	/**
	 * 详细用途
	 */
	private String usage_note;
	
	/**
	 * 备注
	 */
	private String note;
	
	/**
	 * 是否招标
	 */
	private Integer is_bid;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 集体ID
	* @param value 
	*/
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	
	/**
	* 获取 集体ID
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
	* 设置 计划ID
	* @param value 
	*/
	public void setPlan_id(Long value) {
		this.plan_id = value;
	}
	
	/**
	* 获取 计划ID
	* @return Long
	*/
	public Long getPlan_id() {
		return this.plan_id;
	}
	/**
	* 设置 明细序号
	* @param value 
	*/
	public void setPlan_detail_id(Long value) {
		this.plan_detail_id = value;
	}
	
	/**
	* 获取 明细序号
	* @return Long
	*/
	public Long getPlan_detail_id() {
		return this.plan_detail_id;
	}
	/**
	* 设置 购置计划号
	* @param value 
	*/
	public void setPlan_no(String value) {
		this.plan_no = value;
	}
	
	/**
	* 获取 购置计划号
	* @return String
	*/
	public String getPlan_no() {
		return this.plan_no;
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
	* 设置 建议单价
	* @param value 
	*/
	public void setAdvice_price(Double value) {
		this.advice_price = value;
	}
	
	/**
	* 获取 建议单价
	* @return Double
	*/
	public Double getAdvice_price() {
		return this.advice_price;
	}
	/**
	* 设置 生产厂家ID
	* @param value 
	*/
	public void setFac_id(Long value) {
		this.fac_id = value;
	}
	
	/**
	* 获取 生产厂家ID
	* @return Long
	*/
	public Long getFac_id() {
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
	* 设置 计划数量
	* @param value 
	*/
	public void setPlan_amount(Integer value) {
		this.plan_amount = value;
	}
	
	/**
	* 获取 计划数量
	* @return Integer
	*/
	public Integer getPlan_amount() {
		return this.plan_amount;
	}
	/**
	* 设置 希望到货日期
	* @param value 
	*/
	public void setNeed_date(Date value) {
		this.need_date = value;
	}
	
	/**
	* 获取 希望到货日期
	* @return Date
	*/
	public Date getNeed_date() {
		return this.need_date;
	}
	/**
	* 设置 用途编码
	* @param value 
	*/
	public void setAss_usage_code(String value) {
		this.ass_usage_code = value;
	}
	
	/**
	* 获取 用途编码
	* @return String
	*/
	public String getAss_usage_code() {
		return this.ass_usage_code;
	}
	/**
	* 设置 详细用途
	* @param value 
	*/
	public void setUsage_note(String value) {
		this.usage_note = value;
	}
	
	/**
	* 获取 详细用途
	* @return String
	*/
	public String getUsage_note() {
		return this.usage_note;
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
	* 设置 是否招标
	* @param value 
	*/
	public void setIs_bid(Integer value) {
		this.is_bid = value;
	}
	
	/**
	* 获取 是否招标
	* @return Integer
	*/
	public Integer getIs_bid() {
		return this.is_bid;
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

	/**
	 * @return the ass_code
	 */
	public String getAss_code() {
		return ass_code;
	}

	/**
	 * @param ass_code the ass_code to set
	 */
	public void setAss_code(String ass_code) {
		this.ass_code = ass_code;
	}

	/**
	 * @return the ass_name
	 */
	public String getAss_name() {
		return ass_name;
	}

	/**
	 * @param ass_name the ass_name to set
	 */
	public void setAss_name(String ass_name) {
		this.ass_name = ass_name;
	}

	/**
	 * @return the fac_name
	 */
	public String getFac_name() {
		return fac_name;
	}

	/**
	 * @param fac_name the fac_name to set
	 */
	public void setFac_name(String fac_name) {
		this.fac_name = fac_name;
	}

	/**
	 * @return the fac_code
	 */
	public String getFac_code() {
		return fac_code;
	}

	/**
	 * @param fac_code the fac_code to set
	 */
	public void setFac_code(String fac_code) {
		this.fac_code = fac_code;
	}

	/**
	 * @return the ass_usage_name
	 */
	public String getAss_usage_name() {
		return ass_usage_name;
	}

	/**
	 * @param ass_usage_name the ass_usage_name to set
	 */
	public void setAss_usage_name(String ass_usage_name) {
		this.ass_usage_name = ass_usage_name;
	}
	
}