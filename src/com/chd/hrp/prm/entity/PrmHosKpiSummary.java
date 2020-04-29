
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
 * 0210 医院KPI考评总结表
 * @Table:
 * PRM_HOS_KPI_SUMMARY
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class PrmHosKpiSummary implements Serializable {

	
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
	 * 考核医院
	 */
	private Long check_hos_id;
	
	/**
	 * 医院编码
	 */
	private String hos_code;
	/**
	 * 医院名称
	 */
	private String hos_name;
	
	
	/**
	 * 指标评分
	 */
	private Double kpi_score;
	
	/**
	 * 绩效总结
	 */
	private String summary;
	
	/**
	 * 图片路径
	 */
	private String led_path;

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	
	
	public String getHos_code() {
	return hos_code;
}

public void setHos_code(String hos_code) {
	this.hos_code = hos_code;
}

	public String getLed_path() {
	return led_path;
}

public void setLed_path(String led_path) {
	this.led_path = led_path;
}

	public String getHos_name() {
	return hos_name;
}

public void setHos_name(String hos_name) {
	this.hos_name = hos_name;
}

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
	* 设置 考核医院
	* @param value 
	*/
	public void setCheck_hos_id(Long value) {
		this.check_hos_id = value;
	}
	
	/**
	* 获取 考核医院
	* @return Long
	*/
	public Long getCheck_hos_id() {
		return this.check_hos_id;
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