package com.chd.hrp.mat.entity;

public class MatDuraDeptSurplus {
	
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
	 * 期间年
	 */
	private String year;
	
	/**
	 * 期间月
	 */
	private String month;
	
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
	 * 期初数量
	 */
	private Double begin_amount;
	
	/**
	 * 期初金额
	 */
	private Double begin_money;
	
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
	 * 期末结存数量
	 */
	private Double end_amount;
	
	/**
	 * 期末结存金额
	 */
	private Double end_money;
	

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

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getBegin_amount() {
		return begin_amount;
	}

	public void setBegin_amount(Double begin_amount) {
		this.begin_amount = begin_amount;
	}

	public Double getBegin_money() {
		return begin_money;
	}

	public void setBegin_money(Double begin_money) {
		this.begin_money = begin_money;
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

	public Double getEnd_amount() {
		return end_amount;
	}

	public void setEnd_amount(Double end_amount) {
		this.end_amount = end_amount;
	}

	public Double getEnd_money() {
		return end_money;
	}

	public void setEnd_money(Double end_money) {
		this.end_money = end_money;
	}
}
