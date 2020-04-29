/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.entity.sysstruc;

import java.io.Serializable;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_COM_TYPE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class HrComType implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 
	 */
	private String com_type_code;
	
	/**
	 * 
	 */
	private String com_type_name;
	
	/**
	 * input:下拉框 text:文本框 date:日期框
	 */
	private String com_type_nature;
	
	/**
	 * 
	 */
	private String com_type_note;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 
	* @param value 
	*/
	public void setCom_type_code(String value) {
		this.com_type_code = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getCom_type_code() {
		return this.com_type_code;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setCom_type_name(String value) {
		this.com_type_name = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getCom_type_name() {
		return this.com_type_name;
	}
	/**
	* 设置 input:下拉框 text:文本框 date:日期框
	* @param value 
	*/
	public void setCom_type_nature(String value) {
		this.com_type_nature = value;
	}
	
	/**
	* 获取 input:下拉框 text:文本框 date:日期框
	* @return String
	*/
	public String getCom_type_nature() {
		return this.com_type_nature;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setCom_type_note(String value) {
		this.com_type_note = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getCom_type_note() {
		return this.com_type_note;
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