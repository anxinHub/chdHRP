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
 * HR_FIIED_TAB_STRUC
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class HrFiiedTabStruc implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 
	 */
	private Double group_id;
	
	/**
	 * 
	 */
	private Double hos_id;
	
	/**
	 * 
	 */
	//private String copy_code;
	
	/**
	 * 
	 */
	private String field_tab_code;
	
	/**
	 * 
	 */
	private String type_filed_code;
	
	/**
	 * 
	 */
	private String field_tab_name;
	
	/**
	 * 
	 */
	private Integer is_innr;
	
	/**
	 * 1:是 2:否 。如果为1说明是系统字典
	 */
	private Integer is_cite;
	
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
	public void setGroup_id(Double value) {
		this.group_id = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getGroup_id() {
		return this.group_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setHos_id(Double value) {
		this.hos_id = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getHos_id() {
		return this.hos_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	/*public void setCopy_code(String value) {
		this.copy_code = value;
	}*/
	
	/**
	* 获取 
	* @return String
	*/
	/*public String getCopy_code() {
		return this.copy_code;
	}*/
	/**
	* 设置 
	* @param value 
	*/
	public void setField_tab_code(String value) {
		this.field_tab_code = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getField_tab_code() {
		return this.field_tab_code;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setType_filed_code(String value) {
		this.type_filed_code = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getType_filed_code() {
		return this.type_filed_code;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setField_tab_name(String value) {
		this.field_tab_name = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getField_tab_name() {
		return this.field_tab_name;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setIs_innr(Integer value) {
		this.is_innr = value;
	}
	
	/**
	* 获取 
	* @return Integer
	*/
	public Integer getIs_innr() {
		return this.is_innr;
	}
	/**
	* 设置 1:是 2:否 。如果为1说明是系统字典
	* @param value 
	*/
	public void setIs_cite(Integer value) {
		this.is_cite = value;
	}
	
	/**
	* 获取 1:是 2:否 。如果为1说明是系统字典
	* @return Integer
	*/
	public Integer getIs_cite() {
		return this.is_cite;
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