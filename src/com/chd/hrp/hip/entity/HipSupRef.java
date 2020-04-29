package com.chd.hrp.hip.entity;

import java.io.Serializable;

public class HipSupRef implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long group_id;

	private Long hos_id;

	private String ds_code;

	private String hip_sup_code;
	
	private String hip_sup_name;

	private String hrp_sup_code;
	
	private String hrp_sup_name;

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

	public String getHip_sup_code() {
		return hip_sup_code;
	}

	public void setHip_sup_code(String hip_sup_code) {
		this.hip_sup_code = hip_sup_code;
	}

	public String getHrp_sup_code() {
		return hrp_sup_code;
	}

	public void setHrp_sup_code(String hrp_sup_code) {
		this.hrp_sup_code = hrp_sup_code;
	}

	public String getDs_name() {
		return ds_name;
	}

	public void setDs_name(String ds_name) {
		this.ds_name = ds_name;
	}

	public String getHip_sup_name() {
		return hip_sup_name;
	}

	public void setHip_sup_name(String hip_sup_name) {
		this.hip_sup_name = hip_sup_name;
	}

	public String getHrp_sup_name() {
		return hrp_sup_name;
	}

	public void setHrp_sup_name(String hrp_sup_name) {
		this.hrp_sup_name = hrp_sup_name;
	}
	
	

}
