/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.entity;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 人头付费标准维护
 * @Table:
 * BUDG_RT_PAY_STANDARD
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgRtPayStandard implements Serializable {

	
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
	 * 年度
	 */
	private String year;
	
	/**
	 * 医保类型编码
	 */
	private String insurance_code;
	
	/**
	 * 门诊均次费用
	 */
	private Double outpatient_charge;
	
	/**
	 * 床日均次费用
	 */
	private Double day_bed_charge;
	
	/**
	 * 门诊业务量预算
	 */
	private Long o_workload_budg;
	
	/**
	 * 床日业务量预算
	 */
	private Long i_workload_budg;
	
	/**
	 * 备注
	 */
	private String remark;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 集团ID
	* @param value 
	*/
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	
	/**
	* 获取 集团ID
	* @return Long
	*/
	public Long getGroup_id() {
		return this.group_id;
	}
	/**
	* 设置 医院ID
	* @param value 
	*/
	public void setHos_id(Long value) {
		this.hos_id = value;
	}
	
	/**
	* 获取 医院ID
	* @return Long
	*/
	public Long getHos_id() {
		return this.hos_id;
	}
	/**
	* 设置 账套编码
	* @param value 
	*/
	public void setCopy_code(String value) {
		this.copy_code = value;
	}
	
	/**
	* 获取 账套编码
	* @return String
	*/
	public String getCopy_code() {
		return this.copy_code;
	}
	/**
	* 设置 年度
	* @param value 
	*/
	public void setYear(String value) {
		this.year = value;
	}
	
	/**
	* 获取 年度
	* @return String
	*/
	public String getYear() {
		return this.year;
	}
	/**
	* 设置 医保类型编码
	* @param value 
	*/
	public void setInsurance_code(String value) {
		this.insurance_code = value;
	}
	
	/**
	* 获取 医保类型编码
	* @return String
	*/
	public String getInsurance_code() {
		return this.insurance_code;
	}
	/**
	* 设置 门诊均次费用
	* @param value 
	*/
	public void setOutpatient_charge(Double value) {
		this.outpatient_charge = value;
	}
	
	/**
	* 获取 门诊均次费用
	* @return Double
	*/
	public Double getOutpatient_charge() {
		return this.outpatient_charge;
	}
	/**
	* 设置 床日均次费用
	* @param value 
	*/
	public void setDay_bed_charge(Double value) {
		this.day_bed_charge = value;
	}
	
	/**
	* 获取 床日均次费用
	* @return Double
	*/
	public Double getDay_bed_charge() {
		return this.day_bed_charge;
	}
	/**
	* 设置 门诊业务量预算
	* @param value 
	*/
	public void setO_workload_budg(Long value) {
		this.o_workload_budg = value;
	}
	
	/**
	* 获取 门诊业务量预算
	* @return Long
	*/
	public Long getO_workload_budg() {
		return this.o_workload_budg;
	}
	/**
	* 设置 床日业务量预算
	* @param value 
	*/
	public void setI_workload_budg(Long value) {
		this.i_workload_budg = value;
	}
	
	/**
	* 获取 床日业务量预算
	* @return Long
	*/
	public Long getI_workload_budg() {
		return this.i_workload_budg;
	}
	/**
	* 设置 备注
	* @param value 
	*/
	public void setRemark(String value) {
		this.remark = value;
	}
	
	/**
	* 获取 备注
	* @return String
	*/
	public String getRemark() {
		return this.remark;
	}
	
	/**
	 * 设置 导入验证信息
	 */
	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	/**
	 * 获取 导入验证信息
	 */
	public String getError_type() {
		return error_type;
	}
}