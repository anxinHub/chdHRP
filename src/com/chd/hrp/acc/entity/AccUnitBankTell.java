package com.chd.hrp.acc.entity;

import java.io.Serializable;

public class AccUnitBankTell implements Serializable{
	
	private static final long serialVersionUID = 5454155825314635342L;
	/**
	 * 凭证日期
	*/
	private String vouch_date;
	
	/**
	 * 凭证编号
	*/
	private String vouch_no;
	
	/**
	 * 摘要
	*/
	private String summary;
	
	/**
	 * 结算方式
	*/
	private String pay_name;
	
	/**
	 * 票据号
	*/
	private String check_no;
	
	/**
	 * 借方金额
	*/
	private Double debit;
	
	/**
	 * 贷方金额
	*/
	private Double credit;
	
	/**
	 * 余额
	*/
	private Double bal;
	
	/**
	 * 对账人
	*/
	private String create_user;
	
	/**
	 *是否对账 
	 * */
	private String is_check;
	
	public String getvouch_date() {
		return vouch_date;
	}

	public void setvouch_date(String vouch_date) {
		this.vouch_date = vouch_date;
	}

	public String getVouch_no() {
		return vouch_no;
	}

	public void setVouch_no(String vouch_no) {
		this.vouch_no = vouch_no;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getPay_name() {
		return pay_name;
	}

	public void setPay_name(String pay_name) {
		this.pay_name = pay_name;
	}

	public String getCheck_no() {
		return check_no;
	}

	public void setCheck_no(String check_no) {
		this.check_no = check_no;
	}

	public Double getDebit() {
		return debit;
	}

	public void setDebit(Double debit) {
		this.debit = debit;
	}

	public Double getCredit() {
		return credit;
	}

	public void setCredit(Double credit) {
		this.credit = credit;
	}

	public Double getBal() {
		return bal;
	}

	public void setBal(Double bal) {
		this.bal = bal;
	}

	public String getCreate_user() {
		return create_user;
	}

	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	
	public String getis_check() {
		return is_check;
	}

	public void setis_check(String is_check) {
		this.is_check = is_check;
	}

	
}
