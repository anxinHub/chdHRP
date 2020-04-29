﻿/** 
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
 * 项目预算申报明细（按资金来源）
 * @Table:
 * BUDG_PROJ_APPLY_SOURCE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgProjApplySource implements Serializable {

	
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
	 * 申报单号
	 */
	private String apply_code;
	
	/**
	 * 项目ID
	 */
	private Long proj_id;
	
	/**
	 * 用自增ID
	 */
	private Long source_id;
	
	/**
	 * 申报金额
	 */
	private Double apply_amount;
	

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
	* 设置 申报单号
	* @param value 
	*/
	public void setApply_code(String value) {
		this.apply_code = value;
	}
	
	/**
	* 获取 申报单号
	* @return String
	*/
	public String getApply_code() {
		return this.apply_code;
	}
	/**
	* 设置 项目ID
	* @param value 
	*/
	public void setProj_id(Long value) {
		this.proj_id = value;
	}
	
	/**
	* 获取 项目ID
	* @return Long
	*/
	public Long getProj_id() {
		return this.proj_id;
	}
	/**
	* 设置 用自增ID
	* @param value 
	*/
	public void setSource_id(Long value) {
		this.source_id = value;
	}
	
	/**
	* 获取 用自增ID
	* @return Long
	*/
	public Long getSource_id() {
		return this.source_id;
	}
	/**
	* 设置 申报金额
	* @param value 
	*/
	public void setApply_amount(Double value) {
		this.apply_amount = value;
	}
	
	/**
	* 获取 申报金额
	* @return Double
	*/
	public Double getApply_amount() {
		return this.apply_amount;
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