package com.chd.hrp.ass.entity.contract;

import java.io.Serializable;

public class AssContractGroupHos implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 集体ID
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
	private Long group_contract_id;
	private Long hos_contract_id;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	
	
	public Long getGroup_contract_id() {
	return group_contract_id;
	}
	
	public void setGroup_contract_id(Long group_contract_id) {
		this.group_contract_id = group_contract_id;
	}
	
	public Long getHos_contract_id() {
		return hos_contract_id;
	}
	
	public void setHos_contract_id(Long hos_contract_id) {
		this.hos_contract_id = hos_contract_id;
	}

	/**
	* 设置 集体ID
	* @param value 
	*/
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	
	/**
	* 获取 集体ID
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
