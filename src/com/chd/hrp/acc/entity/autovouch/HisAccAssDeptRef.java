package com.chd.hrp.acc.entity.autovouch;

import java.io.Serializable;

public class HisAccAssDeptRef implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long group_id;

	private Long hos_id;

	private String copy_code;

	private String his_dept_code;

	private String his_dept_name;
	
	private Integer his_dept_flag;
	
	private String hrp_dept_code;
	
	private String hrp_dept_name;
	
	private Long hrp_dept_id;
	
	private Long hrp_dept_no;

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

	public String getHis_dept_code() {
		return his_dept_code;
	}

	public void setHis_dept_code(String his_dept_code) {
		this.his_dept_code = his_dept_code;
	}

	public String getHis_dept_name() {
		return his_dept_name;
	}

	public void setHis_dept_name(String his_dept_name) {
		this.his_dept_name = his_dept_name;
	}

	public Integer getHis_dept_flag() {
		return his_dept_flag;
	}

	public void setHis_dept_flag(Integer his_dept_flag) {
		this.his_dept_flag = his_dept_flag;
	}

	public String getHrp_dept_code() {
		return hrp_dept_code;
	}

	public void setHrp_dept_code(String hrp_dept_code) {
		this.hrp_dept_code = hrp_dept_code;
	}

	public String getHrp_dept_name() {
		return hrp_dept_name;
	}

	public void setHrp_dept_name(String hrp_dept_name) {
		this.hrp_dept_name = hrp_dept_name;
	}

	public Long getHrp_dept_id() {
		return hrp_dept_id;
	}

	public void setHrp_dept_id(Long hrp_dept_id) {
		this.hrp_dept_id = hrp_dept_id;
	}

	public Long getHrp_dept_no() {
		return hrp_dept_no;
	}

	public void setHrp_dept_no(Long hrp_dept_no) {
		this.hrp_dept_no = hrp_dept_no;
	}
	
}
