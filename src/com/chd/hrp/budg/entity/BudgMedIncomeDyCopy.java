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
 * 科室年度医疗收入预算备份
 * @Table:
 * BUDG_MED_INCOME_DY_COPY
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgMedIncomeDyCopy implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 调整单号
	 */
	private String adj_code;
	
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
	 * 年度
	 */
	private String year;
	
	/**
	 * 科目编码
	 */
	private String subj_code;
	
	/**
	 * 部门ID
	 */
	private Long dept_id;
	
	/**
	 * 计算值
	 */
	private Double count_value;
	
	/**
	 * 预算值
	 */
	private Double budg_value;
	
	/**
	 * 说明
	 */
	private String remark;
	
	/**
	 * 增长比例
	 */
	private Double grow_rate;
	
	/**
	 * 分解比例
	 */
	private Double resolve_rate;
	
	/**
	 * 上年收入
	 */
	private Double last_year_income;
	
	/**
	 * 医院意见分解
	 */
	private Double hos_suggest_resolve;
	
	/**
	 * 科室意见
	 */
	private Double dept_suggest;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 调整单号
	* @param value 
	*/
	public void setAdj_code(String value) {
		this.adj_code = value;
	}
	
	/**
	* 获取 调整单号
	* @return String
	*/
	public String getAdj_code() {
		return this.adj_code;
	}
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
	* 设置 年度
	* @param value 
	*/
	public void setYear(String value) {
		this.year = value;
	}
	
	/**
	* 获取 年度
	* @return String
	*/
	public String getYear() {
		return this.year;
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
	* 设置 部门ID
	* @param value 
	*/
	public void setDept_id(Long value) {
		this.dept_id = value;
	}
	
	/**
	* 获取 部门ID
	* @return Long
	*/
	public Long getDept_id() {
		return this.dept_id;
	}
	/**
	* 设置 计算值
	* @param value 
	*/
	public void setCount_value(Double value) {
		this.count_value = value;
	}
	
	/**
	* 获取 计算值
	* @return Double
	*/
	public Double getCount_value() {
		return this.count_value;
	}
	/**
	* 设置 预算值
	* @param value 
	*/
	public void setBudg_value(Double value) {
		this.budg_value = value;
	}
	
	/**
	* 获取 预算值
	* @return Double
	*/
	public Double getBudg_value() {
		return this.budg_value;
	}
	/**
	* 设置 说明
	* @param value 
	*/
	public void setRemark(String value) {
		this.remark = value;
	}
	
	/**
	* 获取 说明
	* @return String
	*/
	public String getRemark() {
		return this.remark;
	}
	/**
	* 设置 增长比例
	* @param value 
	*/
	public void setGrow_rate(Double value) {
		this.grow_rate = value;
	}
	
	/**
	* 获取 增长比例
	* @return Double
	*/
	public Double getGrow_rate() {
		return this.grow_rate;
	}
	/**
	* 设置 分解比例
	* @param value 
	*/
	public void setResolve_rate(Double value) {
		this.resolve_rate = value;
	}
	
	/**
	* 获取 分解比例
	* @return Double
	*/
	public Double getResolve_rate() {
		return this.resolve_rate;
	}
	/**
	* 设置 上年收入
	* @param value 
	*/
	public void setLast_year_income(Double value) {
		this.last_year_income = value;
	}
	
	/**
	* 获取 上年收入
	* @return Double
	*/
	public Double getLast_year_income() {
		return this.last_year_income;
	}
	/**
	* 设置 医院意见分解
	* @param value 
	*/
	public void setHos_suggest_resolve(Double value) {
		this.hos_suggest_resolve = value;
	}
	
	/**
	* 获取 医院意见分解
	* @return Double
	*/
	public Double getHos_suggest_resolve() {
		return this.hos_suggest_resolve;
	}
	/**
	* 设置 科室意见
	* @param value 
	*/
	public void setDept_suggest(Double value) {
		this.dept_suggest = value;
	}
	
	/**
	* 获取 科室意见
	* @return Double
	*/
	public Double getDept_suggest() {
		return this.dept_suggest;
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