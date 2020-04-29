package com.chd.hrp.hpm.entity;

import java.io.Serializable;

public class AphiAppMod implements Serializable{
   
	/**
	 * 应用模式表
	 * */
	private static final long serialVersionUID = 1L;
	
	private Long group_id;

	private Long hos_id;
	
	
	
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
	
	private String copy_code;
	


	private String app_mod_code;
	
	private String app_mod_name;
	
	private boolean is_stop;
	
	private String error_type;

	

	

	public String getCopy_code() {
		return copy_code;
	}

	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}

	public String getApp_mod_code() {
		return app_mod_code;
	}

	public void setApp_mod_code(String app_mod_code) {
		this.app_mod_code = app_mod_code;
	}

	public String getApp_mod_name() {
		return app_mod_name;
	}

	public void setApp_mod_name(String app_mod_name) {
		this.app_mod_name = app_mod_name;
	}

	public boolean isIs_stop() {
		return is_stop;
	}

	public void setIs_stop(boolean is_stop) {
		this.is_stop = is_stop;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	
}
