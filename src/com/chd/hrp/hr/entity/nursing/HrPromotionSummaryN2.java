package com.chd.hrp.hr.entity.nursing;

import java.io.Serializable;
import java.util.Date;

/**
 *护理晋级汇总审批表(N2)
 * @author Administrator
 *
 */
public class HrPromotionSummaryN2 implements Serializable {
	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 
	 */
	private Integer group_id;
	
	/**
	 * 
	 */
	private Integer hos_id;
	
	/**
	 * 
	 */
	private String year;
	
	/**
	 * 
	 */
	private String emp_code;
	private Integer emp_id;
	private String emp_name;
	
	/**
	 * 
	 */
	private Integer dept_id;
	
	private String dept_name;
	/**
	 * 
	 */
	private Integer dept_no;
	
	/**
	 * DIC_LEVEL
	 */
	private String cur_level_code;
	
	/**
	 * DIC_LEVEL
	 */
	private String apply_level_code;
	
	/**
	 * 
	 */
	private Double education;
	
	/**
	 * 
	 */
	private Double book_report;
	
	/**
	 * 
	 */
	private Double case_analy;
	
	/**
	 * 
	 */
	private Double special_case_analy;
	
	/**
	 * 
	 */
	private Double cpr_score;
	
	/**
	 * 
	 */
	private Double peer_score;
	
	/**
	 * 
	 */
	private Double write_score;
	
	/**
	 * 
	 */
	private Long hnurse;
	
	/**
	 * 1：通过 0：未通过
	 */
	private Integer hnurse_audit;
	private String hnurse_name;
	
	/**
	 * 
	 */
	private Date hnurse_date;
	
	/**
	 * 
	 */
	private Long dhnurse;
	
	/**
	 * 1：通过 0：未通过
	 */
	private Integer dhnurse_audit;
	private String dhnurse_name;
	
	/**
	 * 
	 */
	private Date dhnurse_date;
	
	/**
	 * 
	 */
	private Long promotion;
	
	/**
	 * 1：通过 0：未通过
	 */
	private Integer promotion_audit;
	private String promotion_name;
	
	/**
	 * 
	 */
	private Date promotion_date;
	
	/**
	 * 
	 */
	private String pass_reason;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;


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


public String getYear() {
	return year;
}


public void setYear(String year) {
	this.year = year;
}


public Integer getEmp_id() {
	return emp_id;
}


public void setEmp_id(Integer emp_id) {
	this.emp_id = emp_id;
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


public String getCur_level_code() {
	return cur_level_code;
}


public void setCur_level_code(String cur_level_code) {
	this.cur_level_code = cur_level_code;
}


public String getApply_level_code() {
	return apply_level_code;
}


public void setApply_level_code(String apply_level_code) {
	this.apply_level_code = apply_level_code;
}


public Double getEducation() {
	return education;
}


public void setEducation(Double education) {
	this.education = education;
}


public Double getBook_report() {
	return book_report;
}


public void setBook_report(Double book_report) {
	this.book_report = book_report;
}


public Double getCase_analy() {
	return case_analy;
}


public void setCase_analy(Double case_analy) {
	this.case_analy = case_analy;
}


public Double getSpecial_case_analy() {
	return special_case_analy;
}


public void setSpecial_case_analy(Double special_case_analy) {
	this.special_case_analy = special_case_analy;
}


public Double getCpr_score() {
	return cpr_score;
}


public void setCpr_score(Double cpr_score) {
	this.cpr_score = cpr_score;
}


public Double getPeer_score() {
	return peer_score;
}


public void setPeer_score(Double peer_score) {
	this.peer_score = peer_score;
}


public Double getWrite_score() {
	return write_score;
}


public void setWrite_score(Double write_score) {
	this.write_score = write_score;
}


public Long getHnurse() {
	return hnurse;
}


public void setHnurse(Long hnurse) {
	this.hnurse = hnurse;
}


public Integer getHnurse_audit() {
	return hnurse_audit;
}


public void setHnurse_audit(Integer hnurse_audit) {
	this.hnurse_audit = hnurse_audit;
}


public Date getHnurse_date() {
	return hnurse_date;
}


public void setHnurse_date(Date hnurse_date) {
	this.hnurse_date = hnurse_date;
}


public Long getDhnurse() {
	return dhnurse;
}


public void setDhnurse(Long dhnurse) {
	this.dhnurse = dhnurse;
}


public Integer getDhnurse_audit() {
	return dhnurse_audit;
}


public void setDhnurse_audit(Integer dhnurse_audit) {
	this.dhnurse_audit = dhnurse_audit;
}


public Date getDhnurse_date() {
	return dhnurse_date;
}


public void setDhnurse_date(Date dhnurse_date) {
	this.dhnurse_date = dhnurse_date;
}


public Long getPromotion() {
	return promotion;
}


public void setPromotion(Long promotion) {
	this.promotion = promotion;
}


public Integer getPromotion_audit() {
	return promotion_audit;
}


public void setPromotion_audit(Integer promotion_audit) {
	this.promotion_audit = promotion_audit;
}


public Date getPromotion_date() {
	return promotion_date;
}


public void setPromotion_date(Date promotion_date) {
	this.promotion_date = promotion_date;
}


public String getPass_reason() {
	return pass_reason;
}


public void setPass_reason(String pass_reason) {
	this.pass_reason = pass_reason;
}


public String getError_type() {
	return error_type;
}


public void setError_type(String error_type) {
	this.error_type = error_type;
}


public String getEmp_code() {
	return emp_code;
}


public void setEmp_code(String emp_code) {
	this.emp_code = emp_code;
}


public String getEmp_name() {
	return emp_name;
}


public void setEmp_name(String emp_name) {
	this.emp_name = emp_name;
}


public String getDept_name() {
	return dept_name;
}


public void setDept_name(String dept_name) {
	this.dept_name = dept_name;
}


public String getHnurse_name() {
	return hnurse_name;
}


public void setHnurse_name(String hnurse_name) {
	this.hnurse_name = hnurse_name;
}


public String getDhnurse_name() {
	return dhnurse_name;
}


public void setDhnurse_name(String dhnurse_name) {
	this.dhnurse_name = dhnurse_name;
}


public String getPromotion_name() {
	return promotion_name;
}


public void setPromotion_name(String promotion_name) {
	this.promotion_name = promotion_name;
}
	

}
