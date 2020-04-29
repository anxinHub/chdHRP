package com.chd.hrp.mat.entity;

import java.io.Serializable;
import java.util.Date;

public class MatAdvice implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer group_id;
	private Integer hos_id;
	private String copy_code;
	private Integer receive_id;
	private Date charge_date;
	private Integer advice_no;
	private String charge_item_id;
	private String charge_item_name;
	private String charge_detail_code;
	private Integer inv_no;
	private Integer inv_id;
	private String inv_code;
	private String inv_name;
	private String inv_model;
	private String unit_name;
	private String batch_no;
	private String bar_code;
	private Integer charge_num;
	private Integer out_num;
	private Double charge_price;
	private Integer order_dept_id;
	private Integer order_dept_no;
	private String order_dept_code;
	private String order_dept_name;
	private Integer exec_dept_id;
	private Integer exec_dept_no;
	private String exec_dept_code;
	private String exec_dept_name;
	private String charge_code;
	private String charge_name;
	private String doctor_code;
	private String doctor_name;
	private String patient_name;
	private String hospital_no;
	private String bed_no;
	private Integer doc_type;
	private String message;
	private Integer hx_flag;
	private String out_no;
	
	private Integer dept_id;
	private Integer dept_no;
	private String dept_code;
	private String dept_name;
	private Integer store_id;
	private Integer store_no;
	
	private String busi_code;
	private String sup_code;
	public String getSup_code() {
		return sup_code;
	}
	public void setSup_code(String sup_code) {
		this.sup_code = sup_code;
	}
	private String sup_name;
	
	
	public String getSup_name() {
		return sup_name;
	}
	public void setSup_name(String sup_name) {
		this.sup_name = sup_name;
	}
	public String getBusi_code() {
		return busi_code;
	}
	public void setBusi_code(String busi_code) {
		this.busi_code = busi_code;
	}
	public Integer getStore_id() {
		return store_id;
	}
	public void setStore_id(Integer store_id) {
		this.store_id = store_id;
	}
	public Integer getStore_no() {
		return store_no;
	}
	public void setStore_no(Integer store_no) {
		this.store_no = store_no;
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
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public String getOut_no() {
		return out_no;
	}
	public void setOut_no(String out_no) {
		this.out_no = out_no;
	}
	public String getInv_model() {
		return inv_model;
	}
	public void setInv_model(String inv_model) {
		this.inv_model = inv_model;
	}
	public String getUnit_name() {
		return unit_name;
	}
	public void setUnit_name(String unit_name) {
		this.unit_name = unit_name;
	}
	public String getCharge_item_name() {
		return charge_item_name;
	}
	public void setCharge_item_name(String charge_item_name) {
		this.charge_item_name = charge_item_name;
	}
	public String getInv_name() {
		return inv_name;
	}
	public void setInv_name(String inv_name) {
		this.inv_name = inv_name;
	}
	public String getOrder_dept_name() {
		return order_dept_name;
	}
	public void setOrder_dept_name(String order_dept_name) {
		this.order_dept_name = order_dept_name;
	}
	public String getExec_dept_name() {
		return exec_dept_name;
	}
	public void setExec_dept_name(String exec_dept_name) {
		this.exec_dept_name = exec_dept_name;
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
	public Integer getReceive_id() {
		return receive_id;
	}
	public void setReceive_id(Integer receive_id) {
		this.receive_id = receive_id;
	}
	public Date getCharge_date() {
		return charge_date;
	}
	public void setCharge_date(Date charge_date) {
		this.charge_date = charge_date;
	}
	public Integer getAdvice_no() {
		return advice_no;
	}
	public void setAdvice_no(Integer advice_no) {
		this.advice_no = advice_no;
	}
	public String getCharge_item_id() {
		return charge_item_id;
	}
	public void setCharge_item_id(String charge_item_id) {
		this.charge_item_id = charge_item_id;
	}
	public String getCharge_detail_code() {
		return charge_detail_code;
	}
	public void setCharge_detail_code(String charge_detail_code) {
		this.charge_detail_code = charge_detail_code;
	}
	public Integer getInv_no() {
		return inv_no;
	}
	public void setInv_no(Integer inv_no) {
		this.inv_no = inv_no;
	}
	public Integer getInv_id() {
		return inv_id;
	}
	public void setInv_id(Integer inv_id) {
		this.inv_id = inv_id;
	}
	public String getInv_code() {
		return inv_code;
	}
	public void setInv_code(String inv_code) {
		this.inv_code = inv_code;
	}
	public String getBatch_no() {
		return batch_no;
	}
	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}
	public String getBar_code() {
		return bar_code;
	}
	public void setBar_code(String bar_code) {
		this.bar_code = bar_code;
	}
	public Integer getCharge_num() {
		return charge_num;
	}
	public void setCharge_num(Integer charge_num) {
		this.charge_num = charge_num;
	}
	public Integer getOut_num() {
		return out_num;
	}
	public void setOut_num(Integer out_num) {
		this.out_num = out_num;
	}
	public Double getCharge_price() {
		return charge_price;
	}
	public void setCharge_price(Double charge_price) {
		this.charge_price = charge_price;
	}
	public Integer getOrder_dept_id() {
		return order_dept_id;
	}
	public void setOrder_dept_id(Integer order_dept_id) {
		this.order_dept_id = order_dept_id;
	}
	public Integer getOrder_dept_no() {
		return order_dept_no;
	}
	public void setOrder_dept_no(Integer order_dept_no) {
		this.order_dept_no = order_dept_no;
	}
	public String getOrder_dept_code() {
		return order_dept_code;
	}
	public void setOrder_dept_code(String order_dept_code) {
		this.order_dept_code = order_dept_code;
	}
	public Integer getExec_dept_id() {
		return exec_dept_id;
	}
	public void setExec_dept_id(Integer exec_dept_id) {
		this.exec_dept_id = exec_dept_id;
	}
	public Integer getExec_dept_no() {
		return exec_dept_no;
	}
	public void setExec_dept_no(Integer exec_dept_no) {
		this.exec_dept_no = exec_dept_no;
	}
	public String getExec_dept_code() {
		return exec_dept_code;
	}
	public void setExec_dept_code(String exec_dept_code) {
		this.exec_dept_code = exec_dept_code;
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
	public String getDoctor_code() {
		return doctor_code;
	}
	public void setDoctor_code(String doctor_code) {
		this.doctor_code = doctor_code;
	}
	public String getDoctor_name() {
		return doctor_name;
	}
	public void setDoctor_name(String doctor_name) {
		this.doctor_name = doctor_name;
	}
	public String getPatient_name() {
		return patient_name;
	}
	public void setPatient_name(String patient_name) {
		this.patient_name = patient_name;
	}
	public String getHospital_no() {
		return hospital_no;
	}
	public void setHospital_no(String hospital_no) {
		this.hospital_no = hospital_no;
	}
	public String getBed_no() {
		return bed_no;
	}
	public void setBed_no(String bed_no) {
		this.bed_no = bed_no;
	}
	public Integer getDoc_type() {
		return doc_type;
	}
	public void setDoc_type(Integer doc_type) {
		this.doc_type = doc_type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getHx_flag() {
		return hx_flag;
	}
	public void setHx_flag(Integer hx_flag) {
		this.hx_flag = hx_flag;
	}
	
}
