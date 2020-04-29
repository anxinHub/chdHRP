/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.acc.entity;

import java.io.Serializable;
/**
 * 
 * @Description:
 * 
 * @Table:
 * ACC_WAGE_PAY_DESC
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AccWagePayDesc implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 
	 */
	private Long desc_id;
	
	/**
	 * 
	 */
	private Long group_id;
	
	/**
	 * 
	 */
	private Long hos_id;
	
	/**
	 * 
	 */
	private String copy_code;
	
	/**
	 * 
	 */
	private String wage_code;
	
	/**
	 * 
	 */
	private String acc_year;
	
	/**
	 * 
	 */
	private String acc_month;
	
	/**
	 * 
	 */
	private String kined_code;
	
	/**
	 * 
	 */
	private String kined_name;
	
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
	public void setDesc_id(Long value) {
		this.desc_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getDesc_id() {
		return this.desc_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getGroup_id() {
		return this.group_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setHos_id(Long value) {
		this.hos_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getHos_id() {
		return this.hos_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setCopy_code(String value) {
		this.copy_code = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getCopy_code() {
		return this.copy_code;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setWage_code(String value) {
		this.wage_code = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getWage_code() {
		return this.wage_code;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setAcc_year(String value) {
		this.acc_year = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getAcc_year() {
		return this.acc_year;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setAcc_month(String value) {
		this.acc_month = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getAcc_month() {
		return this.acc_month;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setKined_code(String value) {
		this.kined_code = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getKined_code() {
		return this.kined_code;
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

	public String getKined_name() {
		return kined_name;
	}

	public void setKined_name(String kined_name) {
		this.kined_name = kined_name;
	}
	
	
}