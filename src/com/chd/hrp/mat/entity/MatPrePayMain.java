package com.chd.hrp.mat.entity;

import java.io.Serializable;
import java.util.Date;

public class MatPrePayMain implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long group_id ;
	
	private Long hos_id ;
	
	private String copy_code ;
	
	private Long pre_pay_id ;
	
	private String pre_pay_no ;
	
	private Date pay_date ;
	
	private Long pay_bill_type ;
	
	private Long sup_id ;
	
	private Long sup_no ;
	
	private String pay_code ;
	
	private String cheq_no  ;
	
	private String pay_type_code ;
	
	private String acct_code ;
	
	private Double payable_money ;
	
	private Double pre_pay_mney ;
	
	private int is_init ;
	
	private Long maker ;
	
	private Date make_date ;
	
	private Long checker ;
	
	private Date chk_date ;
	
	private int state ;
	
	private String note ;
	
	 /**
	 * 导入验证信息
	 */
	private String error_type;

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

	public Long getPre_pay_id() {
		return pre_pay_id;
	}

	public void setPre_pay_id(Long pre_pay_id) {
		this.pre_pay_id = pre_pay_id;
	}

	public String getPre_pay_no() {
		return pre_pay_no;
	}

	public void setPre_pay_no(String pre_pay_no) {
		this.pre_pay_no = pre_pay_no;
	}

	public Date getPay_date() {
		return pay_date;
	}

	public void setPay_date(Date pay_date) {
		this.pay_date = pay_date;
	}

	public Long getPay_bill_type() {
		return pay_bill_type;
	}

	public void setPay_bill_type(Long pay_bill_type) {
		this.pay_bill_type = pay_bill_type;
	}

	public Long getSup_id() {
		return sup_id;
	}

	public void setSup_id(Long sup_id) {
		this.sup_id = sup_id;
	}

	public Long getSup_no() {
		return sup_no;
	}

	public void setSup_no(Long sup_no) {
		this.sup_no = sup_no;
	}

	public String getPay_code() {
		return pay_code;
	}

	public void setPay_code(String pay_code) {
		this.pay_code = pay_code;
	}

	public String getCheq_no() {
		return cheq_no;
	}

	public void setCheq_no(String cheq_no) {
		this.cheq_no = cheq_no;
	}

	public String getPay_type_code() {
		return pay_type_code;
	}

	public void setPay_type_code(String pay_type_code) {
		this.pay_type_code = pay_type_code;
	}

	public String getAcct_code() {
		return acct_code;
	}

	public void setAcct_code(String acct_code) {
		this.acct_code = acct_code;
	}

	public Double getPayable_money() {
		return payable_money;
	}

	public void setPayable_money(Double payable_money) {
		this.payable_money = payable_money;
	}

	public Double getPre_pay_mney() {
		return pre_pay_mney;
	}

	public void setPre_pay_mney(Double pre_pay_mney) {
		this.pre_pay_mney = pre_pay_mney;
	}

	public int getIs_init() {
		return is_init;
	}

	public void setIs_init(int is_init) {
		this.is_init = is_init;
	}

	public Long getMaker() {
		return maker;
	}

	public void setMaker(Long maker) {
		this.maker = maker;
	}

	public Date getMake_date() {
		return make_date;
	}

	public void setMake_date(Date make_date) {
		this.make_date = make_date;
	}

	public Long getChecker() {
		return checker;
	}

	public void setChecker(Long checker) {
		this.checker = checker;
	}

	public Date getChk_date() {
		return chk_date;
	}

	public void setChk_date(Date chk_date) {
		this.chk_date = chk_date;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	
	

}
