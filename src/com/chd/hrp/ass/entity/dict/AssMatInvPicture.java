package com.chd.hrp.ass.entity.dict;

import java.io.Serializable;

public class AssMatInvPicture implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Long hos_id;
	
	/**
	 * 
	 */
	private Long group_id;
	
	/**
	 * 
	 */
	private Object copy_code;
	
	private Long inv_id;
	
	private String picture_name;
	
	private String picture_url;

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

	public Long getInv_id() {
		return inv_id;
	}

	public void setInv_id(Long inv_id) {
		this.inv_id = inv_id;
	}

	public String getPicture_name() {
		return picture_name;
	}

	public void setPicture_name(String picture_name) {
		this.picture_name = picture_name;
	}

	public String getPicture_url() {
		return picture_url;
	}

	public void setPicture_url(String picture_url) {
		this.picture_url = picture_url;
	}
	
}
