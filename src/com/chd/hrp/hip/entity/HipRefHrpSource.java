package com.chd.hrp.hip.entity;

import java.io.Serializable;

public class HipRefHrpSource implements Serializable {

	private static final long serialVersionUID = 1L;

	private String mod_code;

	private String hip_view_code;

	private String hrp_view_code;

	private String hrp_view_name;

	private String dfd_flag;

	private String mod_name;

	private String hip_view_name;

	public String getMod_code() {
		return mod_code;
	}

	public void setMod_code(String mod_code) {
		this.mod_code = mod_code;
	}

	public String getHip_view_code() {
		return hip_view_code;
	}

	public void setHip_view_code(String hip_view_code) {
		this.hip_view_code = hip_view_code;
	}

	public String getHrp_view_code() {
		return hrp_view_code;
	}

	public void setHrp_view_code(String hrp_view_code) {
		this.hrp_view_code = hrp_view_code;
	}

	public String getHrp_view_name() {
		return hrp_view_name;
	}

	public void setHrp_view_name(String hrp_view_name) {
		this.hrp_view_name = hrp_view_name;
	}

	public String getDfd_flag() {
		return dfd_flag;
	}

	public void setDfd_flag(String dfd_flag) {
		this.dfd_flag = dfd_flag;
	}

	public String getMod_name() {
		return mod_name;
	}

	public void setMod_name(String mod_name) {
		this.mod_name = mod_name;
	}

	public String getHip_view_name() {
		return hip_view_name;
	}

	public void setHip_view_name(String hip_view_name) {
		this.hip_view_name = hip_view_name;
	}

}
