package com.chd.hrp.mat.entity;

public class MatDuraDeptCheckRela {
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 医院ID
	 */
	private Long hos_id;
	
	/**
	 * 集团ID
	 */
	private Long group_id;
	
	/**
	 * 账套编码 
	 */
	private Object copy_code;
	
	/**
	 * 盘点单ID
	 */
	private Long check_id;
	
	/**
	 * 出库单ID
	 */
	private Long out_dura_id;
	
	/**
	 * 入库单ID
	 */
	private String in_dura_id;

	public Long getHos_id() {
		return hos_id;
	}

	public void setHos_id(Long hos_id) {
		this.hos_id = hos_id;
	}

	public Long getGroup_id() {
		return group_id;
	}

	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}

	public Object getCopy_code() {
		return copy_code;
	}

	public void setCopy_code(Object copy_code) {
		this.copy_code = copy_code;
	}

	public Long getCheck_id() {
		return check_id;
	}

	public void setCheck_id(Long check_id) {
		this.check_id = check_id;
	}

	public Long getOut_dura_id() {
		return out_dura_id;
	}

	public void setOut_dura_id(Long out_dura_id) {
		this.out_dura_id = out_dura_id;
	}

	public String getIn_dura_id() {
		return in_dura_id;
	}

	public void setIn_dura_id(String in_dura_id) {
		this.in_dura_id = in_dura_id;
	}
	
	
}
