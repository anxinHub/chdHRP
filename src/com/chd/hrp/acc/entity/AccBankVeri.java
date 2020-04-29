package com.chd.hrp.acc.entity;

import java.io.Serializable;
import java.util.Date;

public class AccBankVeri implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7312665414602403981L;
	private Long veri_id;
	private Long group_id;
	private Long hos_id;
	private String copy_code;
	private String acc_year;
	private Long bank_id;
	private Long tell_id;
	private Long vouch_check_id;
	private Long vouch_id;
	private Long veri_money;
	private String create_user;
	private Date create_date;
	private String is_auto;
	public Long getVeri_id() {
		return veri_id;
	}
	public void setVeri_id(Long veri_id) {
		this.veri_id = veri_id;
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
	public Long getBank_id() {
		return bank_id;
	}
	public void setBank_id(Long bank_id) {
		this.bank_id = bank_id;
	}
	public Long getTell_id() {
		return tell_id;
	}
	public void setTell_id(Long tell_id) {
		this.tell_id = tell_id;
	}
	public Long getVouch_check_id() {
		return vouch_check_id;
	}
	public void setVouch_check_id(Long vouch_check_id) {
		this.vouch_check_id = vouch_check_id;
	}
	public Long getVouch_id() {
		return vouch_id;
	}
	public void setVouch_id(Long vouch_id) {
		this.vouch_id = vouch_id;
	}
	public Long getVeri_money() {
		return veri_money;
	}
	public void setVeri_money(Long veri_money) {
		this.veri_money = veri_money;
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
	public String getIs_auto() {
		return is_auto;
	}
	public void setIs_auto(String is_auto) {
		this.is_auto = is_auto;
	}
	
}
