package com.chd.hrp.pac.entity.skht.guarantee;

import java.io.Serializable;
import java.util.Date;

public class PactLetterSKHTEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8940579464140773750L;
	private Integer group_id;
	private Integer hos_id;
	private String copy_code;
	private String pact_code;
	private String letter_code;
	private Date sign_date;
	private String bank_code;
	private String bank_man;
	private String bank_tel;
	private String bank_no;
	private String hospital_man;
	private String hospital_tel;
	private Integer cus_id;
	private Integer cus_no;
	private Integer money;
	private Date start_date;
	private Date end_date;
	private String sp_cond;
	private String content;
	private String note;
	private String letter_state;
	private Integer maker;
	private Date make_date;
	private Integer checker;
	private Date check_date;
	private String confirmer;
	private Date confirm_date;
	private Integer disabler;
	private Date disable_date;

	private String cus_name;
	private String bank_name;
	private String pact_name;
	private String maker_name;
	private String checker_name;
	private String confirmer_name;
	private String disabler_name;

	public Integer getGroup_id() {
		return group_id;
	}

	public void setGroup_id(Integer group_id) {
		this.group_id = group_id;
	}

	public Integer getHos_id() {
		return hos_id;
	}

	public void setHos_id(Integer hos_id) {
		this.hos_id = hos_id;
	}

	public String getCopy_code() {
		return copy_code;
	}

	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}

	public String getPact_code() {
		return pact_code;
	}

	public void setPact_code(String pact_code) {
		this.pact_code = pact_code;
	}

	public String getLetter_code() {
		return letter_code;
	}

	public void setLetter_code(String letter_code) {
		this.letter_code = letter_code;
	}

	public Date getSign_date() {
		return sign_date;
	}

	public void setSign_date(Date sign_date) {
		this.sign_date = sign_date;
	}

	public String getBank_code() {
		return bank_code;
	}

	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}

	public String getBank_man() {
		return bank_man;
	}

	public void setBank_man(String bank_man) {
		this.bank_man = bank_man;
	}

	public String getBank_tel() {
		return bank_tel;
	}

	public void setBank_tel(String bank_tel) {
		this.bank_tel = bank_tel;
	}

	public String getBank_no() {
		return bank_no;
	}

	public void setBank_no(String bank_no) {
		this.bank_no = bank_no;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public String getSp_cond() {
		return sp_cond;
	}

	public void setSp_cond(String sp_cond) {
		this.sp_cond = sp_cond;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getLetter_state() {
		return letter_state;
	}

	public void setLetter_state(String letter_state) {
		this.letter_state = letter_state;
	}

	public Integer getMaker() {
		return maker;
	}

	public void setMaker(Integer maker) {
		this.maker = maker;
	}

	public Date getMake_date() {
		return make_date;
	}

	public void setMake_date(Date make_date) {
		this.make_date = make_date;
	}

	public Integer getChecker() {
		return checker;
	}

	public void setChecker(Integer checker) {
		this.checker = checker;
	}

	public Date getCheck_date() {
		return check_date;
	}

	public void setCheck_date(Date check_date) {
		this.check_date = check_date;
	}

	public String getConfirmer() {
		return confirmer;
	}

	public void setConfirmer(String confirmer) {
		this.confirmer = confirmer;
	}

	public Date getConfirm_date() {
		return confirm_date;
	}

	public void setConfirm_date(Date confirm_date) {
		this.confirm_date = confirm_date;
	}

	public Integer getDisabler() {
		return disabler;
	}

	public void setDisabler(Integer disabler) {
		this.disabler = disabler;
	}

	public Date getDisable_date() {
		return disable_date;
	}

	public void setDisable_date(Date disable_date) {
		this.disable_date = disable_date;
	}

	public String getHospital_man() {
		return hospital_man;
	}

	public void setHospital_man(String hospital_man) {
		this.hospital_man = hospital_man;
	}

	public String getHospital_tel() {
		return hospital_tel;
	}

	public void setHospital_tel(String hospital_tel) {
		this.hospital_tel = hospital_tel;
	}

	public Integer getCus_id() {
		return cus_id;
	}

	public void setCus_id(Integer cus_id) {
		this.cus_id = cus_id;
	}

	public Integer getCus_no() {
		return cus_no;
	}

	public void setCus_no(Integer cus_no) {
		this.cus_no = cus_no;
	}

	public String getCus_name() {
		return cus_name;
	}

	public void setCus_name(String cus_name) {
		this.cus_name = cus_name;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPact_name() {
		return pact_name;
	}

	public void setPact_name(String pact_name) {
		this.pact_name = pact_name;
	}

	public String getMaker_name() {
		return maker_name;
	}

	public void setMaker_name(String maker_name) {
		this.maker_name = maker_name;
	}

	public String getChecker_name() {
		return checker_name;
	}

	public void setChecker_name(String checker_name) {
		this.checker_name = checker_name;
	}

	public String getConfirmer_name() {
		return confirmer_name;
	}

	public void setConfirmer_name(String confirmer_name) {
		this.confirmer_name = confirmer_name;
	}

	public String getDisabler_name() {
		return disabler_name;
	}

	public void setDisabler_name(String disabler_name) {
		this.disabler_name = disabler_name;
	}

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

}
