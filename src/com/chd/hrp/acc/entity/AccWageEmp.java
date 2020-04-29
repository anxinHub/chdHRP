package com.chd.hrp.acc.entity;

import java.io.Serializable;

public class AccWageEmp implements Serializable {

	/**
	 *  
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 集团ID
	 */
	private Long group_id;
	/**
	 * 医院ID
	 */
	private Long hos_id;
	/**
	 * 账套编码
	 */
	private String copy_code;
	/**
	 * 工资编码
	 */
	private String wage_code;
	/**
	 * 工资名称
	 */
	private String wage_name;
	/**
	 * 职工ID
	 */
	private Long emp_id;
	/**
	 * 职工变更号
	 */
	private Long emp_no;
	
	private String emp_code;
	/**
	 * 职工名称
	 */
	private String emp_name;
	/**
	 * 职工分类编码
	 */
	private String kind_code;
	/**
	 * 职工分类名称
	 */
	private String kind_name;
	/**
	 * 部门ID
	 */
	private Long dept_id;
	private Long dept_no;
	/**
	 * 部门编码
	 */
	private String dept_code;
	/**
	 * 部门名称
	 */
	private String dept_name;
	/**
	 * 备注
	 */
	private String note;
	
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

	public String getWage_code() {
		return wage_code;
	}

	public void setWage_code(String wage_code) {
		this.wage_code = wage_code;
	}

	public Long getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(Long emp_id) {
		this.emp_id = emp_id;
	}

	public Long getEmp_no() {
		return emp_no;
	}

	public void setEmp_no(Long emp_no) {
		this.emp_no = emp_no;
	}

	public String getEmp_name() {
		return emp_name;
	}

	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}

	public String getKind_code() {
		return kind_code;
	}

	public void setKind_code(String kind_code) {
		this.kind_code = kind_code;
	}

	public String getKind_name() {
		return kind_name;
	}

	public void setKind_name(String kind_name) {
		this.kind_name = kind_name;
	}

	public Long getDept_id() {
		return dept_id;
	}

	public void setDept_id(Long dept_id) {
		this.dept_id = dept_id;
	}

	public String getDept_code() {
		return dept_code;
	}

	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getWage_name() {
		return wage_name;
	}

	public void setWage_name(String wage_name) {
		this.wage_name = wage_name;
	}

	public String getEmp_code() {
		return emp_code;
	}

	public void setEmp_code(String emp_code) {
		this.emp_code = emp_code;
	}

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}

	public Long getDept_no() {
		return dept_no;
	}

	public void setDept_no(Long dept_no) {
		this.dept_no = dept_no;
	}

	
}
