package com.chd.hrp.hip.entity;

import java.io.Serializable;

public class HipPaymentItemRef implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long group_id;

	private Long hos_id;
	
	private String copy_code;

	private String ds_code;

	private String hip_payment_item_code;
	
	private String hip_payment_item_name;

	private String hrp_payment_item_code;
	
	private String hrp_payment_item_name;

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

	public String getHip_payment_item_code() {
		return hip_payment_item_code;
	}

	public void setHip_payment_item_code(String hip_payment_item_code) {
		this.hip_payment_item_code = hip_payment_item_code;
	}

	public String getHrp_payment_item_code() {
		return hrp_payment_item_code;
	}

	public void setHrp_payment_item_code(String hrp_payment_item_code) {
		this.hrp_payment_item_code = hrp_payment_item_code;
	}

	public String getDs_name() {
		return ds_name;
	}

	public void setDs_name(String ds_name) {
		this.ds_name = ds_name;
	}

	public String getHip_payment_item_name() {
		return hip_payment_item_name;
	}

	public void setHip_payment_item_name(String hip_payment_item_name) {
		this.hip_payment_item_name = hip_payment_item_name;
	}

	public String getHrp_payment_item_name() {
		return hrp_payment_item_name;
	}

	public void setHrp_payment_item_name(String hrp_payment_item_name) {
		this.hrp_payment_item_name = hrp_payment_item_name;
	}
	

}
