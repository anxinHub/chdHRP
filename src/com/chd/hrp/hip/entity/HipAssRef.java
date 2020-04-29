package com.chd.hrp.hip.entity;

import java.io.Serializable;

public class HipAssRef implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long group_id;

	private Long hos_id;
	
	private String copy_code;

	private String ds_code;

	private String hip_ass_code;
	
	private String hip_ass_name;

	private String hrp_ass_code;
	
	private String hrp_ass_name;

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

	public String getHip_ass_code() {
		return hip_ass_code;
	}

	public void setHip_ass_code(String hip_ass_code) {
		this.hip_ass_code = hip_ass_code;
	}

	public String getHrp_ass_code() {
		return hrp_ass_code;
	}

	public void setHrp_ass_code(String hrp_ass_code) {
		this.hrp_ass_code = hrp_ass_code;
	}

	public String getDs_name() {
		return ds_name;
	}

	public void setDs_name(String ds_name) {
		this.ds_name = ds_name;
	}

	public String getHip_ass_name() {
		return hip_ass_name;
	}

	public void setHip_ass_name(String hip_ass_name) {
		this.hip_ass_name = hip_ass_name;
	}

	public String getHrp_ass_name() {
		return hrp_ass_name;
	}

	public void setHrp_ass_name(String hrp_ass_name) {
		this.hrp_ass_name = hrp_ass_name;
	}

	
}
