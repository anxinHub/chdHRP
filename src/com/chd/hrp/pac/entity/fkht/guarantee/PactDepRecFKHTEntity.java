package com.chd.hrp.pac.entity.fkht.guarantee;

import java.io.Serializable;
import java.util.Date;

/**
 * 付款合同保证金收款
 * 
 * @author haotong
 * @date 2018年9月12日 下午2:02:09
 */
public class PactDepRecFKHTEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7750190976861629928L;
	private Integer group_id;
	private Integer hos_id;
	private String copy_code;
	private String rec_code;
	private String pact_code;
	private String pact_name;
	private String sup_id;
	private String sup_no;
	private String sup_name;
	private Date rec_date;
	private String pay_way;
	private String cheq_no;
	private String money;
	private Date ret_plan_date;
	private String note;
	private String state;
	private Integer maker;
	private String maker_name;
	private Date make_date;
	private Integer checker;
	private String checker_name;
	private Date check_date;
	private Integer confirmer;
	private String confirmer_name;
	private Date confirm_date;
	private Integer is_init;

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

	public String getRec_code() {
		return rec_code;
	}

	public void setRec_code(String rec_code) {
		this.rec_code = rec_code;
	}

	public String getPact_code() {
		return pact_code;
	}

	public void setPact_code(String pact_code) {
		this.pact_code = pact_code;
	}

	public String getSup_id() {
		return sup_id;
	}

	public void setSup_id(String sup_id) {
		this.sup_id = sup_id;
	}

	public String getSup_no() {
		return sup_no;
	}

	public void setSup_no(String sup_no) {
		this.sup_no = sup_no;
	}

	public Date getDate() {
		return rec_date;
	}

	public void setDate(Date date) {
		this.rec_date = date;
	}

	public String getPay_way() {
		return pay_way;
	}

	public void setPay_way(String pay_way) {
		this.pay_way = pay_way;
	}

	public String getCheq_no() {
		return cheq_no;
	}

	public void setCheq_no(String cheq_no) {
		this.cheq_no = cheq_no;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public Date getRet_plan_date() {
		return ret_plan_date;
	}

	public void setRet_plan_date(Date ret_plan_date) {
		this.ret_plan_date = ret_plan_date;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

	public Integer getIs_init() {
		return is_init;
	}

	public void setIs_init(Integer is_init) {
		this.is_init = is_init;
	}

	public String getPact_name() {
		return pact_name;
	}

	public void setPact_name(String pact_name) {
		this.pact_name = pact_name;
	}

	public String getSup_name() {
		return sup_name;
	}

	public void setSup_name(String sup_name) {
		this.sup_name = sup_name;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
