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
 * 是否结转，默认否
是否末级，默认是
 * @Table:
 * BUDG_INCOME_SUBJ
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgIncomeSubj implements Serializable {

	
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
	 * 预算年度
	 */
	private String budg_year;
	
	/**
	 * 科目编码
	 */
	private String subj_code;
	
	/**
	 * 科目名称
	 */
	private String subj_name;
	
	/**
	 * 科目全称
	 */
	private String subj_name_all;
	
	/**
	 * 上级编码
	 */
	private String super_code;
	
	/**
	 * 上级科目全称
	 */
	private String super_name;
	
	/**
	 * 科目级次
	 */
	private Integer subj_level;
	
	/**
	 * 科目性质
	 */
	private String subj_nature;
	
	private String subj_nature_name;
	/**
	 * 收入预算科目类别
	 */
	private String type_code;
	private String type_name;
	/**
	 * 是否末级
	 */
	private Integer is_last;
	
	/**
	 * 是否结转
	 */
	private Integer is_caarried;
	
	/**
	 * 拼音码
	 */
	private String spell_code;
	
	/**
	 * 五笔码
	 */
	private String wbx_code;
	

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
	* 设置 预算年度
	* @param value 
	*/
	public void setBudg_year(String value) {
		this.budg_year = value;
	}
	
	/**
	* 获取 预算年度
	* @return String
	*/
	public String getBudg_year() {
		return this.budg_year;
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
	* 设置 科目名称
	* @param value 
	*/
	public void setSubj_name(String value) {
		this.subj_name = value;
	}
	
	/**
	* 获取 科目名称
	* @return String
	*/
	public String getSubj_name() {
		return this.subj_name;
	}
	/**
	* 设置 科目全称
	* @param value 
	*/
	public void setSubj_name_all(String value) {
		this.subj_name_all = value;
	}
	
	/**
	* 获取 科目全称
	* @return String
	*/
	public String getSubj_name_all() {
		return this.subj_name_all;
	}
	/**
	* 设置 上级编码
	* @param value 
	*/
	public void setSuper_code(String value) {
		this.super_code = value;
	}
	
	/**
	* 获取 上级编码
	* @return String
	*/
	public String getSuper_code() {
		return this.super_code;
	}
	/**
	* 设置 科目级次
	* @param value 
	*/
	public void setSubj_level(Integer value) {
		this.subj_level = value;
	}
	
	/**
	* 获取 科目级次
	* @return Integer
	*/
	public Integer getSubj_level() {
		return this.subj_level;
	}
	/**
	* 设置 科目性质
	* @param value 
	*/
	public void setSubj_nature(String value) {
		this.subj_nature = value;
	}
	
	/**
	* 获取 科目性质
	* @return String
	*/
	public String getSubj_nature() {
		return this.subj_nature;
	}
	/**
	* 设置 是否末级
	* @param value 
	*/
	public void setIs_last(Integer value) {
		this.is_last = value;
	}
	
	/**
	* 获取 是否末级
	* @return Integer
	*/
	public Integer getIs_last() {
		return this.is_last;
	}
	/**
	* 设置 是否结转
	* @param value 
	*/
	public void setIs_caarried(Integer value) {
		this.is_caarried = value;
	}
	
	/**
	* 获取 是否结转
	* @return Integer
	*/
	public Integer getIs_caarried() {
		return this.is_caarried;
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
	
	public String getSubj_nature_name() {
		return subj_nature_name;
	}

	public void setSubj_nature_name(String subj_nature_name) {
		this.subj_nature_name = subj_nature_name;
	}
	
	public String getSuper_name() {
		return super_name;
	}

	public void setSuper_name(String super_name) {
		this.super_name = super_name;
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

	public String getType_code() {
		return type_code;
	}

	public void setType_code(String type_code) {
		this.type_code = type_code;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	
}