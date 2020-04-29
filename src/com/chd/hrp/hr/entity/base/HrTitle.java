/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.entity.base;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 职称信息
 * @Table:
 * HR_TITLE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class HrTitle implements Serializable {

	
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
	 * 职称编码
	 */
	private String title_code;
	
	/**
	 * 职称名称
	 */
	private String title_name;
	
	/**
	 * 是否停用
	 */
	private Integer is_stop;
	/**
	 * 是否停用
	 */
	private String is_stop_name;
	
	/**
	 * 上级编码
	 */
	private String sup_code;
	
	private String sup_name;
	
	/**
	 * 是否末级
	 */
	private Integer is_last;
	private String is_last_name;
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
	
	public String getTitle_code() {
		return title_code;
	}

	public void setTitle_code(String title_code) {
		this.title_code = title_code;
	}

	public String getTitle_name() {
		return title_name;
	}

	public void setTitle_name(String title_name) {
		this.title_name = title_name;
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
	
	public String getIs_stop_name() {
		return is_stop_name;
	}

	public void setIs_stop_name(String is_stop_name) {
		this.is_stop_name = is_stop_name;
	}

	/**
	* 设置 上级编码
	* @param value 
	*/
	public void setSup_code(String value) {
		this.sup_code = value;
	}
	
	/**
	* 获取 上级编码
	* @return String
	*/
	public String getSup_code() {
		return this.sup_code;
	}
	
	
	public String getSup_name() {
		return sup_name;
	}

	public void setSup_name(String sup_name) {
		this.sup_name = sup_name;
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
	
	public String getIs_last_name() {
		return is_last_name;
	}

	public void setIs_last_name(String is_last_name) {
		this.is_last_name = is_last_name;
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