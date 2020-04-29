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
 * HR_RESEARCH
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class HrResearch implements Serializable {

	
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
	 * DIC_PROB_NATURE
	 */
	private String prob_nature;
	private String prob_nature_name;
	/**
	 * DIC_PROB_TYPE
	 */
	private String prob_type;
	private String prob_type_name;
	
	/**
	 * 0：无 1:有
	 */
	private String case_discuss;
	
	/**
	 * 
	 */
	private String case_no;
	
	/**
	 * 
	 */
	private String join_doc;
	
	/**
	 * 
	 */
	private String opinion;
	
	/**
	 * 
	 */
	private String prob_reason;
	
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
	* 设置 DIC_PROB_NATURE
	* @param value 
	*/
	public void setProb_nature(String value) {
		this.prob_nature = value;
	}
	
	/**
	* 获取 DIC_PROB_NATURE
	* @return String
	*/
	public String getProb_nature() {
		return this.prob_nature;
	}
	/**
	* 设置 DIC_PROB_TYPE
	* @param value 
	*/
	public void setProb_type(String value) {
		this.prob_type = value;
	}
	
	/**
	* 获取 DIC_PROB_TYPE
	* @return String
	*/
	public String getProb_type() {
		return this.prob_type;
	}
	/**
	* 设置 0：无 1:有
	* @param value 
	*/
	public void setCase_discuss(String value) {
		this.case_discuss = value;
	}
	
	/**
	* 获取 0：无 1:有
	* @return String
	*/
	public String getCase_discuss() {
		return this.case_discuss;
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
	public void setJoin_doc(String value) {
		this.join_doc = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getJoin_doc() {
		return this.join_doc;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setOpinion(String value) {
		this.opinion = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getOpinion() {
		return this.opinion;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setProb_reason(String value) {
		this.prob_reason = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getProb_reason() {
		return this.prob_reason;
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
	
	public String getCommit_name() {
		return commit_name;
	}

	public void setCommit_name(String commit_name) {
		this.commit_name = commit_name;
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

	public String getProb_nature_name() {
		return prob_nature_name;
	}

	public void setProb_nature_name(String prob_nature_name) {
		this.prob_nature_name = prob_nature_name;
	}

	public String getProb_type_name() {
		return prob_type_name;
	}

	public void setProb_type_name(String prob_type_name) {
		this.prob_type_name = prob_type_name;
	}
	
}