/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.entity;

import java.io.Serializable;
/**
 * 
 * @Description:
 * 奖金项目调整
 * @Table:
 * 
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgAwardsItemAdj implements Serializable {

	
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
	private String budg_year;

	/**
	 * 职工类别编码
	 */
	private String emp_type_code;
	
	/**
	 * 调整金额
	 */
	private Long adj_amount;
	
	/**
	 * 奖金项目编码
	 */
	private String awards_item_code;
	
	/**
	 * 调整比例
	 */
	private Long adj_rate;
	
	/**
	 * 计划调整月份
	 */
	private String adj_month;
	
	/**
	 * 说明
	 */
	private String remark;
	

	/**
	 * 导入验证信息
	 */
	private String error_type;


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
	
	
	public String getCopy_code() {
		return copy_code;
	}
	
	
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	
	
	public String getBudg_year() {
		return budg_year;
	}
	
	
	public void setBudg_year(String budg_year) {
		this.budg_year = budg_year;
	}
	
	
	public String getEmp_type_code() {
		return emp_type_code;
	}
	
	
	public void setEmp_type_code(String emp_type_code) {
		this.emp_type_code = emp_type_code;
	}
	
	
	public Long getAdj_amount() {
		return adj_amount;
	}
	
	
	public void setAdj_amount(Long adj_amount) {
		this.adj_amount = adj_amount;
	}
	
	
	public String getAwards_item_code() {
		return awards_item_code;
	}
	
	
	public void setAwards_item_code(String awards_item_code) {
		this.awards_item_code = awards_item_code;
	}
	
	
	public Long getAdj_rate() {
		return adj_rate;
	}
	
	
	public void setAdj_rate(Long adj_rate) {
		this.adj_rate = adj_rate;
	}
	
	
	public String getAdj_month() {
		return adj_month;
	}
	
	
	public void setAdj_month(String adj_month) {
		this.adj_month = adj_month;
	}
	
	
	public String getRemark() {
		return remark;
	}
	
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	public String getError_type() {
		return error_type;
	}
	
	
	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	
	
}