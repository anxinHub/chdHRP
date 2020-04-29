package com.chd.hrp.hr.entity.training.exam;

import java.util.Date;

public class HrTrainCert {

	private Long group_id;
	
	private Long hos_id;
	
	private Long plan_id;
	
	private Date cert_date;
	
	private String cert_date_str;
	
	private String cert_name;
	
	private String cert_unit;

	public String getCert_date_str() {
		return cert_date_str;
	}

	public void setCert_date_str(String cert_date_str) {
		this.cert_date_str = cert_date_str;
	}

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

	public Long getPlan_id() {
		return plan_id;
	}

	public void setPlan_id(Long plan_id) {
		this.plan_id = plan_id;
	}

	public Date getCert_date() {
		return cert_date;
	}

	public void setCert_date(Date cert_date) {
		this.cert_date = cert_date;
	}

	public String getCert_name() {
		return cert_name;
	}

	public void setCert_name(String cert_name) {
		this.cert_name = cert_name;
	}

	public String getCert_unit() {
		return cert_unit;
	}

	public void setCert_unit(String cert_unit) {
		this.cert_unit = cert_unit;
	}
	
}
