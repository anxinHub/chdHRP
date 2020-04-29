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
 * 期初借款记账
 * @Table:
 * BUDG_BORR_BEGAIN_MARK
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgBorrBegainMark implements Serializable {

	
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
	 * 0未记账1记账
	 */
	private Integer state;
	
	/**
	 * 记账人
	 */
	private Long user_id;
	
	/**
	 * 期初记账日期
	 */
	private Date mark_date;
	

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
	* 设置 0未记账1记账
	* @param value 
	*/
	public void setState(Integer value) {
		this.state = value;
	}
	
	/**
	* 获取 0未记账1记账
	* @return Integer
	*/
	public Integer getState() {
		return this.state;
	}
	/**
	* 设置 记账人
	* @param value 
	*/
	public void setUser_id(Long value) {
		this.user_id = value;
	}
	
	/**
	* 获取 记账人
	* @return Long
	*/
	public Long getUser_id() {
		return this.user_id;
	}
	/**
	* 设置 期初记账日期
	* @param value 
	*/
	public void setMark_date(Date value) {
		this.mark_date = value;
	}
	
	/**
	* 获取 期初记账日期
	* @return Date
	*/
	public Date getMark_date() {
		return this.mark_date;
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