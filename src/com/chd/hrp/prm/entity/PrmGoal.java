
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
 * 0101 目标管理表
 * @Table:
 * PRM_GOAL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class PrmGoal implements Serializable {

	
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
	 * 年度
	 */
	private String acc_year;
	
	/**
	 * 开始月份
	 */
	private String start_month;
	
	/**
	 * 目标编码
	 */
	private String goal_code;
	
	/**
	 * 目标名称
	 */
	private String goal_name;
	
	/**
	 * 拼音码
	 */
	private String spell_code;
	
	/**
	 * 五笔码
	 */
	private String wbx_code;
	
	/**
	 * 目标描述
	 */
	private String goal_note;
	
	/**
	 * 审核状态
	 */
	private Integer is_audit;
	
	/**
	 * 审核人
	 */
	private String user_code;
	
	/**
	 * yyyy-mm-dd
	 */
	private String audit_date;
	
	/**
	 * 单位信息
	 */
	private String hos_name;
	

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
	* 设置 年度
	* @param value 
	*/
	public void setAcc_year(String value) {
		this.acc_year = value;
	}
	
	/**
	* 获取 年度
	* @return String
	*/
	public String getAcc_year() {
		return this.acc_year;
	}
	/**
	* 设置 开始月份
	* @param value 
	*/
	public void setStart_month(String value) {
		this.start_month = value;
	}
	
	/**
	* 获取 开始月份
	* @return String
	*/
	public String getStart_month() {
		return this.start_month;
	}
	/**
	* 设置 目标编码
	* @param value 
	*/
	public void setGoal_code(String value) {
		this.goal_code = value;
	}
	
	/**
	* 获取 目标编码
	* @return String
	*/
	public String getGoal_code() {
		return this.goal_code;
	}
	/**
	* 设置 目标名称
	* @param value 
	*/
	public void setGoal_name(String value) {
		this.goal_name = value;
	}
	
	/**
	* 获取 目标名称
	* @return String
	*/
	public String getGoal_name() {
		return this.goal_name;
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
	* 设置 目标描述
	* @param value 
	*/
	public void setGoal_note(String value) {
		this.goal_note = value;
	}
	
	/**
	* 获取 目标描述
	* @return String
	*/
	public String getGoal_note() {
		return this.goal_note;
	}
	/**
	* 设置 审核状态
	* @param value 
	*/
	public void setIs_audit(Integer value) {
		this.is_audit = value;
	}
	
	/**
	* 获取 审核状态
	* @return Integer
	*/
	public Integer getIs_audit() {
		return this.is_audit;
	}
	/**
	* 设置 审核人
	* @param value 
	*/
	public void setUser_code(String value) {
		this.user_code = value;
	}
	
	/**
	* 获取 审核人
	* @return String
	*/
	public String getUser_code() {
		return this.user_code;
	}
	/**
	* 设置 yyyy-mm-dd
	* @param value 
	*/
	public void setAudit_date(String value) {
		this.audit_date = value;
	}
	
	/**
	* 获取 yyyy-mm-dd
	* @return String
	*/
	public String getAudit_date() {
		return this.audit_date;
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

	public String getHos_name() {
		return hos_name;
	}

	public void setHos_name(String hos_name) {
		this.hos_name = hos_name;
	}
	
}