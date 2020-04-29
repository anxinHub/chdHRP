package com.chd.hrp.hip.entity;

import java.io.Serializable;

public class HipMatInvRef implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long group_id;

	private Long hos_id;
	
	private String copy_code;

	private String ds_code;

	private String hip_inv_code;
	
	private String hip_inv_name;

	private String hrp_inv_code;
	
	private String hrp_inv_name;

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

	public String getCopy_code() {
		return copy_code;
	}

	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}

	public String getDs_code() {
		return ds_code;
	}

	public void setDs_code(String ds_code) {
		this.ds_code = ds_code;
	}

	public String getHip_inv_code() {
		return hip_inv_code;
	}

	public void setHip_inv_code(String hip_inv_code) {
		this.hip_inv_code = hip_inv_code;
	}

	public String getHrp_inv_code() {
		return hrp_inv_code;
	}

	public void setHrp_inv_code(String hrp_inv_code) {
		this.hrp_inv_code = hrp_inv_code;
	}

	public String getDs_name() {
		return ds_name;
	}

	public void setDs_name(String ds_name) {
		this.ds_name = ds_name;
	}

	public String getHip_inv_name() {
		return hip_inv_name;
	}

	public void setHip_inv_name(String hip_inv_name) {
		this.hip_inv_name = hip_inv_name;
	}

	public String getHrp_inv_name() {
		return hrp_inv_name;
	}

	public void setHrp_inv_name(String hrp_inv_name) {
		this.hrp_inv_name = hrp_inv_name;
	}

	
}
