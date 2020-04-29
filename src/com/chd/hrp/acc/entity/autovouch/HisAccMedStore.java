package com.chd.hrp.acc.entity.autovouch;

import java.io.Serializable;

public class HisAccMedStore implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long group_id;

	private Long hos_id;

	private String copy_code;

	private String store_code;

	private String store_name;
	
	private Integer store_flag;

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

	public String getStore_code() {
		return store_code;
	}

	public void setStore_code(String store_code) {
		this.store_code = store_code;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public Integer getStore_flag() {
		return store_flag;
	}

	public void setStore_flag(Integer store_flag) {
		this.store_flag = store_flag;
	}
	
	
}
