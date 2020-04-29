package com.chd.hrp.acc.entity;

import java.io.Serializable;

public class AccProjAuxiliaryAccount implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2197579063177480420L;

	private Long group_id;

	private Long hos_id;
	
	private String copy_code;
	
	private String acc_year;
	
	private String acc_month;
	
	private String proj_code;
	
	private String proj_name;
	
	private String subj_code;
	
	private String subj_name;
	
	private String summary;
	
	private Double debit;
	
	private Double credit;
	
	private String subj_dire;
	
	private Double end_os;
	
	private Double end_price;
	
	private String vouch_date;
	
	private String vouch_no;
	
	private Long vouch_id;

	public Double getEnd_price() {
		return end_price;
	}

	public void setEnd_price(Double end_price) {
		this.end_price = end_price;
	}

	public String getVouch_date() {
		return vouch_date;
	}

	public void setVouch_date(String vouch_date) {
		this.vouch_date = vouch_date;
	}

	public String getVouch_no() {
		return vouch_no;
	}

	public void setVouch_no(String vouch_no) {
		this.vouch_no = vouch_no;
	}

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

	public String getAcc_year() {
		return acc_year;
	}

	public void setAcc_year(String acc_year) {
		this.acc_year = acc_year;
	}

	public String getAcc_month() {
		return acc_month;
	}

	public void setAcc_month(String acc_month) {
		this.acc_month = acc_month;
	}

	public String getProj_code() {
		return proj_code;
	}

	public void setProj_code(String proj_code) {
		this.proj_code = proj_code;
	}

	public String getProj_name() {
		return proj_name;
	}

	public void setProj_name(String proj_name) {
		this.proj_name = proj_name;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
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

	public String getSubj_dire() {
		return subj_dire;
	}

	public void setSubj_dire(String subj_dire) {
		this.subj_dire = subj_dire;
	}

	public Double getEnd_os() {
		return end_os;
	}

	public void setEnd_os(Double end_os) {
		this.end_os = end_os;
	}

	public Long getVouch_id() {
		return vouch_id;
	}

	public void setVouch_id(Long vouch_id) {
		this.vouch_id = vouch_id;
	}


}
