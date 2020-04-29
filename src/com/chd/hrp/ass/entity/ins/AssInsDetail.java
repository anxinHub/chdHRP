/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.ins;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 050601 资产安装明细
 * @Table:
 * ASS_INS_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AssInsDetail implements Serializable {

	
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
	 * 安装ID
	 */
	private Long ins_id;
	
	/**
	 * 安装明细ID
	 */
	private Long ins_detail_id;
	
	/**
	 * 安装单号
	 */
	private String ins_no;
	
	/*
	 * 资产编码
	 */
	private String ass_code;
	
	/*
	 * 资产名称
	 */
	
	private String ass_name;
	
	/*
	 * 生产厂商名称
	 */
	private String fac_name;
	/**
	 * 资产ID
	 */
	private Long ass_id;
	
	/**
	 * 资产变更NO
	 */
	private Long ass_no;
	
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
	/**
	 * 单价
	 */
	private Double ins_price;
	
	/**
	 * 安装数量
	 */
	private Integer ins_amount;
	
	/**
	 * 安装费用
	 */
	private Double ins_money;
	
	/**
	 * 安装单位
	 */
	private String ins_company;
	
	/**
	 * 联系电话
	 */
	private String ins_tele;
	
	/**
	 * 主要安装工程师
	 */
	private String ins_engr;
	
	/**
	 * 安装说明
	 */
	private String ins_desc;
	

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
	* 设置 安装ID
	* @param value 
	*/
	public void setIns_id(Long value) {
		this.ins_id = value;
	}
	
	/**
	* 获取 安装ID
	* @return Long
	*/
	public Long getIns_id() {
		return this.ins_id;
	}
	/**
	* 设置 安装明细ID
	* @param value 
	*/
	public void setIns_detail_id(Long value) {
		this.ins_detail_id = value;
	}
	
	/**
	* 获取 安装明细ID
	* @return Long
	*/
	public Long getIns_detail_id() {
		return this.ins_detail_id;
	}
	/**
	* 设置 安装单号
	* @param value 
	*/
	public void setIns_no(String value) {
		this.ins_no = value;
	}
	
	/**
	* 获取 安装单号
	* @return String
	*/
	public String getIns_no() {
		return this.ins_no;
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
	* 设置 安装数量
	* @param value 
	*/
	public void setIns_amount(Integer value) {
		this.ins_amount = value;
	}
	
	/**
	* 获取 安装数量
	* @return Integer
	*/
	public Integer getIns_amount() {
		return this.ins_amount;
	}
	/**
	* 设置 安装费用
	* @param value 
	*/
	public void setIns_money(Double value) {
		this.ins_money = value;
	}
	
	/**
	* 获取 安装费用
	* @return Double
	*/
	public Double getIns_money() {
		return this.ins_money;
	}
	/**
	* 设置 安装单位
	* @param value 
	*/
	public void setIns_company(String value) {
		this.ins_company = value;
	}
	
	/**
	* 获取 安装单位
	* @return String
	*/
	public String getIns_company() {
		return this.ins_company;
	}
	/**
	* 设置 联系电话
	* @param value 
	*/
	public void setIns_tele(String value) {
		this.ins_tele = value;
	}
	
	/**
	* 获取 联系电话
	* @return String
	*/
	public String getIns_tele() {
		return this.ins_tele;
	}
	/**
	* 设置 主要安装工程师
	* @param value 
	*/
	public void setIns_engr(String value) {
		this.ins_engr = value;
	}
	
	/**
	* 获取 主要安装工程师
	* @return String
	*/
	public String getIns_engr() {
		return this.ins_engr;
	}
	
	/**
	* 设置 安装说明
	* @param value 
	*/
	public void setIns_desc(String value) {
		this.ins_desc = value;
	}
	
	/**
	* 获取 安装说明
	* @return String
	*/
	public String getIns_desc() {
		return this.ins_desc;
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

	public Double getIns_price() {
		return ins_price;
	}

	public void setIns_price(Double ins_price) {
		this.ins_price = ins_price;
	}
	
}