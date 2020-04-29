package com.chd.hrp.htcg.entity.setout;

import java.io.Serializable;

public class HtcgSchemeMrSampleData implements Serializable {
	private static final long serialVersionUID = -1829681576558490910L;
	private long group_id;
	private long hos_id;
	private String copy_code;
	private String period_type_code;
	private String period_type_name;
	private String period_code;
	private String period_name;
	private String acc_year;
	private String acc_month;
	private String scheme_code;
	private String scheme_name;
	private String drgs_code;
	private String drgs_name;
	private String mr_no;
	private String in_hos_no;
	private Integer extract_amount;
	private Integer sample_amount;
	private String mr_rule_code;
	private String mr_rule_name;
	public long getGroup_id() {
		return group_id;
	}
	public long getHos_id() {
		return hos_id;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public String getPeriod_type_code() {
		return period_type_code;
	}
	public String getPeriod_type_name() {
		return period_type_name;
	}
	public String getPeriod_code() {
		return period_code;
	}
	public String getPeriod_name() {
		return period_name;
	}
	public String getAcc_year() {
		return acc_year;
	}
	public String getAcc_month() {
		return acc_month;
	}
	public String getScheme_code() {
		return scheme_code;
	}
	public String getScheme_name() {
		return scheme_name;
	}
	public String getDrgs_code() {
		return drgs_code;
	}
	public String getDrgs_name() {
		return drgs_name;
	}
	public String getMr_no() {
		return mr_no;
	}
	public String getIn_hos_no() {
		return in_hos_no;
	}
	public Integer getExtract_amount() {
		return extract_amount;
	}
	public Integer getSample_amount() {
		return sample_amount;
	}
	public String getMr_rule_code() {
		return mr_rule_code;
	}
	public String getMr_rule_name() {
		return mr_rule_name;
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
	public void setPeriod_type_code(String period_type_code) {
		this.period_type_code = period_type_code;
	}
	public void setPeriod_type_name(String period_type_name) {
		this.period_type_name = period_type_name;
	}
	public void setPeriod_code(String period_code) {
		this.period_code = period_code;
	}
	public void setPeriod_name(String period_name) {
		this.period_name = period_name;
	}
	public void setAcc_year(String acc_year) {
		this.acc_year = acc_year;
	}
	public void setAcc_month(String acc_month) {
		this.acc_month = acc_month;
	}
	public void setScheme_code(String scheme_code) {
		this.scheme_code = scheme_code;
	}
	public void setScheme_name(String scheme_name) {
		this.scheme_name = scheme_name;
	}
	public void setDrgs_code(String drgs_code) {
		this.drgs_code = drgs_code;
	}
	public void setDrgs_name(String drgs_name) {
		this.drgs_name = drgs_name;
	}
	public void setMr_no(String mr_no) {
		this.mr_no = mr_no;
	}
	public void setIn_hos_no(String in_hos_no) {
		this.in_hos_no = in_hos_no;
	}
	public void setExtract_amount(Integer extract_amount) {
		this.extract_amount = extract_amount;
	}
	public void setSample_amount(Integer sample_amount) {
		this.sample_amount = sample_amount;
	}
	public void setMr_rule_code(String mr_rule_code) {
		this.mr_rule_code = mr_rule_code;
	}
	public void setMr_rule_name(String mr_rule_name) {
		this.mr_rule_name = mr_rule_name;
	}
	
}
