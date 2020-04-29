package com.chd.hrp.budg.entity;

import java.io.Serializable;

public class Buget implements Serializable{

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
	 * 预算年度
	 */
	private String budg_year;
	
	/**
	 * 资金来源ID
	 */
	private Long source_id;
	
	/**
	 * 资金类别编码
	 */
	private String ass_type_id;
	/**
	 * 资产分类名称
	 */
	private String ass_type_name;
	/**
	 * 年度计划金额
	 */
	private Double year_plan_amount;
	
	/**
	 * 追加计划金额
	 */
	private Double add_plan_amount;
	
	/**
	 *预算总额
	 */
	private Double pur_budg;
	
	/**
	 * 资金来源编码
	 * @return
	 */
	private String source_code;
	/**
	 * 资金来源名称
	 * @return
	 */
	private String source_name;
	
	
	public String getSource_code() {
		return source_code;
	}

	public void setSource_code(String source_code) {
		this.source_code = source_code;
	}

	public String getSource_name() {
		return source_name;
	}

	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}

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

	public String getBudg_year() {
		return budg_year;
	}

	public void setBudg_year(String budg_year) {
		this.budg_year = budg_year;
	}

	public Long getSource_id() {
		return source_id;
	}

	public void setSource_id(Long source_id) {
		this.source_id = source_id;
	}

	public String getAss_type_id() {
		return ass_type_id;
	}

	public void setAss_type_id(String ass_type_id) {
		this.ass_type_id = ass_type_id;
	}

	public Double getYear_plan_amount() {
		return year_plan_amount;
	}

	public void setYear_plan_amount(Double year_plan_amount) {
		this.year_plan_amount = year_plan_amount;
	}

	public Double getAdd_plan_amount() {
		return add_plan_amount;
	}

	public void setAdd_plan_amount(Double add_plan_amount) {
		this.add_plan_amount = add_plan_amount;
	}

	public Double getPur_budg() {
		return pur_budg;
	}

	public void setPur_budg(Double pur_budg) {
		this.pur_budg = pur_budg;
	}

	public String getAss_type_name() {
		return ass_type_name;
	}

	public void setAss_type_name(String ass_type_name) {
		this.ass_type_name = ass_type_name;
	}
}
