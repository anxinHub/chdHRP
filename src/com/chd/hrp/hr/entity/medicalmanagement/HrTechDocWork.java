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
 * HR_TECH_DOC_WORK
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class HrTechDocWork implements Serializable {

	
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
	private Double dept_id;
	private String dept_name;
	
	/**
	 * 
	 */
	private Double emp_id;
	private String emp_name;
	
	/**
	 * 
	 */
	private String year_month;
	
	/**
	 * 
	 */
	private Double rep_mz;
	
	/**
	 * 
	 */
	private Double rep_zy;
	
	/**
	 * 
	 */
	private Double rep_tj;
	
	/**
	 * 
	 */
	private Double rep_sum;
	
	/**
	 * 
	 */
	private Double aut_mz;
	
	/**
	 * 
	 */
	private Double aut_zy;
	
	/**
	 * 
	 */
	private Double aut_tj;
	
	/**
	 * 
	 */
	private Double aut_sum;
	
	/**
	 * 
	 */
	private Double tech_money;
	
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
	public void setDept_id(Double value) {
		this.dept_id = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getDept_id() {
		return this.dept_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setEmp_id(Double value) {
		this.emp_id = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getEmp_id() {
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
	public void setRep_mz(Double value) {
		this.rep_mz = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getRep_mz() {
		return this.rep_mz;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setRep_zy(Double value) {
		this.rep_zy = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getRep_zy() {
		return this.rep_zy;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setRep_tj(Double value) {
		this.rep_tj = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getRep_tj() {
		return this.rep_tj;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setRep_sum(Double value) {
		this.rep_sum = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getRep_sum() {
		return this.rep_sum;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setAut_mz(Double value) {
		this.aut_mz = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getAut_mz() {
		return this.aut_mz;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setAut_zy(Double value) {
		this.aut_zy = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getAut_zy() {
		return this.aut_zy;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setAut_tj(Double value) {
		this.aut_tj = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getAut_tj() {
		return this.aut_tj;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setAut_sum(Double value) {
		this.aut_sum = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getAut_sum() {
		return this.aut_sum;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setTech_money(Double value) {
		this.tech_money = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getTech_money() {
		return this.tech_money;
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