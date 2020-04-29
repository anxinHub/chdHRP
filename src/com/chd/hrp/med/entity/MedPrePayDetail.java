package com.chd.hrp.med.entity;

import java.io.Serializable;

public class MedPrePayDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long group_id ;
	
	private Long hos_id ;
	
	private String copy_code ;
	
	private Long pre_pay_id ;
	
	private String pre_pay_no ;
	
	private Long pre_detail_id ;
	
	private Long in_id  ;
	
	private Long in_detail_id ;
	
	private Double payable_money ;
	
	private Double pre_pay_money ;
	
	 /**
	 * 导入验证信息
	 */
	private String error_type;

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

	public Long getPre_pay_id() {
		return pre_pay_id;
	}

	public void setPre_pay_id(Long pre_pay_id) {
		this.pre_pay_id = pre_pay_id;
	}

	public String getPre_pay_no() {
		return pre_pay_no;
	}

	public void setPre_pay_no(String pre_pay_no) {
		this.pre_pay_no = pre_pay_no;
	}

	public Long getPre_detail_id() {
		return pre_detail_id;
	}

	public void setPre_detail_id(Long pre_detail_id) {
		this.pre_detail_id = pre_detail_id;
	}

	public Long getIn_id() {
		return in_id;
	}

	public void setIn_id(Long in_id) {
		this.in_id = in_id;
	}

	public Long getIn_detail_id() {
		return in_detail_id;
	}

	public void setIn_detail_id(Long in_detail_id) {
		this.in_detail_id = in_detail_id;
	}

	public Double getPayable_money() {
		return payable_money;
	}

	public void setPayable_money(Double payable_money) {
		this.payable_money = payable_money;
	}

	public Double getPre_pay_money() {
		return pre_pay_money;
	}

	public void setPre_pay_money(Double pre_pay_money) {
		this.pre_pay_money = pre_pay_money;
	}

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	
	
}
