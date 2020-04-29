package com.chd.hrp.acc.entity.superVouch;

import java.io.Serializable;
import java.util.Date;

public class SuperVouchMain implements Serializable{

	private static final long serialVersionUID = 6325391966301541803L;
	
	private String group_id;
	private String hos_id;
	private String copy_code;
	/**
	 * 凭证ID
	 */
	private String vouch_id;
	/**
	 * 会计年度
	 */
	private String acc_year;
	/**
	 * 会计期间
	 */
	private String acc_month;
	
	/**
	 * 凭证类型编码
	 */
	private String vouch_type_code;
	
	/**
	 * 凭证类型名称
	 */
	private String vouch_type_name;
	/**
	 * 凭证编号
	 */
	private String vouch_no;
	
	/**
	 * 凭证日期
	 */
	private String vouch_date;
	/**
	 * 附件数量
	 */
	private int vouch_att_num;
	/**
	 * 被冲销凭证ID
	 */
	private long vouch_id_cx;
	/**
	 * 制单人ID
	 */
	private long create_user;
	/**
	 * 制单人
	 */
	private String create_name;
	/**
	 * 制单时间
	 */
	private Date create_date;
	/**
	 * 出纳签字人id
	 */
	private long cash_user;
	/**
	 * 出纳签字人
	 */
	private String cash_name;
	/**
	 * 出纳签字时间
	 */
	private Date cashe_date;
	/**
	 * 审核人id
	 */
	private long audit_user;
	/**
	 * 审核人
	 */
	private String audit_name;
	/**
	 * 审核时间
	 */
	private Date audit_date;
	/**
	 * 记账人id
	 */
	private long acc_user;
	/**
	 * 记账人
	 */
	private String acc_name;
	/**
	 * 记账时间
	 */
	private Date acc_date;
	/**
	 * -1草稿，0作废，1新建，2出纳签字，3审核，4记账
	 */
	private String state;
	
	/**
	 * 标注说明
	 */
	private String note;
	
	private String busi_type_code;

	public String getVouch_id() {
		return vouch_id;
	}

	public void setVouch_id(String vouch_id) {
		this.vouch_id = vouch_id;
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

	public String getVouch_no() {
		return vouch_no;
	}

	public void setVouch_no(String vouch_no) {
		this.vouch_no = vouch_no;
	}

	public String getVouch_date() {
		return vouch_date;
	}

	public void setVouch_date(String vouch_date) {
		this.vouch_date = vouch_date;
	}

	public int getVouch_att_num() {
		return vouch_att_num;
	}

	public void setVouch_att_num(int vouch_att_num) {
		this.vouch_att_num = vouch_att_num;
	}

	public long getVouch_id_cx() {
		return vouch_id_cx;
	}

	public void setVouch_id_cx(long vouch_id_cx) {
		this.vouch_id_cx = vouch_id_cx;
	}

	public long getCreate_user() {
		return create_user;
	}

	public void setCreate_user(long create_user) {
		this.create_user = create_user;
	}

	public String getCreate_name() {
		return create_name;
	}

	public void setCreate_name(String create_name) {
		this.create_name = create_name;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public long getCash_user() {
		return cash_user;
	}

	public void setCash_user(long cash_user) {
		this.cash_user = cash_user;
	}

	public String getCash_name() {
		return cash_name;
	}

	public void setCash_name(String cash_name) {
		this.cash_name = cash_name;
	}

	public Date getCashe_date() {
		return cashe_date;
	}

	public void setCashe_date(Date cashe_date) {
		this.cashe_date = cashe_date;
	}

	public long getAudit_user() {
		return audit_user;
	}

	public void setAudit_user(long audit_user) {
		this.audit_user = audit_user;
	}

	public String getAudit_name() {
		return audit_name;
	}

	public void setAudit_name(String audit_name) {
		this.audit_name = audit_name;
	}

	public Date getAudit_date() {
		return audit_date;
	}

	public void setAudit_date(Date audit_date) {
		this.audit_date = audit_date;
	}

	public long getAcc_user() {
		return acc_user;
	}

	public void setAcc_user(long acc_user) {
		this.acc_user = acc_user;
	}

	public String getAcc_name() {
		return acc_name;
	}

	public void setAcc_name(String acc_name) {
		this.acc_name = acc_name;
	}

	public Date getAcc_date() {
		return acc_date;
	}

	public void setAcc_date(Date acc_date) {
		this.acc_date = acc_date;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getBusi_type_code() {
		return busi_type_code;
	}

	public void setBusi_type_code(String busi_type_code) {
		this.busi_type_code = busi_type_code;
	}

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public String getHos_id() {
		return hos_id;
	}

	public void setHos_id(String hos_id) {
		this.hos_id = hos_id;
	}

	public String getCopy_code() {
		return copy_code;
	}

	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}



}
