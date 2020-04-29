package com.chd.hrp.mat.entity;

import java.io.Serializable;
/**
 * 
 * @Description:
 * 医疗器械分类字典
 * @Table:
 * MAT_INSTRU_TYPE
 * @Author: weixiaofeng
 * @Version: 1.0
 */

public class MatInstruType implements Serializable {

	
	private static long serialVersionUID = 5454155825314635342L;

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
	 * 医疗器械分类ID
	 */
	private Long instru_type_id;
	
	/**
	 * 医疗器械分类编码
	 */
	private String instru_type_code;
	
	/**
	 * 医疗器械分类名称
	 */
	private String instru_type_name;
	
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
	 * 导入验证信息
	 */
	private String error_type;

	public Long getGroup_id() {
		return group_id;
	}

	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}

	public Long getHos_id() {
		return hos_id;
	}

	public void setHos_id(Long hos_id) {
		this.hos_id = hos_id;
	}

	public String getCopy_code() {
		return copy_code;
	}

	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}

	public Long getInstru_type_id() {
		return instru_type_id;
	}

	public void setInstru_type_id(Long instru_type_id) {
		this.instru_type_id = instru_type_id;
	}

	public String getInstru_type_code() {
		return instru_type_code;
	}

	public void setInstru_type_code(String instru_type_code) {
		this.instru_type_code = instru_type_code;
	}

	public String getInstru_type_name() {
		return instru_type_name;
	}

	public void setInstru_type_name(String instru_type_name) {
		this.instru_type_name = instru_type_name;
	}

	public String getSuper_code() {
		return super_code;
	}

	public void setSuper_code(String super_code) {
		this.super_code = super_code;
	}

	public String getSuper_name() {
		return super_name;
	}

	public void setSuper_name(String super_name) {
		this.super_name = super_name;
	}

	public Integer getType_level() {
		return type_level;
	}

	public void setType_level(Integer type_level) {
		this.type_level = type_level;
	}

	public Integer getIs_last() {
		return is_last;
	}

	public void setIs_last(Integer is_last) {
		this.is_last = is_last;
	}

	public Integer getIs_stop() {
		return is_stop;
	}

	public void setIs_stop(Integer is_stop) {
		this.is_stop = is_stop;
	}

	public String getSpell_code() {
		return spell_code;
	}

	public void setSpell_code(String spell_code) {
		this.spell_code = spell_code;
	}

	public String getWbx_code() {
		return wbx_code;
	}

	public void setWbx_code(String wbx_code) {
		this.wbx_code = wbx_code;
	}

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	
}