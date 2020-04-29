package com.chd.hrp.htcg.entity.calculation;

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

public class HtcgChargeCostHos implements Serializable {

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
	 private String charge_item_code;
	 private String charge_item_name;
	 private double charge_num;
	 private double charge_price;
	 private double charge_money;
	 private double dir_cost;
	 private double pub_cost;
	 private double man_cost;
	 private double mea_cost;
	 private double cost_price;
	 private double cost_money;
	 private double profit_money;
	 private long source_id;
	 private String source_code;
	 private String source_name;
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
	public String getCharge_item_code() {
		return charge_item_code;
	}
	public String getCharge_item_name() {
		return charge_item_name;
	}
	public double getCharge_num() {
		return charge_num;
	}
	public double getCharge_price() {
		return charge_price;
	}
	public double getCharge_money() {
		return charge_money;
	}
	public double getDir_cost() {
		return dir_cost;
	}
	public double getPub_cost() {
		return pub_cost;
	}
	public double getMan_cost() {
		return man_cost;
	}
	public double getMea_cost() {
		return mea_cost;
	}
	public double getCost_price() {
		return cost_price;
	}
	public double getCost_money() {
		return cost_money;
	}
	public double getProfit_money() {
		return profit_money;
	}
	public long getSource_id() {
		return source_id;
	}
	public String getSource_code() {
		return source_code;
	}
	public String getSource_name() {
		return source_name;
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
	public void setCharge_item_code(String charge_item_code) {
		this.charge_item_code = charge_item_code;
	}
	public void setCharge_item_name(String charge_item_name) {
		this.charge_item_name = charge_item_name;
	}
	public void setCharge_num(double charge_num) {
		this.charge_num = charge_num;
	}
	public void setCharge_price(double charge_price) {
		this.charge_price = charge_price;
	}
	public void setCharge_money(double charge_money) {
		this.charge_money = charge_money;
	}
	public void setDir_cost(double dir_cost) {
		this.dir_cost = dir_cost;
	}
	public void setPub_cost(double pub_cost) {
		this.pub_cost = pub_cost;
	}
	public void setMan_cost(double man_cost) {
		this.man_cost = man_cost;
	}
	public void setMea_cost(double mea_cost) {
		this.mea_cost = mea_cost;
	}
	public void setCost_price(double cost_price) {
		this.cost_price = cost_price;
	}
	public void setCost_money(double cost_money) {
		this.cost_money = cost_money;
	}
	public void setProfit_money(double profit_money) {
		this.profit_money = profit_money;
	}
	public void setSource_id(long source_id) {
		this.source_id = source_id;
	}
	public void setSource_code(String source_code) {
		this.source_code = source_code;
	}
	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}
	
}