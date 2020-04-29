package com.chd.hrp.ass.entity.dict;

public class AssInspectionItem {
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
	private Long ins_id;

	/**
	 * 五笔码
	 */
	private Long detail_id;

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

	public String getCopy_code() {
		return copy_code;
	}

	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
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

	public Long getIns_id() {
		return ins_id;
	}

	public void setIns_id(Long ins_id) {
		this.ins_id = ins_id;
	}

	public Long getDetail_id() {
		return detail_id;
	}

	public void setDetail_id(Long detail_id) {
		this.detail_id = detail_id;
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

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}

}
