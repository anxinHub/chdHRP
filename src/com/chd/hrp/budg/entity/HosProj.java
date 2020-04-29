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
 * HOS_PROJ
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class HosProj implements Serializable {

	
	private static long serialVersionUID = 5454155825314635342L;

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
	 * 类型编码
	 */
	private String type_code;
	
	/**
	 * 项目名称
	 */
	private String proj_name;
	
	/**
	 * 项目简称
	 */
	private String proj_simple;
	
	/**
	 * 排序号
	 */
	private Long sort_code;
	
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
	 * 备注
	 */
	private String note;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;


public static long getSerialversionuid() {
	return serialVersionUID;
}


public static void setSerialversionuid(long serialversionuid) {
	serialVersionUID = serialversionuid;
}


public Double getGroup_id() {
	return group_id;
}


public void setGroup_id(Double group_id) {
	this.group_id = group_id;
}


public Double getHos_id() {
	return hos_id;
}


public void setHos_id(Double hos_id) {
	this.hos_id = hos_id;
}


public Double getProj_id() {
	return proj_id;
}


public void setProj_id(Double proj_id) {
	this.proj_id = proj_id;
}


public String getProj_code() {
	return proj_code;
}


public void setProj_code(String proj_code) {
	this.proj_code = proj_code;
}


public String getType_code() {
	return type_code;
}


public void setType_code(String type_code) {
	this.type_code = type_code;
}


public String getProj_name() {
	return proj_name;
}


public void setProj_name(String proj_name) {
	this.proj_name = proj_name;
}


public String getProj_simple() {
	return proj_simple;
}


public void setProj_simple(String proj_simple) {
	this.proj_simple = proj_simple;
}


public Long getSort_code() {
	return sort_code;
}


public void setSort_code(Long sort_code) {
	this.sort_code = sort_code;
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


public Integer getIs_stop() {
	return is_stop;
}


public void setIs_stop(Integer is_stop) {
	this.is_stop = is_stop;
}


public String getNote() {
	return note;
}


public void setNote(String note) {
	this.note = note;
}


public String getError_type() {
	return error_type;
}


public void setError_type(String error_type) {
	this.error_type = error_type;
}
	
}