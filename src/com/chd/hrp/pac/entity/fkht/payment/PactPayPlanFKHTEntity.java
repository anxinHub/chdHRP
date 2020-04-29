package com.chd.hrp.pac.entity.fkht.payment;

import java.io.Serializable;

public class PactPayPlanFKHTEntity implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8986508610587245334L;
	private Integer group_id;
	private Integer hos_id;
	private String copy_code;
	private String pay_code;
	private String pact_code;
	private Integer plan_det_id;
	private Double pay_money;

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

	public String getPay_code() {
		return pay_code;
	}

	public void setPay_code(String pay_code) {
		this.pay_code = pay_code;
	}

	public String getPact_code() {
		return pact_code;
	}

	public void setPact_code(String pact_code) {
		this.pact_code = pact_code;
	}

	public Integer getPlan_det_id() {
		return plan_det_id;
	}

	public void setPlan_det_id(Integer plan_det_id) {
		this.plan_det_id = plan_det_id;
	}

	public Double getPay_money() {
		return pay_money;
	}

	public void setPay_money(Double pay_money) {
		this.pay_money = pay_money;
	}

}
