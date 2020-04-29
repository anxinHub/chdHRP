package com.chd.hrp.htcg.entity.collect;

import java.io.Serializable;
import java.util.Date;

public class HtcgIntegratedQuery implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long group_id;
	private Long hos_id;
	private String copy_code;
	private String mr_no;
	private String in_hos_no;
	private String patient_name;
	private Integer patient_sex;
	private Date birth_date;
	private Long in_dept_no;
	private Long in_dept_id;
	private String in_dept_code;
	private String in_dept_name;
	private Long out_dept_no;
	private Long out_dept_id;
	private String out_dept_code;
	private String out_dept_name;
	private Date in_date;
	private Date out_date;
	private Integer in_days;
	private String in_hos_name;
	private String outcome_code;
	private String outcome_name;
	private String icd10_type_code;
	private String icd10_type_name;
	private String icd10_code;
	private String icd10_name;
	private String icd9_code;
	private String icd9_name;
	private Date icd9_date;
	public Long getGroup_id() {
		return group_id;
	}
	public Long getHos_id() {
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
	public Integer getPatient_sex() {
		return patient_sex;
	}
	public Date getBirth_date() {
		return birth_date;
	}
	public Long getIn_dept_no() {
		return in_dept_no;
	}
	public Long getIn_dept_id() {
		return in_dept_id;
	}
	public String getIn_dept_code() {
		return in_dept_code;
	}
	public String getIn_dept_name() {
		return in_dept_name;
	}
	public Long getOut_dept_no() {
		return out_dept_no;
	}
	public Long getOut_dept_id() {
		return out_dept_id;
	}
	public String getOut_dept_code() {
		return out_dept_code;
	}
	public String getOut_dept_name() {
		return out_dept_name;
	}
	public Date getIn_date() {
		return in_date;
	}
	public Date getOut_date() {
		return out_date;
	}
	public Integer getIn_days() {
		return in_days;
	}
	public String getIn_hos_name() {
		return in_hos_name;
	}
	public String getOutcome_code() {
		return outcome_code;
	}
	public String getOutcome_name() {
		return outcome_name;
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
	public String getIcd9_code() {
		return icd9_code;
	}
	public String getIcd9_name() {
		return icd9_name;
	}
	public Date getIcd9_date() {
		return icd9_date;
	}
	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}
	public void setHos_id(Long hos_id) {
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
	public void setPatient_sex(Integer patient_sex) {
		this.patient_sex = patient_sex;
	}
	public void setBirth_date(Date birth_date) {
		this.birth_date = birth_date;
	}
	public void setIn_dept_no(Long in_dept_no) {
		this.in_dept_no = in_dept_no;
	}
	public void setIn_dept_id(Long in_dept_id) {
		this.in_dept_id = in_dept_id;
	}
	public void setIn_dept_code(String in_dept_code) {
		this.in_dept_code = in_dept_code;
	}
	public void setIn_dept_name(String in_dept_name) {
		this.in_dept_name = in_dept_name;
	}
	public void setOut_dept_no(Long out_dept_no) {
		this.out_dept_no = out_dept_no;
	}
	public void setOut_dept_id(Long out_dept_id) {
		this.out_dept_id = out_dept_id;
	}
	public void setOut_dept_code(String out_dept_code) {
		this.out_dept_code = out_dept_code;
	}
	public void setOut_dept_name(String out_dept_name) {
		this.out_dept_name = out_dept_name;
	}
	public void setIn_date(Date in_date) {
		this.in_date = in_date;
	}
	public void setOut_date(Date out_date) {
		this.out_date = out_date;
	}
	public void setIn_days(Integer in_days) {
		this.in_days = in_days;
	}
	public void setIn_hos_name(String in_hos_name) {
		this.in_hos_name = in_hos_name;
	}
	public void setOutcome_code(String outcome_code) {
		this.outcome_code = outcome_code;
	}
	public void setOutcome_name(String outcome_name) {
		this.outcome_name = outcome_name;
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
	public void setIcd9_code(String icd9_code) {
		this.icd9_code = icd9_code;
	}
	public void setIcd9_name(String icd9_name) {
		this.icd9_name = icd9_name;
	}
	public void setIcd9_date(Date icd9_date) {
		this.icd9_date = icd9_date;
	}
}
