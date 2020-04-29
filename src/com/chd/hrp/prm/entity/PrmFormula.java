
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
 * 9906 绩效计算公式表
 * @Table:
 * PRM_FORMULA
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class PrmFormula implements Serializable {

	
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
	 * 公式名称
	 */
	private String formula_name;
	
	/**
	 * 计算公式(中文）
	 */
	private String formula_method_chs;
	
	/**
	 * 计算公式(英文)
	 */
	private String formula_method_eng;
	
	/**
	 * 是否停用
	 */
	private Integer is_stop;
	

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
	* 设置 公式名称
	* @param value 
	*/
	public void setFormula_name(String value) {
		this.formula_name = value;
	}
	
	/**
	* 获取 公式名称
	* @return String
	*/
	public String getFormula_name() {
		return this.formula_name;
	}
	/**
	* 设置 计算公式(中文）
	* @param value 
	*/
	public void setFormula_method_chs(String value) {
		this.formula_method_chs = value;
	}
	
	/**
	* 获取 计算公式(中文）
	* @return String
	*/
	public String getFormula_method_chs() {
		return this.formula_method_chs;
	}
	/**
	* 设置 计算公式(英文)
	* @param value 
	*/
	public void setFormula_method_eng(String value) {
		this.formula_method_eng = value;
	}
	
	/**
	* 获取 计算公式(英文)
	* @return String
	*/
	public String getFormula_method_eng() {
		return this.formula_method_eng;
	}
	/**
	* 设置 是否停用
	* @param value 
	*/
	public void setIs_stop(Integer value) {
		this.is_stop = value;
	}
	
	/**
	* 获取 是否停用
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