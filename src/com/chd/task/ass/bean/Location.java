package com.chd.task.ass.bean;

import org.nutz.dao.entity.annotation.View;

@View("v_mobile_location")
public class Location {
	//资产位置
	private String group_id;
	private String hos_id;
	private String copy_code;
	private String loc_name;
	private String loc_code;
	
	/**
	 * @return the group_id
	 */
	public String getGroup_id() {
		return group_id;
	}
	/**
	 * @param group_id the group_id to set
	 */
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	/**
	 * @return the hos_id
	 */
	public String getHos_id() {
		return hos_id;
	}
	/**
	 * @param hos_id the hos_id to set
	 */
	public void setHos_id(String hos_id) {
		this.hos_id = hos_id;
	}
	/**
	 * @return the copy_code
	 */
	public String getCopy_code() {
		return copy_code;
	}
	/**
	 * @param copy_code the copy_code to set
	 */
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	
	/**
	 * @return the loc_name
	 */
	public String getLoc_name() {
		return loc_name;
	}
	/**
	 * @param loc_name the loc_name to set
	 */
	public void setLoc_name(String loc_name) {
		this.loc_name = loc_name;
	}
	/**
	 * @return the loc_code
	 */
	public String getLoc_code() {
		return loc_code;
	}
	/**
	 * @param loc_code the loc_code to set
	 */
	public void setLoc_code(String loc_code) {
		this.loc_code = loc_code;
	}
	
	
	
}
