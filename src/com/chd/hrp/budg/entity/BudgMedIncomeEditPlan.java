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
 * BUDG_MED_INCOME_EDIT_PLAN
 * @Table:
 * BUDG_MED_INCOME_EDIT_PLAN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgMedIncomeEditPlan implements Serializable {

	
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
	 * 科目编码
	 */
	private String subj_code;
	private String subj_name;
	private String subj_name_all;
	private Integer subj_level;
	/**
	 * 是否独立核算
	 */
	private Integer is_single_count;
	
	/**
	 * 编制方法
	 */
	private String edit_method;
	private String edit_method_name;
	
	/**
	 * 取值方法
	 */
	private String get_way;
	private String get_way_name;
	
	/**
	 * 计算公式ID
	 */
	private String formula_id;
	
	private String formula_name;
	
	/**
	 * 取值函数ID
	 */
	private String fun_id;
	
	private String fun_name;
	
	/**
	 * 分解或汇总
	 */
	private String resolve_or_sum;
	private String resolve_or_sum_name;
	
	/**
	 * 分解方法
	 */
	private String resolve_way;
	private String resolve_way_name;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
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
	* 设置 预算年度
	* @param value 
	*/
	public void setBudg_year(String value) {
		this.budg_year = value;
	}
	
	/**
	* 获取 预算年度
	* @return String
	*/
	public String getBudg_year() {
		return this.budg_year;
	}
	/**
	* 设置 科目编码
	* @param value 
	*/
	public void setSubj_code(String value) {
		this.subj_code = value;
	}
	
	/**
	* 获取 科目编码
	* @return String
	*/
	public String getSubj_code() {
		return this.subj_code;
	}
	/**
	* 设置 是否独立核算
	* @param value 
	*/
	public void setIs_single_count(Integer value) {
		this.is_single_count = value;
	}
	
	/**
	* 获取 是否独立核算
	* @return Integer
	*/
	public Integer getIs_single_count() {
		return this.is_single_count;
	}
	/**
	* 设置 编制方法
	* @param value 
	*/
	public void setEdit_method(String value) {
		this.edit_method = value;
	}
	
	/**
	* 获取 编制方法
	* @return String
	*/
	public String getEdit_method() {
		return this.edit_method;
	}
	/**
	* 设置 取值方法
	* @param value 
	*/
	public void setGet_way(String value) {
		this.get_way = value;
	}
	
	/**
	* 获取 取值方法
	* @return String
	*/
	public String getGet_way() {
		return this.get_way;
	}
	/**
	* 设置 计算公式ID
	* @param value 
	*/
	public void setFormula_id(String value) {
		this.formula_id = value;
	}
	
	/**
	* 获取 计算公式ID
	* @return String
	*/
	public String getFormula_id() {
		return this.formula_id;
	}
	
	public String getFormula_name() {
		return formula_name;
	}

	public void setFormula_name(String formula_name) {
		this.formula_name = formula_name;
	}

	/**
	* 设置 取值函数ID
	* @param value 
	*/
	public void setFun_id(String value) {
		this.fun_id = value;
	}
	
	/**
	* 获取 取值函数ID
	* @return String
	*/
	public String getFun_id() {
		return this.fun_id;
	}
	
	public String getFun_name() {
		return fun_name;
	}

	public void setFun_name(String fun_name) {
		this.fun_name = fun_name;
	}

	/**
	* 设置 分解或汇总
	* @param value 
	*/
	public void setResolve_or_sum(String value) {
		this.resolve_or_sum = value;
	}
	
	/**
	* 获取 分解或汇总
	* @return String
	*/
	public String getResolve_or_sum() {
		return this.resolve_or_sum;
	}
	/**
	* 设置 分解方法
	* @param value 
	*/
	public void setResolve_way(String value) {
		this.resolve_way = value;
	}
	
	/**
	* 获取 分解方法
	* @return String
	*/
	public String getResolve_way() {
		return this.resolve_way;
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
	 * @return the subj_name
	 */
	public String getSubj_name() {
		return subj_name;
	}

	/**
	 * @param subj_name the subj_name to set
	 */
	public void setSubj_name(String subj_name) {
		this.subj_name = subj_name;
	}

	/**
	 * @return the subj_name_all
	 */
	public String getSubj_name_all() {
		return subj_name_all;
	}

	/**
	 * @param subj_name_all the subj_name_all to set
	 */
	public void setSubj_name_all(String subj_name_all) {
		this.subj_name_all = subj_name_all;
	}

	/**
	 * @return the subj_level
	 */
	public Integer getSubj_level() {
		return subj_level;
	}

	/**
	 * @param subj_level the subj_level to set
	 */
	public void setSubj_level(Integer subj_level) {
		this.subj_level = subj_level;
	}

	/**
	 * @return the edit_method_name
	 */
	public String getEdit_method_name() {
		return edit_method_name;
	}

	/**
	 * @param edit_method_name the edit_method_name to set
	 */
	public void setEdit_method_name(String edit_method_name) {
		this.edit_method_name = edit_method_name;
	}

	/**
	 * @return the get_way_name
	 */
	public String getGet_way_name() {
		return get_way_name;
	}

	/**
	 * @param get_way_name the get_way_name to set
	 */
	public void setGet_way_name(String get_way_name) {
		this.get_way_name = get_way_name;
	}

	/**
	 * @return the resolve_or_sum_name
	 */
	public String getResolve_or_sum_name() {
		return resolve_or_sum_name;
	}

	/**
	 * @param resolve_or_sum_name the resolve_or_sum_name to set
	 */
	public void setResolve_or_sum_name(String resolve_or_sum_name) {
		this.resolve_or_sum_name = resolve_or_sum_name;
	}

	/**
	 * @return the resolve_way_name
	 */
	public String getResolve_way_name() {
		return resolve_way_name;
	}

	/**
	 * @param resolve_way_name the resolve_way_name to set
	 */
	public void setResolve_way_name(String resolve_way_name) {
		this.resolve_way_name = resolve_way_name;
	}

	
	
}