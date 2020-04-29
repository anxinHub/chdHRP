package com.chd.hrp.hpm.entity;

import java.io.Serializable;

public class AphiDeptEmpMap implements Serializable {

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
	 * 科室项目编码
	 */
	private String dept_item_code;

	/**
	 * 科室项目名称
	 */
	private String dept_item_name;

	/**
	 * 员工项目编码
	 */
	private String emp_item_code;

	/**
	 * 员工项目名称
	 */
	private String emp_item_name;

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

	public String getDept_item_code() {
		return dept_item_code;
	}

	public void setDept_item_code(String dept_item_code) {
		this.dept_item_code = dept_item_code;
	}

	public String getDept_item_name() {
		return dept_item_name;
	}

	public void setDept_item_name(String dept_item_name) {
		this.dept_item_name = dept_item_name;
	}

	public String getEmp_item_code() {
		return emp_item_code;
	}

	public void setEmp_item_code(String emp_item_code) {
		this.emp_item_code = emp_item_code;
	}

	public String getEmp_item_name() {
		return emp_item_name;
	}

	public void setEmp_item_name(String emp_item_name) {
		this.emp_item_name = emp_item_name;
	}

}
