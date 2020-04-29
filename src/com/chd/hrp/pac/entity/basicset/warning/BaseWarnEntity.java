package com.chd.hrp.pac.entity.basicset.warning;

import java.io.Serializable;

public class BaseWarnEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3583694036225740908L;
	private Integer group_id;
	private Integer hos_id;
	private String copy_code;
	private String pact_type_code;
	private Integer pact_end_w;
	private String type_name;
	private Float pact_exe_w;
	
	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getGroup_id() {
		return group_id;
	}

	public void setGroup_id(Integer group_id) {
		this.group_id = group_id;
	}

	public Integer getHos_id() {
		return hos_id;
	}

	public void setHos_id(Integer hos_id) {
		this.hos_id = hos_id;
	}

	public String getCopy_code() {
		return copy_code;
	}

	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}

	public Integer getPact_end_w() {
		return pact_end_w;
	}

	public void setPact_end_w(Integer pact_end_w) {
		this.pact_end_w = pact_end_w;
	}

	public String getPact_type_code() {
		return pact_type_code;
	}

	public void setPact_type_code(String pact_type_code) {
		this.pact_type_code = pact_type_code;
	}
	
	public Float getPact_exe_w() {
		return pact_exe_w;
	}
	public void setPact_exe_w(Float pact_exe_w){
		this.pact_exe_w = pact_exe_w;
	}
	
}
