package com.chd.hrp.cost.entity;

import java.io.Serializable;

public class CostStructureAnalysis implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -252937375041073549L;
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
	 * 统计年月
	 */
	private String year_month;
	
	private String dept_id;
	
	private String dept_no;
	
	/**
	 * 科室编码
	 */
	private String dept_code;
	/**
	 * 科室名称
	 */
	private String dept_name;
	
	/**
	 * 人员经费
	 */
	private double cost_emp_amount;
	/**
	 * 卫生材料费
	 */
	private double cost_mate_amount;
	/**
	 * 药品费
	 */
	private double cost_drug_amount;
	/**
	 * 固定资产折旧
	 */
	private double cost_fasset_amount;
	/**
	 * 无形资产摊销
	 */
	private double cost_iasset_amount;
	/**
	 * 提取医疗风险基金
	 */
	private double cost_risk_amount;
	/**
	 * 其他费用
	 */
	private double cost_other_amount;
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
	public String getYear_month() {
		return year_month;
	}
	public void setYear_month(String year_month) {
		this.year_month = year_month;
	}
	public String getDept_id() {
		return dept_id;
	}
	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}
	public String getDept_no() {
		return dept_no;
	}
	public void setDept_no(String dept_no) {
		this.dept_no = dept_no;
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
	public double getCost_emp_amount() {
		return cost_emp_amount;
	}
	public void setCost_emp_amount(double cost_emp_amount) {
		this.cost_emp_amount = cost_emp_amount;
	}
	public double getCost_mate_amount() {
		return cost_mate_amount;
	}
	public void setCost_mate_amount(double cost_mate_amount) {
		this.cost_mate_amount = cost_mate_amount;
	}
	public double getCost_drug_amount() {
		return cost_drug_amount;
	}
	public void setCost_drug_amount(double cost_drug_amount) {
		this.cost_drug_amount = cost_drug_amount;
	}
	public double getCost_fasset_amount() {
		return cost_fasset_amount;
	}
	public void setCost_fasset_amount(double cost_fasset_amount) {
		this.cost_fasset_amount = cost_fasset_amount;
	}
	public double getCost_iasset_amount() {
		return cost_iasset_amount;
	}
	public void setCost_iasset_amount(double cost_iasset_amount) {
		this.cost_iasset_amount = cost_iasset_amount;
	}
	public double getCost_risk_amount() {
		return cost_risk_amount;
	}
	public void setCost_risk_amount(double cost_risk_amount) {
		this.cost_risk_amount = cost_risk_amount;
	}
	public double getCost_other_amount() {
		return cost_other_amount;
	}
	public void setCost_other_amount(double cost_other_amount) {
		this.cost_other_amount = cost_other_amount;
	}
	
}
