package com.chd.hrp.pac.entity.skht.payment;

import java.io.Serializable;

public class PactRecPlanSKHTEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3204082153488920328L;

	private Integer group_id;
	private Integer hos_id;
	private String copy_code;
	private String rec_code;
	private Integer plan_detail_id;
	private Double rec_money;
	private String pact_code;

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

	public String getRec_code() {
		return rec_code;
	}

	public void setRec_code(String rec_code) {
		this.rec_code = rec_code;
	}

	public Integer getPlan_detail_id() {
		return plan_detail_id;
	}

	public void setPlan_detail_id(Integer plan_detail_id) {
		this.plan_detail_id = plan_detail_id;
	}

	public Double getRec_money() {
		return rec_money;
	}

	public void setRec_money(Double rec_money) {
		this.rec_money = rec_money;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPact_code() {
		return pact_code;
	}

	public void setPact_code(String pact_code) {
		this.pact_code = pact_code;
	}

}
