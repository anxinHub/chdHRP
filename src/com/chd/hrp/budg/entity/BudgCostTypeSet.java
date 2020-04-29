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
 * 支出科目类别维护
 * @Table:
 * BUDG_COST_TYPE_SET
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgCostTypeSet implements Serializable {

	
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
	 * 支出科目类别编码
	 */
	private String type_code;
	private String type_code_old;
	
	private String type_name;
	
	/**
	 * 支出科目编码
	 */
	private String subj_code;
	private String subj_code_old;
	
	private String subj_name;
	

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
	* 设置 支出科目类别编码
	* @param value 
	*/
	public void setType_code(String value) {
		this.type_code = value;
	}
	
	/**
	* 获取 支出科目类别编码
	* @return String
	*/
	public String getType_code() {
		return this.type_code;
	}
	/**
	* 设置 支出科目编码
	* @param value 
	*/
	public void setSubj_code(String value) {
		this.subj_code = value;
	}
	
	/**
	* 获取 支出科目编码
	* @return String
	*/
	public String getSubj_code() {
		return this.subj_code;
	}
	
	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public String getSubj_name() {
		return subj_name;
	}

	public void setSubj_name(String subj_name) {
		this.subj_name = subj_name;
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
	
	public String getType_code_old() {
		return type_code_old;
	}

	public void setType_code_old(String type_code_old) {
		this.type_code_old = type_code_old;
	}

	public String getSubj_code_old() {
		return subj_code_old;
	}

	public void setSubj_code_old(String subj_code_old) {
		this.subj_code_old = subj_code_old;
	}
}