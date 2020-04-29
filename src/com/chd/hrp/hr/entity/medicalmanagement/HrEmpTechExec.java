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
 * HR_EMP_TECH_EXEC 技术准入开展情况
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class HrEmpTechExec implements Serializable {

	
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
	private String app_no;
	
	/**
	 * 
	 */
	private Double emp_id;
	
	/**
	 * 
	 */
	private String oper_name;
	
	/**
	 * 
	 */
	private String case_no;
	
	/**
	 * 
	 */
	private String patient_name;
	
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
	public void setApp_no(String value) {
		this.app_no = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getApp_no() {
		return this.app_no;
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
	public void setOper_name(String value) {
		this.oper_name = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getOper_name() {
		return this.oper_name;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setCase_no(String value) {
		this.case_no = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getCase_no() {
		return this.case_no;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setPatient_name(String value) {
		this.patient_name = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getPatient_name() {
		return this.patient_name;
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
}