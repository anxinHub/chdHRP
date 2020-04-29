package com.chd.hrp.pac.entity.fkht.pactinfo;

import java.io.Serializable;
import java.util.Date;

/**
 * 付款合同
 * 
 * @author haotong
 * @date 2018年9月12日 下午2:00:06
 */
public class PactMainFKHTEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7204692405872531470L;
	private Integer group_id;
	private Integer hos_id;
	private String copy_code;
	private String pact_code;
	private String pact_type_code;
	private String state_code;
	private String pact_name;
	private String original_code;
	private String master_pact_code;
	private Date sign_date;
	private Integer dept_id;
	private Integer dept_no;
	private Integer emp_id;
	private Integer sup_id;
	private Integer sup_no;
	private String opp_emp;
	private String opp_phone;
	private String note;
	private String trade_type;
	private String curr_code;
	private Double pact_money_w;
	private Double pact_money;
	private Integer sign_money;
	private Integer change_money;
	private Date start_date;
	private Date end_date;
	private Integer is_bid;
	private String organ_type;
	private String buy_type;
	private Integer is_deposit;
	private String deposit_type;
	private Integer deposit_money;
	private Integer is_init;
	private Integer state;
	private Integer maker;
	private Date make_date;
	private Integer checker;
	private Date check_date;
	private Integer confirmer;
	private Date confirm_date;
	private Integer stoper;
	private Date stop_date;
	private Integer filer;
	private Date file_date;
	private Integer proj_id;
	private Integer proj_no;

	private String sup_name;
	private String dept_name;
	private String trade_name;
	private String state_name;
	private String state_code_name;
	
	/**
	 * 预交货期限(月）
	 */
	private Integer delivery_term ;
	/**
	 * 服务商
	 */
	private String server;
	/**
	 * 服务联系人
	 */
	private String ser_emp;
	/**
	 * 服务电话
	 */
	private String ser_phone;
	/**
	 * 服务条款
	 */
	private String cont_term1;
	/**
	 * 付款条款
	 */
	private String cont_term2;
	/**
	 * 验收标准
	 */
	private String cont_term3;
	/**
	 * 违约处理
	 */
	private String cont_term4;
	/**
	 * 交货条款
	 */
	private String cont_term5;
	/**
	 * 质保条款
	 */
	private String cont_term6;
	
	/**
	 * 定标id
	 */
	private Integer bid_id;
	
	/**
	 * 定标编码
	 */
	private String bid_code;
	

	public Integer getBid_id() {
		return bid_id;
	}

	public void setBid_id(Integer bid_id) {
		this.bid_id = bid_id;
	}

	public String getBid_code() {
		return bid_code;
	}

	public void setBid_code(String bid_code) {
		this.bid_code = bid_code;
	}

	public Integer getProj_id() {
		return proj_id;
	}

	public void setProj_id(Integer proj_id) {
		this.proj_id = proj_id;
	}

	public Integer getProj_no() {
		return proj_no;
	}

	public void setProj_no(Integer proj_no) {
		this.proj_no = proj_no;
	}

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

	public String getPact_type_code() {
		return pact_type_code;
	}

	public void setPact_type_code(String pact_type_code) {
		this.pact_type_code = pact_type_code;
	}

	public String getState_code() {
		return state_code;
	}

	public void setState_code(String state_code) {
		this.state_code = state_code;
	}

	public String getPact_name() {
		return pact_name;
	}

	public void setPact_name(String pact_name) {
		this.pact_name = pact_name;
	}

	public String getOriginal_code() {
		return original_code;
	}

	public void setOriginal_code(String original_code) {
		this.original_code = original_code;
	}

	public String getMaster_pact_code() {
		return master_pact_code;
	}

	public void setMaster_pact_code(String master_pact_code) {
		this.master_pact_code = master_pact_code;
	}

	public Date getSign_date() {
		return sign_date;
	}

	public void setSign_date(Date sign_date) {
		this.sign_date = sign_date;
	}

	public Integer getDept_id() {
		return dept_id;
	}

	public void setDept_id(Integer dept_id) {
		this.dept_id = dept_id;
	}

	public Integer getDept_no() {
		return dept_no;
	}

	public void setDept_no(Integer dept_no) {
		this.dept_no = dept_no;
	}

	public Integer getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(Integer emp_id) {
		this.emp_id = emp_id;
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

	public String getOpp_emp() {
		return opp_emp;
	}

	public void setOpp_emp(String opp_emp) {
		this.opp_emp = opp_emp;
	}

	public String getOpp_phone() {
		return opp_phone;
	}

	public void setOpp_phone(String opp_phone) {
		this.opp_phone = opp_phone;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public String getCurr_code() {
		return curr_code;
	}

	public void setCurr_code(String curr_code) {
		this.curr_code = curr_code;
	}

	public Double getPact_money_w() {
		return pact_money_w;
	}

	public void setPact_money_w(Double pact_money_w) {
		this.pact_money_w = pact_money_w;
	}

	public Double getPact_money() {
		return pact_money;
	}

	public void setPact_money(Double pact_money) {
		this.pact_money = pact_money;
	}

	public Integer getSign_money() {
		return sign_money;
	}

	public void setSign_money(Integer sign_money) {
		this.sign_money = sign_money;
	}

	public Integer getChange_money() {
		return change_money;
	}

	public void setChange_money(Integer change_money) {
		this.change_money = change_money;
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

	public Integer getIs_bid() {
		return is_bid;
	}

	public void setIs_bid(Integer is_bid) {
		this.is_bid = is_bid;
	}

	public String getOrgan_type() {
		return organ_type;
	}

	public void setOrgan_type(String organ_type) {
		this.organ_type = organ_type;
	}

	public String getBuy_type() {
		return buy_type;
	}

	public void setBuy_type(String buy_type) {
		this.buy_type = buy_type;
	}

	public Integer getIs_deposit() {
		return is_deposit;
	}

	public void setIs_deposit(Integer is_deposit) {
		this.is_deposit = is_deposit;
	}

	public String getDeposit_type() {
		return deposit_type;
	}

	public void setDeposit_type(String deposit_type) {
		this.deposit_type = deposit_type;
	}

	public Integer getDeposit_money() {
		return deposit_money;
	}

	public void setDeposit_money(Integer deposit_money) {
		this.deposit_money = deposit_money;
	}

	public Integer getIs_init() {
		return is_init;
	}

	public void setIs_init(Integer is_init) {
		this.is_init = is_init;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
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

	public Integer getStoper() {
		return stoper;
	}

	public void setStoper(Integer stoper) {
		this.stoper = stoper;
	}

	public Date getStop_date() {
		return stop_date;
	}

	public void setStop_date(Date stop_date) {
		this.stop_date = stop_date;
	}

	public Integer getFiler() {
		return filer;
	}

	public void setFiler(Integer filer) {
		this.filer = filer;
	}

	public Date getFile_date() {
		return file_date;
	}

	public void setFile_date(Date file_date) {
		this.file_date = file_date;
	}

	public String getSup_name() {
		return sup_name;
	}

	public void setSup_name(String sup_name) {
		this.sup_name = sup_name;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getTrade_name() {
		return trade_name;
	}

	public void setTrade_name(String trade_name) {
		this.trade_name = trade_name;
	}

	public String getState_name() {
		return state_name;
	}

	public void setState_name(String state_name) {
		this.state_name = state_name;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getState_code_name() {
		return state_code_name;
	}

	public void setState_code_name(String state_code_name) {
		this.state_code_name = state_code_name;
	}

	public Integer getDelivery_term() {
		return delivery_term;
	}

	public void setDelivery_term(Integer delivery_term) {
		this.delivery_term = delivery_term;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getSer_emp() {
		return ser_emp;
	}

	public void setSer_emp(String ser_emp) {
		this.ser_emp = ser_emp;
	}

	public String getSer_phone() {
		return ser_phone;
	}

	public void setSer_phone(String ser_phone) {
		this.ser_phone = ser_phone;
	}

	public String getCont_term1() {
		return cont_term1;
	}

	public void setCont_term1(String cont_term1) {
		this.cont_term1 = cont_term1;
	}

	public String getCont_term2() {
		return cont_term2;
	}

	public void setCont_term2(String cont_term2) {
		this.cont_term2 = cont_term2;
	}

	public String getCont_term3() {
		return cont_term3;
	}

	public void setCont_term3(String cont_term3) {
		this.cont_term3 = cont_term3;
	}

	public String getCont_term4() {
		return cont_term4;
	}

	public void setCont_term4(String cont_term4) {
		this.cont_term4 = cont_term4;
	}

	public String getCont_term5() {
		return cont_term5;
	}

	public void setCont_term5(String cont_term5) {
		this.cont_term5 = cont_term5;
	}

	public String getCont_term6() {
		return cont_term6;
	}

	public void setCont_term6(String cont_term6) {
		this.cont_term6 = cont_term6;
	}
	
	

}
