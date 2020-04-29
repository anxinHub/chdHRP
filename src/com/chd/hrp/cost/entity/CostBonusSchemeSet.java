/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 职工奖金查询方案表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class CostBonusSchemeSet implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 方案ID
	 */
	private Long scheme_id;
	/**
	 * 方案名称
	 */
	private String scheme_name;
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
	 * 职工分类
	 */
	private String emp_kind_code;
	/**
	 * 奖金项编码
	 */
	private String bonus_item_code;
	
	/**
	 * 职工分类名称
	 */
	private String emp_kind_name;
	/**
	 * 奖金项名称
	 */
	private String bonus_item_name;
	/**
	 * 奖金项目
	 */
	private String bonus_code;
	/**
	 * 显示顺序
	 */
	private Integer order_no;
	/**
	 * 拼音码
	 */
	private String spell_code;
	/**
	 * 五笔码
	 */
	private String wbx_code;
	/**
	 * 导入验证信息
	 */
	private String error_type;
	
	private String bonus_column;
	
	
	public String getBonus_column() {
		return bonus_column;
	}
	public void setBonus_column(String bonus_column) {
		this.bonus_column = bonus_column;
	}
	/**
	 * 设置 方案ID
	 */
	public void setScheme_id(Long value) {
		this.scheme_id = value;
	}
	/**
	 * 获取 方案ID
	 */	
	public Long getScheme_id() {
		return this.scheme_id;
	}
	/**
	 * 设置 方案名称
	 */
	public void setScheme_name(String value) {
		this.scheme_name = value;
	}
	/**
	 * 获取 方案名称
	 */	
	public String getScheme_name() {
		return this.scheme_name;
	}
	/**
	 * 设置 集团ID
	 */
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	/**
	 * 获取 集团ID
	 */	
	public Long getGroup_id() {
		return this.group_id;
	}
	/**
	 * 设置 医院ID
	 */
	public void setHos_id(Long value) {
		this.hos_id = value;
	}
	/**
	 * 获取 医院ID
	 */	
	public Long getHos_id() {
		return this.hos_id;
	}
	/**
	 * 设置 账套编码
	 */
	public void setCopy_code(String value) {
		this.copy_code = value;
	}
	/**
	 * 获取 账套编码
	 */	
	public String getCopy_code() {
		return this.copy_code;
	}
	/**
	 * 设置 职工分类
	 */
	public void setEmp_kind_code(String value) {
		this.emp_kind_code = value;
	}
	/**
	 * 获取 职工分类
	 */	
	public String getEmp_kind_code() {
		return this.emp_kind_code;
	}
	/**
	 * 设置 奖金项编码
	 */
	public void setBonus_item_code(String value) {
		this.bonus_item_code = value;
	}
	/**
	 * 获取 奖金项编码
	 */	
	public String getBonus_item_code() {
		return this.bonus_item_code;
	}
	/**
	 * 设置 奖金项目
	 */
	public void setBonus_code(String value) {
		this.bonus_code = value;
	}
	/**
	 * 获取 奖金项目
	 */	
	public String getBonus_code() {
		return this.bonus_code;
	}
	/**
	 * 设置 显示顺序
	 */
	public void setOrder_no(Integer value) {
		this.order_no = value;
	}
	/**
	 * 获取 显示顺序
	 */	
	public Integer getOrder_no() {
		return this.order_no;
	}
	/**
	 * 设置 拼音码
	 */
	public void setSpell_code(String value) {
		this.spell_code = value;
	}
	/**
	 * 获取 拼音码
	 */	
	public String getSpell_code() {
		return this.spell_code;
	}
	/**
	 * 设置 五笔码
	 */
	public void setWbx_code(String value) {
		this.wbx_code = value;
	}
	/**
	 * 获取 五笔码
	 */	
	public String getWbx_code() {
		return this.wbx_code;
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
	public String getEmp_kind_name() {
		return emp_kind_name;
	}
	public void setEmp_kind_name(String emp_kind_name) {
		this.emp_kind_name = emp_kind_name;
	}
	public String getBonus_item_name() {
		return bonus_item_name;
	}
	public void setBonus_item_name(String bonus_item_name) {
		this.bonus_item_name = bonus_item_name;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}