package com.chd.hrp.hip.entity;

import java.io.Serializable;

public class HipInitView implements Serializable {

	private static final long serialVersionUID = 1L;

	private String mod_code;
	
	private String mod_name;

	private String hip_view_code;

	private String hip_view_name;

	private String ref_tab_name;

	private Integer view_level;

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

	public String getHip_view_name() {
		return hip_view_name;
	}

	public void setHip_view_name(String hip_view_name) {
		this.hip_view_name = hip_view_name;
	}

	public String getRef_tab_name() {
		return ref_tab_name;
	}

	public void setRef_tab_name(String ref_tab_name) {
		this.ref_tab_name = ref_tab_name;
	}

	public Integer getView_level() {
		return view_level;
	}

	public void setView_level(Integer view_level) {
		this.view_level = view_level;
	}

	public String getMod_name() {
		return mod_name;
	}

	public void setMod_name(String mod_name) {
		this.mod_name = mod_name;
	}
	

}
