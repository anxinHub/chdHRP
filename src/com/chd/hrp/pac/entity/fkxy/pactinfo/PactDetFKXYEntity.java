package com.chd.hrp.pac.entity.fkxy.pactinfo;

import java.io.Serializable;

public class PactDetFKXYEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 108331936778673951L;

	private Integer group_id;
	private Integer hos_id;
	private String copy_code;
	private String pact_code;
	private Integer detail_id;
	private String subject_type;
	private Integer subject_id;
	private Integer subject_no;
	private String item_spec;
	private String item_model;
	private Double price;
	private String note;
	private String subject_type_name;
	private String subject_name;
	private Integer fac_id;
	private Integer fac_no;
	private String fac_name;
	private String unit_code;
	private String unit_name;
	private String item_name;
	private String change_code;
	
	
	public String getChange_code() {
		return change_code;
	}

	public void setChange_code(String change_code) {
		this.change_code = change_code;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public Integer getFac_id() {
		return fac_id;
	}

	public void setFac_id(Integer fac_id) {
		this.fac_id = fac_id;
	}

	public Integer getFac_no() {
		return fac_no;
	}

	public void setFac_no(Integer fac_no) {
		this.fac_no = fac_no;
	}

	public String getFac_name() {
		return fac_name;
	}

	public void setFac_name(String fac_name) {
		this.fac_name = fac_name;
	}

	public String getUnit_code() {
		return unit_code;
	}

	public void setUnit_code(String unit_code) {
		this.unit_code = unit_code;
	}

	public String getUnit_name() {
		return unit_name;
	}

	public void setUnit_name(String unit_name) {
		this.unit_name = unit_name;
	}

	public String getSubject_type_name() {
		return subject_type_name;
	}

	public void setSubject_type_name(String subject_type_name) {
		this.subject_type_name = subject_type_name;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public String getPact_code() {
		return pact_code;
	}

	public void setPact_code(String pact_code) {
		this.pact_code = pact_code;
	}

	public Integer getDetail_id() {
		return detail_id;
	}

	public void setDetail_id(Integer detail_id) {
		this.detail_id = detail_id;
	}

	public String getSubject_type() {
		return subject_type;
	}

	public void setSubject_type(String subject_type) {
		this.subject_type = subject_type;
	}

	public Integer getSubject_id() {
		return subject_id;
	}

	public void setSubject_id(Integer subject_id) {
		this.subject_id = subject_id;
	}

	public Integer getSubject_no() {
		return subject_no;
	}

	public void setSubject_no(Integer subject_no) {
		this.subject_no = subject_no;
	}

	public String getItem_spec() {
		return item_spec;
	}

	public void setItem_spec(String item_spec) {
		this.item_spec = item_spec;
	}

	public String getItem_model() {
		return item_model;
	}

	public void setItem_model(String item_model) {
		this.item_model = item_model;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getSubject_name() {
		return subject_name;
	}

	public void setSubject_name(String subject_name) {
		this.subject_name = subject_name;
	}

}
