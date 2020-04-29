/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 项目字典属性表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class AccProjAttr implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 项目ID
	 */
	private int proj_id;
	/**
	 * 集团ID
	 */
	private int group_id;
	/**
	 * 医院ID
	 */
	private int hos_id;
	/**
	 * 项目级别
	 */
	private String level_code;
	
	private String level_name;
	/**
	 * 项目用途
	 */
	private String use_code;
	
	private String use_name;
	/**
	 * 项目负责人
	 */
	private int con_emp_id;
	
	private String con_emp_name;
	/**
	 * 项目负责人电话
	 */
	private String con_phone;
	/**
	 * 财务负责人
	 */
	private int acc_emp_id;
	
	private String acc_emp_name;
	/**
	 * 财务负责人电话
	 */
	private String acc_phone;
	/**
	 * 填报人
	 */
	private int app_emp_id;
	
	private String app_emp_name;
	/**
	 * 填报部门
	 */
	private int dept_id;
	
	private String dept_name;
	
	private int dept_no;
	
	private String dept_code;
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
	
	private String sort_code;
	
	private Date set_up_date;
	private Date complete_date;
	private Date pay_end_date ;
	private Date sespend_date ;
	public Date getSet_up_date() {
		return set_up_date;
	}
	public void setSet_up_date(Date set_up_date) {
		this.set_up_date = set_up_date;
	}
	public Date getComplete_date() {
		return complete_date;
	}
	public void setComplete_date(Date complete_date) {
		this.complete_date = complete_date;
	}
	public Date getPay_end_date() {
		return pay_end_date;
	}
	public void setPay_end_date(Date pay_end_date) {
		this.pay_end_date = pay_end_date;
	}
	public Date getSespend_date() {
		return sespend_date;
	}
	public void setSespend_date(Date sespend_date) {
		this.sespend_date = sespend_date;
	}
	public String getApply_code() {
		return apply_code;
	}
	public void setApply_code(String apply_code) {
		this.apply_code = apply_code;
	}
	public Long getComplete_per() {
		return complete_per;
	}
	public void setComplete_per(Long complete_per) {
		this.complete_per = complete_per;
	}
	public Long getSespend_per() {
		return sespend_per;
	}
	public void setSespend_per(Long sespend_per) {
		this.sespend_per = sespend_per;
	}
	public Long getIs_carry() {
		return is_carry;
	}
	public void setIs_carry(Long is_carry) {
		this.is_carry = is_carry;
	}
	private String apply_code;
	private Long complete_per ;
	private Long sespend_per;
	private Long is_carry;
	
	private String proj_code;
	private String proj_name;
	private String type_code;
	private String type_name;
	private String proj_simple;
	private String is_stop;
	private String proj_state;
	private Integer is_disable;
	
	
	public Integer getIs_disable() {
		return is_disable;
	}
	public void setIs_disable(Integer is_disable) {
		this.is_disable = is_disable;
	}
	public String getProj_state() {
		return proj_state;
	}
	public void setProj_state(String proj_state) {
		this.proj_state = proj_state;
	}
	public String getLevel_name() {
		return level_name;
	}
	public void setLevel_name(String level_name) {
		this.level_name = level_name;
	}
	public String getUse_name() {
		return use_name;
	}
	public void setUse_name(String use_name) {
		this.use_name = use_name;
	}
	public String getCon_emp_name() {
		return con_emp_name;
	}
	public void setCon_emp_name(String con_emp_name) {
		this.con_emp_name = con_emp_name;
	}
	public String getAcc_emp_name() {
		return acc_emp_name;
	}
	public void setAcc_emp_name(String acc_emp_name) {
		this.acc_emp_name = acc_emp_name;
	}
	public String getApp_emp_name() {
		return app_emp_name;
	}
	public void setApp_emp_name(String app_emp_name) {
		this.app_emp_name = app_emp_name;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public String getProj_code() {
		return proj_code;
	}
	public void setProj_code(String proj_code) {
		this.proj_code = proj_code;
	}
	public String getProj_name() {
		return proj_name;
	}
	public void setProj_name(String proj_name) {
		this.proj_name = proj_name;
	}
	public String getType_code() {
		return type_code;
	}
	public void setType_code(String type_code) {
		this.type_code = type_code;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	public String getProj_simple() {
		return proj_simple;
	}
	public void setProj_simple(String proj_simple) {
		this.proj_simple = proj_simple;
	}
	public String getIs_stop() {
		return is_stop;
	}
	public void setIs_stop(String is_stop) {
		this.is_stop = is_stop;
	}
	/**
	 * 设置 项目级别
	 */
	public void setLevel_code(String value) {
		this.level_code = value;
	}
	/**
	 * 获取 项目级别
	 */	
	public String getLevel_code() {
		return this.level_code;
	}
	/**
	 * 设置 项目用途
	 */
	public void setUse_code(String value) {
		this.use_code = value;
	}
	/**
	 * 获取 项目用途
	 */	
	public String getUse_code() {
		return this.use_code;
	}
	/**
	 * 设置 项目负责人
	 */
	public void setCon_emp_id(int value) {
		this.con_emp_id = value;
	}
	/**
	 * 获取 项目负责人
	 */	
	public int getCon_emp_id() {
		return this.con_emp_id;
	}
	/**
	 * 设置 项目负责人电话
	 */
	public void setCon_phone(String value) {
		this.con_phone = value;
	}
	/**
	 * 获取 项目负责人电话
	 */	
	public String getCon_phone() {
		return this.con_phone;
	}
	/**
	 * 设置 财务负责人
	 */
	public void setAcc_emp_id(int value) {
		this.acc_emp_id = value;
	}
	/**
	 * 获取 财务负责人
	 */	
	public int getAcc_emp_id() {
		return this.acc_emp_id;
	}
	/**
	 * 设置 财务负责人电话
	 */
	public void setAcc_phone(String value) {
		this.acc_phone = value;
	}
	/**
	 * 获取 财务负责人电话
	 */	
	public String getAcc_phone() {
		return this.acc_phone;
	}
	/**
	 * 设置 填报人
	 */
	public void setApp_emp_id(int value) {
		this.app_emp_id = value;
	}
	/**
	 * 获取 填报人
	 */	
	public int getApp_emp_id() {
		return this.app_emp_id;
	}
	/**
	 * 设置 填报部门
	 */
	public void setDept_id(int value) {
		this.dept_id = value;
	}
	/**
	 * 获取 填报部门
	 */	
	public int getDept_id() {
		return this.dept_id;
	}
	/**
	 * 设置 填报日期
	 */
	public void setApp_date(Date value) {
		this.app_date = value;
	}
	/**
	 * 获取 填报日期
	 */	
	public Date getApp_date() {
		return this.app_date;
	}
	/**
	 * 设置 联系电话
	 */
	public void setApp_phone(String value) {
		this.app_phone = value;
	}
	/**
	 * 获取 联系电话
	 */	
	public String getApp_phone() {
		return this.app_phone;
	}
	/**
	 * 设置 EMAIL
	 */
	public void setEmail(String value) {
		this.email = value;
	}
	/**
	 * 获取 EMAIL
	 */	
	public String getEmail() {
		return this.email;
	}
	/**
	 * 设置 备注
	 */
	public void setNote(String value) {
		this.note = value;
	}
	/**
	 * 获取 备注
	 */	
	public String getNote() {
		return this.note;
	}
	public int getDept_no() {
		return dept_no;
	}
	public void setDept_no(int dept_no) {
		this.dept_no = dept_no;
	}
	public String getDept_code() {
		return dept_code;
	}
	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}
	public int getProj_id() {
		return proj_id;
	}
	public void setProj_id(int proj_id) {
		this.proj_id = proj_id;
	}
	public int getGroup_id() {
		return group_id;
	}
	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}
	public int getHos_id() {
		return hos_id;
	}
	public void setHos_id(int hos_id) {
		this.hos_id = hos_id;
	}
	public String getSort_code() {
		return sort_code;
	}
	public void setSort_code(String sort_code) {
		this.sort_code = sort_code;
	}
	
}