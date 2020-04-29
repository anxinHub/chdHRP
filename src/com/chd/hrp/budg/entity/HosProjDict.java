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
 * tabledesc
 * @Table:
 * HOS_PROJ_DICT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class HosProjDict implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 变更ID
	 */
	private Double proj_no;
	
	/**
	 * 集团ID
	 */
	private Double group_id;
	
	/**
	 * 医院ID
	 */
	private Double hos_id;
	
	/**
	 * 项目ID
	 */
	private Double proj_id;
	
	/**
	 * 项目编码
	 */
	private String proj_code;
	
	/**
	 * 项目名称
	 */
	private String proj_name;
	
	/**
	 * 类型编码
	 */
	private String type_code;
	
	/**
	 * 项目简称
	 */
	private String proj_simple;
	
	/**
	 * 排序号
	 */
	private Long sort_code;
	
	/**
	 * 备注
	 */
	private String note;
	
	/**
	 * 拼音码
	 */
	private String spell_code;
	
	/**
	 * 五笔码
	 */
	private String wbx_code;
	
	/**
	 * 变更人
	 */
	private String user_code;
	
	/**
	 * 变更时间
	 */
	private Date create_date;
	
	/**
	 * 变更原因
	 */
	private String dlog;
	
	/**
	 * 是否停用
	 */
	private Integer is_stop;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 变更ID
	* @param value 
	*/
	public void setProj_no(Double value) {
		this.proj_no = value;
	}
	
	/**
	* 获取 变更ID
	* @return Double
	*/
	public Double getProj_no() {
		return this.proj_no;
	}
	/**
	* 设置 集团ID
	* @param value 
	*/
	public void setGroup_id(Double value) {
		this.group_id = value;
	}
	
	/**
	* 获取 集团ID
	* @return Double
	*/
	public Double getGroup_id() {
		return this.group_id;
	}
	/**
	* 设置 医院ID
	* @param value 
	*/
	public void setHos_id(Double value) {
		this.hos_id = value;
	}
	
	/**
	* 获取 医院ID
	* @return Double
	*/
	public Double getHos_id() {
		return this.hos_id;
	}
	/**
	* 设置 项目ID
	* @param value 
	*/
	public void setProj_id(Double value) {
		this.proj_id = value;
	}
	
	/**
	* 获取 项目ID
	* @return Double
	*/
	public Double getProj_id() {
		return this.proj_id;
	}
	/**
	* 设置 项目编码
	* @param value 
	*/
	public void setProj_code(String value) {
		this.proj_code = value;
	}
	
	/**
	* 获取 项目编码
	* @return String
	*/
	public String getProj_code() {
		return this.proj_code;
	}
	/**
	* 设置 项目名称
	* @param value 
	*/
	public void setProj_name(String value) {
		this.proj_name = value;
	}
	
	/**
	* 获取 项目名称
	* @return String
	*/
	public String getProj_name() {
		return this.proj_name;
	}
	/**
	* 设置 类型编码
	* @param value 
	*/
	public void setType_code(String value) {
		this.type_code = value;
	}
	
	/**
	* 获取 类型编码
	* @return String
	*/
	public String getType_code() {
		return this.type_code;
	}
	/**
	* 设置 项目简称
	* @param value 
	*/
	public void setProj_simple(String value) {
		this.proj_simple = value;
	}
	
	/**
	* 获取 项目简称
	* @return String
	*/
	public String getProj_simple() {
		return this.proj_simple;
	}
	/**
	* 设置 排序号
	* @param value 
	*/
	public void setSort_code(Long value) {
		this.sort_code = value;
	}
	
	/**
	* 获取 排序号
	* @return Long
	*/
	public Long getSort_code() {
		return this.sort_code;
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
	* 设置 变更人
	* @param value 
	*/
	public void setUser_code(String value) {
		this.user_code = value;
	}
	
	/**
	* 获取 变更人
	* @return String
	*/
	public String getUser_code() {
		return this.user_code;
	}
	/**
	* 设置 变更时间
	* @param value 
	*/
	public void setCreate_date(Date value) {
		this.create_date = value;
	}
	
	/**
	* 获取 变更时间
	* @return Date
	*/
	public Date getCreate_date() {
		return this.create_date;
	}
	/**
	* 设置 变更原因
	* @param value 
	*/
	public void setDlog(String value) {
		this.dlog = value;
	}
	
	/**
	* 获取 变更原因
	* @return String
	*/
	public String getDlog() {
		return this.dlog;
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