
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
 * 0209 医院KPI指标考评计算表
 * @Table:
 * PRM_HOS_KPI_SCORE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class PrmHosKpiScore implements Serializable {

	
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
	
	private String kpi_name;
	
	/**
	 * 部门ID
	 */
	private Long check_hos_id;
	
	private String check_hos_code;
	
	private String check_hos_name;
	
	
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
	 * 指标评分
	 */
	private Double kpi_score;
	
	private Double full_score;
	
	/**
	 * 1:审核 0:未审核
	 */
	private Integer is_audit;
	
	/**
	 * 审核人
	 */
	private String user_code;
	
	private String led_path;
	
	private String nature_code;
	private String nature_name;
	
	private Double ratio;
	
	private Double kpi_value;
	
	private Double goal_value;
	
	private String sec_code;
	
	private String grade_meth_code;
	
	private String grade_meth_name;
	
	private String fun_code;
	
	private String fun_name;
	
	private String fun_method_chs;
	
	private String method_code;
	
	private String method_name;
	
	private String formula_code;
	
	private String formula_name;
	
	private String formula_method_chs;
	
	
	public Long getCheck_hos_id() {
		return check_hos_id;
	}

	public void setCheck_hos_id(Long check_hos_id) {
		this.check_hos_id = check_hos_id;
	}

	public String getCheck_hos_code() {
		return check_hos_code;
	}

	public void setCheck_hos_code(String check_hos_code) {
		this.check_hos_code = check_hos_code;
	}

	public String getCheck_hos_name() {
		return check_hos_name;
	}

	public void setCheck_hos_name(String check_hos_name) {
		this.check_hos_name = check_hos_name;
	}

	public Double getFull_score() {
		return full_score;
	}

	public void setFull_score(Double full_score) {
		this.full_score = full_score;
	}

	public Double getGoal_value() {
		return goal_value;
	}

	public void setGoal_value(Double goal_value) {
		this.goal_value = goal_value;
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

	public Double getRatio() {
		return ratio;
	}

	public void setRatio(Double ratio) {
		this.ratio = ratio;
	}

	public Double getKpi_value() {
		return kpi_value;
	}

	public void setKpi_value(Double kpi_value) {
		this.kpi_value = kpi_value;
	}

	public String getSec_code() {
		return sec_code;
	}

	public void setSec_code(String sec_code) {
		this.sec_code = sec_code;
	}

	public String getGrade_meth_code() {
		return grade_meth_code;
	}

	public void setGrade_meth_code(String grade_meth_code) {
		this.grade_meth_code = grade_meth_code;
	}

	public String getGrade_meth_name() {
		return grade_meth_name;
	}

	public void setGrade_meth_name(String grade_meth_name) {
		this.grade_meth_name = grade_meth_name;
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

	public String getFun_method_chs() {
		return fun_method_chs;
	}

	public void setFun_method_chs(String fun_method_chs) {
		this.fun_method_chs = fun_method_chs;
	}

	public String getMethod_code() {
		return method_code;
	}

	public void setMethod_code(String method_code) {
		this.method_code = method_code;
	}

	public String getMethod_name() {
		return method_name;
	}

	public void setMethod_name(String method_name) {
		this.method_name = method_name;
	}

	public String getFormula_code() {
		return formula_code;
	}

	public void setFormula_code(String formula_code) {
		this.formula_code = formula_code;
	}

	public String getFormula_name() {
		return formula_name;
	}

	public void setFormula_name(String formula_name) {
		this.formula_name = formula_name;
	}

	public String getFormula_method_chs() {
		return formula_method_chs;
	}

	public void setFormula_method_chs(String formula_method_chs) {
		this.formula_method_chs = formula_method_chs;
	}

	public String getKpi_name() {
		return kpi_name;
	}

	public void setKpi_name(String kpi_name) {
		this.kpi_name = kpi_name;
	}

	public String getLed_path() {
		return led_path;
	}

	public void setLed_path(String led_path) {
		this.led_path = led_path;
	}
	/**
	 * YYYY-MM-DD
	 */
	private String audit_date;
	

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
	
	
	
	public Integer getKpi_level() {
		return kpi_level;
	}

	public void setKpi_level(Integer kpi_level) {
		this.kpi_level = kpi_level;
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
	* 设置 1:审核 0:未审核
	* @param value 
	*/
	public void setIs_audit(Integer value) {
		this.is_audit = value;
	}
	
	/**
	* 获取 1:审核 0:未审核
	* @return Integer
	*/
	public Integer getIs_audit() {
		return this.is_audit;
	}
	/**
	* 设置 审核人
	* @param value 
	*/
	public void setUser_code(String value) {
		this.user_code = value;
	}
	
	/**
	* 获取 审核人
	* @return String
	*/
	public String getUser_code() {
		return this.user_code;
	}
	/**
	* 设置 YYYY-MM-DD
	* @param value 
	*/
	public void setAudit_date(String value) {
		this.audit_date = value;
	}
	
	/**
	* 获取 YYYY-MM-DD
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
}