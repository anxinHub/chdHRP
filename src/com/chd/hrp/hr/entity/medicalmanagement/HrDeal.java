package com.chd.hrp.hr.entity.medicalmanagement;

import java.io.Serializable;
import java.util.Date;

public class HrDeal implements Serializable {

	
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
	private String deal_type;
	
	/**
	 * 
	 */
	private Double damage;
	
	/**
	 * 
	 */
	private String other;
	
	/**
	 * 0：未提交 1：已提交
	 */
	private Integer is_commit;
	
	private String  commit_name;
	
	   private String patient;
	  
	   private String plaint_tel;
	   
	   private String content;
	   
	   private String plainter;
	   private String patient_ref;
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
	public void setDeal_type(String value) {
		this.deal_type = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getDeal_type() {
		return this.deal_type;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setDamage(Double value) {
		this.damage = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getDamage() {
		return this.damage;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setOther(String value) {
		this.other = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getOther() {
		return this.other;
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

	public String getCommit_name() {
		return commit_name;
	}

	public void setCommit_name(String commit_name) {
		this.commit_name = commit_name;
	}

	public String getPatient() {
		return patient;
	}

	public void setPatient(String patient) {
		this.patient = patient;
	}

	public String getPlaint_tel() {
		return plaint_tel;
	}

	public void setPlaint_tel(String plaint_tel) {
		this.plaint_tel = plaint_tel;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPlainter() {
		return plainter;
	}

	public void setPlainter(String plainter) {
		this.plainter = plainter;
	}

	public String getPatient_ref() {
		return patient_ref;
	}

	public void setPatient_ref(String patient_ref) {
		this.patient_ref = patient_ref;
	}
	
}


