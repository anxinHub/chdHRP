/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.entity.medicalmanagement;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_CLINIC_DOC_WORK 临床医生工作量
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class HrClinicDocWork implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 
	 */
	private Double group_id;
	
	/**
	 * 
	 */
	private Double hos_id;
	
	/**
	 * 
	 */
	private Integer dept_id;
	private String dept_name;
	
	/**
	 * 
	 */
	private Integer emp_id;
	private String emp_name;
	
	/**
	 * 
	 */
	private String year_month;
	
	/**
	 * 
	 */
	private Double man_patient_num;
	
	/**
	 * 
	 */
	private Double cmi;
	
	/**
	 * 
	 */
	private Double rw_patient_num;
	
	/**
	 * 
	 */
	private Double man_oper_num;
	
	/**
	 * 
	 */
	private Double man_oper_perc;
	
	/**
	 * 
	 */
	private Double avg_inhos_charge;
	
	/**
	 * 
	 */
	private Double avg_inhos_days;
	
	/**
	 * 
	 */
	private Double med_val_add;
	
	/**
	 * 
	 */
	private String note;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 
	* @param value 
	*/
	public void setGroup_id(Double value) {
		this.group_id = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getGroup_id() {
		return this.group_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setHos_id(Double value) {
		this.hos_id = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getHos_id() {
		return this.hos_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setDept_id(Integer value) {
		this.dept_id = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Integer getDept_id() {
		return this.dept_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setEmp_id(Integer value) {
		this.emp_id = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Integer getEmp_id() {
		return this.emp_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setYear_month(String value) {
		this.year_month = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getYear_month() {
		return this.year_month;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setMan_patient_num(Double value) {
		this.man_patient_num = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getMan_patient_num() {
		return this.man_patient_num;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setCmi(Double value) {
		this.cmi = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getCmi() {
		return this.cmi;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setRw_patient_num(Double value) {
		this.rw_patient_num = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getRw_patient_num() {
		return this.rw_patient_num;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setMan_oper_num(Double value) {
		this.man_oper_num = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getMan_oper_num() {
		return this.man_oper_num;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setMan_oper_perc(Double value) {
		this.man_oper_perc = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getMan_oper_perc() {
		return this.man_oper_perc;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setAvg_inhos_charge(Double value) {
		this.avg_inhos_charge = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getAvg_inhos_charge() {
		return this.avg_inhos_charge;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setAvg_inhos_days(Double value) {
		this.avg_inhos_days = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getAvg_inhos_days() {
		return this.avg_inhos_days;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setMed_val_add(Double value) {
		this.med_val_add = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getMed_val_add() {
		return this.med_val_add;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setNote(String value) {
		this.note = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getNote() {
		return this.note;
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

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getEmp_name() {
		return emp_name;
	}

	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
	
}