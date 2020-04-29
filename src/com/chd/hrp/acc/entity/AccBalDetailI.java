package com.chd.hrp.acc.entity;

import java.io.Serializable;
import java.util.Date;

public class AccBalDetailI implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer group_id;
    private Integer hos_id;
    private String copy_code;
    private String rep_no;
    private Date charge_date;
    private Integer dept_id;
    private Integer dept_no;
    private String dept_code;
    private String dpet_name;
    private Integer charge_kind_id;
    private String charge_kind_code;
    private String charge_kind_name;
    private Integer patient_id;
    private String patient_type_code;
    private Long charge_money;
    private String check_no;
    private String patient_name;
    private String charge_code;
    private String charge_name;
    private String begin_no;
    private String end_no;
    private Integer is_back;
    
    
	public String getDpet_name() {
		return dpet_name;
	}
	public void setDpet_name(String dpet_name) {
		this.dpet_name = dpet_name;
	}
	public String getCharge_kind_name() {
		return charge_kind_name;
	}
	public void setCharge_kind_name(String charge_kind_name) {
		this.charge_kind_name = charge_kind_name;
	}
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
	public String getCopy_code() {
		return copy_code;
	}
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	public String getRep_no() {
		return rep_no;
	}
	public void setRep_no(String rep_no) {
		this.rep_no = rep_no;
	}
	public Date getCharge_date() {
		return charge_date;
	}
	public void setCharge_date(Date charge_date) {
		this.charge_date = charge_date;
	}
	public Integer getDept_id() {
		return dept_id;
	}
	public void setDept_id(Integer dept_id) {
		this.dept_id = dept_id;
	}
	public Integer getDept_no() {
		return dept_no;
	}
	public void setDept_no(Integer dept_no) {
		this.dept_no = dept_no;
	}
	public String getDept_code() {
		return dept_code;
	}
	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}
	public Integer getCharge_kind_id() {
		return charge_kind_id;
	}
	public void setCharge_kind_id(Integer charge_kind_id) {
		this.charge_kind_id = charge_kind_id;
	}
	public String getCharge_kind_code() {
		return charge_kind_code;
	}
	public void setCharge_kind_code(String charge_kind_code) {
		this.charge_kind_code = charge_kind_code;
	}
	public Integer getPatient_id() {
		return patient_id;
	}
	public void setPatient_id(Integer patient_id) {
		this.patient_id = patient_id;
	}
	public String getPatient_type_code() {
		return patient_type_code;
	}
	public void setPatient_type_code(String patient_type_code) {
		this.patient_type_code = patient_type_code;
	}
	public Long getCharge_money() {
		return charge_money;
	}
	public void setCharge_money(Long charge_money) {
		this.charge_money = charge_money;
	}
	public String getCheck_no() {
		return check_no;
	}
	public void setCheck_no(String check_no) {
		this.check_no = check_no;
	}
	public String getPatient_name() {
		return patient_name;
	}
	public void setPatient_name(String patient_name) {
		this.patient_name = patient_name;
	}
	public String getCharge_code() {
		return charge_code;
	}
	public void setCharge_code(String charge_code) {
		this.charge_code = charge_code;
	}
	public String getCharge_name() {
		return charge_name;
	}
	public void setCharge_name(String charge_name) {
		this.charge_name = charge_name;
	}
	public String getBegin_no() {
		return begin_no;
	}
	public void setBegin_no(String begin_no) {
		this.begin_no = begin_no;
	}
	public String getEnd_no() {
		return end_no;
	}
	public void setEnd_no(String end_no) {
		this.end_no = end_no;
	}
	public Integer getIs_back() {
		return is_back;
	}
	public void setIs_back(Integer is_back) {
		this.is_back = is_back;
	} 
}
