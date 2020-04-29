package com.chd.hrp.mat.entity;

public class MatDuraDeptBar {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	/**
	 * 集团ID
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
	 * 科室ID
	 */
	private Long dept_id;
	
	/**
	 * 材料ID
	 */
	private Long inv_id;
	
	/**
	 * 条码
	 */
	private String bar_code;

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

	public Long getDept_id() {
		return dept_id;
	}

	public void setDept_id(Long dept_id) {
		this.dept_id = dept_id;
	}

	public Long getInv_id() {
		return inv_id;
	}

	public void setInv_id(Long inv_id) {
		this.inv_id = inv_id;
	}

	public String getBar_code() {
		return bar_code;
	}

	public void setBar_code(String bar_code) {
		this.bar_code = bar_code;
	}
}
