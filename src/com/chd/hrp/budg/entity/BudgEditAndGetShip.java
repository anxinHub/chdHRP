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
 * BUDG_EDIT_AND_GET_SHIP
 * @Table:
 * BUDG_EDIT_AND_GET_SHIP
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgEditAndGetShip implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 编制方法
	 */
	private String edit_method;
	
	/**
	 * 取值方法
	 */
	private String get_way;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
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