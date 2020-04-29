/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 成本_职工分类奖金项配置表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class CostEmpKindBonusItemSet implements Serializable {

	
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
	 * 职工分类
	 */
	private String emp_kind_code;
	
	private String emp_kind_name;
	/**
	 * 奖金项编码
	 */
	private String bonus_item_code;
	/**
	 * 奖金项名称
	 */
	private String bonus_item_name;
	
	/**
	 * 导入验证信息
	 */
	private String error_type;
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
	 * 设置 奖金项名称
	 */
	public void setBonus_item_name(String value) {
		this.bonus_item_name = value;
	}
	/**
	 * 获取 奖金项名称
	 */	
	public String getBonus_item_name() {
		return this.bonus_item_name;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}