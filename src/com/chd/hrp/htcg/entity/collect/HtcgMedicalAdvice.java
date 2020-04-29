package com.chd.hrp.htcg.entity.collect;
import java.io.Serializable;
import java.util.Date;
public class HtcgMedicalAdvice implements Serializable {
	private static final long serialVersionUID = 1L;
	private long group_id;
	private long hos_id;
	private String copy_code;
	private String mr_no;
	private String in_hos_no;
	private String patient_name;
	private Date advice_date;
	private long order_by_no;
	private long order_by_id;
	private String order_by_code;
	private String order_by_name;
	private long perform_by_no;
	private long perform_by_id;
	private String perform_by_code;
	private String perform_by_name;
	private String charge_item_code;
	private String charge_item_name;
	private Double amount;
	private Double price;
	private Double income_money;
	private String recipe_type_code;
	private String recipe_type_name;
	private String place;
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
	public Date getAdvice_date() {
		return advice_date;
	}
	public long getOrder_by_no() {
		return order_by_no;
	}
	public long getOrder_by_id() {
		return order_by_id;
	}
	public String getOrder_by_code() {
		return order_by_code;
	}
	public String getOrder_by_name() {
		return order_by_name;
	}
	public long getPerform_by_no() {
		return perform_by_no;
	}
	public long getPerform_by_id() {
		return perform_by_id;
	}
	public String getPerform_by_code() {
		return perform_by_code;
	}
	public String getPerform_by_name() {
		return perform_by_name;
	}
	public String getCharge_item_code() {
		return charge_item_code;
	}
	public String getCharge_item_name() {
		return charge_item_name;
	}
	public Double getAmount() {
		return amount;
	}
	public Double getPrice() {
		return price;
	}
	public Double getIncome_money() {
		return income_money;
	}
	public String getRecipe_type_code() {
		return recipe_type_code;
	}
	public String getRecipe_type_name() {
		return recipe_type_name;
	}
	public String getPlace() {
		return place;
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
	public void setAdvice_date(Date advice_date) {
		this.advice_date = advice_date;
	}
	public void setOrder_by_no(long order_by_no) {
		this.order_by_no = order_by_no;
	}
	public void setOrder_by_id(long order_by_id) {
		this.order_by_id = order_by_id;
	}
	public void setOrder_by_code(String order_by_code) {
		this.order_by_code = order_by_code;
	}
	public void setOrder_by_name(String order_by_name) {
		this.order_by_name = order_by_name;
	}
	public void setPerform_by_no(long perform_by_no) {
		this.perform_by_no = perform_by_no;
	}
	public void setPerform_by_id(long perform_by_id) {
		this.perform_by_id = perform_by_id;
	}
	public void setPerform_by_code(String perform_by_code) {
		this.perform_by_code = perform_by_code;
	}
	public void setPerform_by_name(String perform_by_name) {
		this.perform_by_name = perform_by_name;
	}
	public void setCharge_item_code(String charge_item_code) {
		this.charge_item_code = charge_item_code;
	}
	public void setCharge_item_name(String charge_item_name) {
		this.charge_item_name = charge_item_name;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public void setIncome_money(Double income_money) {
		this.income_money = income_money;
	}
	public void setRecipe_type_code(String recipe_type_code) {
		this.recipe_type_code = recipe_type_code;
	}
	public void setRecipe_type_name(String recipe_type_name) {
		this.recipe_type_name = recipe_type_name;
	}
	public void setPlace(String place) {
		this.place = place;
	}
}
