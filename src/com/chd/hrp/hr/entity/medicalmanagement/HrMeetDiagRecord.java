package com.chd.hrp.hr.entity.medicalmanagement;

import java.io.Serializable;
import java.util.Date;

public class HrMeetDiagRecord implements Serializable {
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 集团
	 */
	private Integer group_id;

	/**
	 * 医院
	 * 
	 */
	private Integer hos_id;

	/**
	 * 会诊单号
	 */
	private String bill_no;
	/**
	 * 记录日期
	 */
	private Date rec_date;
	/**
	 * 科室
	 */
	private Integer dept_id;
	private String dept_name;

	/**
	 * 病案号
	 */
	private String case_no;
	/**
	 * 病人姓名
	 */
	private String patient;
	/**
	 * 病床号
	 */
	private String bed_no;
	/**
	 * 诊断
	 */
	private String diagnose;

	/**
	 * 会诊时间
	 */
	private Date md_date;
	/**
	 * 申请原因
	 */
	private String reason;
	/**
	 * 会诊结果
	 */
	private String md_result;
	/**
	 * 是否提交
	 */
	private Integer is_commit;
	private String commit_name;

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

	public String getBill_no() {
		return bill_no;
	}

	public void setBill_no(String bill_no) {
		this.bill_no = bill_no;
	}

	public Date getRec_date() {
		return rec_date;
	}

	public void setRec_date(Date rec_date) {
		this.rec_date = rec_date;
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

	public String getCase_no() {
		return case_no;
	}

	public void setCase_no(String case_no) {
		this.case_no = case_no;
	}

	public String getPatient() {
		return patient;
	}

	public void setPatient(String patient) {
		this.patient = patient;
	}

	public String getBed_no() {
		return bed_no;
	}

	public void setBed_no(String bed_no) {
		this.bed_no = bed_no;
	}

	public String getDiagnose() {
		return diagnose;
	}

	public void setDiagnose(String diagnose) {
		this.diagnose = diagnose;
	}

	public Date getMd_date() {
		return md_date;
	}

	public void setMd_date(Date md_date) {
		this.md_date = md_date;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getMd_result() {
		return md_result;
	}

	public void setMd_result(String md_result) {
		this.md_result = md_result;
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

}
