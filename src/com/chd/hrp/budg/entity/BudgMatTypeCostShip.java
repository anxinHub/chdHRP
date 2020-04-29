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
 * 物资分类与预算支出科目对应关系
 * @Table:
 * BUDG_MAT_TYPE_COST_SHIP
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgMatTypeCostShip implements Serializable {

	
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
	 * 物资类别ID
	 */
	private Long mat_type_id;
	private String mat_type_code ;
	private String mat_type_name;
	
	/**
	 * 支出预算科目
	 */
	private String cost_subj_code;
	private String cost_subj_name;
	
	/**
	 * 收入预算科目
	 */
	private String income_subj_code;
	private String income_subj_name;
	
	private Integer no_medical ;
	
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

	public Long getMat_type_id() {
		return mat_type_id;
	}

	public void setMat_type_id(Long mat_type_id) {
		this.mat_type_id = mat_type_id;
	}

	public String getMat_type_code() {
		return mat_type_code;
	}

	public void setMat_type_code(String mat_type_code) {
		this.mat_type_code = mat_type_code;
	}

	public String getMat_type_name() {
		return mat_type_name;
	}

	public void setMat_type_name(String mat_type_name) {
		this.mat_type_name = mat_type_name;
	}

	public String getCost_subj_code() {
		return cost_subj_code;
	}

	public void setCost_subj_code(String cost_subj_code) {
		this.cost_subj_code = cost_subj_code;
	}

	public String getCost_subj_name() {
		return cost_subj_name;
	}

	public void setCost_subj_name(String cost_subj_name) {
		this.cost_subj_name = cost_subj_name;
	}

	public String getIncome_subj_code() {
		return income_subj_code;
	}

	public void setIncome_subj_code(String income_subj_code) {
		this.income_subj_code = income_subj_code;
	}

	public String getIncome_subj_name() {
		return income_subj_name;
	}

	public void setIncome_subj_name(String income_subj_name) {
		this.income_subj_name = income_subj_name;
	}
	
	public Integer getNo_medical() {
		return no_medical;
	}

	public void setNo_medical(Integer no_medical) {
		this.no_medical = no_medical;
	}

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	
}