/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.sup.entity;
 
import java.io.Serializable;
import java.util.*;

/** 
 * @Description: 供应商管理用户
 * @Table: SYS_SUP_USER
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
  
public class SysSupUser implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 集团ID
	 */
	private Long group_id;

	/**
	 * 集团用户没有医院ID，添加0
	 */
	private Long hos_id;

	/**
	 * 用户编码
	 */
	private String user_code;

	/**
	 * 用户名称
	 */
	private String user_name;

	/**
	 * 密码
	 */
	private String user_pwd;

	/**
	 * 拼音码
	 */
	private String spell_code;

	/**
	 * 五笔码
	 */
	private String wbx_code;

	/**
	 * 系统模块
	 */
	private String mod_code;

	/**
	 * 账套
	 */
	private String copy_code;

	/**
	 * 账套
	 */
	private String copy_name;

	/**
	 * 所属供应商
	 */
	private Long sup_id;

	/**
	 * 所属供应商
	 */
	private Long sup_no;

	/**
	 * 所属供应商
	 */
	private String sup_code;

	/**
	 * 所属供应商
	 */
	private String sup_name;

	/**
	 * 导入验证信息
	 */
	private String error_type;

	private int is_disable;
	private int is_manager;
	
	public Long getGroup_id() {
		return group_id;
	}

	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}

	public Long getHos_id() {
		return hos_id;
	}

	public void setHos_id(Long hos_id) {
		this.hos_id = hos_id;
	}

	public String getUser_code() {
		return user_code;
	}

	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_pwd() {
		return user_pwd;
	}

	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
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

	public String getMod_code() {
		return mod_code;
	}

	public void setMod_code(String mod_code) {
		this.mod_code = mod_code;
	}

	public String getCopy_code() {
		return copy_code;
	}

	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}

	public String getCopy_name() {
		return copy_name;
	}

	public void setCopy_name(String copy_name) {
		this.copy_name = copy_name;
	}

	public Long getSup_id() {
		return sup_id;
	}

	public void setSup_id(Long sup_id) {
		this.sup_id = sup_id;
	}

	public Long getSup_no() {
		return sup_no;
	}

	public void setSup_no(Long sup_no) {
		this.sup_no = sup_no;
	}

	public String getSup_code() {
		return sup_code;
	}

	public void setSup_code(String sup_code) {
		this.sup_code = sup_code;
	}

	public String getSup_name() {
		return sup_name;
	}

	public void setSup_name(String sup_name) {
		this.sup_name = sup_name;
	}

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}

	public int getIs_disable() {
		return is_disable;
	}

	public void setIs_disable(int is_disable) {
		this.is_disable = is_disable;
	}

	public int getIs_manager() {
		return is_manager;
	}

	public void setIs_manager(int is_manager) {
		this.is_manager = is_manager;
	}

}