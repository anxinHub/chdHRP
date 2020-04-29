/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.entity;

import java.io.Serializable;

/**
* @Title. @Description.
* 奖金项与成本项目的对应关系<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class CostBonusCostRela implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;
	
	/**
	 * ID
	 */
	private Long id;
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
	 * 统计年月
	 */
	private String year_month;
	private String acc_month;
	private String acc_year;
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
	 * 成本项目ID
	 */
	private Long cost_item_id;
	/**
	 * 成本项目变更ID
	 */
	private Long cost_item_no;
	/**
	 * 成本项目编码
	 */
	private String cost_item_code;
	/**
	 * 成本项目名称
	 */
	private String cost_item_name;
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
	 * 设置 统计年月
	 */
	public void setYear_month(String value) {
		this.year_month = value;
	}
	/**
	 * 获取 统计年月
	 */	
	public String getYear_month() {
		return this.year_month;
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
	 * 设置 成本项目ID
	 */
	public void setCost_item_id(Long value) {
		this.cost_item_id = value;
	}
	/**
	 * 获取 成本项目ID
	 */	
	public Long getCost_item_id() {
		return this.cost_item_id;
	}
	/**
	 * 设置 成本项目变更ID
	 */
	public void setCost_item_no(Long value) {
		this.cost_item_no = value;
	}
	/**
	 * 获取 成本项目变更ID
	 */	
	public Long getCost_item_no() {
		return this.cost_item_no;
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCost_item_name() {
		return cost_item_name;
	}
	public void setCost_item_name(String cost_item_name) {
		this.cost_item_name = cost_item_name;
	}
	public String getCost_item_code() {
		return cost_item_code;
	}
	public void setCost_item_code(String cost_item_code) {
		this.cost_item_code = cost_item_code;
	}
	/**
	 * @return the acc_month
	 */
	public String getAcc_month() {
		return acc_month;
	}
	/**
	 * @param acc_month the acc_month to set
	 */
	public void setAcc_month(String acc_month) {
		this.acc_month = acc_month;
	}
	/**
	 * @return the acc_year
	 */
	public String getAcc_year() {
		return acc_year;
	}
	/**
	 * @param acc_year the acc_year to set
	 */
	public void setAcc_year(String acc_year) {
		this.acc_year = acc_year;
	}
	
}