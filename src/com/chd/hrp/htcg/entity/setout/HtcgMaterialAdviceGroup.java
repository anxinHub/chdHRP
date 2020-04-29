package com.chd.hrp.htcg.entity.setout;

import java.io.Serializable;
import java.util.Date;

public class HtcgMaterialAdviceGroup implements Serializable{
	private static final long serialVersionUID = 2481542810867711192L;
	private Long group_id;
	private Long hos_id;
	private String copy_code;
	private String period_type_code;
	private String period_type_name;
	private String period_code;
	private String period_name;
	private String acc_year;
	private String acc_month;
	private String scheme_code;
	private String scheme_name;
	private String drgs_code;
	private String drgs_name;
	private String mr_no;
	private String in_hos_no;
	private Date advice_date;
	private long order_by_no;
	private long order_by_id;
	private String order_by_code;
	private String order_by_name;
	private long perform_by_no;
	private long perform_by_id;	
	private String perform_by_code;
	private String perform_by_name;
	private String mate_code;
	private String mate_name;
	private double amount;
	private double price;
	private double income_money;
	private String recipe_type_code;
	private String recipe_type_name;
	private String place;
	public Long getGroup_id() {
		return group_id;
	}
	public Long getHos_id() {
		return hos_id;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public String getPeriod_type_code() {
		return period_type_code;
	}
	public String getPeriod_type_name() {
		return period_type_name;
	}
	public String getPeriod_code() {
		return period_code;
	}
	public String getPeriod_name() {
		return period_name;
	}
	public String getAcc_year() {
		return acc_year;
	}
	public String getAcc_month() {
		return acc_month;
	}
	public String getScheme_code() {
		return scheme_code;
	}
	public String getScheme_name() {
		return scheme_name;
	}
	public String getDrgs_code() {
		return drgs_code;
	}
	public String getDrgs_name() {
		return drgs_name;
	}
	public String getMr_no() {
		return mr_no;
	}
	public String getIn_hos_no() {
		return in_hos_no;
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
	public String getMate_code() {
		return mate_code;
	}
	public String getMate_name() {
		return mate_name;
	}
	public double getAmount() {
		return amount;
	}
	public double getPrice() {
		return price;
	}
	public double getIncome_money() {
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
	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}
	public void setHos_id(Long hos_id) {
		this.hos_id = hos_id;
	}
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	public void setPeriod_type_code(String period_type_code) {
		this.period_type_code = period_type_code;
	}
	public void setPeriod_type_name(String period_type_name) {
		this.period_type_name = period_type_name;
	}
	public void setPeriod_code(String period_code) {
		this.period_code = period_code;
	}
	public void setPeriod_name(String period_name) {
		this.period_name = period_name;
	}
	public void setAcc_year(String acc_year) {
		this.acc_year = acc_year;
	}
	public void setAcc_month(String acc_month) {
		this.acc_month = acc_month;
	}
	public void setScheme_code(String scheme_code) {
		this.scheme_code = scheme_code;
	}
	public void setScheme_name(String scheme_name) {
		this.scheme_name = scheme_name;
	}
	public void setDrgs_code(String drgs_code) {
		this.drgs_code = drgs_code;
	}
	public void setDrgs_name(String drgs_name) {
		this.drgs_name = drgs_name;
	}
	public void setMr_no(String mr_no) {
		this.mr_no = mr_no;
	}
	public void setIn_hos_no(String in_hos_no) {
		this.in_hos_no = in_hos_no;
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
	public void setMate_code(String mate_code) {
		this.mate_code = mate_code;
	}
	public void setMate_name(String mate_name) {
		this.mate_name = mate_name;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public void setIncome_money(double income_money) {
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
