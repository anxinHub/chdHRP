/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.entity;

import java.io.Serializable;
import java.util.Date;

/**
* @Title. @Description.
* 单据核销<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class AccMpayVeri implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_id;
	
	private Long hos_id;
	
	private String copy_code;
	
	private String acc_year;
	
	private Long veri_id;
	
	private Long pay_id;
	
	private double veri_money;
	
	private String create_user;
	
	private Date create_date;
	
	private String sup_name;
	
	private String in_no;
	
	private String bill_no;
	
	private Date in_date;
	
	private double amount_money;
	
	private double pay_money;

	public Long getGroup_id() {
		return group_id;
	}

	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}

	public Long getHos_id() {
		return hos_id;
	}

	public void setHos_id(Long hos_id) {
		this.hos_id = hos_id;
	}

	public String getCopy_code() {
		return copy_code;
	}

	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}

	public String getAcc_year() {
		return acc_year;
	}

	public void setAcc_year(String acc_year) {
		this.acc_year = acc_year;
	}

	public Long getVeri_id() {
		return veri_id;
	}

	public void setVeri_id(Long veri_id) {
		this.veri_id = veri_id;
	}

	public Long getPay_id() {
		return pay_id;
	}

	public void setPay_id(Long pay_id) {
		this.pay_id = pay_id;
	}

	public double getVeri_money() {
		return veri_money;
	}

	public void setVeri_money(double veri_money) {
		this.veri_money = veri_money;
	}

	public String getCreate_user() {
		return create_user;
	}

	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public String getSup_name() {
		return sup_name;
	}

	public void setSup_name(String sup_name) {
		this.sup_name = sup_name;
	}

	public String getIn_no() {
		return in_no;
	}

	public void setIn_no(String in_no) {
		this.in_no = in_no;
	}

	public String getBill_no() {
		return bill_no;
	}

	public void setBill_no(String bill_no) {
		this.bill_no = bill_no;
	}

	public Date getIn_date() {
		return in_date;
	}

	public void setIn_date(Date in_date) {
		this.in_date = in_date;
	}

	public double getAmount_money() {
		return amount_money;
	}

	public void setAmount_money(double amount_money) {
		this.amount_money = amount_money;
	}

	public double getPay_money() {
		return pay_money;
	}

	public void setPay_money(double pay_money) {
		this.pay_money = pay_money;
	}
	
}