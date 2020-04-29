package com.chd.hrp.hip.entity;

import java.io.Serializable;

public class HipStoreRef implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long group_id;

	private Long hos_id;

	private String ds_code;

	private String hip_store_code;
	
	private String hip_store_name;

	private String hrp_store_code;
	
	private String hrp_store_name;

	private String ds_name;

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

	public String getDs_code() {
		return ds_code;
	}

	public void setDs_code(String ds_code) {
		this.ds_code = ds_code;
	}

	public String getHip_store_code() {
		return hip_store_code;
	}

	public void setHip_store_code(String hip_store_code) {
		this.hip_store_code = hip_store_code;
	}

	public String getHrp_store_code() {
		return hrp_store_code;
	}

	public void setHrp_store_code(String hrp_store_code) {
		this.hrp_store_code = hrp_store_code;
	}

	public String getDs_name() {
		return ds_name;
	}

	public void setDs_name(String ds_name) {
		this.ds_name = ds_name;
	}

	public String getHip_store_name() {
		return hip_store_name;
	}

	public void setHip_store_name(String hip_store_name) {
		this.hip_store_name = hip_store_name;
	}

	public String getHrp_store_name() {
		return hrp_store_name;
	}

	public void setHrp_store_name(String hrp_store_name) {
		this.hrp_store_name = hrp_store_name;
	}
	
	

}
