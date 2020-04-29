package com.chd.hrp.pac.entity.fkht.breach;

import java.io.Serializable;
import java.util.Date;

public class PactBreakFKHTEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8245195246466989566L;
	private Integer group_id;
	private Integer hos_id;
	private String copy_code;
	private String break_code;
	private String pact_code;
	private Date sign_date;
	private Integer sup_id;
	private Integer sup_no;
	private Integer first_man;
	private String second_man;
	private String party;
	private Integer break_money;
	private Date start_date;
	private Date end_date;
	private String note;
	private String break_reason;
	private String break_process;
	private String break_result;
	private String state;
	private Integer maker;
	private Date make_date;
	private Integer checker;
	private Date check_date;
	private Integer confirmer;
	private Date confirm_date;

	private String sup_name;
	private String pact_name;
	private String party_name;
	private String state_name;
	private String maker_name;
	private String checker_name;
	private String confirmer_name;

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

	public String getBreak_code() {
		return break_code;
	}

	public void setBreak_code(String break_code) {
		this.break_code = break_code;
	}

	public String getPact_code() {
		return pact_code;
	}

	public void setPact_code(String pact_code) {
		this.pact_code = pact_code;
	}

	public Date getSign_date() {
		return sign_date;
	}

	public void setSign_date(Date sign_date) {
		this.sign_date = sign_date;
	}

	public Integer getSup_id() {
		return sup_id;
	}

	public void setSup_id(Integer sup_id) {
		this.sup_id = sup_id;
	}

	public Integer getSup_no() {
		return sup_no;
	}

	public void setSup_no(Integer sup_no) {
		this.sup_no = sup_no;
	}

	public Integer getFirst_man() {
		return first_man;
	}

	public void setFirst_man(Integer first_man) {
		this.first_man = first_man;
	}

	public String getSecond_man() {
		return second_man;
	}

	public void setSecond_man(String second_man) {
		this.second_man = second_man;
	}

	public String getParty() {
		return party;
	}

	public void setParty(String party) {
		this.party = party;
	}

	public Integer getBreak_money() {
		return break_money;
	}

	public void setBreak_money(Integer break_money) {
		this.break_money = break_money;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getBreak_reason() {
		return break_reason;
	}

	public void setBreak_reason(String break_reason) {
		this.break_reason = break_reason;
	}

	public String getBreak_process() {
		return break_process;
	}

	public void setBreak_process(String break_process) {
		this.break_process = break_process;
	}

	public String getBreak_result() {
		return break_result;
	}

	public void setBreak_result(String break_result) {
		this.break_result = break_result;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public Integer getConfirmer() {
		return confirmer;
	}

	public void setConfirmer(Integer confirmer) {
		this.confirmer = confirmer;
	}

	public Date getConfirm_date() {
		return confirm_date;
	}

	public void setConfirm_date(Date confirm_date) {
		this.confirm_date = confirm_date;
	}

	public String getSup_name() {
		return sup_name;
	}

	public void setSup_name(String sup_name) {
		this.sup_name = sup_name;
	}

	public String getPact_name() {
		return pact_name;
	}

	public void setPact_name(String pact_name) {
		this.pact_name = pact_name;
	}

	public String getParty_name() {
		return party_name;
	}

	public void setParty_name(String party_name) {
		this.party_name = party_name;
	}

	public String getState_name() {
		return state_name;
	}

	public void setState_name(String state_name) {
		this.state_name = state_name;
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

}
