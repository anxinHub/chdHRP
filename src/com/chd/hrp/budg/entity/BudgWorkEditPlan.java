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
 * BUDG_WORK_EDIT_PLAN
 * @Table:
 * BUDG_WORK_EDIT_PLAN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgWorkEditPlan implements Serializable {

	
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
	 * 预算层次
	 */
	private String budg_level;
	
	/**
	 * 指标编码
	 */
	private String index_code;
	
	/**
	 * 是否独立核算
	 */
	private Integer is_single_count;
	
	/**
	 * 编制方法
	 */
	private String edit_method;
	
	/**
	 * 取值方法
	 */
	private String get_way;
	
	/**
	 * 计算公式ID
	 */
	private String formula_id;
	
	/**
	 * 取值函数ID
	 */
	private String fun_id;
	
	/**
	 * 分解或汇总
	 */
	private String resolve_or_sum;
	
	/**
	 * 分解方法
	 */
	private String resolve_way;
	
	/**
	 * 分解层次
	 */
	private String resolve_level;
	
	/**
	 * 汇总层次
	 */
	private String sum_level;
	/**
	 * 参考年限
	 */
	private int reference_years ;
	
	/**
	 * 自定义分解系数
	 */
	private String resolve_data;
	

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
	* 设置 预算层次
	* @param value 
	*/
	public void setBudg_level(String value) {
		this.budg_level = value;
	}
	
	/**
	* 获取 预算层次
	* @return String
	*/
	public String getBudg_level() {
		return this.budg_level;
	}
	/**
	* 设置 指标编码
	* @param value 
	*/
	public void setIndex_code(String value) {
		this.index_code = value;
	}
	
	/**
	* 获取 指标编码
	* @return String
	*/
	public String getIndex_code() {
		return this.index_code;
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
	* 设置 分解层次
	* @param value 
	*/
	public void setResolve_level(String value) {
		this.resolve_level = value;
	}
	
	/**
	* 获取 分解层次
	* @return String
	*/
	public String getResolve_level() {
		return this.resolve_level;
	}
	/**
	* 设置 汇总层次
	* @param value 
	*/
	public void setSum_level(String value) {
		this.sum_level = value;
	}
	
	/**
	* 获取 汇总层次
	* @return String
	*/
	public String getSum_level() {
		return this.sum_level;
	}
	
	public int getReference_years() {
		return reference_years;
	}

	public void setReference_years(int reference_years) {
		this.reference_years = reference_years;
	}
	
	public String getResolve_data() {
		return resolve_data;
	}

	public void setResolve_data(String resolve_data) {
		this.resolve_data = resolve_data;
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
}