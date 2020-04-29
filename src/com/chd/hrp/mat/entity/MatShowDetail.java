package com.chd.hrp.mat.entity;

public class MatShowDetail {

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
	 * 业务类型编码
	 */
	private String bus_type_code;
	
	/**
	 * 业务类型名称
	 */
	private String bus_type_name;
	
	/**
	 * 是否选中
	 */
	private int is_choose;

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

	public String getBus_type_code() {
		return bus_type_code;
	}

	public void setBus_type_code(String bus_type_code) {
		this.bus_type_code = bus_type_code;
	}

	public String getBus_type_name() {
		return bus_type_name;
	}

	public void setBus_type_name(String bus_type_name) {
		this.bus_type_name = bus_type_name;
	}

	public int getIs_choose() {
		return is_choose;
	}

	public void setIs_choose(int is_choose) {
		this.is_choose = is_choose;
	}
}
