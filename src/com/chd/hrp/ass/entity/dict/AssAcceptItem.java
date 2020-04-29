package com.chd.hrp.ass.entity.dict;

import java.io.Serializable;

public class AssAcceptItem implements Serializable {
	private static final long serialVersionUID = 5454155825314635342L;

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
	
	/**
	 * 验收项目编码
	 */
	private String item_code;
	
	/**
	 * 验收项目名称
	 */
	private String item_name;
	
	/**
	 * 拼音码
	 */
	private Long accept_id;
	
	/**
	 * 五笔码
	 */
	private Long accept_detail_id;
	
	/**
	 * 是否停用
	 */
	private Integer is_maintain;
	
	private Integer is_normal;
	
	private String note;
  /**
	 * 导入验证信息
	 */
	private String error_type;
	
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

	public String getItem_code() {
		return item_code;
	}

	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public Long getAccept_id() {
		return accept_id;
	}

	public void setAccept_id(Long accept_id) {
		this.accept_id = accept_id;
	}

	public Long getAccept_detail_id() {
		return accept_detail_id;
	}

	public void setAccept_detail_id(Long accept_detail_id) {
		this.accept_detail_id = accept_detail_id;
	}

	public Integer getIs_maintain() {
		return is_maintain;
	}

	public void setIs_maintain(Integer is_maintain) {
		this.is_maintain = is_maintain;
	}

	public Integer getIs_normal() {
		return is_normal;
	}

	public void setIs_normal(Integer is_normal) {
		this.is_normal = is_normal;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
}
