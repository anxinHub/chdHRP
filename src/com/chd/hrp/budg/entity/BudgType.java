/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.entity;

import java.io.Serializable;
/**
 * 
 * @Description:
 * 04103 物资分类字典
 * @Table:
 * MAT_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class BudgType implements Serializable {

	
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
	 * 物资类别ID
	 */
	private Long mat_type_id;
	
	/**
	 * 物资类别编码
	 */
	private String mat_type_code;
	
	/**
	 * 物资类别名称
	 */
	private String mat_type_name;
	
	/**
	 * 上级编码
	 */
	private String super_code;
	
	/**
	 * 上级名称
	 */
	private String super_name;
	
	/**
	 * 级次
	 */
	private Integer type_level;
	
	/**
	 * 是否末级
	 */
	private Integer is_last;
	
	/**
	 * 是否停用
	 */
	private Integer is_stop;
	
	/**
	 * 拼音码
	 */
	private String spell_code;
	
	/**
	 * 五笔码
	 */
	private String wbx_code;
	
	/**
	 * 备注
	 */
	private String note;
	
	/**
	 * 是否作预算
	 */
	private Integer is_budg;
	
	/**
	 * 自动有效期
	 */
	private Integer is_auto_exp;
	

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
	* 设置 物资类别ID
	* @param value 
	*/
	public void setMat_type_id(Long value) {
		this.mat_type_id = value;
	}
	
	/**
	* 获取 物资类别ID
	* @return Long
	*/
	public Long getMat_type_id() {
		return this.mat_type_id;
	}
	/**
	* 设置 物资类别编码
	* @param value 
	*/
	public void setMat_type_code(String value) {
		this.mat_type_code = value;
	}
	
	/**
	* 获取 物资类别编码
	* @return String
	*/
	public String getMat_type_code() {
		return this.mat_type_code;
	}
	/**
	* 设置 物资类别名称
	* @param value 
	*/
	public void setMat_type_name(String value) {
		this.mat_type_name = value;
	}
	
	/**
	* 获取 物资类别名称
	* @return String
	*/
	public String getMat_type_name() {
		return this.mat_type_name;
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
	* 设置 上级名称
	* @param value 
	*/
	public void setSuper_name(String value) {
		this.super_name = value;
	}
	
	/**
	* 获取 上级名称
	* @return String
	*/
	public String getSuper_name() {
		return this.super_name;
	}
	/**
	* 设置 级次
	* @param value 
	*/
	public void setType_level(Integer value) {
		this.type_level = value;
	}
	
	/**
	* 获取 级次
	* @return Integer
	*/
	public Integer getType_level() {
		return this.type_level;
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
	/**
	* 设置 备注
	* @param value 
	*/
	public void setNote(String value) {
		this.note = value;
	}
	
	/**
	* 获取 备注
	* @return String
	*/
	public String getNote() {
		return this.note;
	}
	/**
	* 设置 是否作预算
	* @param value 
	*/
	public void setIs_budg(Integer value) {
		this.is_budg = value;
	}
	
	/**
	* 获取 是否作预算
	* @return Integer
	*/
	public Integer getIs_budg() {
		return this.is_budg;
	}
	
	/**
	* 设置 自动有效期
	* @param value 
	*/
	public void setIs_auto_exp(Integer value) {
		this.is_auto_exp = value;
	}
	
	/**
	* 获取 自动有效期
	* @return Integer
	*/
	public Integer getIs_auto_exp() {
		return this.is_auto_exp;
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