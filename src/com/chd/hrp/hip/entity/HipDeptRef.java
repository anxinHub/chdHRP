package com.chd.hrp.hip.entity;

import java.io.Serializable;

public class HipDeptRef implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long group_id;

	private Long hos_id;

	private String ds_code;

	private String hip_dept_code;
	
	private String hip_dept_name;

	private String hrp_dept_code;
	
	private String hrp_dept_name;

	private String ds_name;

	private Integer doc_type;

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

	public String getHip_dept_code() {
		return hip_dept_code;
	}

	public void setHip_dept_code(String hip_dept_code) {
		this.hip_dept_code = hip_dept_code;
	}

	public String getHrp_dept_code() {
		return hrp_dept_code;
	}

	public void setHrp_dept_code(String hrp_dept_code) {
		this.hrp_dept_code = hrp_dept_code;
	}

	public String getDs_name() {
		return ds_name;
	}

	public void setDs_name(String ds_name) {
		this.ds_name = ds_name;
	}

	public String getHip_dept_name() {
		return hip_dept_name;
	}

	public void setHip_dept_name(String hip_dept_name) {
		this.hip_dept_name = hip_dept_name;
	}

	public String getHrp_dept_name() {
		return hrp_dept_name;
	}

	public void setHrp_dept_name(String hrp_dept_name) {
		this.hrp_dept_name = hrp_dept_name;
	}

	public Integer getDoc_type() {
		return doc_type;
	}

	public void setDoc_type(Integer doc_type) {
		this.doc_type = doc_type;
	}

}
