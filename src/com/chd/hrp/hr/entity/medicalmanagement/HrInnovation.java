package com.chd.hrp.hr.entity.medicalmanagement;

import java.io.Serializable;
import java.util.Date;

public class HrInnovation implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 集团
	 */
	private Integer group_id;

	/**
	 * 医院
	 */
	private Integer hos_id;

	/**
	 * 新技术新项目
	 */
	private String nint;

	/**
	 * 日期
	 */
	private Date nint_date;

	/**
	 * 参与角色
	 */
	private String role;

	/**
	 * 参与成员
	 */
	private String emp;

	/**
	 * 完成例数
	 */
	private Integer case_num;

	/**
	 * 总例数
	 * 
	 */
	private Integer case_tol;

	/**
	 * 获奖情况
	 */
	private String prize;

	/**
	 * 是否提交
	 */
	private Integer is_commit;
	private String commit_name;

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

	public String getNint() {
		return nint;
	}

	public void setNint(String nint) {
		this.nint = nint;
	}

	public Date getNint_date() {
		return nint_date;
	}

	public void setNint_date(Date nint_date) {
		this.nint_date = nint_date;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmp() {
		return emp;
	}

	public void setEmp(String emp) {
		this.emp = emp;
	}

	public Integer getCase_num() {
		return case_num;
	}

	public void setCase_num(Integer case_num) {
		this.case_num = case_num;
	}

	public Integer getCase_tol() {
		return case_tol;
	}

	public void setCase_tol(Integer case_tol) {
		this.case_tol = case_tol;
	}

	public String getPrize() {
		return prize;
	}

	public void setPrize(String prize) {
		this.prize = prize;
	}

	public Integer getIs_commit() {
		return is_commit;
	}

	public void setIs_commit(Integer is_commit) {
		this.is_commit = is_commit;
	}

	public String getCommit_name() {
		return commit_name;
	}

	public void setCommit_name(String commit_name) {
		this.commit_name = commit_name;
	}

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}

}
