package com.chd.hrp.acc.entity;

import java.io.Serializable;

public class AccMatchInit implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1435181145405006752L;
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
	 * 会计年度
	 */
	private String acc_year;
	
	private Integer proj_id;
	
	private Integer proj_no;
	
	private double bal_os;
	
	private double sum_od;
	
	private double sum_oc;
	
	private double end_os;
	
	private String proj_code;
	
	private String proj_name;
	
	private String emp_code;
	
	private String emp_name;
	

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

	public String getProj_code() {
		return proj_code;
	}

	public void setProj_code(String proj_code) {
		this.proj_code = proj_code;
	}

	public String getProj_name() {
		return proj_name;
	}

	public void setProj_name(String proj_name) {
		this.proj_name = proj_name;
	}

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

	public String getAcc_year() {
		return acc_year;
	}

	public void setAcc_year(String acc_year) {
		this.acc_year = acc_year;
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

	public double getBal_os() {
		return bal_os;
	}

	public void setBal_os(double bal_os) {
		this.bal_os = bal_os;
	}

	public double getSum_od() {
		return sum_od;
	}

	public void setSum_od(double sum_od) {
		this.sum_od = sum_od;
	}

	public double getSum_oc() {
		return sum_oc;
	}

	public void setSum_oc(double sum_oc) {
		this.sum_oc = sum_oc;
	}

	public double getEnd_os() {
		return end_os;
	}

	public void setEnd_os(double end_os) {
		this.end_os = end_os;
	}
	
}
