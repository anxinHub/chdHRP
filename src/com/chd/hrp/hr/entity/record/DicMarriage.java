/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.entity.record;

import java.io.Serializable;
/**
 * 
 * @Description:
 * 
 * @Table:
 * DIC_MARRIAGE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class DicMarriage implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 
	 */
	private String marriage_code;
	
	/**
	 * 
	 */
	private String marriage_name;
	
	/**
	 * 0否，1是
	 */
	private Integer is_stop;
	
	/**
	 * 
	 */
	private String note;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 
	* @param value 
	*/
	public void setMarriage_code(String value) {
		this.marriage_code = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getMarriage_code() {
		return this.marriage_code;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setMarriage_name(String value) {
		this.marriage_name = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getMarriage_name() {
		return this.marriage_name;
	}
	/**
	* 设置 0否，1是
	* @param value 
	*/
	public void setIs_stop(Integer value) {
		this.is_stop = value;
	}
	
	/**
	* 获取 0否，1是
	* @return Integer
	*/
	public Integer getIs_stop() {
		return this.is_stop;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setNote(String value) {
		this.note = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getNote() {
		return this.note;
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