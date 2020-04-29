
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.hpm.entity;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 9904 绩效指标取值方法配置表
 * @Table:
 * PRM_TARGET_METHOD
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AphiTargetMethod implements Serializable {

	
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
	
	private String target_code;
	
	private String target_name;
	
	private String target_note;
	
	private String nature_code;
	
	private String nature_name;
	
	private String formula_name;
	
	private String formula_method_chs;
	
	private String formula_method_eng;
	
	private String fun_name;
	
	private String fun_note;
	
	private String fun_method_chs;
	
	private String fun_method_eng;

	private String method_code;
	
	private String method_name ; 

	private String formula_code;

	private String fun_code;
	
	private String f_p_v;
	
	  
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
	/**
	 * 指标中的是否停用
	 */
	private String is_stop;
	
	private String error_type;
	
	
	
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

	public String getFun_note() {
		return fun_note;
	}

	public void setFun_note(String fun_note) {
		this.fun_note = fun_note;
	}

	public String getF_p_v() {
		return f_p_v;
	}

	public void setF_p_v(String f_p_v) {
		this.f_p_v = f_p_v;
	}

public String getFun_name() {
		return fun_name;
	}

	public void setFun_name(String fun_name) {
		this.fun_name = fun_name;
	}

public String getNature_code() {
		return nature_code;
	}

	public void setNature_code(String nature_code) {
		this.nature_code = nature_code;
	}

public String getFormula_name() {
		return formula_name;
	}

	public void setFormula_name(String formula_name) {
		this.formula_name = formula_name;
	}

 
public String getMethod_name() {
		return method_name;
	}

	public void setMethod_name(String method_name) {
		this.method_name = method_name;
	}

public String getTarget_name() {
		return target_name;
	}

	public void setTarget_name(String target_name) {
		this.target_name = target_name;
	}

	
	public String getTarget_note() {
		return target_note;
	}

	public void setTarget_note(String target_note) {
		this.target_note = target_note;
	}

	public String getNature_name() {
		return nature_name;
	}

	public void setNature_name(String nature_name) {
		this.nature_name = nature_name;
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
	public void setTarget_code(String value) {
		this.target_code = value;
	}
	
	/**
	* 获取 指标编码
	* @return String
	*/
	public String getTarget_code() {
		return this.target_code;
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
	
	
	
	public String getIs_stop() {
		return is_stop;
	}

	public void setIs_stop(String is_stop) {
		this.is_stop = is_stop;
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