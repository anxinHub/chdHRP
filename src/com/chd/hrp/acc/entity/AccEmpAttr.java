package com.chd.hrp.acc.entity;

import java.io.Serializable;

public class AccEmpAttr implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1453154188992561463L;
	
	private String emp_id;
	private String emp_no;
	private String group_id; 
	private String hos_id;     
	private String sex;     
	private String is_pay;   
	private String pay_type_code; 
	private String pay_type_name;
	private String station_code;
	private String station_name;
	private String duty_code; 
	private String duty_name;
	private String id_number; 
	private String phone;   
	private String mobile; 
	private String email; 
	private String note;
	private String is_buyer; 
	private Integer is_disable;
	private String attr_code; 
	private String attr_name; 
	
	/**
	 * @return the attr_code
	 */
	public String getAttr_code() {
		return attr_code;
	}
	/**
	 * @param attr_code the attr_code to set
	 */
	public void setAttr_code(String attr_code) {
		this.attr_code = attr_code;
	}
	/**
	 * @return the attr_name
	 */
	public String getAttr_name() {
		return attr_name;
	}
	/**
	 * @param attr_name the attr_name to set
	 */
	public void setAttr_name(String attr_name) {
		this.attr_name = attr_name;
	}
	public Integer getIs_disable() {
		return is_disable;
	}
	public void setIs_disable(Integer is_disable) {
		this.is_disable = is_disable;
	}
	public String getStation_name() {
		return station_name;
	}
	public void setStation_name(String station_name) {
		this.station_name = station_name;
	}
	public String getDuty_name() {
		return duty_name;
	}
	public void setDuty_name(String duty_name) {
		this.duty_name = duty_name;
	}
	private String emp_code;
	private String emp_name;
	private String dept_no;
	private String dept_name;
	private String dept_id;
	private String kind_code;
	private String kind_name;
	private String is_stop;
	
	
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
	public String getDept_no() {
		return dept_no;
	}
	public void setDept_no(String dept_no) {
		this.dept_no = dept_no;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public String getDept_id() {
		return dept_id;
	}
	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}
	public String getKind_code() {
		return kind_code;
	}
	public void setKind_code(String kind_code) {
		this.kind_code = kind_code;
	}
	public String getKind_name() {
		return kind_name;
	}
	public void setKind_name(String kind_name) {
		this.kind_name = kind_name;
	}
	public String getIs_stop() {
		return is_stop;
	}
	public void setIs_stop(String is_stop) {
		this.is_stop = is_stop;
	}
	public String getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}
	public String getGroup_id() {
		return group_id;
	}
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	public String getHos_id() {
		return hos_id;
	}
	public void setHos_id(String hos_id) {
		this.hos_id = hos_id;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getIs_pay() {
		return is_pay;
	}
	public void setIs_pay(String is_pay) {
		this.is_pay = is_pay;
	}
	public String getPay_type_code() {
		return pay_type_code;
	}
	public void setPay_type_code(String pay_type_code) {
		this.pay_type_code = pay_type_code;
	}
	public String getPay_type_name() {
		return pay_type_name;
	}
	public void setPay_type_name(String pay_type_name) {
		this.pay_type_name = pay_type_name;
	}
	public String getStation_code() {
		return station_code;
	}
	public void setStation_code(String station_code) {
		this.station_code = station_code;
	}
	public String getDuty_code() {
		return duty_code;
	}
	public void setDuty_code(String duty_code) {
		this.duty_code = duty_code;
	}
	public String getId_number() {
		return id_number;
	}
	public void setId_number(String id_number) {
		this.id_number = id_number;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getIs_buyer() {
		return is_buyer;
	}
	public void setIs_buyer(String is_buyer) {
		this.is_buyer = is_buyer;
	}
	public String getEmp_no() {
		return emp_no;
	}
	public void setEmp_no(String emp_no) {
		this.emp_no = emp_no;
	}
	
}
