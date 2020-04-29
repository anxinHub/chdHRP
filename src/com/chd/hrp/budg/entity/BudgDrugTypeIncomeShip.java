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
 * 末级药品分类对应预算收入科目。对应关系中要求药品分类对收入科目，为一对一或一对多。
 * @Table:
 * BUDG_DRUG_TYPE_INCOME_SHIP2
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgDrugTypeIncomeShip implements Serializable {

	
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
	
	private String budg_year;
	
	/**
	 * 药品分类ID
	 */
	private Long drug_type_id;
	
	/**
	 * 药品分类变更号
	 */
	private Long drug_type_no;
	
	/**
	 * 药品分类编码
	 */
	private String drug_type_code;
	
	/**
	 * 科目编码
	 */
	private String subj_code;
	

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
	
	public String getBudg_year() {
		return budg_year;
	}

	public void setBudg_year(String budg_year) {
		this.budg_year = budg_year;
	}

	/**
	* 设置 药品分类编码
	* @param value 
	*/
	public void setDrug_type_code(String value) {
		this.drug_type_code = value;
	}
	
	/**
	* 获取 药品分类编码
	* @return String
	*/
	public String getDrug_type_code() {
		return this.drug_type_code;
	}
	/**
	* 设置 科目编码
	* @param value 
	*/
	public void setSubj_code(String value) {
		this.subj_code = value;
	}
	
	/**
	* 获取 科目编码
	* @return String
	*/
	public String getSubj_code() {
		return this.subj_code;
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

	public Long getDrug_type_id() {
		return drug_type_id;
	}

	public void setDrug_type_id(Long drug_type_id) {
		this.drug_type_id = drug_type_id;
	}

	public Long getDrug_type_no() {
		return drug_type_no;
	}

	public void setDrug_type_no(Long drug_type_no) {
		this.drug_type_no = drug_type_no;
	}
	
	
}