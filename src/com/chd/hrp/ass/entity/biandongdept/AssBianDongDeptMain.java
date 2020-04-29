package com.chd.hrp.ass.entity.biandongdept;

import java.io.Serializable;

public class AssBianDongDeptMain implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;
	/**
	 * 集体ID
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
	 * 统计年月
	 */
	private Long year_month;
	
	/**
	 * 资产ID
	 */
	private Long ass_id;
	
	/**
	 * 科室ID
	 */
	private Long dept_id;
	
	/**
	 * 科室编码
	 */
	private String dept_code;
	
	/**
	 * 科室名称
	 */
	private String dept_name;
	
	/**
	 * 使用库房ID
	 */
    private Long store_id;
    
	/**
	 * 期初数量
	 */
	private Integer begin_amount;
	
	/**
	 * 期初金额
	 */
	private Double begin_money;
	
	/**
	 * 本期增加数量
	 */
	private Integer add_amount;
	
	/**
	 * 本期增加金额
	 */
	private Double add_money;
	
	/**
	 * 本期减少数量
	 */
	private Integer dec_amount;
	
	/**
	 * 本期减少金额
	 */
	private Double dec_money;
	
	/**
	 * 期末数量
	 */
	private Integer end_amount;
	
	/**
	 * 期末金额
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

	public Long getAss_id() {
		return ass_id;
	}

	public void setAss_id(Long ass_id) {
		this.ass_id = ass_id;
	}

	
	public Long getStore_id() {
		return store_id;
	}

	public void setStore_id(Long store_id) {
		this.store_id = store_id;
	}

	

	public Double getBegin_money() {
		return begin_money;
	}

	public void setBegin_money(Double begin_money) {
		this.begin_money = begin_money;
	}

	public Long getDept_id() {
		return dept_id;
	}

	public void setDept_id(Long dept_id) {
		this.dept_id = dept_id;
	}

	public Integer getBegin_amount() {
		return begin_amount;
	}

	public void setBegin_amount(Integer begin_amount) {
		this.begin_amount = begin_amount;
	}

	public Integer getAdd_amount() {
		return add_amount;
	}

	public void setAdd_amount(Integer add_amount) {
		this.add_amount = add_amount;
	}

	public Double getAdd_money() {
		return add_money;
	}

	public void setAdd_money(Double add_money) {
		this.add_money = add_money;
	}

	public Integer getDec_amount() {
		return dec_amount;
	}

	public void setDec_amount(Integer dec_amount) {
		this.dec_amount = dec_amount;
	}

	public Double getDec_money() {
		return dec_money;
	}

	public void setDec_money(Double dec_money) {
		this.dec_money = dec_money;
	}

	public Integer getEnd_amount() {
		return end_amount;
	}

	public void setEnd_amount(Integer end_amount) {
		this.end_amount = end_amount;
	}

	public Double getEnd_money() {
		return end_money;
	}

	public void setEnd_money(Double end_money) {
		this.end_money = end_money;
	}

	public Long getYear_month() {
		return year_month;
	}

	public void setYear_month(Long year_month) {
		this.year_month = year_month;
	}
	
	public String getDept_code() {
		return dept_code;
	}

	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
}
