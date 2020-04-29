/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.entity;

import java.io.Serializable;
/**
 * 
 * @Description:
 * 药品分类与预算支出科目对应关系
 * @Table:
 * BUDG_DRUG_TYPE_COST_SHIP
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgDrugTypeCostShip implements Serializable {

	
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
	 * 药品分类ID
	 */
	private Long med_type_id;
	
	/**
	 * 药品分类变更ID
	 */
	private Long med_type_no;
	
	/**
	 * 预算支出科目编码
	 */
	private String cost_subj_code;
	
	/**
	 * 预算收入科目编码
	 */
	
	private String income_subj_code;
	

	/**
	 * 导入验证信息
	 */
	private String error_type;


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


	public Long getMed_type_id() {
		return med_type_id;
	}


	public void setMed_type_id(Long med_type_id) {
		this.med_type_id = med_type_id;
	}

	public Long getMed_type_no() {
		return med_type_no;
	}


	public void setMed_type_no(Long med_type_no) {
		this.med_type_no = med_type_no;
	}


	public String getCost_subj_code() {
		return cost_subj_code;
	}


	public void setCost_subj_code(String cost_subj_code) {
		this.cost_subj_code = cost_subj_code;
	}


	public String getIncome_subj_code() {
		return income_subj_code;
	}


	public void setIncome_subj_code(String income_subj_code) {
		this.income_subj_code = income_subj_code;
	}


	public String getError_type() {
		return error_type;
	}


	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	
	
}