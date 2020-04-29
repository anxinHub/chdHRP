package com.chd.hrp.htcg.entity.setout;

import java.io.Serializable;
/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

public class HtcgDrugAdviceDmerge implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private long group_id;
	private long hos_id;
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
	private String drug_code;
	private String drug_name;
	private Double amount;
	private Double price;
	private Double income_money;
	private String recipe_type_code;
	private String recipe_type_name;
	private String clp_d_step;
	private String recipe_merge_code;
	public long getGroup_id() {
		return group_id;
	}
	public long getHos_id() {
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
	public String getDrug_code() {
		return drug_code;
	}
	public String getDrug_name() {
		return drug_name;
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
	public String getClp_d_step() {
		return clp_d_step;
	}
	public String getRecipe_merge_code() {
		return recipe_merge_code;
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
	public void setDrug_code(String drug_code) {
		this.drug_code = drug_code;
	}
	public void setDrug_name(String drug_name) {
		this.drug_name = drug_name;
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
	public void setClp_d_step(String clp_d_step) {
		this.clp_d_step = clp_d_step;
	}
	public void setRecipe_merge_code(String recipe_merge_code) {
		this.recipe_merge_code = recipe_merge_code;
	}
}