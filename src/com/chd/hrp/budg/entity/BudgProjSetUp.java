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
 * 状态（STATE），取自系统字典表
“01新建、02已提交、03已审核”
 * @Table:
 * BUDG_PROJ_SET_UP
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgProjSetUp implements Serializable {

	
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
	 * 立项申请单号
	 */
	private String apply_code;
	
	/**
	 * 项目编码
	 */
	private String proj_code;
	
	/**
	 * 项目名称
	 */
	private String proj_name;
	
	/**
	 * 项目类型
	 */
	private String type_code;
	
	/**
	 * 项目级别
	 */
	private String level_code;
	
	/**
	 * 项目用途
	 */
	private String use_code;
	
	/**
	 * 项目负责人
	 */
	private Long con_emp_id;
	
	/**
	 * 项目负责人电话
	 */
	private String con_phone;
	
	/**
	 * 财务负责人
	 */
	private Long acc_emp_id;
	
	/**
	 * 财务负责人电话
	 */
	private String acc_phone;
	
	/**
	 * 填报部门变更ID
	 */
	private Long dept_no;
	
	/**
	 * 填报部门ID
	 */
	private Long dept_id;
	
	/**
	 * 填报人
	 */
	private Long app_emp_id;
	
	/**
	 * 填报日期
	 */
	private Date app_date;
	
	/**
	 * 联系电话
	 */
	private String app_phone;
	
	/**
	 * EMAIL
	 */
	private String email;
	
	/**
	 * 备注
	 */
	private String note;
	
	/**
	 * 审核人
	 */
	private Long checker;
	
	/**
	 * 审核日期
	 */
	private Date check_date;
	
	/**
	 * 拼音码
	 */
	private String spell_code;
	
	/**
	 * 五笔码
	 */
	private String wbx_code;
	
	/**
	 * 状态
	 */
	private String state;
	

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
	* 设置 立项申请单号
	* @param value 
	*/
	public void setApply_code(String value) {
		this.apply_code = value;
	}
	
	/**
	* 获取 立项申请单号
	* @return String
	*/
	public String getApply_code() {
		return this.apply_code;
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
	* 设置 项目类型
	* @param value 
	*/
	public void setType_code(String value) {
		this.type_code = value;
	}
	
	/**
	* 获取 项目类型
	* @return String
	*/
	public String getType_code() {
		return this.type_code;
	}
	/**
	* 设置 项目级别
	* @param value 
	*/
	public void setLevel_code(String value) {
		this.level_code = value;
	}
	
	/**
	* 获取 项目级别
	* @return String
	*/
	public String getLevel_code() {
		return this.level_code;
	}
	/**
	* 设置 项目用途
	* @param value 
	*/
	public void setUse_code(String value) {
		this.use_code = value;
	}
	
	/**
	* 获取 项目用途
	* @return String
	*/
	public String getUse_code() {
		return this.use_code;
	}
	/**
	* 设置 项目负责人
	* @param value 
	*/
	public void setCon_emp_id(Long value) {
		this.con_emp_id = value;
	}
	
	/**
	* 获取 项目负责人
	* @return Long
	*/
	public Long getCon_emp_id() {
		return this.con_emp_id;
	}
	/**
	* 设置 项目负责人电话
	* @param value 
	*/
	public void setCon_phone(String value) {
		this.con_phone = value;
	}
	
	/**
	* 获取 项目负责人电话
	* @return String
	*/
	public String getCon_phone() {
		return this.con_phone;
	}
	/**
	* 设置 财务负责人
	* @param value 
	*/
	public void setAcc_emp_id(Long value) {
		this.acc_emp_id = value;
	}
	
	/**
	* 获取 财务负责人
	* @return Long
	*/
	public Long getAcc_emp_id() {
		return this.acc_emp_id;
	}
	/**
	* 设置 财务负责人电话
	* @param value 
	*/
	public void setAcc_phone(String value) {
		this.acc_phone = value;
	}
	
	/**
	* 获取 财务负责人电话
	* @return String
	*/
	public String getAcc_phone() {
		return this.acc_phone;
	}
	/**
	* 设置 填报部门变更ID
	* @param value 
	*/
	public void setDept_no(Long value) {
		this.dept_no = value;
	}
	
	/**
	* 获取 填报部门变更ID
	* @return Long
	*/
	public Long getDept_no() {
		return this.dept_no;
	}
	/**
	* 设置 填报部门ID
	* @param value 
	*/
	public void setDept_id(Long value) {
		this.dept_id = value;
	}
	
	/**
	* 获取 填报部门ID
	* @return Long
	*/
	public Long getDept_id() {
		return this.dept_id;
	}
	/**
	* 设置 填报人
	* @param value 
	*/
	public void setApp_emp_id(Long value) {
		this.app_emp_id = value;
	}
	
	/**
	* 获取 填报人
	* @return Long
	*/
	public Long getApp_emp_id() {
		return this.app_emp_id;
	}
	/**
	* 设置 填报日期
	* @param value 
	*/
	public void setApp_date(Date value) {
		this.app_date = value;
	}
	
	/**
	* 获取 填报日期
	* @return Date
	*/
	public Date getApp_date() {
		return this.app_date;
	}
	/**
	* 设置 联系电话
	* @param value 
	*/
	public void setApp_phone(String value) {
		this.app_phone = value;
	}
	
	/**
	* 获取 联系电话
	* @return String
	*/
	public String getApp_phone() {
		return this.app_phone;
	}
	/**
	* 设置 EMAIL
	* @param value 
	*/
	public void setEmail(String value) {
		this.email = value;
	}
	
	/**
	* 获取 EMAIL
	* @return String
	*/
	public String getEmail() {
		return this.email;
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
	* 设置 审核人
	* @param value 
	*/
	public void setChecker(Long value) {
		this.checker = value;
	}
	
	/**
	* 获取 审核人
	* @return Long
	*/
	public Long getChecker() {
		return this.checker;
	}
	/**
	* 设置 审核日期
	* @param value 
	*/
	public void setCheck_date(Date value) {
		this.check_date = value;
	}
	
	/**
	* 获取 审核日期
	* @return Date
	*/
	public Date getCheck_date() {
		return this.check_date;
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
	* 设置 状态
	* @param value 
	*/
	public void setState(String value) {
		this.state = value;
	}
	
	/**
	* 获取 状态
	* @return String
	*/
	public String getState() {
		return this.state;
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