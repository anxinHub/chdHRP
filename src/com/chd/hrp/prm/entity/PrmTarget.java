
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
 * 9901 绩效指标字典表
 * @Table:
 * PRM_TARGET
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class PrmTarget implements Serializable {

	
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
	 * 指标编码
	 */
	private String target_code;
	
	/**
	 * 指标性质编码
	 */
	private String nature_code;
	
	private String nature_name;
	
	/**
	 * 指标名称
	 */
	private String target_name;
	
	public String getNature_name() {
		return nature_name;
	}

	public void setNature_name(String nature_name) {
		this.nature_name = nature_name;
	}
	/**
	 * 01:全院 02:科室分类 03:科室 04:职工
	 */
	private String target_nature;
	
	/**
	 * 01:全院 02:科室分类 03:科室 04:职工
	 */
	private String target_nature_code;
	
	public String getTarget_nature_code() {
		return target_nature_code;
	}

	public void setTarget_nature_code(String target_nature_code) {
		this.target_nature_code = target_nature_code;
	}
	/**
	 * 指标描述
	 */
	private String target_note;
	
	/**
	 * 拼音码
	 */
	private String spell_code;
	
	/**
	 * 五笔码
	 */
	private String wbx_code;
	
	/**
	 * 0:不停用 1:停用
	 */
	private Integer is_stop;
	

	
	private String method_code;
  public String getMethod_code() {
		return method_code;
	}

	public void setMethod_code(String method_code) {
		this.method_code = method_code;
	}
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
	* 设置 指标性质编码
	* @param value 
	*/
	public void setNature_code(String value) {
		this.nature_code = value;
	}
	
	/**
	* 获取 指标性质编码
	* @return String
	*/
	public String getNature_code() {
		return this.nature_code;
	}
	/**
	* 设置 指标名称
	* @param value 
	*/
	public void setTarget_name(String value) {
		this.target_name = value;
	}
	
	/**
	* 获取 指标名称
	* @return String
	*/
	public String getTarget_name() {
		return this.target_name;
	}
	/**
	* 设置 01:全院 02:科室分类 03:科室 04:职工
	* @param value 
	*/
	public void setTarget_nature(String value) {
		this.target_nature = value;
	}
	
	/**
	* 获取 01:全院 02:科室分类 03:科室 04:职工
	* @return String
	*/
	public String getTarget_nature() {
		return this.target_nature;
	}
	/**
	* 设置 指标描述
	* @param value 
	*/
	public void setTarget_note(String value) {
		this.target_note = value;
	}
	
	/**
	* 获取 指标描述
	* @return String
	*/
	public String getTarget_note() {
		return this.target_note;
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
	* 设置 0:不停用 1:停用
	* @param value 
	*/
	public void setIs_stop(Integer value) {
		this.is_stop = value;
	}
	
	/**
	* 获取 0:不停用 1:停用
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