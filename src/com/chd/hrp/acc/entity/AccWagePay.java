package com.chd.hrp.acc.entity;

import java.io.Serializable;
import java.util.Date;

public class AccWagePay implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long group_id;
	
	private Long hos_id;
	
	private String copy_code;
	
	private String wage_code;
	
	private String wage_name;
	
	private String acc_year;
	
	private String acc_month;
	
	private Long emp_id;
	
	private Long pay_id;
	
	private Long emp_no;
	
	private Long dept_id;
	
	private Long dept_no;
	
	private String dept_name;
	
	private String kind_code;
	
	private String kind_name;
	
	private String pay_type_code;
	
	private String pay_type_name;
	
	private String station_code;
	
	private String station_name;
	
	private String duty_code;
	
	private String duty_name;
	
	private String sex;
	
	private String id_number;
	
	private Long user_id;
	
	private Date create_date;
	
	private String note;
	
	private Integer state;
	
	private Integer pay_state;
	
	private String item_code;
	
	private String item_name;

	private String scheme_id;
	
	private String scheme_code;
	
	private String scheme_name;
	
	private String emp_code;
	
	private String emp_name;
	
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

	public String getWage_code() {
		return wage_code;
	}

	public void setWage_code(String wage_code) {
		this.wage_code = wage_code;
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

	public Long getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(Long emp_id) {
		this.emp_id = emp_id;
	}

	public Long getPay_id() {
		return pay_id;
	}

	public void setPay_id(Long pay_id) {
		this.pay_id = pay_id;
	}

	public Long getEmp_no() {
		return emp_no;
	}

	public void setEmp_no(Long emp_no) {
		this.emp_no = emp_no;
	}

	public Long getDept_id() {
		return dept_id;
	}

	public void setDept_id(Long dept_id) {
		this.dept_id = dept_id;
	}

	public Long getDept_no() {
		return dept_no;
	}

	public void setDept_no(Long dept_no) {
		this.dept_no = dept_no;
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

	public String getStation_name() {
		return station_name;
	}

	public void setStation_name(String station_name) {
		this.station_name = station_name;
	}

	public String getDuty_code() {
		return duty_code;
	}

	public void setDuty_code(String duty_code) {
		this.duty_code = duty_code;
	}

	public String getDuty_name() {
		return duty_name;
	}

	public void setDuty_name(String duty_name) {
		this.duty_name = duty_name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getId_number() {
		return id_number;
	}

	public void setId_number(String id_number) {
		this.id_number = id_number;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getWage_name() {
		return wage_name;
	}

	public void setWage_name(String wage_name) {
		this.wage_name = wage_name;
	}

	public String getItem_code() {
		return item_code;
	}

	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getScheme_id() {
		return scheme_id;
	}

	public void setScheme_id(String scheme_id) {
		this.scheme_id = scheme_id;
	}

	public String getScheme_code() {
		return scheme_code;
	}

	public void setScheme_code(String scheme_code) {
		this.scheme_code = scheme_code;
	}

	public String getScheme_name() {
		return scheme_name;
	}

	public void setScheme_name(String scheme_name) {
		this.scheme_name = scheme_name;
	}

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

	public Integer getPay_state() {
		return pay_state;
	}

	public void setPay_state(Integer pay_state) {
		this.pay_state = pay_state;
	}
	
}
