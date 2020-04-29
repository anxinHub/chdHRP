package com.chd.hrp.med.entity;

public class MedShowSet {
	
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
	 *显示ID 
	 */
	private Long show_id;
	
	/**
	 * 显示名称
	 */
	private String show_name;
	
	/**
	 * 是否显示
	 */
	private int show_flag;
	
	/**
	 * 方向
	 */
	private int direction_flag;

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

	public Long getShow_id() {
		return show_id;
	}

	public void setShow_id(Long show_id) {
		this.show_id = show_id;
	}

	public String getShow_name() {
		return show_name;
	}

	public void setShow_name(String show_name) {
		this.show_name = show_name;
	}

	public int getShow_flag() {
		return show_flag;
	}

	public void setShow_flag(int show_flag) {
		this.show_flag = show_flag;
	}

	public int getDirection_flag() {
		return direction_flag;
	}

	public void setDirection_flag(int direction_flag) {
		this.direction_flag = direction_flag;
	}
}
