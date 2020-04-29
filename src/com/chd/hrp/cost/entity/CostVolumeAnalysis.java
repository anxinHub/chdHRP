/**
* @Copyright: Copyright (c) 2015-3-22 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.cost.entity;

import java.io.Serializable;

/**
* @Title. @Description.
* 量本利分析<BR>
* @Author: XueWanLi
* @email: bell@s-chd.com
* @Version: 1.0
*/
public class CostVolumeAnalysis implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6340523416075434195L;
	private String group_id;
	private String hos_id;
	private String copy_code;
	private String year_month;
	private String dept_id;
	private String dept_no;
	private String dept_name;
	private String dept_code;
	private String clinic_num;
	private String bed_use_day_num;
	private String fixed_cost;
	private String variable_cost;
	private String outpatient_income;
	private String hospitalization_income;
	private String unit_income;
	private String break_even_income;
	private String break_even_point;
	
	
	public String getHospitalization_income() {
		return hospitalization_income;
	}
	public void setHospitalization_income(String hospitalization_income) {
		this.hospitalization_income = hospitalization_income;
	}
	public String getGroup_id() {
		return group_id;
	}
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	public String getHos_id() {
		return hos_id;
	}
	public void setHos_id(String hos_id) {
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
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public String getDept_code() {
		return dept_code;
	}
	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}
	public String getClinic_num() {
		return clinic_num;
	}
	public void setClinic_num(String clinic_num) {
		this.clinic_num = clinic_num;
	}
	public String getBed_use_day_num() {
		return bed_use_day_num;
	}
	public void setBed_use_day_num(String bed_use_day_num) {
		this.bed_use_day_num = bed_use_day_num;
	}
	public String getFixed_cost() {
		return fixed_cost;
	}
	public void setFixed_cost(String fixed_cost) {
		this.fixed_cost = fixed_cost;
	}
	public String getVariable_cost() {
		return variable_cost;
	}
	public void setVariable_cost(String variable_cost) {
		this.variable_cost = variable_cost;
	}
	public String getOutpatient_income() {
		return outpatient_income;
	}
	public void setOutpatient_income(String outpatient_income) {
		this.outpatient_income = outpatient_income;
	}
	public String getUnit_income() {
		return unit_income;
	}
	public void setUnit_income(String unit_income) {
		this.unit_income = unit_income;
	}
	public String getBreak_even_income() {
		return break_even_income;
	}
	public void setBreak_even_income(String break_even_income) {
		this.break_even_income = break_even_income;
	}
	public String getBreak_even_point() {
		return break_even_point;
	}
	public void setBreak_even_point(String break_even_point) {
		this.break_even_point = break_even_point;
	}
	
}
