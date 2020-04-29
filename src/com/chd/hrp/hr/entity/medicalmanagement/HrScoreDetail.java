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
 * HR_SCORE_DETAIL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class HrScoreDetail implements Serializable {

	
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
	 * YYYY-MM-DD
	 */
	private Date plaint_date;
	
	/**
	 * 
	 */
	private String in_hos_no;
	
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
	private String emp_nature;
	
	/**
	 * 
	 */
	private Double ratio;
	
	/**
	 * 
	 */
	private Double score;
	
	/**
	 * 
	 */
	private Integer is_commit;
	private String commit_name;
	
   private String patient;
   private Integer age;
   private String plainter;
   private String content;
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
	* 设置 YYYY-MM-DD
	* @param value 
	*/
	public void setPlaint_date(Date value) {
		this.plaint_date = value;
	}
	
	/**
	* 获取 YYYY-MM-DD
	* @return Date
	*/
	public Date getPlaint_date() {
		return this.plaint_date;
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
	public void setEmp_nature(String value) {
		this.emp_nature = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getEmp_nature() {
		return this.emp_nature;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setRatio(Double value) {
		this.ratio = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getRatio() {
		return this.ratio;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setScore(Double value) {
		this.score = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getScore() {
		return this.score;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setIs_commit(Integer value) {
		this.is_commit = value;
	}
	
	/**
	* 获取 
	* @return Integer
	*/
	public Integer getIs_commit() {
		return this.is_commit;
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

	public String getPatient() {
		return patient;
	}

	public void setPatient(String patient) {
		this.patient = patient;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}



	public String getPlainter() {
		return plainter;
	}

	public void setPlainter(String plainter) {
		this.plainter = plainter;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCommit_name() {
		return commit_name;
	}

	public void setCommit_name(String commit_name) {
		this.commit_name = commit_name;
	}
	
}