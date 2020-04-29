/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.accept;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 050601 资产验收明细
 * @Table:
 * ASS_ACCEPT_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AssAcceptDetail implements Serializable {

	
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
	 * 安装验收单号
	 */
	private Long accept_id;
	
	/**
	 * 安装验收明细ID
	 */
	private Long accept_detail_id;
	
	/**
	 * 合同明细号
	 */
	private Long contract_detail_id;
	
	/**
	 * 资产ID
	 */
	private Long ass_id;
	
	/**
	 * 资产变更NO
	 */
	private Long ass_no;
	
	private String ass_name;
	
	/**
	 * 型号
	 */
	private String ass_model;
	
	/**
	 * 规格
	 */
	private String ass_spec;
	
	/**
	 * 品牌
	 */
	private String ass_brand;
	
	/**
	 * 生产厂商ID
	 */
	private String fac_id;
	
	/**
	 * 生产厂家变更ID
	 */
	private String fac_no;
	
	private String fac_code;
	
	private String fac_name;
	/**
	 * 验收数量
	 */
	private Integer accept_amount;
	
	private Long accept_price;
	
	private Long accept_money;
	
	/**
	 * 验收结果
	 */
	private Integer is_well;
	
	/**
	 * 验收说明
	 */
	private String accept_desc;
	
	private String depre_years;
	private String ass_depre_code;
	private String ass_depre_name;
	private Long remain_value;
	private Date warranty_date;
	private Date depre_begin_date;
	private String unit_code;
	private String unit_name;
	
  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	
	public Long getAccept_price() {
	return accept_price;
	}
	
	public void setAccept_price(Long accept_price) {
		this.accept_price = accept_price;
	}
	
	public Long getAccept_money() {
		return accept_money;
	}
	
	public void setAccept_money(Long accept_money) {
		this.accept_money = accept_money;
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
	* 设置 安装验收单号
	* @param value 
	*/
	public void setAccept_id(Long value) {
		this.accept_id = value;
	}
	
	/**
	* 获取 安装验收单号
	* @return Long
	*/
	public Long getAccept_id() {
		return this.accept_id;
	}
	/**
	* 设置 安装验收明细ID
	* @param value 
	*/
	public void setAccept_detail_id(Long value) {
		this.accept_detail_id = value;
	}
	
	/**
	* 获取 安装验收明细ID
	* @return Long
	*/
	public Long getAccept_detail_id() {
		return this.accept_detail_id;
	}
	/**
	* 设置 合同明细号
	* @param value 
	*/
	public void setContract_detail_id(Long value) {
		this.contract_detail_id = value;
	}
	
	/**
	* 获取 合同明细号
	* @return Long
	*/
	public Long getContract_detail_id() {
		return this.contract_detail_id;
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
	* 设置 生产厂商ID
	* @param value 
	*/
	public void setFac_id(String value) {
		this.fac_id = value;
	}
	
	/**
	* 获取 生产厂商ID
	* @return String
	*/
	public String getFac_id() {
		return this.fac_id;
	}
	/**
	* 设置 生产厂家变更ID
	* @param value 
	*/
	public void setFac_no(String value) {
		this.fac_no = value;
	}
	
	/**
	* 获取 生产厂家变更ID
	* @return String
	*/
	public String getFac_no() {
		return this.fac_no;
	}
	/**
	* 设置 验收数量
	* @param value 
	*/
	public void setAccept_amount(Integer value) {
		this.accept_amount = value;
	}
	
	/**
	* 获取 验收数量
	* @return Integer
	*/
	public Integer getAccept_amount() {
		return this.accept_amount;
	}
	/**
	* 设置 验收结果
	* @param value 
	*/
	public void setIs_well(Integer value) {
		this.is_well = value;
	}
	
	/**
	* 获取 验收结果
	* @return Integer
	*/
	public Integer getIs_well() {
		return this.is_well;
	}
	/**
	* 设置 验收说明
	* @param value 
	*/
	public void setAccept_desc(String value) {
		this.accept_desc = value;
	}
	
	/**
	* 获取 验收说明
	* @return String
	*/
	public String getAccept_desc() {
		return this.accept_desc;
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

	public String getDepre_years() {
		return depre_years;
	}

	public void setDepre_years(String depre_years) {
		this.depre_years = depre_years;
	}

	public String getAss_depre_code() {
		return ass_depre_code;
	}

	public void setAss_depre_code(String ass_depre_code) {
		this.ass_depre_code = ass_depre_code;
	}

	public String getAss_depre_name() {
		return ass_depre_name;
	}

	public void setAss_depre_name(String ass_depre_name) {
		this.ass_depre_name = ass_depre_name;
	}

	public Long getRemain_value() {
		return remain_value;
	}

	public void setRemain_value(Long remain_value) {
		this.remain_value = remain_value;
	}

	public Date getWarranty_date() {
		return warranty_date;
	}

	public void setWarranty_date(Date warranty_date) {
		this.warranty_date = warranty_date;
	}

	public Date getDepre_begin_date() {
		return depre_begin_date;
	}

	public void setDepre_begin_date(Date depre_begin_date) {
		this.depre_begin_date = depre_begin_date;
	}

	public String getUnit_code() {
		return unit_code;
	}

	public void setUnit_code(String unit_code) {
		this.unit_code = unit_code;
	}

	public String getUnit_name() {
		return unit_name;
	}

	public void setUnit_name(String unit_name) {
		this.unit_name = unit_name;
	}

	
}