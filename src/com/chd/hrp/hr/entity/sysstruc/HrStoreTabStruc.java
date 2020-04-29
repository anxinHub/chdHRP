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
 * HR_STORE_TAB_STRUC 人员档案库数据集表 
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class HrStoreTabStruc implements Serializable {

	
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
	private String store_type_code;
	
	private String store_type_name;
	
	/**
	 * 
	 */
	private String tab_code;
	
	private String tab_name;
	
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
	public void setStore_type_code(String value) {
		this.store_type_code = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getStore_type_code() {
		return this.store_type_code;
	}
	
	
	public String getStore_type_name() {
		return store_type_name;
	}

	public void setStore_type_name(String store_type_name) {
		this.store_type_name = store_type_name;
	}

	/**
	* 设置 
	* @param value 
	*/
	public void setTab_code(String value) {
		this.tab_code = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getTab_code() {
		return this.tab_code;
	}
	
	public String getTab_name() {
		return tab_name;
	}

	public void setTab_name(String tab_name) {
		this.tab_name = tab_name;
	}

	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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