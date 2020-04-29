
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
 * 0405 职工指标区间法参数表
 * @Table:
 * PRM_EMP_KPI_SECTION
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class PrmEmpKpiSection implements Serializable {

	
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
	 * 指标编码
	 */
	private String kpi_code;
	
	/**
	 * 职工变更ID
	 */
	private Long emp_no;
	
	/**
	 * 职工ID
	 */
	private Long emp_id;
	
	/**
	 * 区间序列
	 */
	private Integer section;
	
	/**
	 * KPI起始值
	 */
	private Double kpi_beg_value;
	
	/**
	 * KPI结束值
	 */
	private Double kpi_end_value;
	
	/**
	 * KPI起始分
	 */
	private Double kpi_beg_score;
	
	/**
	 * KPI结束分
	 */
	private Double kpi_end_score;
	

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
	* 设置 指标编码
	* @param value 
	*/
	public void setKpi_code(String value) {
		this.kpi_code = value;
	}
	
	/**
	* 获取 指标编码
	* @return String
	*/
	public String getKpi_code() {
		return this.kpi_code;
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
	* 设置 区间序列
	* @param value 
	*/
	public void setSection(Integer value) {
		this.section = value;
	}
	
	/**
	* 获取 区间序列
	* @return Integer
	*/
	public Integer getSection() {
		return this.section;
	}
	/**
	* 设置 KPI起始值
	* @param value 
	*/
	public void setKpi_beg_value(Double value) {
		this.kpi_beg_value = value;
	}
	
	/**
	* 获取 KPI起始值
	* @return Double
	*/
	public Double getKpi_beg_value() {
		return this.kpi_beg_value;
	}
	/**
	* 设置 KPI结束值
	* @param value 
	*/
	public void setKpi_end_value(Double value) {
		this.kpi_end_value = value;
	}
	
	/**
	* 获取 KPI结束值
	* @return Double
	*/
	public Double getKpi_end_value() {
		return this.kpi_end_value;
	}
	/**
	* 设置 KPI起始分
	* @param value 
	*/
	public void setKpi_beg_score(Double value) {
		this.kpi_beg_score = value;
	}
	
	/**
	* 获取 KPI起始分
	* @return Double
	*/
	public Double getKpi_beg_score() {
		return this.kpi_beg_score;
	}
	/**
	* 设置 KPI结束分
	* @param value 
	*/
	public void setKpi_end_score(Double value) {
		this.kpi_end_score = value;
	}
	
	/**
	* 获取 KPI结束分
	* @return Double
	*/
	public Double getKpi_end_score() {
		return this.kpi_end_score;
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