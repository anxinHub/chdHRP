package com.chd.hrp.med.entity;

import java.io.Serializable;

public class MedAccountBalanceInvHold implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long inv_id; 
	private String inv_code; 
	private String memory_encoding;
	private String inv_name; 
	private String inv_model; 
	private String unit_name; 
	private String med_type_code; 
	private String med_type_name; 
	private Double begin_price; 
	private Integer begin_amount; 
	private Double begin_money; 
	private Double in_price; 
	private Integer in_amount; 
	private Double in_money; 
	private Double out_price; 
	private Integer out_amount; 
	private Double out_money; 
	private Double end_price; 
	private Integer end_amount; 
	private Double end_money;
	public Long getInv_id() {
		return inv_id;
	}
	public String getInv_code() {
		return inv_code;
	}
	public String getInv_name() {
		return inv_name;
	}
	public String getInv_model() {
		return inv_model;
	}
	public String getUnit_name() {
		return unit_name;
	}
	public String getMed_type_code() {
		return med_type_code;
	}
	public String getMed_type_name() {
		return med_type_name;
	}
	public Double getBegin_price() {
		return begin_price;
	}
	public Integer getBegin_amount() {
		return begin_amount;
	}
	public Double getBegin_money() {
		return begin_money;
	}
	public Double getIn_price() {
		return in_price;
	}
	public Integer getIn_amount() {
		return in_amount;
	}
	public Double getIn_money() {
		return in_money;
	}
	public Double getOut_price() {
		return out_price;
	}
	public Integer getOut_amount() {
		return out_amount;
	}
	public Double getOut_money() {
		return out_money;
	}
	public Double getEnd_price() {
		return end_price;
	}
	public Integer getEnd_amount() {
		return end_amount;
	}
	public Double getEnd_money() {
		return end_money;
	}
	public void setInv_id(Long inv_id) {
		this.inv_id = inv_id;
	}
	public void setInv_code(String inv_code) {
		this.inv_code = inv_code;
	}
	public void setInv_name(String inv_name) {
		this.inv_name = inv_name;
	}
	public void setInv_model(String inv_model) {
		this.inv_model = inv_model;
	}
	public void setUnit_name(String unit_name) {
		this.unit_name = unit_name;
	}
	public void setMed_type_code(String med_type_code) {
		this.med_type_code = med_type_code;
	}
	public void setMed_type_name(String med_type_name) {
		this.med_type_name = med_type_name;
	}
	public void setBegin_price(Double begin_price) {
		this.begin_price = begin_price;
	}
	public void setBegin_amount(Integer begin_amount) {
		this.begin_amount = begin_amount;
	}
	public void setBegin_money(Double begin_money) {
		this.begin_money = begin_money;
	}
	public void setIn_price(Double in_price) {
		this.in_price = in_price;
	}
	public void setIn_amount(Integer in_amount) {
		this.in_amount = in_amount;
	}
	public void setIn_money(Double in_money) {
		this.in_money = in_money;
	}
	public void setOut_price(Double out_price) {
		this.out_price = out_price;
	}
	public void setOut_amount(Integer out_amount) {
		this.out_amount = out_amount;
	}
	public void setOut_money(Double out_money) {
		this.out_money = out_money;
	}
	public void setEnd_price(Double end_price) {
		this.end_price = end_price;
	}
	public void setEnd_amount(Integer end_amount) {
		this.end_amount = end_amount;
	}
	public void setEnd_money(Double end_money) {
		this.end_money = end_money;
	}
	public String getMemory_encoding() {
		return memory_encoding;
	}
	public void setMemory_encoding(String memory_encoding) {
		this.memory_encoding = memory_encoding;
	}
}
