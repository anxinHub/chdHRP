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
 * BUDG_ASSET_PUR
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgAssetPur implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 资产性质
	 */
	private String naturs_code;
	
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
	 * 计划年度
	 */
	private String budg_year;
	
	/**
	 * 月
	 */
	private String month;
	
	/**
	 * 计划ID
	 */
	private Long plan_id;
	
	private String plan_no;
	
	/**
	 * 科室
	 */
	private Long dept_id;
	private String dept_code;
	private String dept_name;
	
	/**
	 * 资产ID
	 */
	private Long ass_id;
	private String ass_code;
	private String ass_name;
	
	/**
	 * 资金来源
	 */
	private Long source_id;
	private String source_code;
	private String source_name;
	
	
	/**
	 * 折旧计算值
	 */
	private Double depr_count;
	
	/**
	 * 折旧预算
	 */
	private Double depr_budg;
	
	/**
	 * 折旧预算_财政
	 */
	private Double financial_deprec;
	
	/**
	 * 折旧预算_科研
	 */
	private Double reserch_deprec;
	
	/**
	 * 折旧预算_教学
	 */
	private Double education_deprec;
	
	/**
	 * 折旧预算_自筹
	 */
	private Double self_collect_deprec;
	

	/**
	 * 导入验证信息
	 */
	private String error_type;


	public String getNaturs_code() {
		return naturs_code;
	}


	public void setNaturs_code(String naturs_code) {
		this.naturs_code = naturs_code;
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


	public Long getPlan_id() {
		return plan_id;
	}


	public void setPlan_id(Long plan_id) {
		this.plan_id = plan_id;
	}


	public Long getDept_id() {
		return dept_id;
	}


	public void setDept_id(Long dept_id) {
		this.dept_id = dept_id;
	}


	public Long getAss_id() {
		return ass_id;
	}


	public void setAss_id(Long ass_id) {
		this.ass_id = ass_id;
	}


	public Long getSource_id() {
		return source_id;
	}


	public void setSource_id(Long source_id) {
		this.source_id = source_id;
	}


	public Double getDepr_count() {
		return depr_count;
	}


	public void setDepr_count(Double depr_count) {
		this.depr_count = depr_count;
	}


	public Double getDepr_budg() {
		return depr_budg;
	}


	public void setDepr_budg(Double depr_budg) {
		this.depr_budg = depr_budg;
	}


	public Double getFinancial_deprec() {
		return financial_deprec;
	}


	public void setFinancial_deprec(Double financial_deprec) {
		this.financial_deprec = financial_deprec;
	}


	public Double getReserch_deprec() {
		return reserch_deprec;
	}


	public void setReserch_deprec(Double reserch_deprec) {
		this.reserch_deprec = reserch_deprec;
	}


	public Double getEducation_deprec() {
		return education_deprec;
	}


	public void setEducation_deprec(Double education_deprec) {
		this.education_deprec = education_deprec;
	}


	public Double getSelf_collect_deprec() {
		return self_collect_deprec;
	}


	public void setSelf_collect_deprec(Double self_collect_deprec) {
		this.self_collect_deprec = self_collect_deprec;
	}


	public String getError_type() {
		return error_type;
	}


	public void setError_type(String error_type) {
		this.error_type = error_type;
	}


	public String getPlan_no() {
		return plan_no;
	}


	public void setPlan_no(String plan_no) {
		this.plan_no = plan_no;
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