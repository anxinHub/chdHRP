package com.chd.hrp.mat.entity;

import java.util.Date;

public class MatDuraDeptBalance {
	
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
	 * 科室ID
	 */
	private Long dept_id;
	
	/**
	 * 材料ID
	 */
	private Long inv_id;
	
	/**
	 * 单价
	 */
	private Double price;
	
	/**
	 * 入库数量
	 */
	private Double in_amount;
	
	/**
	 * 入库金额
	 */
	private Double in_money;
	
	/**
	 * 出库数量
	 */
	private Double out_amount;
	
	/**
	 * 出库金额
	 */
	private Double out_money;
	
	/**
	 * 剩余数量
	 */
	private Double left_amount;
	
	/**
	 * 剩余金额
	 */
	private Double left_money;

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

	public Long getDept_id() {
		return dept_id;
	}

	public void setDept_id(Long dept_id) {
		this.dept_id = dept_id;
	}

	public Long getInv_id() {
		return inv_id;
	}

	public void setInv_id(Long inv_id) {
		this.inv_id = inv_id;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getIn_amount() {
		return in_amount;
	}

	public void setIn_amount(Double in_amount) {
		this.in_amount = in_amount;
	}

	public Double getIn_money() {
		return in_money;
	}

	public void setIn_money(Double in_money) {
		this.in_money = in_money;
	}

	public Double getOut_amount() {
		return out_amount;
	}

	public void setOut_amount(Double out_amount) {
		this.out_amount = out_amount;
	}

	public Double getOut_money() {
		return out_money;
	}

	public void setOut_money(Double out_money) {
		this.out_money = out_money;
	}

	public Double getLeft_amount() {
		return left_amount;
	}

	public void setLeft_amount(Double left_amount) {
		this.left_amount = left_amount;
	}

	public Double getLeft_money() {
		return left_money;
	}

	public void setLeft_money(Double left_money) {
		this.left_money = left_money;
	}
}
