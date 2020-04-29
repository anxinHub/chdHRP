
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.entity;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 0410 职工KPI考评总结表
 * @Table:
 * PRM_EMP_KPI_SUMMARY
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class PrmEmpKpiSummary implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 集团ID
	 */
	private Long group_id;
	
	/**
	 * 医院ID
	 */
	private Long hos_id;
	
	/**
	 * 账套编码
	 */
	private String copy_code;
	
	/**
	 * 年度
	 */
	private String acc_year;
	
	/**
	 * 月份
	 */
	private String acc_month;
	
	/**
	 * 目标编码
	 */
	private String goal_code;
	
	/**
	 * 职工变更ID
	 */
	private Long emp_no;
	
	/**
	 * 职工ID
	 */
	private Long emp_id;
	
	/**
	 * 指标评分
	 */
	private Double kpi_score;
	
	/**
	 * 绩效总结
	 */
	private String summary;
	
	private String dept_code;
	
	private String dept_name;
	
	private String emp_code;
	
	private String emp_name;
	
	private String led_path;

	
	
  public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

public String getDept_code() {
		return dept_code;
	}

	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}

	public String getEmp_code() {
		return emp_code;
	}

	public void setEmp_code(String emp_code) {
		this.emp_code = emp_code;
	}

	public String getEmp_name() {
		return emp_name;
	}

	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}

	public String getLed_path() {
		return led_path;
	}

	public void setLed_path(String led_path) {
		this.led_path = led_path;
	}
/**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 集团ID
	* @param value 
	*/
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	
	/**
	* 获取 集团ID
	* @return Long
	*/
	public Long getGroup_id() {
		return this.group_id;
	}
	/**
	* 设置 医院ID
	* @param value 
	*/
	public void setHos_id(Long value) {
		this.hos_id = value;
	}
	
	/**
	* 获取 医院ID
	* @return Long
	*/
	public Long getHos_id() {
		return this.hos_id;
	}
	/**
	* 设置 账套编码
	* @param value 
	*/
	public void setCopy_code(String value) {
		this.copy_code = value;
	}
	
	/**
	* 获取 账套编码
	* @return String
	*/
	public String getCopy_code() {
		return this.copy_code;
	}
	/**
	* 设置 年度
	* @param value 
	*/
	public void setAcc_year(String value) {
		this.acc_year = value;
	}
	
	/**
	* 获取 年度
	* @return String
	*/
	public String getAcc_year() {
		return this.acc_year;
	}
	/**
	* 设置 月份
	* @param value 
	*/
	public void setAcc_month(String value) {
		this.acc_month = value;
	}
	
	/**
	* 获取 月份
	* @return String
	*/
	public String getAcc_month() {
		return this.acc_month;
	}
	/**
	* 设置 目标编码
	* @param value 
	*/
	public void setGoal_code(String value) {
		this.goal_code = value;
	}
	
	/**
	* 获取 目标编码
	* @return String
	*/
	public String getGoal_code() {
		return this.goal_code;
	}
	/**
	* 设置 职工变更ID
	* @param value 
	*/
	public void setEmp_no(Long value) {
		this.emp_no = value;
	}
	
	/**
	* 获取 职工变更ID
	* @return Long
	*/
	public Long getEmp_no() {
		return this.emp_no;
	}
	/**
	* 设置 职工ID
	* @param value 
	*/
	public void setEmp_id(Long value) {
		this.emp_id = value;
	}
	
	/**
	* 获取 职工ID
	* @return Long
	*/
	public Long getEmp_id() {
		return this.emp_id;
	}
	/**
	* 设置 指标评分
	* @param value 
	*/
	public void setKpi_score(Double value) {
		this.kpi_score = value;
	}
	
	/**
	* 获取 指标评分
	* @return Double
	*/
	public Double getKpi_score() {
		return this.kpi_score;
	}
	/**
	* 设置 绩效总结
	* @param value 
	*/
	public void setSummary(String value) {
		this.summary = value;
	}
	
	/**
	* 获取 绩效总结
	* @return String
	*/
	public String getSummary() {
		return this.summary;
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