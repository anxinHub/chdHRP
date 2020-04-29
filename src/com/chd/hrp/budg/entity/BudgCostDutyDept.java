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
 * 支出预算归口设置
 * @Table:
 * BUDG_COST_DUTY_DEPT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgCostDutyDept implements Serializable {

	
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
	private Object copy_code;
	
	/**
	 * 预算年度
	 */
	private Object budg_year;
	
	/**
	 * 预算科室ID
	 */
	private Long dept_id;
	
	/**
	 * 支出预算科目
	 */
	private Object subj_code;
	
	/**
	 * 归口科室ID
	 */
	private Long duty_dept_id;
	

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
	public void setCopy_code(Object value) {
		this.copy_code = value;
	}
	
	/**
	* 获取 账套编码
	* @return Object
	*/
	public Object getCopy_code() {
		return this.copy_code;
	}
	/**
	* 设置 预算年度
	* @param value 
	*/
	public void setBudg_year(Object value) {
		this.budg_year = value;
	}
	
	/**
	* 获取 预算年度
	* @return Object
	*/
	public Object getBudg_year() {
		return this.budg_year;
	}
	/**
	* 设置 预算科室ID
	* @param value 
	*/
	public void setDept_id(Long value) {
		this.dept_id = value;
	}
	
	/**
	* 获取 预算科室ID
	* @return Long
	*/
	public Long getDept_id() {
		return this.dept_id;
	}
	/**
	* 设置 支出预算科目
	* @param value 
	*/
	public void setSubj_code(Object value) {
		this.subj_code = value;
	}
	
	/**
	* 获取 支出预算科目
	* @return Object
	*/
	public Object getSubj_code() {
		return this.subj_code;
	}
	/**
	* 设置 归口科室ID
	* @param value 
	*/
	public void setDuty_dept_id(Long value) {
		this.duty_dept_id = value;
	}
	
	/**
	* 获取 归口科室ID
	* @return Long
	*/
	public Long getDuty_dept_id() {
		return this.duty_dept_id;
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