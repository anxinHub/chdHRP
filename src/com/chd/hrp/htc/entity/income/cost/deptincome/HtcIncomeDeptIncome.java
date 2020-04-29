package com.chd.hrp.htc.entity.income.cost.deptincome;

import java.io.Serializable;
import java.util.*;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public class HtcIncomeDeptIncome implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	 private long group_id;
	 private long hos_id;
	 private String copy_code;
	 private String  acc_year;
	 private String  acc_month;
	 private long income_detail_id;
	 private long appl_dept_id;
     private long appl_dept_no;
     private String appl_dept_code;
     private String appl_dept_name;
     private long exec_dept_id;
     private long exec_dept_no;
     private String exec_dept_code;
     private String exec_dept_name;
     private long charge_kind_id;
     private String charge_kind_code;
     private String charge_kind_name;
     private long charge_item_id;
     private String charge_item_code;
     private String charge_item_name;
     private double price;
     private double num;
     private double money;
     private String busi_data_source_code;
     private String busi_data_source_name;
     private long create_user;
     private Date  create_date;
     
		public long getGroup_id() {
			return group_id;
		}
		public long getHos_id() {
			return hos_id;
		}
		public String getCopy_code() {
			return copy_code;
		}
		public String getAcc_year() {
			return acc_year;
		}
		public String getAcc_month() {
			return acc_month;
		}
		public long getIncome_detail_id() {
			return income_detail_id;
		}
		public long getAppl_dept_id() {
			return appl_dept_id;
		}
		public long getAppl_dept_no() {
			return appl_dept_no;
		}
		public String getAppl_dept_code() {
			return appl_dept_code;
		}
		public String getAppl_dept_name() {
			return appl_dept_name;
		}
		public long getExec_dept_id() {
			return exec_dept_id;
		}
		public long getExec_dept_no() {
			return exec_dept_no;
		}
		public String getExec_dept_code() {
			return exec_dept_code;
		}
		public String getExec_dept_name() {
			return exec_dept_name;
		}
		public long getCharge_kind_id() {
			return charge_kind_id;
		}
		public String getCharge_kind_code() {
			return charge_kind_code;
		}
		public String getCharge_kind_name() {
			return charge_kind_name;
		}
		public long getCharge_item_id() {
			return charge_item_id;
		}
		public String getCharge_item_code() {
			return charge_item_code;
		}
		public String getCharge_item_name() {
			return charge_item_name;
		}
		public double getPrice() {
			return price;
		}
		public double getNum() {
			return num;
		}
		public double getMoney() {
			return money;
		}
		public String getBusi_data_source_code() {
			return busi_data_source_code;
		}
		public String getBusi_data_source_name() {
			return busi_data_source_name;
		}
		public long getCreate_user() {
			return create_user;
		}
		public Date getCreate_date() {
			return create_date;
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
		public void setAcc_year(String acc_year) {
			this.acc_year = acc_year;
		}
		public void setAcc_month(String acc_month) {
			this.acc_month = acc_month;
		}
		public void setIncome_detail_id(long income_detail_id) {
			this.income_detail_id = income_detail_id;
		}
		public void setAppl_dept_id(long appl_dept_id) {
			this.appl_dept_id = appl_dept_id;
		}
		public void setAppl_dept_no(long appl_dept_no) {
			this.appl_dept_no = appl_dept_no;
		}
		public void setAppl_dept_code(String appl_dept_code) {
			this.appl_dept_code = appl_dept_code;
		}
		public void setAppl_dept_name(String appl_dept_name) {
			this.appl_dept_name = appl_dept_name;
		}
		public void setExec_dept_id(long exec_dept_id) {
			this.exec_dept_id = exec_dept_id;
		}
		public void setExec_dept_no(long exec_dept_no) {
			this.exec_dept_no = exec_dept_no;
		}
		public void setExec_dept_code(String exec_dept_code) {
			this.exec_dept_code = exec_dept_code;
		}
		public void setExec_dept_name(String exec_dept_name) {
			this.exec_dept_name = exec_dept_name;
		}
		public void setCharge_kind_id(long charge_kind_id) {
			this.charge_kind_id = charge_kind_id;
		}
		public void setCharge_kind_code(String charge_kind_code) {
			this.charge_kind_code = charge_kind_code;
		}
		public void setCharge_kind_name(String charge_kind_name) {
			this.charge_kind_name = charge_kind_name;
		}
		public void setCharge_item_id(long charge_item_id) {
			this.charge_item_id = charge_item_id;
		}
		public void setCharge_item_code(String charge_item_code) {
			this.charge_item_code = charge_item_code;
		}
		public void setCharge_item_name(String charge_item_name) {
			this.charge_item_name = charge_item_name;
		}
		public void setPrice(double price) {
			this.price = price;
		}
		public void setNum(double num) {
			this.num = num;
		}
		public void setMoney(double money) {
			this.money = money;
		}
		public void setBusi_data_source_code(String busi_data_source_code) {
			this.busi_data_source_code = busi_data_source_code;
		}
		public void setBusi_data_source_name(String busi_data_source_name) {
			this.busi_data_source_name = busi_data_source_name;
		}
		public void setCreate_user(long create_user) {
			this.create_user = create_user;
		}
		public void setCreate_date(Date create_date) {
			this.create_date = create_date;
		}
}