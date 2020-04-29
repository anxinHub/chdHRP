
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.ass.entity.dict;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 050101 资产分类字典
 * @Table:
 * ASS_TYPE_DICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AssFinaDict implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 集体ID
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
	 * 分类编码
	 */
	private String fina_type_code;
	
	/**
	 * 分类名称
	 */
	private String fina_type_name;
	
	/**
	 * 上级分类
	 */
	private String super_code;
	
	/**
	 * 拼音码
	 */
	private String spell_code;
	
	/**
	 * 五笔码
	 */
	private String wbx_code;
	
	/**
	 * 是否末级分类
	 */
	private Integer is_last;
	
	/**
	 * 分类级别
	 */
	private Integer type_level;
	
	
	/**
	 * 是否停用
	 */
	private Integer is_stop;
	/**
	 * 是否预算
	 */
	private Integer is_budg;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 集体ID
	* @param value 
	*/
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	
	/**
	* 获取 集体ID
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
	* 设置 上级分类
	* @param value 
	*/
	public void setSuper_code(String value) {
		this.super_code = value;
	}
	
	/**
	* 获取 上级分类
	* @return String
	*/
	public String getSuper_code() {
		return this.super_code;
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
	* 获取 五笔码
	* @return String
	*/
    public String getWbx_code() {
    	return wbx_code;
    }

    /**
	* 设置 五笔码
	* @param value 
	*/
    public void setWbx_code(String wbx_code) {
    	this.wbx_code = wbx_code;
    }
	/**
	* 设置 是否末级分类
	* @param value 
	*/
	public void setIs_last(Integer value) {
		this.is_last = value;
	}
	
	/**
	* 获取 是否末级分类
	* @return Integer
	*/
	public Integer getIs_last() {
		return this.is_last;
	}
	/**
	* 设置 分类级别
	* @param value 
	*/
	public void setType_level(Integer value) {
		this.type_level = value;
	}
	
	/**
	* 获取 分类级别
	* @return Integer
	*/
	public Integer getType_level() {
		return this.type_level;
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

	
	/**
	 * 获取 分类编码
	 * @return fina_type_code
	 */
	public String getFina_type_code() {
		return fina_type_code;
	}

	
	/**
	 * 设置 分类编码
	 * @param fina_type_code 
	 */
	public void setFina_type_code(String fina_type_code) {
		this.fina_type_code = fina_type_code;
	}

	
	/**
	 * 获取分类名称
	 * @return fina_type_name
	 */
	public String getFina_type_name() {
		return fina_type_name;
	}

	
	/**
	 * 设置 分类名称
	 * @param fina_type_name 
	 */
	public void setFina_type_name(String fina_type_name) {
		this.fina_type_name = fina_type_name;
	}

	
	/**
	 * 获取 是否预算
	 * @return is_budg
	 */
	public Integer getIs_budg() {
		return is_budg;
	}

	
	/**
	 * 设置 是否预算
	 * @param is_budg 
	 */
	public void setIs_budg(Integer is_budg) {
		this.is_budg = is_budg;
	}
	
}