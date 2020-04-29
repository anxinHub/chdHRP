package com.chd.hrp.htcg.entity.audit;

import java.io.Serializable;

public class HtcgSchemeConf implements Serializable {

	private static final long serialVersionUID = 1L;
	private long group_id;
	private long hos_id;
	private String copy_code;
	private String acc_year;
	private String acc_month;
	private long scheme_seq_no;
	private String scheme_note;
	private String period_type_name;
	private String period_type_code;
	private String scheme_code;
	private String scheme_name;
	private String period_code;
	private String period_name;
	public long getGroup_id() {
		return group_id;
	}
	public long getHos_id() {
		return hos_id;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public String getAcc_year() {
		return acc_year;
	}
	public String getAcc_month() {
		return acc_month;
	}
	public long getScheme_seq_no() {
		return scheme_seq_no;
	}
	public String getScheme_note() {
		return scheme_note;
	}
	public String getPeriod_type_name() {
		return period_type_name;
	}
	public String getPeriod_type_code() {
		return period_type_code;
	}
	public String getScheme_code() {
		return scheme_code;
	}
	public String getScheme_name() {
		return scheme_name;
	}
	public String getPeriod_code() {
		return period_code;
	}
	public String getPeriod_name() {
		return period_name;
	}
	public void setGroup_id(long group_id) {
		this.group_id = group_id;
	}
	public void setHos_id(long hos_id) {
		this.hos_id = hos_id;
	}
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	public void setAcc_year(String acc_year) {
		this.acc_year = acc_year;
	}
	public void setAcc_month(String acc_month) {
		this.acc_month = acc_month;
	}
	public void setScheme_seq_no(long scheme_seq_no) {
		this.scheme_seq_no = scheme_seq_no;
	}
	public void setScheme_note(String scheme_note) {
		this.scheme_note = scheme_note;
	}
	public void setPeriod_type_name(String period_type_name) {
		this.period_type_name = period_type_name;
	}
	public void setPeriod_type_code(String period_type_code) {
		this.period_type_code = period_type_code;
	}
	public void setScheme_code(String scheme_code) {
		this.scheme_code = scheme_code;
	}
	public void setScheme_name(String scheme_name) {
		this.scheme_name = scheme_name;
	}
	public void setPeriod_code(String period_code) {
		this.period_code = period_code;
	}
	public void setPeriod_name(String period_name) {
		this.period_name = period_name;
	}
	
	
}
