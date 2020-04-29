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
 * 科室资产折旧预算
 * @Table:
 * BUDG_ASSET
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgAsset implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 资产性质
	 */
	private String asset_nature;
	
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
	 * 月
	 */
	private String month;
	
	/**
	 * 部门ID
	 */
	private Long dept_id;
	
	/**
	 * 部门编码
	 */
	private String dept_code;
	
	/**
	 * 部门名称
	 */
	private String dept_name;
	
	/**
	 * 资产类别ID
	 */
	private Long ass_type_id;
	
	/**
	 * 资产类别编码
	 */
	private String ass_type_code;
	
	/**
	 * 资产类别名称
	 */
	private String ass_type_name;
	
	/**
	 * 资金来源ID
	 */
	private Long source_id;
	
	/**
	 * 资金来源编码
	 */
	private String source_code;
	
	/**
	 * 资金来源名称
	 */
	private String source_name;
	
	/**
	 * 资金性质编码
	 */
	private String fund_nature;
	
	/**
	 * 现有资产折旧预算
	 */
	private Double now_budg;
	
	/**
	 * 预购资产折旧预算
	 */
	private Double pur_budg;
	
	/**
	 * 合计
	 */
	private Double sum_budg;
	

	/**
	 * 导入验证信息
	 */
	private String error_type;


	public String getAsset_nature() {
		return asset_nature;
	}


	public void setAsset_nature(String asset_nature) {
		this.asset_nature = asset_nature;
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


	public Long getDept_id() {
		return dept_id;
	}


	public void setDept_id(Long dept_id) {
		this.dept_id = dept_id;
	}


	public Long getAss_type_id() {
		return ass_type_id;
	}


	public void setAss_type_id(Long ass_type_id) {
		this.ass_type_id = ass_type_id;
	}


	public String getAss_type_code() {
		return ass_type_code;
	}


	public void setAss_type_code(String ass_type_code) {
		this.ass_type_code = ass_type_code;
	}


	public Long getSource_id() {
		return source_id;
	}


	public void setSource_id(Long source_id) {
		this.source_id = source_id;
	}


	public String getFund_nature() {
		return fund_nature;
	}


	public void setFund_nature(String fund_nature) {
		this.fund_nature = fund_nature;
	}


	public Double getNow_budg() {
		return now_budg;
	}


	public void setNow_budg(Double now_budg) {
		this.now_budg = now_budg;
	}


	public Double getPur_budg() {
		return pur_budg;
	}


	public void setPur_budg(Double pur_budg) {
		this.pur_budg = pur_budg;
	}


	public Double getSum_budg() {
		return sum_budg;
	}


	public void setSum_budg(Double sum_budg) {
		this.sum_budg = sum_budg;
	}

	public String getDept_code() {
		return dept_code;
	}


	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}


	public String getDept_name() {
		return dept_name;
	}


	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}


	public String getAss_type_name() {
		return ass_type_name;
	}


	public void setAss_type_name(String ass_type_name) {
		this.ass_type_name = ass_type_name;
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


	public String getError_type() {
		return error_type;
	}


	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	
}