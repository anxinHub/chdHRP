package com.chd.hrp.hip.entity;

import java.io.Serializable;

public class HipSourceRef implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long group_id;

	private Long hos_id;

	private String ds_code;

	private String hip_source_code;
	
	private String hip_source_name;

	private String hrp_source_code;
	
	private String hrp_source_name;

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

	public String getHip_source_code() {
		return hip_source_code;
	}

	public void setHip_source_code(String hip_source_code) {
		this.hip_source_code = hip_source_code;
	}

	public String getHrp_source_code() {
		return hrp_source_code;
	}

	public void setHrp_source_code(String hrp_source_code) {
		this.hrp_source_code = hrp_source_code;
	}

	public String getDs_name() {
		return ds_name;
	}

	public void setDs_name(String ds_name) {
		this.ds_name = ds_name;
	}

	public String getHip_source_name() {
		return hip_source_name;
	}

	public void setHip_source_name(String hip_source_name) {
		this.hip_source_name = hip_source_name;
	}

	public String getHrp_source_name() {
		return hrp_source_name;
	}

	public void setHrp_source_name(String hrp_source_name) {
		this.hrp_source_name = hrp_source_name;
	}

	
}
