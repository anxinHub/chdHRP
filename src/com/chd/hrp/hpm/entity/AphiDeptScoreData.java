package com.chd.hrp.hpm.entity;

import java.io.Serializable;

public class AphiDeptScoreData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2618556740911006763L;
	private Long group_id;

	private Long hos_id;
	
	
	
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
	private String copy_code;
	private String acct_year;
	private String acct_month;
	private Long dept_no;
	private Long dept_id;
	private String dept_name;
	private String f_score;
	private String c_score;
	private String p_score;
	private String l_score;
	private String root_score;
	private String error_type;
	public String getError_type() {
		return error_type;
	}
	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	
	
	public String getCopy_code() {
		return copy_code;
	}
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	public String getAcct_year() {
		return acct_year;
	}
	public void setAcct_year(String acct_year) {
		this.acct_year = acct_year;
	}
	public String getAcct_month() {
		return acct_month;
	}
	public void setAcct_month(String acct_month) {
		this.acct_month = acct_month;
	}
	
	public Long getDept_no() {
		return dept_no;
	}

	public void setDept_no(Long dept_no) {
		this.dept_no = dept_no;
	}

	public Long getDept_id() {
		return dept_id;
	}

	public void setDept_id(Long dept_id) {
		this.dept_id = dept_id;
	}

	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public String getF_score() {
		return f_score;
	}
	public void setF_score(String f_score) {
		this.f_score = f_score;
	}
	public String getC_score() {
		return c_score;
	}
	public void setC_score(String c_score) {
		this.c_score = c_score;
	}
	public String getP_score() {
		return p_score;
	}
	public void setP_score(String p_score) {
		this.p_score = p_score;
	}
	public String getL_score() {
		return l_score;
	}
	public void setL_score(String l_score) {
		this.l_score = l_score;
	}
	public String getRoot_score() {
		return root_score;
	}
	public void setRoot_score(String root_score) {
		this.root_score = root_score;
	}
	
}
