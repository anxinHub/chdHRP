/*
 *
 */package com.chd.hrp.htcg.entity.calculation;

import java.io.Serializable;
import java.util.*;
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

public class HtcgDrugAdminCost implements Serializable {

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
	private String drug_type_code;
	private String drug_type_name;
	private String drug_code;
	private String drug_name;
	private String mode_code;
	private String unit_code;
	private String unit_name;
	private long fac_no;
	private long fac_id;
	private String fac_code;
	private String fac_name;
	private double amount;
	private double price;
	private double income_money;
	private double admin_cost_price;
	private double admin_cost_money;
	private String note;
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
	public String getDrug_type_code() {
		return drug_type_code;
	}
	public String getDrug_type_name() {
		return drug_type_name;
	}
	public String getDrug_code() {
		return drug_code;
	}
	public String getDrug_name() {
		return drug_name;
	}
	public String getMode_code() {
		return mode_code;
	}
	public String getUnit_code() {
		return unit_code;
	}
	public String getUnit_name() {
		return unit_name;
	}
	public long getFac_no() {
		return fac_no;
	}
	public long getFac_id() {
		return fac_id;
	}
	public String getFac_code() {
		return fac_code;
	}
	public String getFac_name() {
		return fac_name;
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
	public double getAdmin_cost_price() {
		return admin_cost_price;
	}
	public double getAdmin_cost_money() {
		return admin_cost_money;
	}
	public String getNote() {
		return note;
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
	public void setDrug_type_code(String drug_type_code) {
		this.drug_type_code = drug_type_code;
	}
	public void setDrug_type_name(String drug_type_name) {
		this.drug_type_name = drug_type_name;
	}
	public void setDrug_code(String drug_code) {
		this.drug_code = drug_code;
	}
	public void setDrug_name(String drug_name) {
		this.drug_name = drug_name;
	}
	public void setMode_code(String mode_code) {
		this.mode_code = mode_code;
	}
	public void setUnit_code(String unit_code) {
		this.unit_code = unit_code;
	}
	public void setUnit_name(String unit_name) {
		this.unit_name = unit_name;
	}
	public void setFac_no(long fac_no) {
		this.fac_no = fac_no;
	}
	public void setFac_id(long fac_id) {
		this.fac_id = fac_id;
	}
	public void setFac_code(String fac_code) {
		this.fac_code = fac_code;
	}
	public void setFac_name(String fac_name) {
		this.fac_name = fac_name;
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
	public void setAdmin_cost_price(double admin_cost_price) {
		this.admin_cost_price = admin_cost_price;
	}
	public void setAdmin_cost_money(double admin_cost_money) {
		this.admin_cost_money = admin_cost_money;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
}