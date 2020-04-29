package com.chd.hrp.pac.entity.fkht.payment;

import java.io.Serializable;
import java.util.Date;

public class PactPayMainFKHTEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -469823937019788247L;
	private Integer group_id;
	private Integer hos_id;
	private String copy_code;
	private String pay_code;
	private String pact_code;
	private Date pay_date;
	private Integer sup_id;
	private Integer sup_no;
	private String bill_code;
	private Double pay_money;
	private String note;
	private Integer is_init;
	private Integer maker;
	private Date make_date;
	private Integer checker;
	private Date ckeck_date;
	private Integer confimer;
	private Date confirm_date;
	private String state;

	private String sup_name;
	private String pact_name;
	private String maker_name;
	private String checker_name;
	private String confirmer_name;
	private String state_name;

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

	public Date getPay_date() {
		return pay_date;
	}

	public void setPay_date(Date pay_date) {
		this.pay_date = pay_date;
	}

	public Integer getSup_id() {
		return sup_id;
	}

	public void setSup_id(Integer sup_id) {
		this.sup_id = sup_id;
	}

	public Integer getSup_no() {
		return sup_no;
	}

	public void setSup_no(Integer sup_no) {
		this.sup_no = sup_no;
	}

	public String getBill_code() {
		return bill_code;
	}

	public void setBill_code(String bill_code) {
		this.bill_code = bill_code;
	}

	public Double getPay_money() {
		return pay_money;
	}

	public void setPay_money(Double pay_money) {
		this.pay_money = pay_money;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getIs_init() {
		return is_init;
	}

	public void setIs_init(Integer is_init) {
		this.is_init = is_init;
	}

	public Integer getMaker() {
		return maker;
	}

	public void setMaker(Integer maker) {
		this.maker = maker;
	}

	public Date getMake_date() {
		return make_date;
	}

	public void setMake_date(Date make_date) {
		this.make_date = make_date;
	}

	public Integer getChecker() {
		return checker;
	}

	public void setChecker(Integer checker) {
		this.checker = checker;
	}

	public Date getCkeck_date() {
		return ckeck_date;
	}

	public void setCkeck_date(Date ckeck_date) {
		this.ckeck_date = ckeck_date;
	}

	public Integer getConfimer() {
		return confimer;
	}

	public void setConfimer(Integer confimer) {
		this.confimer = confimer;
	}

	public Date getConfirm_date() {
		return confirm_date;
	}

	public void setConfirm_date(Date confirm_date) {
		this.confirm_date = confirm_date;
	}

	public String getSup_name() {
		return sup_name;
	}

	public void setSup_name(String sup_name) {
		this.sup_name = sup_name;
	}

	public String getPact_name() {
		return pact_name;
	}

	public void setPact_name(String pact_name) {
		this.pact_name = pact_name;
	}

	public String getMaker_name() {
		return maker_name;
	}

	public void setMaker_name(String maker_name) {
		this.maker_name = maker_name;
	}

	public String getChecker_name() {
		return checker_name;
	}

	public void setChecker_name(String checker_name) {
		this.checker_name = checker_name;
	}

	public String getConfirmer_name() {
		return confirmer_name;
	}

	public void setConfirmer_name(String confirmer_name) {
		this.confirmer_name = confirmer_name;
	}

	public String getState_name() {
		return state_name;
	}

	public void setState_name(String state_name) {
		this.state_name = state_name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
