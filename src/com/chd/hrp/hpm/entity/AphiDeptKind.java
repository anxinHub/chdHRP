
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
 * 8803 科室分类字典表
 * @Table:
 * APHI_DEPT_KIND
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AphiDeptKind implements Serializable {

	
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
	 * 科室分类编码
	 */
	private String dept_kind_code;
	
	/**
	 * 科室分类名称
	 */
	private String dept_kind_name;
	
	/**
	 * 科室分类说明
	 */
	private String dept_kind_note;
	
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
	* 设置 科室分类编码
	* @param value 
	*/
	public void setDept_kind_code(String value) {
		this.dept_kind_code = value;
	}
	
	/**
	* 获取 科室分类编码
	* @return String
	*/
	public String getDept_kind_code() {
		return this.dept_kind_code;
	}
	/**
	* 设置 科室分类名称
	* @param value 
	*/
	public void setDept_kind_name(String value) {
		this.dept_kind_name = value;
	}
	
	/**
	* 获取 科室分类名称
	* @return String
	*/
	public String getDept_kind_name() {
		return this.dept_kind_name;
	}
	/**
	* 设置 科室分类说明
	* @param value 
	*/
	public void setDept_kind_note(String value) {
		this.dept_kind_note = value;
	}
	
	/**
	* 获取 科室分类说明
	* @return String
	*/
	public String getDept_kind_note() {
		return this.dept_kind_note;
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