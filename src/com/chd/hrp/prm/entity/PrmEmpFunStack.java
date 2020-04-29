package com.chd.hrp.prm.entity;

import java.io.Serializable;

public class PrmEmpFunStack implements Serializable {
	private static final long serialVersionUID = 5454155825314635342L;

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

	private String acc_year;

	private String acc_month;

	private String kpi_code;

	private Long emp_id;

	private Long emp_no;
	
	private String goal_code;

	/**
	 * 等级代码
	 */
	private String sec_code;

	/**
	 * 等级名称
	 */
	private String fun_para_code;
	
	private String fun_para_value;
	


	public String getFun_para_code() {
		return fun_para_code;
	}

	public void setFun_para_code(String fun_para_code) {
		this.fun_para_code = fun_para_code;
	}

	public String getFun_para_value() {
		return fun_para_value;
	}

	public void setFun_para_value(String fun_para_value) {
		this.fun_para_value = fun_para_value;
	}

	/**
	 * 导入验证信息
	 */
	private String error_type;
	
	

	public String getGoal_code() {
		return goal_code;
	}

	public void setGoal_code(String goal_code) {
		this.goal_code = goal_code;
	}

	public String getAcc_year() {
		return acc_year;
	}

	public void setAcc_year(String acc_year) {
		this.acc_year = acc_year;
	}

	public String getAcc_month() {
		return acc_month;
	}

	public void setAcc_month(String acc_month) {
		this.acc_month = acc_month;
	}

	public String getKpi_code() {
		return kpi_code;
	}

	public void setKpi_code(String kpi_code) {
		this.kpi_code = kpi_code;
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

	/**
	 * 设置 集团ID
	 * 
	 * @param value
	 */
	public void setGroup_id(Long value) {
		this.group_id = value;
	}

	/**
	 * 获取 集团ID
	 * 
	 * @return Long
	 */
	public Long getGroup_id() {
		return this.group_id;
	}

	/**
	 * 设置 医院ID
	 * 
	 * @param value
	 */
	public void setHos_id(Long value) {
		this.hos_id = value;
	}

	/**
	 * 获取 医院ID
	 * 
	 * @return Long
	 */
	public Long getHos_id() {
		return this.hos_id;
	}

	/**
	 * 设置 账套编码
	 * 
	 * @param value
	 */
	public void setCopy_code(String value) {
		this.copy_code = value;
	}

	/**
	 * 获取 账套编码
	 * 
	 * @return String
	 */
	public String getCopy_code() {
		return this.copy_code;
	}

	/**
	 * 设置 等级代码
	 * 
	 * @param value
	 */
	public void setSec_code(String value) {
		this.sec_code = value;
	}

	/**
	 * 获取 等级代码
	 * 
	 * @return String
	 */
	public String getSec_code() {
		return this.sec_code;
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
}
