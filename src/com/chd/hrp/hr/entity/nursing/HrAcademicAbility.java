package com.chd.hrp.hr.entity.nursing;

import java.io.Serializable;

/**
 * 学术能力
 * @author Administrator
 *
 */
public class HrAcademicAbility implements Serializable {


	
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
	private String duty_code;
	
	/**
	 * 
	 */
	private String title_code;
	
	/**
	 * 
	 */
	private String level_code;
	
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
	 * 0：新建 1:提交
	 */
	private Integer state;
	private String state_name;
	
	/**
	 * 
	 */
	private String note;

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

	public String getDuty_code() {
		return duty_code;
	}

	public void setDuty_code(String duty_code) {
		this.duty_code = duty_code;
	}

	public String getTitle_code() {
		return title_code;
	}

	public void setTitle_code(String title_code) {
		this.title_code = title_code;
	}

	public String getLevel_code() {
		return level_code;
	}

	public void setLevel_code(String level_code) {
		this.level_code = level_code;
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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getState_name() {
		return state_name;
	}

	public void setState_name(String state_name) {
		this.state_name = state_name;
	}
	

	
}
