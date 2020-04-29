package com.chd.hrp.ass.entity.apply;

import java.io.Serializable;

public class AssApplyDeptProofDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	 * 主表ID
	 */
	private Long proof_id;
	
	private Long proof_detail_id;
	/**
	 * 第N年
	 */
	private Integer year_num;
	/**
	 * 项目收入
	 */
	private double project_income;
	/**
	 * 年工作量
	 */
	private double year_workload;
	/**
	 * 收费标准
	 */
	private double charge_stand;
	/**
	 * 耗材费用
	 */
	private double consumable_cost;
	/**
	 * 人员经费
	 */
	private double employee_cost;
	/**
	 * 维修费
	 */
	private double maintenance_cost;
	/**
	 * 水电费
	 */
	private double waterele_cost;
	/**
	 * 折旧费
	 */
	private double depreciation_cost;
	/***
	 * 其他成本
	 */
	private double other_cost;
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


	public Long getProof_id() {
		return proof_id;
	}
	public void setProof_id(Long proof_id) {
		this.proof_id = proof_id;
	}
	public Long getProof_detail_id() {
		return proof_detail_id;
	}
	public void setProof_detail_id(Long proof_detail_id) {
		this.proof_detail_id = proof_detail_id;
	}
	public Integer getYear_num() {
		return year_num;
	}
	public void setYear_num(Integer year_num) {
		this.year_num = year_num;
	}
	public double getProject_income() {
		return project_income;
	}
	public void setProject_income(double project_income) {
		this.project_income = project_income;
	}
	public double getYear_workload() {
		return year_workload;
	}
	public void setYear_workload(double year_workload) {
		this.year_workload = year_workload;
	}
	public double getCharge_stand() {
		return charge_stand;
	}
	public void setCharge_stand(double charge_stand) {
		this.charge_stand = charge_stand;
	}
	public double getConsumable_cost() {
		return consumable_cost;
	}
	public void setConsumable_cost(double consumable_cost) {
		this.consumable_cost = consumable_cost;
	}
	public double getEmployee_cost() {
		return employee_cost;
	}
	public void setEmployee_cost(double employee_cost) {
		this.employee_cost = employee_cost;
	}
	public double getMaintenance_cost() {
		return maintenance_cost;
	}
	public void setMaintenance_cost(double maintenance_cost) {
		this.maintenance_cost = maintenance_cost;
	}
	public double getWaterele_cost() {
		return waterele_cost;
	}
	public void setWaterele_cost(double waterele_cost) {
		this.waterele_cost = waterele_cost;
	}
	public double getDepreciation_cost() {
		return depreciation_cost;
	}
	public void setDepreciation_cost(double depreciation_cost) {
		this.depreciation_cost = depreciation_cost;
	}
	public double getOther_cost() {
		return other_cost;
	}
	public void setOther_cost(double other_cost) {
		this.other_cost = other_cost;
	}
	@Override
	public String toString() {
		return "AssApplyDeptProofDetail [group_id=" + group_id + ", hos_id="
				+ hos_id + ", copy_code=" + copy_code + ", proof_ID=" + proof_id + ", year_num="
				+ year_num + ", project_income=" + project_income
				+ ", year_workload=" + year_workload + ", charge_stand="
				+ charge_stand + ", consumable_cost=" + consumable_cost
				+ ", employee_cost=" + employee_cost + ", maintenance_cost="
				+ maintenance_cost + ", waterele_cost=" + waterele_cost
				+ ", depreciation_cost=" + depreciation_cost + ", other_cost="
				+ other_cost + "]";
	}
	
}
