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
 * 支出预算科目与成本项目对应关系
 * @Table:
 * BUDG_COST_SUBJ_SHIP
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgCostSubjShip implements Serializable {

	
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
	 * 预算科目编码
	 */
	private Object budg_subj_code;
	
	/**
	 * 成本项目ID
	 */
	private Long cost_item_id;
	
	/**
	 * 成本项目变更ID
	 */
	private Long cost_item_no;
	

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
	* 设置 预算科目编码
	* @param value 
	*/
	public void setBudg_subj_code(Object value) {
		this.budg_subj_code = value;
	}
	
	/**
	* 获取 预算科目编码
	* @return Object
	*/
	public Object getBudg_subj_code() {
		return this.budg_subj_code;
	}
	/**
	* 设置 成本项目ID
	* @param value 
	*/
	public void setCost_item_id(Long value) {
		this.cost_item_id = value;
	}
	
	/**
	* 获取 成本项目ID
	* @return Long
	*/
	public Long getCost_item_id() {
		return this.cost_item_id;
	}
	/**
	* 设置 成本项目变更ID
	* @param value 
	*/
	public void setCost_item_no(Long value) {
		this.cost_item_no = value;
	}
	
	/**
	* 获取 成本项目变更ID
	* @return Long
	*/
	public Long getCost_item_no() {
		return this.cost_item_no;
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