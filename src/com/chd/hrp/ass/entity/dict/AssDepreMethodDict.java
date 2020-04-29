
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
 * 050106 资产折旧方法字典
 * @Table:
 * ASS_DEPRE_METHOD_DICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AssDepreMethodDict implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 折旧方法编码
	 */
	private String ass_depre_code;
	
	/**
	 * 折旧方法名称
	 */
	private String ass_depre_name;
	
	/**
	 * 折旧方法描述
	 */
	private String ass_depre_define;
	
	/**
	 * 拼音码
	 */
	private String spell_code;
	
	/**
	 * 五笔码
	 */
	private String wbx_code;
	
	/**
	 * 是否停用
	 */
	private Integer is_stop;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 折旧方法编码
	* @param value 
	*/
	public void setAss_depre_code(String value) {
		this.ass_depre_code = value;
	}
	
	/**
	* 获取 折旧方法编码
	* @return String
	*/
	public String getAss_depre_code() {
		return this.ass_depre_code;
	}
	/**
	* 设置 折旧方法名称
	* @param value 
	*/
	public void setAss_depre_name(String value) {
		this.ass_depre_name = value;
	}
	
	/**
	* 获取 折旧方法名称
	* @return String
	*/
	public String getAss_depre_name() {
		return this.ass_depre_name;
	}
	/**
	* 设置 折旧方法描述
	* @param value 
	*/
	public void setAss_depre_define(String value) {
		this.ass_depre_define = value;
	}
	
	/**
	* 获取 折旧方法描述
	* @return String
	*/
	public String getAss_depre_define() {
		return this.ass_depre_define;
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