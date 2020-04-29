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
 * @Description: 0308 科室KPI指标数据准备表
 * @Table: PRM_DEPT_KPI_VALUE
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public class PrmDeptKpiValue implements Serializable {

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
	 * 变更ID
	 */
	private Long dept_no;

	/**
	 * 部门ID
	 */
	private Long dept_id;

	/**
	 * 指标值
	 */
	private Double kpi_value;

	/**
	 * 1:审核 0:未审核
	 */
	private Integer is_audit;

	/**
	 * 审核人
	 */
	private String user_code;

	/**
	 * YYYY-MM-DD
	 */
	private String audit_date;

	private String kpi_name;

	private String dept_name;

	private String method_name;

	private String formula_method_chs;

	private String dept_code;

	private String user_name;

	private String method_code;

	private String formula_code;

	private String fun_code;

	private String fun_name;

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getDept_code() {
		return dept_code;
	}

	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getKpi_name() {
		return kpi_name;
	}

	public void setKpi_name(String kpi_name) {
		this.kpi_name = kpi_name;
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
	 * 设置 月份
	 * 
	 * @param value
	 */
	public void setAcc_month(String value) {
		this.acc_month = value;
	}

	/**
	 * 获取 月份
	 * 
	 * @return String
	 */
	public String getAcc_month() {
		return this.acc_month;
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
	 * 设置 指标值
	 * 
	 * @param value
	 */
	public void setKpi_value(Double value) {
		this.kpi_value = value;
	}

	/**
	 * 获取 指标值
	 * 
	 * @return Double
	 */
	public Double getKpi_value() {
		return this.kpi_value;
	}

	/**
	 * 设置 1:审核 0:未审核
	 * 
	 * @param value
	 */
	public void setIs_audit(Integer value) {
		this.is_audit = value;
	}

	/**
	 * 获取 1:审核 0:未审核
	 * 
	 * @return Integer
	 */
	public Integer getIs_audit() {
		return this.is_audit;
	}

	/**
	 * 设置 审核人
	 * 
	 * @param value
	 */
	public void setUser_code(String value) {
		this.user_code = value;
	}

	/**
	 * 获取 审核人
	 * 
	 * @return String
	 */
	public String getUser_code() {
		return this.user_code;
	}

	/**
	 * 设置 YYYY-MM-DD
	 * 
	 * @param value
	 */
	public void setAudit_date(String value) {
		this.audit_date = value;
	}

	/**
	 * 获取 YYYY-MM-DD
	 * 
	 * @return String
	 */
	public String getAudit_date() {
		return this.audit_date;
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

	public String getMethod_name() {
		return method_name;
	}

	public void setMethod_name(String method_name) {
		this.method_name = method_name;
	}

	public String getFormula_method_chs() {
		return formula_method_chs;
	}

	public void setFormula_method_chs(String formula_method_chs) {
		this.formula_method_chs = formula_method_chs;
	}

	public String getMethod_code() {
		return method_code;
	}

	public void setMethod_code(String method_code) {
		this.method_code = method_code;
	}

	public String getFormula_code() {
		return formula_code;
	}

	public void setFormula_code(String formula_code) {
		this.formula_code = formula_code;
	}

	public String getFun_code() {
		return fun_code;
	}

	public void setFun_code(String fun_code) {
		this.fun_code = fun_code;
	}

	public String getFun_name() {
		return fun_name;
	}

	public void setFun_name(String fun_name) {
		this.fun_name = fun_name;
	}

}