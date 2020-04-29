package com.chd.hrp.hip.entity;

import java.io.Serializable;

public class HipPayTypeRef implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long group_id;

	private Long hos_id;
	
	private String copy_code;

	private String ds_code;

	private String hip_pay_type_code;
	
	private String hip_pay_type_name;

	private String hrp_pay_type_code;
	
	private String hrp_pay_type_name;

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

	public String getHip_pay_type_code() {
		return hip_pay_type_code;
	}

	public void setHip_pay_type_code(String hip_pay_type_code) {
		this.hip_pay_type_code = hip_pay_type_code;
	}

	public String getHrp_pay_type_code() {
		return hrp_pay_type_code;
	}

	public void setHrp_pay_type_code(String hrp_pay_type_code) {
		this.hrp_pay_type_code = hrp_pay_type_code;
	}

	public String getDs_name() {
		return ds_name;
	}

	public void setDs_name(String ds_name) {
		this.ds_name = ds_name;
	}

	public String getHip_pay_type_name() {
		return hip_pay_type_name;
	}

	public void setHip_pay_type_name(String hip_pay_type_name) {
		this.hip_pay_type_name = hip_pay_type_name;
	}

	public String getHrp_pay_type_name() {
		return hrp_pay_type_name;
	}

	public void setHrp_pay_type_name(String hrp_pay_type_name) {
		this.hrp_pay_type_name = hrp_pay_type_name;
	}

	
}
