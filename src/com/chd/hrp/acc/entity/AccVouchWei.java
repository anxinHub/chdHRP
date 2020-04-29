package com.chd.hrp.acc.entity;

import java.io.Serializable;
import java.util.Date;

public class AccVouchWei implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long group_id;
	
	private Long hos_id;
	
	private Long wei_id;
	
	private String copy_code;
	
	private String acc_year;
	
	private String acc_month;
	
	private String vouch_type_code;
	
	private String vouch_type_name;
	
	private String wei_name;
	
	private String vouch_no_begin;
	
	private String vouch_no_end;
	
	private Long create_user;
	
	private Date create_date;
	
	private String note; 
	
	private String subj_code;
	
	private String subj_name;
	
	private double debit;
	
	private double credit;

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

	public Long getWei_id() {
		return wei_id;
	}

	public void setWei_id(Long wei_id) {
		this.wei_id = wei_id;
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

	public String getVouch_type_code() {
		return vouch_type_code;
	}

	public void setVouch_type_code(String vouch_type_code) {
		this.vouch_type_code = vouch_type_code;
	}

	public String getVouch_type_name() {
		return vouch_type_name;
	}

	public void setVouch_type_name(String vouch_type_name) {
		this.vouch_type_name = vouch_type_name;
	}

	public String getWei_name() {
		return wei_name;
	}

	public void setWei_name(String wei_name) {
		this.wei_name = wei_name;
	}

	public String getVouch_no_begin() {
		return vouch_no_begin;
	}

	public void setVouch_no_begin(String vouch_no_begin) {
		this.vouch_no_begin = vouch_no_begin;
	}

	public String getVouch_no_end() {
		return vouch_no_end;
	}

	public void setVouch_no_end(String vouch_no_end) {
		this.vouch_no_end = vouch_no_end;
	}

	public Long getCreate_user() {
		return create_user;
	}

	public void setCreate_user(Long create_user) {
		this.create_user = create_user;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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
	
	

}
