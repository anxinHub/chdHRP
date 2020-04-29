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
 * 支出科目类别
 * @Table:
 * BUDG_COST_SUBJ_TYPE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgCostSubjType implements Serializable {

	
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
	 * 支出科目类别编码
	 */
	private Object cost_subj_type_code;
	
	/**
	 * 支出科目类别名称
	 */
	private Object cost_subj_type_name;
	
	/**
	 * 拼音码
	 */
	private Object spell_code;
	
	/**
	 * 五笔码
	 */
	private Object wbx_code;
	
	/**
	 * 是否停用
	 */
	private Integer is_stop;
	
	/**
	 * 是否内置
	 */
	private Integer is_fixed;
	

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
	* 设置 支出科目类别编码
	* @param value 
	*/
	public void setCost_subj_type_code(Object value) {
		this.cost_subj_type_code = value;
	}
	
	/**
	* 获取 支出科目类别编码
	* @return Object
	*/
	public Object getCost_subj_type_code() {
		return this.cost_subj_type_code;
	}
	/**
	* 设置 支出科目类别名称
	* @param value 
	*/
	public void setCost_subj_type_name(Object value) {
		this.cost_subj_type_name = value;
	}
	
	/**
	* 获取 支出科目类别名称
	* @return Object
	*/
	public Object getCost_subj_type_name() {
		return this.cost_subj_type_name;
	}
	/**
	* 设置 拼音码
	* @param value 
	*/
	public void setSpell_code(Object value) {
		this.spell_code = value;
	}
	
	/**
	* 获取 拼音码
	* @return Object
	*/
	public Object getSpell_code() {
		return this.spell_code;
	}
	/**
	* 设置 五笔码
	* @param value 
	*/
	public void setWbx_code(Object value) {
		this.wbx_code = value;
	}
	
	/**
	* 获取 五笔码
	* @return Object
	*/
	public Object getWbx_code() {
		return this.wbx_code;
	}
	/**
	* 设置 是否停用
	* @param value 
	*/
	public void setIs_stop(Integer value) {
		this.is_stop = value;
	}
	
	/**
	* 获取 是否停用
	* @return Integer
	*/
	public Integer getIs_stop() {
		return this.is_stop;
	}
	/**
	* 设置 是否内置
	* @param value 
	*/
	public void setIs_fixed(Integer value) {
		this.is_fixed = value;
	}
	
	/**
	* 获取 是否内置
	* @return Integer
	*/
	public Integer getIs_fixed() {
		return this.is_fixed;
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