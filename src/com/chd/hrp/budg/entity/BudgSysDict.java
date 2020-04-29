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
 * BUDG_SYS_DICT
 * @Table:
 * BUDG_SYS_DICT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgSysDict implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 字段编码
	 */
	private String f_code;
	
	/**
	 * 字段名称
	 */
	private String f_name;
	
	/**
	 * 字段取值编码
	 */
	private String value_code;
	
	/**
	 * 字段取值
	 */
	private String value_name;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 字段编码
	* @param value 
	*/
	public void setF_code(String value) {
		this.f_code = value;
	}
	
	/**
	* 获取 字段编码
	* @return String
	*/
	public String getF_code() {
		return this.f_code;
	}
	/**
	* 设置 字段名称
	* @param value 
	*/
	public void setF_name(String value) {
		this.f_name = value;
	}
	
	/**
	* 获取 字段名称
	* @return String
	*/
	public String getF_name() {
		return this.f_name;
	}
	/**
	* 设置 字段取值编码
	* @param value 
	*/
	public void setValue_code(String value) {
		this.value_code = value;
	}
	
	/**
	* 获取 字段取值编码
	* @return String
	*/
	public String getValue_code() {
		return this.value_code;
	}
	/**
	* 设置 字段取值
	* @param value 
	*/
	public void setValue_name(String value) {
		this.value_name = value;
	}
	
	/**
	* 获取 字段取值
	* @return String
	*/
	public String getValue_name() {
		return this.value_name;
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