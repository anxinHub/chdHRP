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
 * @Description: 0203 医院绩效方案表
 * @Table: PRM_HOS_SCHEME
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public class PrmHosScheme implements Serializable {

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
	private Integer  kpi_level;
	
	/**
	 * 1:末级 0:非末级
	 */
	private Integer is_last;
	
	/**
	 * 权重
	 */
	private Double ratio;
	
	/**
	 * 目标值
	 */
	private Double goal_value;
	
	private Double full_score;
	
	/**
	 * 评分方法
	 */
	private String grade_meth_code;
	
	/**
	 * 01:手工录入 02:计算公式 03:取值函数
	 */
	private String method_code;
	
	/**
	 * 取值公式
	 */
	private String formula_code;
	
	/**
	 * 取值函数
	 */
	private String fun_code;
	
	
	private String nature_name;
	
	private String kpi_name;
	
	private String grade_meth_name;

	private String method_name;

	
    private String formula_name;
	
	private String formula_method_chs;
	
	private String formula_method_eng;
	
	private String fun_name;
	
	private String fun_method_chs;
	
	private String fun_method_eng;
	
	private String hos_code;
	
	private String hos_name;
	

	public String getHos_code() {
		return hos_code;
	}

	public void setHos_code(String hos_code) {
		this.hos_code = hos_code;
	}

	public String getHos_name() {
		return hos_name;
	}

	public void setHos_name(String hos_name) {
		this.hos_name = hos_name;
	}

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

	public String getFormula_method_eng() {
		return formula_method_eng;
	}

	public void setFormula_method_eng(String formula_method_eng) {
		this.formula_method_eng = formula_method_eng;
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

	public String getFun_method_eng() {
		return fun_method_eng;
	}

	public void setFun_method_eng(String fun_method_eng) {
		this.fun_method_eng = fun_method_eng;
	}
	
  public String getGrade_meth_name() {
		return grade_meth_name;
	}

	public void setGrade_meth_name(String grade_meth_name) {
		this.grade_meth_name = grade_meth_name;
	}

	public String getMethod_name() {
		return method_name;
	}

	public void setMethod_name(String method_name) {
		this.method_name = method_name;
	}

	public String getNature_name() {
		return nature_name;
	}

	public void setNature_name(String nature_name) {
		this.nature_name = nature_name;
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
	* 设置 权重
	* @param value 
	*/
	public void setRatio(Double value) {
		this.ratio = value;
	}
	
	/**
	* 获取 权重
	* @return Double
	*/
	public Double getRatio() {
		return this.ratio;
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
	* 设置 评分方法
	* @param value 
	*/
	public void setGrade_meth_code(String value) {
		this.grade_meth_code = value;
	}
	
	/**
	* 获取 评分方法
	* @return String
	*/
	public String getGrade_meth_code() {
		return this.grade_meth_code;
	}
	/**
	* 设置 01:手工录入 02:计算公式 03:取值函数
	* @param value 
	*/
	public void setMethod_code(String value) {
		this.method_code = value;
	}
	
	/**
	* 获取 01:手工录入 02:计算公式 03:取值函数
	* @return String
	*/
	public String getMethod_code() {
		return this.method_code;
	}
	/**
	* 设置 取值公式
	* @param value 
	*/
	public void setFormula_code(String value) {
		this.formula_code = value;
	}
	
	/**
	* 获取 取值公式
	* @return String
	*/
	public String getFormula_code() {
		return this.formula_code;
	}
	/**
	* 设置 取值函数
	* @param value 
	*/
	public void setFun_code(String value) {
		this.fun_code = value;
	}
	
	/**
	* 获取 取值函数
	* @return String
	*/
	public String getFun_code() {
		return this.fun_code;
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