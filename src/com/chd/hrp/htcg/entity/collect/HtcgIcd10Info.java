package com.chd.hrp.htcg.entity.collect;

import java.io.Serializable;

public class HtcgIcd10Info implements Serializable {


	private static final long serialVersionUID = 1L;
	private long group_id;
	private long hos_id;
	private String copy_code;
	private String mr_no;
	private String in_hos_no;
	private String patient_name;
	private String icd10_type_code;
	private String icd10_type_name;
	private String icd10_code;
	private String icd10_name;	
	private Integer icd10_seq;
	private String outcome_code;
	private String outcome_name;
	public long getGroup_id() {
		return group_id;
	}
	public long getHos_id() {
		return hos_id;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public String getMr_no() {
		return mr_no;
	}
	public String getIn_hos_no() {
		return in_hos_no;
	}
	public String getPatient_name() {
		return patient_name;
	}
	public String getIcd10_type_code() {
		return icd10_type_code;
	}
	public String getIcd10_type_name() {
		return icd10_type_name;
	}
	public String getIcd10_code() {
		return icd10_code;
	}
	public String getIcd10_name() {
		return icd10_name;
	}
	public Integer getIcd10_seq() {
		return icd10_seq;
	}
	public String getOutcome_code() {
		return outcome_code;
	}
	public String getOutcome_name() {
		return outcome_name;
	}
	public void setGroup_id(long group_id) {
		this.group_id = group_id;
	}
	public void setHos_id(long hos_id) {
		this.hos_id = hos_id;
	}
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	public void setMr_no(String mr_no) {
		this.mr_no = mr_no;
	}
	public void setIn_hos_no(String in_hos_no) {
		this.in_hos_no = in_hos_no;
	}
	public void setPatient_name(String patient_name) {
		this.patient_name = patient_name;
	}
	public void setIcd10_type_code(String icd10_type_code) {
		this.icd10_type_code = icd10_type_code;
	}
	public void setIcd10_type_name(String icd10_type_name) {
		this.icd10_type_name = icd10_type_name;
	}
	public void setIcd10_code(String icd10_code) {
		this.icd10_code = icd10_code;
	}
	public void setIcd10_name(String icd10_name) {
		this.icd10_name = icd10_name;
	}
	public void setIcd10_seq(Integer icd10_seq) {
		this.icd10_seq = icd10_seq;
	}
	public void setOutcome_code(String outcome_code) {
		this.outcome_code = outcome_code;
	}
	public void setOutcome_name(String outcome_name) {
		this.outcome_name = outcome_name;
	}
	
}
