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
 * 科室年度至科室月份医疗收入预算分解方案
 * @Table:
 * BUDG_MED_INCOME_MONTH_PLAN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgMedIncomeMonthPlan implements Serializable {

	
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
	 * 分解方法
	 */
	private String resolve_way;
	private String resolve_way_name;
	
	private String resolve_data ;
	
	private Integer reference_years ;
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

	public String getResolve_data() {
		return resolve_data;
	}

	public void setResolve_data(String resolve_data) {
		this.resolve_data = resolve_data;
	}

	public Integer getReference_years() {
		return reference_years;
	}

	public void setReference_years(Integer reference_years) {
		this.reference_years = reference_years;
	}
	
	
	
}