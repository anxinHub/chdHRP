package com.chd.hrp.med.entity;

import java.util.Date;

public class MedDuraDeptReg {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	/**
	 * 集团ID
	 */
	private Long group_id;
	
	/**
	 * 医院ID
	 */
	private Long hos_id;
	
	/**
	 * 账套编码
	 */
	private String copy_code;
	
	/**
	 * 登记ID
	 */
	private Long reg_id;
	
	/**
	 * 科室ID
	 */
	private Long dept_id;
	
	/**
	 * 仓库变更号
	 */
	private Long dept_no;
	
	/**
	 * 材料ID
	 */
	private Long inv_id;
	
	/**
	 * 材料变更号
	 */
	private Long inv_no;
	
	/**
	 * 单价
	 */
	private Double price;
	
	/**
	 * 条形码/个体码
	 */
	private String bar_code;
	
	/**
	 * 数量
	 */
	private Float amount;
	
	/**
	 * 金额
	 */
	private Double amount_money;
	
	/**
	 * 登记人
	 */
	private Long maker;
	
	/**
	 * 登记日期
	 */
	private Date make_date;
	
	/**
	 * 审核人
	 */
	private Long checker;
	
	/**
	 * 审核日期
	 */
	private Date check_date;
	
	/**
	 * 状态
	 */
	private int state;
	
	/**
	 * 备注
	 */
	private String note;

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

	public Long getReg_id() {
		return reg_id;
	}

	public void setReg_id(Long reg_id) {
		this.reg_id = reg_id;
	}

	public Long getDept_id() {
		return dept_id;
	}

	public void setDept_id(Long dept_id) {
		this.dept_id = dept_id;
	}

	public Long getDept_no() {
		return dept_no;
	}

	public void setDept_no(Long dept_no) {
		this.dept_no = dept_no;
	}

	public Long getInv_id() {
		return inv_id;
	}

	public void setInv_id(Long inv_id) {
		this.inv_id = inv_id;
	}

	public Long getInv_no() {
		return inv_no;
	}

	public void setInv_no(Long inv_no) {
		this.inv_no = inv_no;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getBar_code() {
		return bar_code;
	}

	public void setBar_code(String bar_code) {
		this.bar_code = bar_code;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public Double getAmount_money() {
		return amount_money;
	}

	public void setAmount_money(Double amount_money) {
		this.amount_money = amount_money;
	}

	public Long getMaker() {
		return maker;
	}

	public void setMaker(Long maker) {
		this.maker = maker;
	}

	public Date getMake_date() {
		return make_date;
	}

	public void setMake_date(Date make_date) {
		this.make_date = make_date;
	}

	public Long getChecker() {
		return checker;
	}

	public void setChecker(Long checker) {
		this.checker = checker;
	}

	public Date getCheck_date() {
		return check_date;
	}

	public void setCheck_date(Date check_date) {
		this.check_date = check_date;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
