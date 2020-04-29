package com.chd.hrp.htcg.entity.collect;

import java.io.Serializable;
import java.util.Date;

public class HtcgIcd9Info implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer group_id;
	private Integer hos_id;
	private String copy_code;
	private String mr_no;
	private String in_hos_no;
	private String patient_name;
	private String icd9_name;
	private String icd9_code;
	private Integer icd9_seq;
	private Date icd9_date;
	private double  icd9_time;
	private String icd9_oper;
	private String anest_type_name;
	private String anest_type_code;
	private String anest_oper;
	public Integer getGroup_id() {
		return group_id;
	}
	public Integer getHos_id() {
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
	public String getIcd9_name() {
		return icd9_name;
	}
	public String getIcd9_code() {
		return icd9_code;
	}
	public Integer getIcd9_seq() {
		return icd9_seq;
	}
	public Date getIcd9_date() {
		return icd9_date;
	}
	public double getIcd9_time() {
		return icd9_time;
	}
	public String getIcd9_oper() {
		return icd9_oper;
	}
	public String getAnest_type_name() {
		return anest_type_name;
	}
	public String getAnest_type_code() {
		return anest_type_code;
	}
	public String getAnest_oper() {
		return anest_oper;
	}
	public void setGroup_id(Integer group_id) {
		this.group_id = group_id;
	}
	public void setHos_id(Integer hos_id) {
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
	public void setIcd9_name(String icd9_name) {
		this.icd9_name = icd9_name;
	}
	public void setIcd9_code(String icd9_code) {
		this.icd9_code = icd9_code;
	}
	public void setIcd9_seq(Integer icd9_seq) {
		this.icd9_seq = icd9_seq;
	}
	public void setIcd9_date(Date icd9_date) {
		this.icd9_date = icd9_date;
	}
	public void setIcd9_time(double icd9_time) {
		this.icd9_time = icd9_time;
	}
	public void setIcd9_oper(String icd9_oper) {
		this.icd9_oper = icd9_oper;
	}
	public void setAnest_type_name(String anest_type_name) {
		this.anest_type_name = anest_type_name;
	}
	public void setAnest_type_code(String anest_type_code) {
		this.anest_type_code = anest_type_code;
	}
	public void setAnest_oper(String anest_oper) {
		this.anest_oper = anest_oper;
	}
}
