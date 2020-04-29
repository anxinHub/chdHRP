package com.chd.hrp.acc.entity;

import java.io.Serializable;
import java.util.Date;

public class AccMatchOut implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6300938645510200577L;
	private Long out_id;
	private Long group_id;
	private Long hos_id;
	private String copy_code;
	private String acc_year;
	private Long proj_id;
	private Long proj_no;
	private String proj_code;
	private String proj_name;
	private Date occur_date;
	private String summary;
	private Long subj_id;
	private String subj_name;
	private Long debit;
	private String business_no;
	private Long vouch_id;
	private Long vouch_check_id;
	private String note;
	private String create_user;
	private Date create_date;
	private String vouch_no;
	private String is_import;
	private String subj_code;
	
	
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
	public Long getOut_id() {
		return out_id;
	}
	public void setOut_id(Long out_id) {
		this.out_id = out_id;
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
	public Long getProj_id() {
		return proj_id;
	}
	public void setProj_id(Long proj_id) {
		this.proj_id = proj_id;
	}
	public Long getProj_no() {
		return proj_no;
	}
	public void setProj_no(Long proj_no) {
		this.proj_no = proj_no;
	}

	public Date getOccur_date() {
		return occur_date;
	}
	public void setOccur_date(Date occur_date) {
		this.occur_date = occur_date;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Long getSubj_id() {
		return subj_id;
	}
	public void setSubj_id(Long subj_id) {
		this.subj_id = subj_id;
	}
	public Long getDebit() {
		return debit;
	}
	public void setDebit(Long debit) {
		this.debit = debit;
	}
	public String getBusiness_no() {
		return business_no;
	}
	public void setBusiness_no(String business_no) {
		this.business_no = business_no;
	}
	public Long getVouch_id() {
		return vouch_id;
	}
	public void setVouch_id(Long vouch_id) {
		this.vouch_id = vouch_id;
	}
	public Long getVouch_check_id() {
		return vouch_check_id;
	}
	public void setVouch_check_id(Long vouch_check_id) {
		this.vouch_check_id = vouch_check_id;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}

	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public String getVouch_no() {
		return vouch_no;
	}
	public void setVouch_no(String vouch_no) {
		this.vouch_no = vouch_no;
	}
	public String getIs_import() {
		return is_import;
	}
	public void setIs_import(String is_import) {
		this.is_import = is_import;
	}
	
}
