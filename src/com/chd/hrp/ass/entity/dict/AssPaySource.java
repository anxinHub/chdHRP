/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.dict;

import java.io.Serializable;
/**
 * 
 * @Description:
 * 付款支付方式与资金来源对应关系
 * @Table:
 * ASS_PAY_SOURCE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class AssPaySource implements Serializable {

	
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
	 * 支付方式
	 */
	private String pay_code;
	
	private String pay_name;
	
	/**
	 * 资金来源编码
	 */
	private Long source_id;
	
	private String source_code;
	
	private String source_name;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	
	
	public String getPay_name() {
		return pay_name;
	}
	
	public void setPay_name(String pay_name) {
		this.pay_name = pay_name;
	}
	
	public String getSource_code() {
		return source_code;
	}
	
	public void setSource_code(String source_code) {
		this.source_code = source_code;
	}
	
	public String getSource_name() {
		return source_name;
	}
	
	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}

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
	* 设置 支付方式
	* @param value 
	*/
	public void setPay_code(String value) {
		this.pay_code = value;
	}
	
	/**
	* 获取 支付方式
	* @return String
	*/
	public String getPay_code() {
		return this.pay_code;
	}
	/**
	* 设置 资金来源编码
	* @param value 
	*/
	public void setSource_id(Long value) {
		this.source_id = value;
	}
	
	/**
	* 获取 资金来源编码
	* @return Long
	*/
	public Long getSource_id() {
		return this.source_id;
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