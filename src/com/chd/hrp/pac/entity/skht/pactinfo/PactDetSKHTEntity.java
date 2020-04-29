package com.chd.hrp.pac.entity.skht.pactinfo;

import java.io.Serializable;
import java.util.Date;

/**
 * 付款合同明细
 * 
 * @author haotong
 * @date 2018年9月12日 下午2:01:53
 */
public class PactDetSKHTEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8493944686943580601L;
	private Integer group_id;
	private Integer hos_id;
	private String copy_code;
	private String pact_code;
	private Integer detail_id;
	private String subject_type;
	private String subject_type_name;
	private Integer subject_id;
	private Integer subject_no;
	private String subject_name;
	private String item_spec;
	private String item_model;
	private Integer amount;
	private Double price;
	private Double money;
	private Date arrive_date;
	private Integer keep_repair_month;
	private String note;
	private Integer dept_id;
	private Integer dept_no;
	private String dept_name;
	private String unit_code;
	private String unit_name;

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

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Date getArrive_date() {
		return arrive_date;
	}

	public void setArrive_date(Date arrive_date) {
		this.arrive_date = arrive_date;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

	public Integer getKeep_repair_month() {
		return keep_repair_month;
	}

	public void setKeep_repair_month(Integer keep_repair_month) {
		this.keep_repair_month = keep_repair_month;
	}

	public String getSubject_type_name() {
		return subject_type_name;
	}

	public void setSubject_type_name(String subject_type_name) {
		this.subject_type_name = subject_type_name;
	}

	public String getSubject_name() {
		return subject_name;
	}

	public void setSubject_name(String subject_name) {
		this.subject_name = subject_name;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

}
