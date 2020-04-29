/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.entity.nursing;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 
 * @Table:在职教育学员表
 * HR_EDUCATION_STUDENT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class HrEducationStudent implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 
	 */
	private Long group_id;
	
	/**
	 * 
	 */
	private Long hos_id;
	
	/**
	 * 
	 */
	private Date edu_date;
	
	/**
	 * 
	 */
	private String class_name;
	
	private Double hours;
	/**
	 * 
	 */
	private String emp_id;
	private String emp_name;
	
	private Integer dept_id;
	private String dept_no;
	private String dept_name;
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
	private String note;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 
	* @param value 
	*/
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getGroup_id() {
		return this.group_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setHos_id(Long value) {
		this.hos_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getHos_id() {
		return this.hos_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setEdu_date(Date value) {
		this.edu_date = value;
	}
	
	/**
	* 获取 
	* @return Date
	*/
	public Date getEdu_date() {
		return this.edu_date;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setClass_name(String value) {
		this.class_name = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getClass_name() {
		return this.class_name;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setEmp_id(String value) {
		this.emp_id = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getEmp_id() {
		return this.emp_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setDuty_code(String value) {
		this.duty_code = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getDuty_code() {
		return this.duty_code;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setTitle_code(String value) {
		this.title_code = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getTitle_code() {
		return this.title_code;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setLevel_code(String value) {
		this.level_code = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getLevel_code() {
		return this.level_code;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setNote(String value) {
		this.note = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getNote() {
		return this.note;
	}
	
	/**
	 * 设置 导入验证信息
	 */
	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	/**
	 * 获取 导入验证信息
	 */
	public String getError_type() {
		return error_type;
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

	public Integer getDept_id() {
		return dept_id;
	}

	public void setDept_id(Integer dept_id) {
		this.dept_id = dept_id;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public Double getHours() {
		return hours;
	}

	public void setHours(Double hours) {
		this.hours = hours;
	}

	public String getDept_no() {
		return dept_no;
	}

	public void setDept_no(String dept_no) {
		this.dept_no = dept_no;
	}
	
	
}