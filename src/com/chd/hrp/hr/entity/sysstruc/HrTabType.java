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
 * @Description: 如：人事档案、组织机构
 * @Table: HR_TAB_TYPE
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

public class HrTabType implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_id;

	private Long hos_id;

	/*private String copy_code;*/

	/**
	 * 
	 */
	private String type_tab_code;

	/**
	 * 
	 */
	private String type_tab_name;

	/**
	 * 
	 */
	private String note;

	/**
	 * 导入验证信息
	 */
	private String error_type;

	public Long getGroup_id() {
		return group_id;
	}

	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}

	public Long getHos_id() {
		return hos_id;
	}

	public void setHos_id(Long hos_id) {
		this.hos_id = hos_id;
	}

	/*public String getCopy_code() {
		return copy_code;
	}

	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}*/

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

	/**
	 * 设置
	 * 
	 * @param value
	 */
	public void setNote(String value) {
		this.note = value;
	}

	/**
	 * 获取
	 * 
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