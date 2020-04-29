/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.entity.teachingmanagement;

import java.io.Serializable;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_WITH_TEACH
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class HrWithTeach implements Serializable {

	
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
	private Double emp_id;
	
	/**
	 * 
	 */
	private String year;
	
	/**
	 * 
	 */
	private String month;
	
	/**
	 * 
	 */
	private Double dept_id;
	
	/**
	 * 
	 */
	private String student;
	
	/**
	 * 
	 */
	private Double rotate;
	
	/**
	 * 
	 */
	private Double stud_eval;
	
	/**
	 * 
	 */
	private Double net_aduit;
	
	/**
	 * 
	 */
	private Double attend_meet;
	
	/**
	 * 
	 */
	private Double with_teach_grade;
	
	/**
	 * 
	 */
	private Double with_teach_money;
	
	/**
	 * 
	 */
	private Double ultrasound;
	
	/**
	 * 
	 */
	private Double case_qc;
	
	/**
	 * 
	 */
	private Double teach;
	
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
	public void setYear(String value) {
		this.year = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getYear() {
		return this.year;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setMonth(String value) {
		this.month = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getMonth() {
		return this.month;
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
	public void setStudent(String value) {
		this.student = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getStudent() {
		return this.student;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setRotate(Double value) {
		this.rotate = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getRotate() {
		return this.rotate;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setStud_eval(Double value) {
		this.stud_eval = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getStud_eval() {
		return this.stud_eval;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setNet_aduit(Double value) {
		this.net_aduit = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getNet_aduit() {
		return this.net_aduit;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setAttend_meet(Double value) {
		this.attend_meet = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getAttend_meet() {
		return this.attend_meet;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setWith_teach_grade(Double value) {
		this.with_teach_grade = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getWith_teach_grade() {
		return this.with_teach_grade;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setWith_teach_money(Double value) {
		this.with_teach_money = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getWith_teach_money() {
		return this.with_teach_money;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setUltrasound(Double value) {
		this.ultrasound = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getUltrasound() {
		return this.ultrasound;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setCase_qc(Double value) {
		this.case_qc = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getCase_qc() {
		return this.case_qc;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setTeach(Double value) {
		this.teach = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getTeach() {
		return this.teach;
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