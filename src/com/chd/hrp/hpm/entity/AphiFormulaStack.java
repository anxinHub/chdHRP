
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
 * 9907 绩效计算公式指标栈
 * @Table:
 * PRM_FORMULA_STACK
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AphiFormulaStack implements Serializable {

	
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
	 * 公式编码
	 */
	private String formula_code;
	
	/**
	 * 指标代码
	 */
	private String target_code;
	
	/**
	 * 出栈序号
	 */
	private Integer stack_seq_no;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	private Double target_value;
	
	private String target_name;
	
	private String nature_code;
	
	private String nature_name;
	
	private String formula_method_chs;
	
	private String fun_method_chs;
	
	
	
	public Double getTarget_value() {
		return target_value;
	}

	public void setTarget_value(Double target_value) {
		this.target_value = target_value;
	}

	public String getTarget_name() {
		return target_name;
	}

	public void setTarget_name(String target_name) {
		this.target_name = target_name;
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

	public String getFormula_method_chs() {
		return formula_method_chs;
	}

	public void setFormula_method_chs(String formula_method_chs) {
		this.formula_method_chs = formula_method_chs;
	}

	public String getFun_method_chs() {
		return fun_method_chs;
	}

	public void setFun_method_chs(String fun_method_chs) {
		this.fun_method_chs = fun_method_chs;
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
	* 设置 公式编码
	* @param value 
	*/
	public void setFormula_code(String value) {
		this.formula_code = value;
	}
	
	/**
	* 获取 公式编码
	* @return String
	*/
	public String getFormula_code() {
		return this.formula_code;
	}
	/**
	* 设置 指标代码
	* @param value 
	*/
	public void setTarget_code(String value) {
		this.target_code = value;
	}
	
	/**
	* 获取 指标代码
	* @return String
	*/
	public String getTarget_code() {
		return this.target_code;
	}
	/**
	* 设置 出栈序号
	* @param value 
	*/
	public void setStack_seq_no(Integer value) {
		this.stack_seq_no = value;
	}
	
	/**
	* 获取 出栈序号
	* @return Integer
	*/
	public Integer getStack_seq_no() {
		return this.stack_seq_no;
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