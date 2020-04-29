/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.entity.teachingmanagement;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_ZYY_NTRAIN_INAREA
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class HrZyyNtrainInarea implements Serializable {

	
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
	private String teacher;
	
	/**
	 * 
	 */
	private Double meet_sign;
	
	/**
	 * 
	 */
	private Double skill_train;
	
	/**
	 * 
	 */
	private Double skill_eval;
	
	/**
	 * 
	 */
	private Double case_num;
	
	/**
	 * 
	 */
	private Double case_dept_num;
	
	/**
	 * 
	 */
	private Double case_eval;
	
	/**
	 * 
	 */
	private Double drg_num;
	
	/**
	 * 
	 */
	private Double drg_eval;
	
	/**
	 * 
	 */
	private Double dept_work;
	
	/**
	 * 
	 */
	private Double bed;
	
	/**
	 * 
	 */
	private Double on_duty;
	
	/**
	 * 
	 */
	private Double mmeet_case;
	
	/**
	 * 
	 */
	private Double dept_advice;
	
	/**
	 * 
	 */
	private Double advice_eval;
	
	/**
	 * 
	 */
	private Double mini_cex;
	
	/**
	 * 
	 */
	private Double physique;
	
	/**
	 * 
	 */
	private Double consultation;
	
	/**
	 * 
	 */
	private Double ade;
	
	/**
	 * 
	 */
	private Double online_reg;
	
	/**
	 * 
	 */
	private Double core_class_eval;
	
	/**
	 * 
	 */
	private Double core_class_test;
	
	/**
	 * 
	 */
	private Double dept_eval;
	
	/**
	 * 
	 */
	private Double tot_score;
	
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
	public void setTeacher(String value) {
		this.teacher = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getTeacher() {
		return this.teacher;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setMeet_sign(Double value) {
		this.meet_sign = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getMeet_sign() {
		return this.meet_sign;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setSkill_train(Double value) {
		this.skill_train = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getSkill_train() {
		return this.skill_train;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setSkill_eval(Double value) {
		this.skill_eval = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getSkill_eval() {
		return this.skill_eval;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setCase_num(Double value) {
		this.case_num = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getCase_num() {
		return this.case_num;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setCase_dept_num(Double value) {
		this.case_dept_num = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getCase_dept_num() {
		return this.case_dept_num;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setCase_eval(Double value) {
		this.case_eval = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getCase_eval() {
		return this.case_eval;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setDrg_num(Double value) {
		this.drg_num = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getDrg_num() {
		return this.drg_num;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setDrg_eval(Double value) {
		this.drg_eval = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getDrg_eval() {
		return this.drg_eval;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setDept_work(Double value) {
		this.dept_work = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getDept_work() {
		return this.dept_work;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setBed(Double value) {
		this.bed = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getBed() {
		return this.bed;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setOn_duty(Double value) {
		this.on_duty = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getOn_duty() {
		return this.on_duty;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setMmeet_case(Double value) {
		this.mmeet_case = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getMmeet_case() {
		return this.mmeet_case;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setDept_advice(Double value) {
		this.dept_advice = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getDept_advice() {
		return this.dept_advice;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setAdvice_eval(Double value) {
		this.advice_eval = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getAdvice_eval() {
		return this.advice_eval;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setMini_cex(Double value) {
		this.mini_cex = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getMini_cex() {
		return this.mini_cex;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setPhysique(Double value) {
		this.physique = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getPhysique() {
		return this.physique;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setConsultation(Double value) {
		this.consultation = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getConsultation() {
		return this.consultation;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setAde(Double value) {
		this.ade = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getAde() {
		return this.ade;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setOnline_reg(Double value) {
		this.online_reg = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getOnline_reg() {
		return this.online_reg;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setCore_class_eval(Double value) {
		this.core_class_eval = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getCore_class_eval() {
		return this.core_class_eval;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setCore_class_test(Double value) {
		this.core_class_test = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getCore_class_test() {
		return this.core_class_test;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setDept_eval(Double value) {
		this.dept_eval = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getDept_eval() {
		return this.dept_eval;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setTot_score(Double value) {
		this.tot_score = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getTot_score() {
		return this.tot_score;
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