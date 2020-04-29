package com.chd.hrp.acc.entity;

import java.io.Serializable;
import java.util.Date;

public class AccBankCheckTell implements Serializable{
	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 银行对账单ID
	 */
	private Long bank_id;
	/**
	 * 集团ID
	 */
	private Long group_id;
	/**
	 * 医院ID
	 */
	private Long hos_id;
	/**
	 * 账套编码
	 */
	private String copy_code;
	/**
	 * 会计年度
	 */
	private String acc_year;
	/**
	 * 摘要
	 */
	private String summary;
	/**
	 * 借方金额
	 */
	private double debit;
	/**
	 * 贷方金额
	 */
	private double credit;
	/**
	 * 余额
	 */
	private double bal;
	/**
	 * 票据号
	 */
	private String check_no;
	
	/**
	 * 发生日期
	 */
	private String occur_date;
	
	/**
	 * 是否对账
	 */
	private String is_check;
	
	
	/**
	 * 科目ID
	 */
	private Long subj_id;
	
	/**
	 * 结算方式编码
	 */
	private String pay_code;
	/**
	 * 结算方式名称
	 */
	private String pay_name;
	/**
	 * 科目名称
	 */
	private String subj_name;
	
	/**
	 * 获取 银行对账单ID
	 */
	public Long getBank_id() {
		return bank_id;
	}
	
	/**
	 * 设置 银行对账单ID
	 */
	public void setBank_id(Long bank_id) {
		this.bank_id = bank_id;
	}
	
	/**
	 * 获取 集团ID
	 */
	public Long getGroup_id() {
		return group_id;
	}
	
	/**
	 * 设置 集团ID
	 */
	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}
	
	/**
	 * 获取 医院ID
	 */
	public Long getHos_id() {
		return hos_id;
	}
	
	/**
	 * 设置 医院ID
	 */
	public void setHos_id(Long hos_id) {
		this.hos_id = hos_id;
	}
	
	/**
	 * 获取 账套编码
	 */
	public String getCopy_code() {
		return copy_code;
	}
	
	/**
	 * 设置 账套编码
	 */
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	
	/**
	 * 获取 会计年度
	 */
	public String getAcc_year() {
		return acc_year;
	}
	
	/**
	 * 设置 会计年度
	 */
	public void setAcc_year(String acc_year) {
		this.acc_year = acc_year;
	}
	
	/**
	 * 获取 摘要
	 */
	public String getSummary() {
		return summary;
	}
	
	/**
	 * 设置 摘要
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	/**
	 * 获取 借方金额
	 */
	public double getDebit() {
		return debit;
	}
	
	/**
	 * 设置 借方金额
	 */
	public void setDebit(double debit) {
		this.debit = debit;
	}
	
	/**
	 * 获取 贷方金额
	 */
	public double getCredit() {
		return credit;
	}
	
	/**
	 * 设置 贷方金额
	 */
	public void setCredit(double credit) {
		this.credit = credit;
	}
	
	/**
	 * 获取 余额
	 */
	public double getBal() {
		return bal;
	}
	
	/**
	 * 设置 余额
	 */
	public void setBal(double bal) {
		this.bal = bal;
	}
	
	/**
	 * 获取 票据号
	 */
	public String getCheck_no() {
		return check_no;
	}
	
	/**
	 * 设置 票据号
	 */
	public void setCheck_no(String check_no) {
		this.check_no = check_no;
	}
	
	/**
	 * 获取 发生日期
	 */
	public String getOccur_date() {
		return occur_date;
	}
	
	/**
	 * 设置 发生日期
	 */
	public void setOccur_date(String occur_date) {
		this.occur_date = occur_date;
	}
	
	public String getPay_code() {
		return pay_code;
	}

	public void setPay_code(String pay_code) {
		this.pay_code = pay_code;
	}

	/**
	 * 获取 是否对账
	 */
	public String getIs_check() {
		return is_check;
	}
	
	/**
	 * 设置 是否对账
	 */
	public void setIs_check(String is_check) {
		this.is_check = is_check;
	}
	
	/**
	 * 获取 科目ID
	 */
	public Long getSubj_id() {
		return subj_id;
	}
	
	/**
	 * 设置 科目ID
	 */
	public void setSubj_id(Long subj_id) {
		this.subj_id = subj_id;
	}
	
	/**
	 * 获取 结算方式名称
	 */
	public String getPay_name() {
		return pay_name;
	}
	
	/**
	 * 设置 结算方式名称
	 */
	public void setPay_name(String pay_name) {
		this.pay_name = pay_name;
	}
	
	/**
	 * 获取 科室名称
	 */
	public String getSubj_name() {
		return subj_name;
	}
	
	/**
	 * 设置 科室名称
	 */
	public void setSubj_name(String subj_name) {
		this.subj_name = subj_name;
	}
}
