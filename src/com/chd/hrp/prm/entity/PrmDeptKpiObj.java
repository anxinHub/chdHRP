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
 * @Description: 0302 科室绩效方案核算对象表
 * @Table: PRM_DEPT_KPI_OBJ
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public class PrmDeptKpiObj implements Serializable {

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
	 * 变更ID
	 */
	private Long dept_no;

	/**
	 * 部门ID
	 */
	private Long dept_id;

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

	private String dept_name;
	
	private String dept_code;
	
	private String dept_kind_code;
	
	private String dept_kind_name;

	
	private String kpi_name;
	
	private String nature_code;
	
	private String nature_name;
	
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


	/**
	 * 导入验证信息
	 */
	private String error_type;

	/**
	 * 设置 集团ID
	 * 
	 * @param value
	 */
	public void setGroup_id(Long value) {
		this.group_id = value;
	}

	/**
	 * 获取 集团ID
	 * 
	 * @return Long
	 */
	public Long getGroup_id() {
		return this.group_id;
	}

	/**
	 * 设置 医院ID
	 * 
	 * @param value
	 */
	public void setHos_id(Long value) {
		this.hos_id = value;
	}

	/**
	 * 获取 医院ID
	 * 
	 * @return Long
	 */
	public Long getHos_id() {
		return this.hos_id;
	}

	/**
	 * 设置 账套编码
	 * 
	 * @param value
	 */
	public void setCopy_code(String value) {
		this.copy_code = value;
	}

	/**
	 * 获取 账套编码
	 * 
	 * @return String
	 */
	public String getCopy_code() {
		return this.copy_code;
	}

	/**
	 * 设置 年度
	 * 
	 * @param value
	 */
	public void setAcc_year(String value) {
		this.acc_year = value;
	}

	/**
	 * 获取 年度
	 * 
	 * @return String
	 */
	public String getAcc_year() {
		return this.acc_year;
	}

	/**
	 * 设置 目标编码
	 * 
	 * @param value
	 */
	public void setGoal_code(String value) {
		this.goal_code = value;
	}

	/**
	 * 获取 目标编码
	 * 
	 * @return String
	 */
	public String getGoal_code() {
		return this.goal_code;
	}

	/**
	 * 设置 指标编码
	 * 
	 * @param value
	 */
	public void setKpi_code(String value) {
		this.kpi_code = value;
	}

	/**
	 * 获取 指标编码
	 * 
	 * @return String
	 */
	public String getKpi_code() {
		return this.kpi_code;
	}

	/**
	 * 设置 变更ID
	 * 
	 * @param value
	 */
	public void setDept_no(Long value) {
		this.dept_no = value;
	}

	/**
	 * 获取 变更ID
	 * 
	 * @return Long
	 */
	public Long getDept_no() {
		return this.dept_no;
	}

	/**
	 * 设置 部门ID
	 * 
	 * @param value
	 */
	public void setDept_id(Long value) {
		this.dept_id = value;
	}

	/**
	 * 获取 部门ID
	 * 
	 * @return Long
	 */
	public Long getDept_id() {
		return this.dept_id;
	}

	/**
	 * 设置 上级指标编码
	 * 
	 * @param value
	 */
	public void setSuper_kpi_code(String value) {
		this.super_kpi_code = value;
	}

	/**
	 * 获取 上级指标编码
	 * 
	 * @return String
	 */
	public String getSuper_kpi_code() {
		return this.super_kpi_code;
	}

	/**
	 * 设置 相当于rowid
	 * 
	 * @param value
	 */
	public void setOrder_no(Integer value) {
		this.order_no = value;
	}

	/**
	 * 获取 相当于rowid
	 * 
	 * @return Integer
	 */
	public Integer getOrder_no() {
		return this.order_no;
	}

	public Integer getKpi_level() {
		return kpi_level;
	}

	public void setKpi_level(Integer kpi_level) {
		this.kpi_level = kpi_level;
	}

	/**
	 * 设置 1:末级 0:非末级
	 * 
	 * @param value
	 */
	public void setIs_last(Integer value) {
		this.is_last = value;
	}

	/**
	 * 获取 1:末级 0:非末级
	 * 
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

	public String getDept_kind_code() {
		return dept_kind_code;
	}

	public void setDept_kind_code(String dept_kind_code) {
		this.dept_kind_code = dept_kind_code;
	}

	public String getDept_kind_name() {
		return dept_kind_name;
	}

	public void setDept_kind_name(String dept_kind_name) {
		this.dept_kind_name = dept_kind_name;
	}

}