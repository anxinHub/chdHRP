package com.chd.hrp.acc.entity.autovouch;

import java.io.Serializable;

public class HisAccMedStoreRef implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long group_id;

	private Long hos_id;

	private String copy_code;

	private String his_store_code;

	private String his_store_name;
	
	private Integer his_store_flag;
	
	private String hrp_store_code;
	
	private String hrp_store_name;
	
	private Long hrp_store_id;
	
	private Long hrp_store_no;

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

	public String getHis_store_code() {
		return his_store_code;
	}

	public void setHis_store_code(String his_store_code) {
		this.his_store_code = his_store_code;
	}

	public String getHis_store_name() {
		return his_store_name;
	}

	public void setHis_store_name(String his_store_name) {
		this.his_store_name = his_store_name;
	}

	public Integer getHis_store_flag() {
		return his_store_flag;
	}

	public void setHis_store_flag(Integer his_store_flag) {
		this.his_store_flag = his_store_flag;
	}

	public String getHrp_store_code() {
		return hrp_store_code;
	}

	public void setHrp_store_code(String hrp_store_code) {
		this.hrp_store_code = hrp_store_code;
	}

	public String getHrp_store_name() {
		return hrp_store_name;
	}

	public void setHrp_store_name(String hrp_store_name) {
		this.hrp_store_name = hrp_store_name;
	}

	public Long getHrp_store_id() {
		return hrp_store_id;
	}

	public void setHrp_store_id(Long hrp_store_id) {
		this.hrp_store_id = hrp_store_id;
	}

	public Long getHrp_store_no() {
		return hrp_store_no;
	}

	public void setHrp_store_no(Long hrp_store_no) {
		this.hrp_store_no = hrp_store_no;
	}

	
}
