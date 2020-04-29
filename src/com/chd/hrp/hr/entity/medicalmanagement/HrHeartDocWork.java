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
 * HR_HEART_DOC_WORK
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class HrHeartDocWork implements Serializable {

	
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
	private Double gen_ecg;
	
	/**
	 * 
	 */
	private Double bed_ecg;
	
	/**
	 * 
	 */
	private Double dyn_ecg;
	
	/**
	 * 
	 */
	private Double dyn_blood;
	
	/**
	 * 
	 */
	private Double bed_ecg_variate;
	
	/**
	 * 
	 */
	private Double move_ecg;
	
	/**
	 * 
	 */
	private Double heart_vector;
	
	/**
	 * 
	 */
	private Double atrial_pcting;
	
	/**
	 * 
	 */
	private Double heart_money;
	
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
	public void setGen_ecg(Double value) {
		this.gen_ecg = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getGen_ecg() {
		return this.gen_ecg;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setBed_ecg(Double value) {
		this.bed_ecg = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getBed_ecg() {
		return this.bed_ecg;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setDyn_ecg(Double value) {
		this.dyn_ecg = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getDyn_ecg() {
		return this.dyn_ecg;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setDyn_blood(Double value) {
		this.dyn_blood = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getDyn_blood() {
		return this.dyn_blood;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setBed_ecg_variate(Double value) {
		this.bed_ecg_variate = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getBed_ecg_variate() {
		return this.bed_ecg_variate;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setMove_ecg(Double value) {
		this.move_ecg = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getMove_ecg() {
		return this.move_ecg;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setHeart_vector(Double value) {
		this.heart_vector = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getHeart_vector() {
		return this.heart_vector;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setAtrial_pcting(Double value) {
		this.atrial_pcting = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getAtrial_pcting() {
		return this.atrial_pcting;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setHeart_money(Double value) {
		this.heart_money = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getHeart_money() {
		return this.heart_money;
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