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
 * HR_FIIED_DATA
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class HrFiiedData implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

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
	//private String copy_code;
	
	/**
	 * 
	 */
	private String field_tab_code;
	
	/**
	 * 
	 */
	private String field_col_code;
	
	/**
	 * 
	 */
	private String field_col_name;
	
	/**
	 * 
	 */
	private String super_col_code;
	
	/**
	 * 
	 */
	private String spell_code;
	
	/**
	 * 
	 */
	private String wbx_code;
	
	/**
	 * 
	 */
	private Integer is_innr;
	
	/**
	 * 
	 */
	private Integer is_stop;
	
	/**
	 * 
	 */
	private Integer is_last;
	
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
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	
	/**
	* 获取 
	* @return Double
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
	* @return Double
	*/
	public Long getHos_id() {
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
	public void setField_col_code(String value) {
		this.field_col_code = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getField_col_code() {
		return this.field_col_code;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setField_col_name(String value) {
		this.field_col_name = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getField_col_name() {
		return this.field_col_name;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setSuper_col_code(String value) {
		this.super_col_code = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getSuper_col_code() {
		return this.super_col_code;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setSpell_code(String value) {
		this.spell_code = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getSpell_code() {
		return this.spell_code;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setWbx_code(String value) {
		this.wbx_code = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getWbx_code() {
		return this.wbx_code;
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
	* 设置 
	* @param value 
	*/
	public void setIs_stop(Integer value) {
		this.is_stop = value;
	}
	
	/**
	* 获取 
	* @return Integer
	*/
	public Integer getIs_stop() {
		return this.is_stop;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setIs_last(Integer value) {
		this.is_last = value;
	}
	
	/**
	* 获取 
	* @return Integer
	*/
	public Integer getIs_last() {
		return this.is_last;
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