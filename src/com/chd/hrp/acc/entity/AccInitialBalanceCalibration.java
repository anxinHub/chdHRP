package com.chd.hrp.acc.entity;

import java.io.Serializable;

public class AccInitialBalanceCalibration implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String subj_code;
	/**
	 * 单位银行账项目名称
	 */
	private String unit_name;
	/**
	 * 银行对账单项目名称
	 */
	private String bank_name;
	/**
	 * 单位银行账项目金额
	 */
	private double debit;
	/**
	 * 银行对账单项目金额
	 */
	private double credit;

	public String getUnit_name() {
		return unit_name;
	}

	public void setUnit_name(String unit_name) {
		this.unit_name = unit_name;
	}

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	public double getDebit() {
		return debit;
	}

	public void setDebit(double debit) {
		this.debit = debit;
	}

	public double getCredit() {
		return credit;
	}

	public void setCredit(double credit) {
		this.credit = credit;
	}

	public String getSubj_code() {
		return subj_code;
	}

	public void setSubj_code(String subj_code) {
		this.subj_code = subj_code;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
