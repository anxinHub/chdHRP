
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.ass.entity.apply;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 050201 资产购置申请明显表
 * @Table:
 * ASS_APPLY_DEPT_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AssApplyDeptDetail implements Serializable {

	
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
	 * 明细序号
	 */
	private Long detail_id;
	
	/**
	 * 购置ID
	 */
	private Long apply_id;
	
	/**
	 * 购置申请号
	 */
	private String apply_no;
	
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
	 * 生产厂家ID
	 */
	private Long fac_id;
	
	/**
	 * 生产厂家NO
	 */
	private Long fac_no;
	
	/**
	 * 申请数量
	 */
	private Integer apply_amount;
	
	/**
	 * 建议单价
	 */
	private Double advice_price;
	
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
	 * 用途名称
	 */
	private String equi_usage_name;
	
	/**
	 * 用途名称
	 */
	private String equi_usage_code;
	
	
	/**
	 * 详细用途
	 */
	private String usage_note;
	
	/**
	 * 申请原因
	 */
	private String apply_reason;
	
	/**
	 * 备注
	 */
	private String note;
	
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
	 * 导入验证信息
	 */
	private String error_type;
	/**
	 * 采购预算ID
	 */
	private String budg_purchase_id;
	/**
	 * 使用科室ID
	 */
	private Integer emp_dept_id;
	/**
	 * 使用科室NO
	 */
	private String emp_dept_no;
	/**
	 * 申购类别
	 */
	private String purchase_type;
	/**
	 * 预算数量
	 */
	private Integer budg_amount;
	/**
	 * 预计费用
	 */
	private Double budg_money;
	/**
	 * 预算单价
	 */
	private Double budg_price;
	/**
	 * 预计到货日期
	 */
	private Date budg_date;
	/**
	 * 功能要求
	 */
	private String features_req;
	/**
	 * 评估信息ID
	 */
	private Integer assess_id;
	/**
	 * 项目名称
	 */
	private String proj_name;
	/**
	 * 设备类组
	 */
	private String naturs_code;
	/**
	 * 设备类型ID
	 */
	private Integer ass_type_id;
	

	public String getBudg_purchase_id() {
		return budg_purchase_id;
	}

	public void setBudg_purchase_id(String budg_purchase_id) {
		this.budg_purchase_id = budg_purchase_id;
	}

	public String getEmp_dept_no() {
		return emp_dept_no;
	}

	public void setEmp_dept_no(String emp_dept_no) {
		this.emp_dept_no = emp_dept_no;
	}

	public String getProj_name() {
		return proj_name;
	}

	public void setProj_name(String proj_name) {
		this.proj_name = proj_name;
	}

	public String getNaturs_code() {
		return naturs_code;
	}

	public void setNaturs_code(String naturs_code) {
		this.naturs_code = naturs_code;
	}

	public Integer getAss_type_id() {
		return ass_type_id;
	}

	public void setAss_type_id(Integer ass_type_id) {
		this.ass_type_id = ass_type_id;
	}

	public Integer getEmp_dept_id() {
		return emp_dept_id;
	}

	public void setEmp_dept_id(Integer emp_dept_id) {
		this.emp_dept_id = emp_dept_id;
	}

	public String getPurchase_type() {
		return purchase_type;
	}

	public void setPurchase_type(String purchase_type) {
		this.purchase_type = purchase_type;
	}

	public Integer getBudg_amount() {
		return budg_amount;
	}

	public void setBudg_amount(Integer budg_amount) {
		this.budg_amount = budg_amount;
	}

	public Double getBudg_money() {
		return budg_money;
	}

	public void setBudg_money(Double budg_money) {
		this.budg_money = budg_money;
	}

	public Double getBudg_price() {
		return budg_price;
	}

	public void setBudg_price(Double budg_price) {
		this.budg_price = budg_price;
	}

	public Date getBudg_date() {
		return budg_date;
	}

	public void setBudg_date(Date budg_date) {
		this.budg_date = budg_date;
	}

	public String getFeatures_req() {
		return features_req;
	}

	public void setFeatures_req(String features_req) {
		this.features_req = features_req;
	}

	public Integer getAssess_id() {
		return assess_id;
	}

	public void setAssess_id(Integer assess_id) {
		this.assess_id = assess_id;
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
	* 设置 购置申请号
	* @param value 
	*/
	public void setApply_no(String value) {
		this.apply_no = value;
	}
	
	/**
	* 获取 购置申请号
	* @return String
	*/
	public String getApply_no() {
		return this.apply_no;
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
	* 设置 申请数量
	* @param value 
	*/
	public void setApply_amount(Integer value) {
		this.apply_amount = value;
	}
	
	/**
	* 获取 申请数量
	* @return Integer
	*/
	public Integer getApply_amount() {
		return this.apply_amount;
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
	* 设置 申请原因
	* @param value 
	*/
	public void setApply_reason(String value) {
		this.apply_reason = value;
	}
	
	/**
	* 获取 申请原因
	* @return String
	*/
	public String getApply_reason() {
		return this.apply_reason;
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

	public Long getApply_id() {
		return apply_id;
	}

	public void setApply_id(Long apply_id) {
		this.apply_id = apply_id;
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

	public String getEqui_usage_name() {
		return equi_usage_name;
	}

	public void setEqui_usage_name(String equi_usage_name) {
		this.equi_usage_name = equi_usage_name;
	}

	public String getEqui_usage_code() {
		return equi_usage_code;
	}

	public void setEqui_usage_code(String equi_usage_code) {
		this.equi_usage_code = equi_usage_code;
	}

	public String getAss_usage_name() {
		return ass_usage_name;
	}

	public void setAss_usage_name(String ass_usage_name) {
		this.ass_usage_name = ass_usage_name;
	}
}