/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 科室收入总账<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class CostInAcctVouch implements Serializable {

	
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
	 * 统计年月
	 */
	private String year_month;
	/**
	 * 科室ID
	 */
	private Long dept_id;
	/**
	 * 科室变更ID
	 */
	private Long dept_no;
	
	private String dept_code;
	
	private String dept_name;
	/**
	 * 收入项目编码
	 */
	private Long income_item_id;
	
	private String income_item_code;
	
	private String income_item_name;
	/**
	 * 金额
	 */
	private double amount;
	/**
	 * 导入验证信息
	 */
	private String error_type;
	
	public String getDept_code() {
		return dept_code;
	}
	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}
	public String getIncome_item_code() {
		return income_item_code;
	}
	public void setIncome_item_code(String income_item_code) {
		this.income_item_code = income_item_code;
	}
	public String getIncome_item_name() {
		return income_item_name;
	}
	public void setIncome_item_name(String income_item_name) {
		this.income_item_name = income_item_name;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
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
	 * 设置 科室ID
	 */
	public void setDept_id(Long value) {
		this.dept_id = value;
	}
	/**
	 * 获取 科室ID
	 */	
	public Long getDept_id() {
		return this.dept_id;
	}
	/**
	 * 设置 科室变更ID
	 */
	public void setDept_no(Long value) {
		this.dept_no = value;
	}
	/**
	 * 获取 科室变更ID
	 */	
	public Long getDept_no() {
		return this.dept_no;
	}
	public Long getIncome_item_id() {
		return income_item_id;
	}
	public void setIncome_item_id(Long income_item_id) {
		this.income_item_id = income_item_id;
	}
	/**
	 * 设置 金额
	 */
	public void setAmount(double value) {
		this.amount = value;
	}
	/**
	 * 获取 金额
	 */	
	public double getAmount() {
		return this.amount;
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