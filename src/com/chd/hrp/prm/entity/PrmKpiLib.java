
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
 * 0502 KPI指标库表
 * @Table:
 * PRM_KPI_LIB
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class PrmKpiLib implements Serializable {

	
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
	 * 指标编码
	 */
	private String kpi_code;
	
	/**
	 * 指标名称
	 */
	private String kpi_name;
	
	/**
	 * 01:正向 02:反向
	 */
	private String nature_code;
	
	
	/**
	 * 01:正向 02:反向
	 * */
	private String nature_name;
	/**
	 * 维度编码
	 */
	private String dim_code;
	
	/**
	 * 维度编码
	 */
	
	private String dim_name;
	
	public String getNature_name() {
		return nature_name;
	}

	public void setNature_name(String nature_name) {
		this.nature_name = nature_name;
	}

	public String getDim_name() {
		return dim_name;
	}

	public void setDim_name(String dim_name) {
		this.dim_name = dim_name;
	}
	/**
	 * 拼音码
	 */
	private String spell_code;
	
	/**
	 * 五笔码
	 */
	private String wbx_code;
	
	/**
	 * 指标描述
	 */
	private String kpi_note;
	
	/**
	 * 0:在用 1:停用
	 */
	private Integer is_stop;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	
	/**
	 * 设立目的
	 */
	
	private String kpi_set_note;
	
	
	/**
	 * 行动要点
	 */
	
	private String kpi_act_note;
	
	
	/**
	 * 计量单位
	 */
	
	private String  unit_code;
	

	private String kpi_hos_type;
	
	private String kpi_dept_type;
	
	private String kpi_emp_type;
	

	public String getKpi_hos_type() {
		return kpi_hos_type;
	}

	public void setKpi_hos_type(String kpi_hos_type) {
		this.kpi_hos_type = kpi_hos_type;
	}

	public String getKpi_dept_type() {
		return kpi_dept_type;
	}

	public void setKpi_dept_type(String kpi_dept_type) {
		this.kpi_dept_type = kpi_dept_type;
	}

	public String getKpi_emp_type() {
		return kpi_emp_type;
	}

	public void setKpi_emp_type(String kpi_emp_type) {
		this.kpi_emp_type = kpi_emp_type;
	}

	public String getKpi_set_note() {
		return kpi_set_note;
	}

	public void setKpi_set_note(String kpi_set_note) {
		this.kpi_set_note = kpi_set_note;
	}

	public String getKpi_act_note() {
		return kpi_act_note;
	}

	public void setKpi_act_note(String kpi_act_note) {
		this.kpi_act_note = kpi_act_note;
	}

	public String getUnit_code() {
		return unit_code;
	}

	public void setUnit_code(String unit_code) {
		this.unit_code = unit_code;
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
	* 设置 01:正向 02:反向
	* @param value 
	*/
	public void setNature_code(String value) {
		this.nature_code = value;
	}
	
	/**
	* 获取 01:正向 02:反向
	* @return String
	*/
	public String getNature_code() {
		return this.nature_code;
	}
	/**
	* 设置 维度编码
	* @param value 
	*/
	public void setDim_code(String value) {
		this.dim_code = value;
	}
	
	/**
	* 获取 维度编码
	* @return String
	*/
	public String getDim_code() {
		return this.dim_code;
	}
	/**
	* 设置 拼音码
	* @param value 
	*/
	public void setSpell_code(String value) {
		this.spell_code = value;
	}
	
	/**
	* 获取 拼音码
	* @return String
	*/
	public String getSpell_code() {
		return this.spell_code;
	}
	/**
	* 设置 五笔码
	* @param value 
	*/
	public void setWbx_code(String value) {
		this.wbx_code = value;
	}
	
	/**
	* 获取 五笔码
	* @return String
	*/
	public String getWbx_code() {
		return this.wbx_code;
	}

	
	public String getKpi_note() {
		return kpi_note;
	}

	public void setKpi_note(String kpi_note) {
		this.kpi_note = kpi_note;
	}

	/**
	* 设置 0:在用 1:停用
	* @param value 
	*/
	public void setIs_stop(Integer value) {
		this.is_stop = value;
	}
	
	/**
	* 获取 0:在用 1:停用
	* @return Integer
	*/
	public Integer getIs_stop() {
		return this.is_stop;
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