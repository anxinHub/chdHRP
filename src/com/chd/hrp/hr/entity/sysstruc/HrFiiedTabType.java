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
 * 如：国家标准、地方标准、医院标准
 * @Table:
 * HR_FIIED_TAB_TYPE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class HrFiiedTabType implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 
	 */
	private String group_id;
	
	/**
	 * 
	 */
	private String hos_id;
	
	/**
	 * 
	 */
	//private String copy_code;
	
	/**
	 * 
	 */
	private String type_filed_code;
	
	/**
	 * 
	 */
	private String type_filed_name;
	
	/**
	 * 
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
	
			 
		public String getGroup_id() {
		return group_id;
	}
	
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	
	public String getHos_id() {
		return hos_id;
	}
	
	public void setHos_id(String hos_id) {
		this.hos_id = hos_id;
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
	public void setType_filed_name(String value) {
		this.type_filed_name = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getType_filed_name() {
		return this.type_filed_name;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setIs_cite(Integer value) {
		this.is_cite = value;
	}
	
	/**
	* 获取 
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