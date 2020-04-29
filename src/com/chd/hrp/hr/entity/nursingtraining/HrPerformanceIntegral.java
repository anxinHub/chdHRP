package com.chd.hrp.hr.entity.nursingtraining;

import java.io.Serializable;

/**
 * 员工年终绩效积分考核
 * @author Administrator
 *
 */
public class HrPerformanceIntegral implements Serializable {


	
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
	private Double morality;
	
	/**
	 * 
	 */
	private Double quality;
	
	/**
	 * 
	 */
	private Double safety;
	/**
	 * 
	 */
	private Double resteach;
	
	/**
	 * 
	 */
	private Double accessory;
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
	public String getEmp_code() {
		return emp_code;
	}
	public void setEmp_code(String emp_code) {
		this.emp_code = emp_code;
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
	public Double getMorality() {
		return morality;
	}
	public void setMorality(Double morality) {
		this.morality = morality;
	}
	public Double getQuality() {
		return quality;
	}
	public void setQuality(Double quality) {
		this.quality = quality;
	}
	public Double getSafety() {
		return safety;
	}
	public void setSafety(Double safety) {
		this.safety = safety;
	}
	public Double getResteach() {
		return resteach;
	}
	public void setResteach(Double resteach) {
		this.resteach = resteach;
	}
	public Double getAccessory() {
		return accessory;
	}
	public void setAccessory(Double accessory) {
		this.accessory = accessory;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
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
   
	
}
