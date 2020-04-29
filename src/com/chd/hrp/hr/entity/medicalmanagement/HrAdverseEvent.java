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
 * HR_ADVERSE EVENT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class HrAdverseEvent implements Serializable {

	
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
	 * YYYY-MM-DD
	 */
	private Date occ_date;
	
	/**
	 * 
	 */
	private Double occ_dept_id;
	private String occ_dept_name;
	private Integer dept_code;
	
	/**
	 * 
	 */
	private Double emp_id;
	private String emp_name;
	
	/**
	 * 
	 */
	private String in_hos_no;
	
	/**
	 * 
	 */
	private String patient;
	private String patient_name;
	
	/**
	 * 
	 */
	private String sex_code;
	private String sex_name;
	
	/**
	 * 
	 */
	private Integer age;
	
	/**
	 * 
	 */
	private String diagnose;
	
	/**
	 * 
	 */
	private String content;
	
	/**
	 * 
	 */
	private String finder;
	private String finder_name;
	
	/**
	 * 
	 */
	private String dept_opinion;
	
	/**
	 * YYYY-MM-DD
	 */
	private Date dept_aff_date;
	
	/**
	 * YYYY-MM-DD
	 */
	private Date hos_aff_date;
	
	/**
	 * YYYY-MM-DD
	 */
	private Date mmd_aff_date;
	
	/**
	 * 
	 */
	private String reper;
	private String reper_name;
	

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
	* 设置 YYYY-MM-DD
	* @param value 
	*/
	public void setOcc_date(Date value) {
		this.occ_date = value;
	}
	
	/**
	* 获取 YYYY-MM-DD
	* @return Date
	*/
	public Date getOcc_date() {
		return this.occ_date;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setOcc_dept_id(Double value) {
		this.occ_dept_id = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getOcc_dept_id() {
		return this.occ_dept_id;
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
	public void setIn_hos_no(String value) {
		this.in_hos_no = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getIn_hos_no() {
		return this.in_hos_no;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setPatient(String value) {
		this.patient = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getPatient() {
		return this.patient;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setSex_code(String value) {
		this.sex_code = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getSex_code() {
		return this.sex_code;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setAge(Integer value) {
		this.age = value;
	}
	
	/**
	* 获取 
	* @return Integer
	*/
	public Integer getAge() {
		return this.age;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setDiagnose(String value) {
		this.diagnose = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getDiagnose() {
		return this.diagnose;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setContent(String value) {
		this.content = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getContent() {
		return this.content;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setFinder(String value) {
		this.finder = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getFinder() {
		return this.finder;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setDept_opinion(String value) {
		this.dept_opinion = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getDept_opinion() {
		return this.dept_opinion;
	}
	/**
	* 设置 YYYY-MM-DD
	* @param value 
	*/
	public void setDept_aff_date(Date value) {
		this.dept_aff_date = value;
	}
	
	/**
	* 获取 YYYY-MM-DD
	* @return Date
	*/
	public Date getDept_aff_date() {
		return this.dept_aff_date;
	}
	/**
	* 设置 YYYY-MM-DD
	* @param value 
	*/
	public void setHos_aff_date(Date value) {
		this.hos_aff_date = value;
	}
	
	/**
	* 获取 YYYY-MM-DD
	* @return Date
	*/
	public Date getHos_aff_date() {
		return this.hos_aff_date;
	}
	/**
	* 设置 YYYY-MM-DD
	* @param value 
	*/
	public void setMmd_aff_date(Date value) {
		this.mmd_aff_date = value;
	}
	
	/**
	* 获取 YYYY-MM-DD
	* @return Date
	*/
	public Date getMmd_aff_date() {
		return this.mmd_aff_date;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setReper(String value) {
		this.reper = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getReper() {
		return this.reper;
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

	public String getOcc_dept_name() {
		return occ_dept_name;
	}

	public void setOcc_dept_name(String occ_dept_name) {
		this.occ_dept_name = occ_dept_name;
	}

	public String getEmp_name() {
		return emp_name;
	}

	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}

	public String getFinder_name() {
		return finder_name;
	}

	public void setFinder_name(String finder_name) {
		this.finder_name = finder_name;
	}

	public String getReper_name() {
		return reper_name;
	}

	public void setReper_name(String reper_name) {
		this.reper_name = reper_name;
	}

	public String getPatient_name() {
		return patient_name;
	}

	public void setPatient_name(String patient_name) {
		this.patient_name = patient_name;
	}

	public String getSex_name() {
		return sex_name;
	}

	public void setSex_name(String sex_name) {
		this.sex_name = sex_name;
	}
	
}