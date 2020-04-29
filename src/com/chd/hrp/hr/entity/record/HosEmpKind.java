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
 * HOS_EMP_KIND
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class HosEmpKind implements Serializable {

	
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
	private String copy_code;
	
	/**
	 * 
	 */
	private String kind_code;
	
	/**
	 * 
	 */
	private String kind_name;
	
	/**
	 * 
	 */
	private String spell_code;
	
	/**
	 * 
	 */
	private String wbx_code;
	
	/**
	 * 0否，1是
	 */
	private Integer is_stop;
	/**
	 * 是否停用
	 */
	private String is_stop_name;
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
	public void setKind_code(String value) {
		this.kind_code = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getKind_code() {
		return this.kind_code;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setKind_name(String value) {
		this.kind_name = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getKind_name() {
		return this.kind_name;
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
	
	public String getIs_stop_name() {
		return is_stop_name;
	}

	public void setIs_stop_name(String is_stop_name) {
		this.is_stop_name = is_stop_name;
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

	public String getCopy_code() {
		return copy_code;
	}

	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	
}