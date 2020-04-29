/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.contract;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 050501 资产合同明细
 * @Table:
 * ASS_CONTRACT_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AssContractDetail implements Serializable {

	
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
	 * 合同ID
	 */
	private Long contract_id;
	
	/**
	 * 合同明细号
	 */
	private Integer contract_detail_id;
	
	/**
	 * 合同号
	 */
	private String contract_no;
	
	/**
	 * 资产ID
	 */
	private Long ass_id;
	
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
	 * 生产厂家ID
	 */
	private Long fac_id;
	
	/**
	 * 生产厂家NO
	 */
	private Long fac_no;
	
	/**
	 * 合同数量
	 */
	private Integer contract_amount;
	
	/**
	 * 合同单价
	 */
	private Double contract_price;
	
	/**
	 * 交货日期
	 */
	private Date send_date;
	
	/**
	 * 保修期
	 */
	private Double keep_repair_times;
	
	/**
	 * 保修期单位
	 */
	private Integer times_unit;
	
	/**
	 * 是否安装
	 */
	private Integer is_fix;
	
	/**
	 * 是否验收
	 */
	private Integer is_accept;
	
	/**
	 * 是否招标
	 */
	private Integer is_bid;
	
	/**
	 * 备注
	 */
	private String note;
	private String ass_code;
	private String ass_name;
	private String fac_name;
	private String fac_code;
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
	* 设置 合同ID
	* @param value 
	*/
	public void setContract_id(Long value) {
		this.contract_id = value;
	}
	
	/**
	* 获取 合同ID
	* @return Long
	*/
	public Long getContract_id() {
		return this.contract_id;
	}
	/**
	* 设置 合同明细号
	* @param value 
	*/
	public void setContract_detail_id(Integer value) {
		this.contract_detail_id = value;
	}
	
	/**
	* 获取 合同明细号
	* @return Integer
	*/
	public Integer getContract_detail_id() {
		return this.contract_detail_id;
	}
	/**
	* 设置 合同号
	* @param value 
	*/
	public void setContract_no(String value) {
		this.contract_no = value;
	}


	/**
	* 获取 合同号
	* @return String
	*/
	public String getContract_no() {
		return this.contract_no;
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
	* 设置 合同数量
	* @param value 
	*/
	public void setContract_amount(Integer value) {
		this.contract_amount = value;
	}
	
	/**
	* 获取 合同数量
	* @return Integer
	*/
	public Integer getContract_amount() {
		return this.contract_amount;
	}
	/**
	* 设置 合同单价
	* @param value 
	*/
	public void setContract_price(Double value) {
		this.contract_price = value;
	}
	
	/**
	* 获取 合同单价
	* @return Double
	*/
	public Double getContract_price() {
		return this.contract_price;
	}
	/**
	* 设置 交货日期
	* @param value 
	*/
	public void setSend_date(Date value) {
		this.send_date = value;
	}
	
	/**
	* 获取 交货日期
	* @return Date
	*/
	public Date getSend_date() {
		return this.send_date;
	}
	/**
	* 设置 保修期
	* @param value 
	*/
	public void setKeep_repair_times(Double value) {
		this.keep_repair_times = value;
	}
	
	/**
	* 获取 保修期
	* @return Double
	*/
	public Double getKeep_repair_times() {
		return this.keep_repair_times;
	}
	/**
	* 设置 保修期单位
	* @param value 
	*/
	public void setTimes_unit(Integer value) {
		this.times_unit = value;
	}
	
	/**
	* 获取 保修期单位
	* @return Integer
	*/
	public Integer getTimes_unit() {
		return this.times_unit;
	}
	/**
	* 设置 是否安装
	* @param value 
	*/
	public void setIs_fix(Integer value) {
		this.is_fix = value;
	}
	
	/**
	* 获取 是否安装
	* @return Integer
	*/
	public Integer getIs_fix() {
		return this.is_fix;
	}
	/**
	* 设置 是否验收
	* @param value 
	*/
	public void setIs_accept(Integer value) {
		this.is_accept = value;
	}
	
	/**
	* 获取 是否验收
	* @return Integer
	*/
	public Integer getIs_accept() {
		return this.is_accept;
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

	public String getFac_name() {
		return fac_name;
	}

	public void setFac_name(String fac_name) {
		this.fac_name = fac_name;
	}

	public String getFac_code() {
		return fac_code;
	}

	public void setFac_code(String fac_code) {
		this.fac_code = fac_code;
	}
}