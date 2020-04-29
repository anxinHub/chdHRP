/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.hr.entity.sc;

import java.io.Serializable;

/**
 * 
 * @Description: 如：人事档案、组织机构
 * @Table: HR_TAB_TYPE
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

public class HrTableType implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 
	 */
	private String type_tab_code;

	/**
	 * 
	 */
	private String type_tab_name;
	
	private Integer table_sort;

	/**
	 * 
	 */
	private String table_note;

	/**
	 * 导入验证信息
	 */
	private String error_type;

	/**
	 * 设置
	 * 
	 * @param value
	 */
	public void setType_tab_code(String value) {
		this.type_tab_code = value;
	}

	/**
	 * 获取
	 * 
	 * @return String
	 */
	public String getType_tab_code() {
		return this.type_tab_code;
	}

	/**
	 * 设置
	 * 
	 * @param value
	 */
	public void setType_tab_name(String value) {
		this.type_tab_name = value;
	}

	/**
	 * 获取
	 * 
	 * @return String
	 */
	public String getType_tab_name() {
		return this.type_tab_name;
	}

	
	public Integer getTable_sort() {
		return table_sort;
	}

	public void setTable_sort(Integer table_sort) {
		this.table_sort = table_sort;
	}

	public String getTable_note() {
		return table_note;
	}

	public void setTable_note(String table_note) {
		this.table_note = table_note;
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