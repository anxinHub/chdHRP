
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
 * 0202 医院绩效方案核算对象表
 * @Table:
 * PRM_HOS_KPI_OBJ
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class PrmHosKpiObj implements Serializable {

	
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
	 * 指标编码
	 */
	private String kpi_code;
	
	/**
	 * 考核医院
	 */
	private Long check_hos_id;
	
	/**
	 * 上级指标编码
	 */
	private String super_kpi_code;
	
	/**
	 * 相当于rowid
	 */
	private Integer order_no;
	
	/**
	 * 用于补空格
	 */
	private Integer kpi_level;
	
	/**
	 * 1:末级 0:非末级
	 */
	private Integer is_last;
	
	/**
	 * 考核单位名称
	 */
	private String check_hos_name;

	private String hos_simple;
	private String hos_name;
	private String hos_code;
	private String check_hos_simple;

	private String kpi_name;
	public String getKpi_name() {
		return kpi_name;
	}

	public void setKpi_name(String kpi_name) {
		this.kpi_name = kpi_name;
	}

	public String getNature_code() {
		return nature_code;
	}

	public void setNature_code(String nature_code) {
		this.nature_code = nature_code;
	}

	public String getNature_name() {
		return nature_name;
	}

	public void setNature_name(String nature_name) {
		this.nature_name = nature_name;
	}

	private String nature_code;
	private String nature_name;

	

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
	* 设置 相当于rowid
	* @param value 
	*/
	public void setOrder_no(Integer value) {
		this.order_no = value;
	}
	
	/**
	* 获取 相当于rowid
	* @return Integer
	*/
	public Integer getOrder_no() {
		return this.order_no;
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

	public Integer getKpi_level() {
		return kpi_level;
	}

	public void setKpi_level(Integer kpi_level) {
		this.kpi_level = kpi_level;
	}

	public String getCheck_hos_name() {
		return check_hos_name;
	}

	public void setCheck_hos_name(String check_hos_name) {
		this.check_hos_name = check_hos_name;
	}

	public String getHos_simple() {
		return hos_simple;
	}

	public void setHos_simple(String hos_simple) {
		this.hos_simple = hos_simple;
	}

	public String getHos_name() {
		return hos_name;
	}

	public void setHos_name(String hos_name) {
		this.hos_name = hos_name;
	}

	public String getHos_code() {
		return hos_code;
	}

	public void setHos_code(String hos_code) {
		this.hos_code = hos_code;
	}

	public String getCheck_hos_simple() {
		return check_hos_simple;
	}

	public void setCheck_hos_simple(String check_hos_simple) {
		this.check_hos_simple = check_hos_simple;
	}
	
}