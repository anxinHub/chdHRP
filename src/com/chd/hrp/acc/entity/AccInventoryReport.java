package com.chd.hrp.acc.entity;

import java.io.Serializable;

/**
 * @author lenovo
 *
 */
public class AccInventoryReport implements Serializable{
	
	private static final long serialVersionUID = 5454155825314635342L;
	/**
	 * 科目编码
	 */
	private String subj_code;
	/**
	 * 科目名称
	 */
	private String subj_name;
	/**
	 * 昨日库存
	 */
	private double pre_bal;
	/**
	 * 贷方金额
	 */
	private double credit;
	/**
	 * 借方金额
	 */
	private double debit;
	/**
	 * 本日库存
	 */
	private double cur_bal;
	/**
	 * 上月库存
	 */
	private double preMonth_bal;
	/**
	 * 本月库存
	 */
	private double curMonth_bal;
	public String getSubj_code() {
		return subj_code;
	}
	public void setSubj_code(String subj_code) {
		this.subj_code = subj_code;
	}
	public String getSubj_name() {
		return subj_name;
	}
	public void setSubj_name(String subj_name) {
		this.subj_name = subj_name;
	}
	public double getPre_bal() {
		return pre_bal;
	}
	public void setPre_bal(double pre_bal) {
		this.pre_bal = pre_bal;
	}
	public double getCredit() {
		return credit;
	}
	public void setCredit(double credit) {
		this.credit = credit;
	}
	public double getDebit() {
		return debit;
	}
	public void setDebit(double debit) {
		this.debit = debit;
	}
	public double getCur_bal() {
		return cur_bal;
	}
	public void setCur_bal(double cur_bal) {
		this.cur_bal = cur_bal;
	}
	public double getPreMonth_bal() {
		return preMonth_bal;
	}
	public void setPreMonth_bal(double preMonth_bal) {
		this.preMonth_bal = preMonth_bal;
	}
	public double getCurMonth_bal() {
		return curMonth_bal;
	}
	public void setCurMonth_bal(double curMonth_bal) {
		this.curMonth_bal = curMonth_bal;
	}
	
}
