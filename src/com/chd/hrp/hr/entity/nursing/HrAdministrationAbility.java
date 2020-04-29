package com.chd.hrp.hr.entity.nursing;

import java.io.Serializable;

/**
 * 行政能力
 * @author Administrator
 *
 */
public class HrAdministrationAbility implements Serializable {


	
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
	private String duty_name;
	
	/**
	 * 
	 */
	private String title_code;
	private String title_name;
	
	/**
	 * 
	 */
	private String level_code;
	private String level_name;
	
	/**
	 * 
	 */
	private String imtheme;
	
	/**
	 * DIC_PRIZE
	 */
	private String prize;
	private String field_col_name;
	
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

	public String getImtheme() {
		return imtheme;
	}

	public void setImtheme(String imtheme) {
		this.imtheme = imtheme;
	}

	public String getPrize() {
		return prize;
	}

	public void setPrize(String prize) {
		this.prize = prize;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

	public String getDuty_name() {
		return duty_name;
	}

	public void setDuty_name(String duty_name) {
		this.duty_name = duty_name;
	}

	public String getTitle_name() {
		return title_name;
	}

	public void setTitle_name(String title_name) {
		this.title_name = title_name;
	}

	public String getLevel_name() {
		return level_name;
	}

	public void setLevel_name(String level_name) {
		this.level_name = level_name;
	}

	public String getField_col_name() {
		return field_col_name;
	}

	public void setField_col_name(String field_col_name) {
		this.field_col_name = field_col_name;
	}
	
	

}
