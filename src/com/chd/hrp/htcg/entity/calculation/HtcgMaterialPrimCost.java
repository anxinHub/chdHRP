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

public class HtcgMaterialPrimCost implements Serializable {

	
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
	private String mate_type_code;
	private String mate_type_name;
	private String mate_code;
	private String mate_name;
	private String mate_mode;
	private String meas_code;
	private String meas_name;
	private long fac_no;
	private long fac_id;
	private String fac_code;
	private String fac_name;
	private double amount;
	private double price;
	private double income_money;
	private double cost_price;
	private double cost_money;
	private double markup_percent;
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
	public String getMate_type_code() {
		return mate_type_code;
	}
	public String getMate_type_name() {
		return mate_type_name;
	}
	public String getMate_code() {
		return mate_code;
	}
	public String getMate_name() {
		return mate_name;
	}
	public String getMate_mode() {
		return mate_mode;
	}
	public String getMeas_code() {
		return meas_code;
	}
	public String getMeas_name() {
		return meas_name;
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
	public double getCost_price() {
		return cost_price;
	}
	public double getCost_money() {
		return cost_money;
	}
	public double getMarkup_percent() {
		return markup_percent;
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
	public void setMate_type_code(String mate_type_code) {
		this.mate_type_code = mate_type_code;
	}
	public void setMate_type_name(String mate_type_name) {
		this.mate_type_name = mate_type_name;
	}
	public void setMate_code(String mate_code) {
		this.mate_code = mate_code;
	}
	public void setMate_name(String mate_name) {
		this.mate_name = mate_name;
	}
	public void setMate_mode(String mate_mode) {
		this.mate_mode = mate_mode;
	}
	public void setMeas_code(String meas_code) {
		this.meas_code = meas_code;
	}
	public void setMeas_name(String meas_name) {
		this.meas_name = meas_name;
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
	public void setCost_price(double cost_price) {
		this.cost_price = cost_price;
	}
	public void setCost_money(double cost_money) {
		this.cost_money = cost_money;
	}
	public void setMarkup_percent(double markup_percent) {
		this.markup_percent = markup_percent;
	}
}