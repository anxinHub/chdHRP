package com.chd.hrp.mat.entity;

public class MatDuraDeptDetail {
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
	 * 单据ID
	 */
	private Long dura_id;
	
	/**
	 * 明细ID
	 */
	private Long detail_id;
	
	/**
	 * 材料ID
	 */
	private Long inv_id;
	
	/**
	 * 材料NO
	 */
	private Long inv_no;
	
	/**
	 * 单价
	 */
	private Double price;
	
	/**
	 * 数量
	 */
	private Double amount;
	
	/**
	 * 金额
	 */
	private Double amount_money;
	
	/**
	 * 条形码/个体码
	 */
	private String bar_code;
	
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

	public Long getDura_id() {
		return dura_id;
	}

	public void setDura_id(Long dura_id) {
		this.dura_id = dura_id;
	}

	public Long getDetail_id() {
		return detail_id;
	}

	public void setDetail_id(Long detail_id) {
		this.detail_id = detail_id;
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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getAmount_money() {
		return amount_money;
	}

	public void setAmount_money(Double amount_money) {
		this.amount_money = amount_money;
	}

	public String getBar_code() {
		return bar_code;
	}

	public void setBar_code(String bar_code) {
		this.bar_code = bar_code;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	
}
