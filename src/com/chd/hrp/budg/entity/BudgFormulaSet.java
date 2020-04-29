/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.entity;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 计算公式
 * @Table:
 * BUDG_FORMULA_SET
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgFormulaSet implements Serializable {

	
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
	 * 计算公式ID
	 */
	private String formula_id;
	
	/**
	 * 计算公式ID
	 */
	private String formula_name;
	
	/**
	 * 公式中文
	 */
	private String formula_ca;
	
	/**
	 * 公式英文
	 */
	private String formula_en;
	

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
	* 设置 计算公式ID
	* @param value 
	*/
	public void setFormula_id(String value) {
		this.formula_id = value;
	}
	
	/**
	* 获取 计算公式ID
	* @return String
	*/
	public String getFormula_id() {
		return this.formula_id;
	}
	/**
	* 设置 公式中文
	* @param value 
	*/
	public void setFormula_ca(String value) {
		this.formula_ca = value;
	}
	
	/**
	* 获取 公式中文
	* @return String
	*/
	public String getFormula_ca() {
		return this.formula_ca;
	}
	/**
	* 设置 公式英文
	* @param value 
	*/
	public void setFormula_en(String value) {
		this.formula_en = value;
	}
	
	/**
	* 获取 公式英文
	* @return String
	*/
	public String getFormula_en() {
		return this.formula_en;
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

	public String getFormula_name() {
		return formula_name;
	}

	public void setFormula_name(String formula_name) {
		this.formula_name = formula_name;
	}
	
	
}