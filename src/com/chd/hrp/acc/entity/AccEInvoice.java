package com.chd.hrp.acc.entity;

import java.io.Serializable;
import java.util.Date;

public class AccEInvoice implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int group_id;
	private int hos_id;
	private String copy_code;
	/**
	 * 发票代码
	 */
	private int ei_id;
	/**
	 * 发票号码
	 */
	private String ei_code;
	/**
	 * 发票日期
	 */
	private Date ei_date;
	/**
	 * 检验码
	 */
	private String check_code;
	/**
	 * 发票金额
	 */
	private Double ei_money;
	/**
	 * 报销人
	 */
	private String reimburse_man;
	/**
	 * 报销日期
	 */
	private Date reimburse_date;
	
	public int getGroup_id() {
		return group_id;
	}
	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}
	public int getHos_id() {
		return hos_id;
	}
	public void setHos_id(int hos_id) {
		this.hos_id = hos_id;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	public int getEi_id() {
		return ei_id;
	}
	public void setEi_id(int ei_id) {
		this.ei_id = ei_id;
	}
	public String getEi_code() {
		return ei_code;
	}
	public void setEi_code(String ei_code) {
		this.ei_code = ei_code;
	}
	public Date getEi_date() {
		return ei_date;
	}
	public void setEi_date(Date ei_date) {
		this.ei_date = ei_date;
	}
	public String getCheck_code() {
		return check_code;
	}
	public void setCheck_code(String check_code) {
		this.check_code = check_code;
	}
	public Double getEi_money() {
		return ei_money;
	}
	public void setEi_money(Double ei_money) {
		this.ei_money = ei_money;
	}
	public String getReimburse_man() {
		return reimburse_man;
	}
	public void setReimburse_man(String reimburse_man) {
		this.reimburse_man = reimburse_man;
	}
	public Date getReimburse_date() {
		return reimburse_date;
	}
	public void setReimburse_date(Date reimburse_date) {
		this.reimburse_date = reimburse_date;
	}
	
}
