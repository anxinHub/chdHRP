package com.chd.hrp.pac.entity.skxy.pactinfo;

import java.io.Serializable;
import java.util.Date;

public class PactMainSKXYEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer group_id;
	private Integer hos_id;
	private String copy_code;
	private String pact_code;
	private String pact_type_code;
	private String pact_type_name;
	private String state_code;
	private String state_code_name;
	private String pact_name;
	private String original_code;
	private String master_pact_code;
	private Date sign_date;
	private Integer dept_id;
	private Integer dept_no;
	private String dept_name;
	private Integer emp_id;
	private String emp_name;
	private Integer cus_id;
	private Integer cus_no;
	private String cus_name;
	private String opp_emp;
	private String opp_phone;
	private Date start_date;
	private Date end_date;
	private Integer is_bid;
	private String organ_type;
	private String buy_type;
	private Integer proj_id;
	private Integer proj_no;
	private Integer is_init;
	private Integer state;
	private String state_name;
	private Integer maker;
	private String maker_name;
	private Date make_date;
	private Integer checker;
	private String checker_name;
	private Date check_date;
	private Integer confirmer;
	private Date confirm_date;
	private Integer stoper;
	private Date stop_date;
	private Integer filer;
	private Date file_date;
	
	private String cont_term1;
	private String cont_term2;
	private String cont_term3;
	private String cont_term4;
	private String cont_term5;
	private String cont_term6;
	private String note;
	private String change_code;
	
	
	
	public String getChange_code() {
		return change_code;
	}

	public void setChange_code(String change_code) {
		this.change_code = change_code;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	private Integer warning_day;

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

	public String getState_code_name() {
		return state_code_name;
	}

	public void setState_code_name(String state_code_name) {
		this.state_code_name = state_code_name;
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

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public Integer getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(Integer emp_id) {
		this.emp_id = emp_id;
	}

	public String getEmp_name() {
		return emp_name;
	}

	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
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

	public String getState_name() {
		return state_name;
	}

	public void setState_name(String state_name) {
		this.state_name = state_name;
	}

	public Integer getMaker() {
		return maker;
	}

	public void setMaker(Integer maker) {
		this.maker = maker;
	}

	public String getMaker_name() {
		return maker_name;
	}

	public void setMaker_name(String maker_name) {
		this.maker_name = maker_name;
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

	public String getChecker_name() {
		return checker_name;
	}

	public void setChecker_name(String checker_name) {
		this.checker_name = checker_name;
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

	public String getPact_type_name() {
		return pact_type_name;
	}

	public void setPact_type_name(String pact_type_name) {
		this.pact_type_name = pact_type_name;
	}

	public Integer getWarning_day() {
		return warning_day;
	}

	public void setWarning_day(Integer warning_day) {
		this.warning_day = warning_day;
	}

}
