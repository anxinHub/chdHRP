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
 * 11:=  12:> 13: >= 14:<  15:<= 16:<> 17:Like 18:NOT LIKE 19:IN 20:NOT IN
 * @Table:
 * HR_CON_SIGN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class HrConSign implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 
	 */
	private String con_sign_code;
	
	/**
	 * 
	 */
	private String con_sign_name;
	
	/**
	 * 
	 */
	private String con_sign_note;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 
	* @param value 
	*/
	public void setCon_sign_code(String value) {
		this.con_sign_code = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getCon_sign_code() {
		return this.con_sign_code;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setCon_sign_name(String value) {
		this.con_sign_name = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getCon_sign_name() {
		return this.con_sign_name;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setCon_sign_note(String value) {
		this.con_sign_note = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getCon_sign_note() {
		return this.con_sign_note;
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