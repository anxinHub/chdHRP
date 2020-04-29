
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
 * 0103 目标管理明细指标表
 * @Table:
 * PRM_GOAL_DETAIL_KPI
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class PrmGoalDetailKpi implements Serializable {

	
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
	 * 目标编码
	 */
	private String goal_code;
	
	/**
	 * 子目标编码
	 */
	private String child_goal_code;
	
	/**
	 * 指标编码
	 */
	private String kpi_code;
	
	/**
	 * 指标名称
	 */
	private String kpi_name;
	
	/**
	 * 01:正向
02:反向
	 */
	private String nature_code;
	
	/**
	 * 上级指标编码
	 */
	private String super_kpi_code;
	
	/**
	 * 1:末级 0:非末级
	 */
	private Integer is_last;
	
	/**
	 * 目标值
	 */
	private Double goal_value;
	
	/**
	 * 行动要点
	 */
	private String action_note;
	

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
	* 设置 子目标编码
	* @param value 
	*/
	public void setChild_goal_code(String value) {
		this.child_goal_code = value;
	}
	
	/**
	* 获取 子目标编码
	* @return String
	*/
	public String getChild_goal_code() {
		return this.child_goal_code;
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
	* 设置 指标名称
	* @param value 
	*/
	public void setKpi_name(String value) {
		this.kpi_name = value;
	}
	
	/**
	* 获取 指标名称
	* @return String
	*/
	public String getKpi_name() {
		return this.kpi_name;
	}
	/**
	* 设置 01:正向
02:反向
	* @param value 
	*/
	public void setNature_code(String value) {
		this.nature_code = value;
	}
	
	/**
	* 获取 01:正向
02:反向
	* @return String
	*/
	public String getNature_code() {
		return this.nature_code;
	}
	/**
	* 设置 上级指标编码
	* @param value 
	*/
	public void setSuper_kpi_code(String value) {
		this.super_kpi_code = value;
	}
	
	/**
	* 获取 上级指标编码
	* @return String
	*/
	public String getSuper_kpi_code() {
		return this.super_kpi_code;
	}
	/**
	* 设置 1:末级 0:非末级
	* @param value 
	*/
	public void setIs_last(Integer value) {
		this.is_last = value;
	}
	
	/**
	* 获取 1:末级 0:非末级
	* @return Integer
	*/
	public Integer getIs_last() {
		return this.is_last;
	}
	/**
	* 设置 目标值
	* @param value 
	*/
	public void setGoal_value(Double value) {
		this.goal_value = value;
	}
	
	/**
	* 获取 目标值
	* @return Double
	*/
	public Double getGoal_value() {
		return this.goal_value;
	}
	/**
	* 设置 行动要点
	* @param value 
	*/
	public void setAction_note(String value) {
		this.action_note = value;
	}
	
	/**
	* 获取 行动要点
	* @return String
	*/
	public String getAction_note() {
		return this.action_note;
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