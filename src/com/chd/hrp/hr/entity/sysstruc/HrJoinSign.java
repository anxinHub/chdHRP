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
 * 11:AND 12:OR 13:NOT
 * @Table:
 * HR_JOIN_SIGN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class HrJoinSign implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 
	 */
	private String join_sign_code;
	
	/**
	 * 
	 */
	private String join_sign_name;
	
	/**
	 * 
	 */
	private String join_sign_note;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 
	* @param value 
	*/
	public void setJoin_sign_code(String value) {
		this.join_sign_code = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getJoin_sign_code() {
		return this.join_sign_code;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setJoin_sign_name(String value) {
		this.join_sign_name = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getJoin_sign_name() {
		return this.join_sign_name;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setJoin_sign_note(String value) {
		this.join_sign_note = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getJoin_sign_note() {
		return this.join_sign_note;
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