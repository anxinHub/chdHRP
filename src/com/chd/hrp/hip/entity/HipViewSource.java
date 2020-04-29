package com.chd.hrp.hip.entity;

import java.io.Serializable;

public class HipViewSource implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long group_id;

	private Long hos_id;

	private String copy_code;

	private String ds_code;

	private String mod_code;

	private String hip_view_code;

	private String his_view_code;

	private String his_view_name;

	private Integer is_ref;
	
	private String mod_name;
	
	private String hip_view_name;
	
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

	public String getHis_view_code() {
		return his_view_code;
	}

	public void setHis_view_code(String his_view_code) {
		this.his_view_code = his_view_code;
	}

	public String getHis_view_name() {
		return his_view_name;
	}

	public void setHis_view_name(String his_view_name) {
		this.his_view_name = his_view_name;
	}

	public Integer getIs_ref() {
		return is_ref;
	}

	public void setIs_ref(Integer is_ref) {
		this.is_ref = is_ref;
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

	public String getDs_name() {
		return ds_name;
	}

	public void setDs_name(String ds_name) {
		this.ds_name = ds_name;
	}

}
