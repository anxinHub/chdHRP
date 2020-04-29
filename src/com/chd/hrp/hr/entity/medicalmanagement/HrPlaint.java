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
 * HR_PLAINT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class HrPlaint implements Serializable {

	
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
	private String patient;
	
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
	private String plainter;
	
	/**
	 * 
	 */
	private String plaint_tel;
	
	/**
	 * 
	 */
	private String patient_ref;
	
	/**
	 * 
	 */
	private String content;
	
	/**
	 * 0：未提交 1：已提交
	 */
	private Integer is_commit;
	private String commit_name;
	

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
	public void setPlainter(String value) {
		this.plainter = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getPlainter() {
		return this.plainter;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setPlaint_tel(String value) {
		this.plaint_tel = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getPlaint_tel() {
		return this.plaint_tel;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setPatient_ref(String value) {
		this.patient_ref = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getPatient_ref() {
		return this.patient_ref;
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
	* 设置 0：未提交 1：已提交
	* @param value 
	*/
	public void setIs_commit(Integer value) {
		this.is_commit = value;
	}
	
	/**
	* 获取 0：未提交 1：已提交
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

	public String getSex_name() {
		return sex_name;
	}

	public void setSex_name(String sex_name) {
		this.sex_name = sex_name;
	}

	public String getCommit_name() {
		return commit_name;
	}

	public void setCommit_name(String commit_name) {
		this.commit_name = commit_name;
	}
	
}