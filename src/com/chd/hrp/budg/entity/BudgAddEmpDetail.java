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
 * 增员计划明细
 * @Table:
 * BUDG_ADD_EMP_DETAIL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgAddEmpDetail implements Serializable {

	
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
	 * 计划年度
	 */
	private String plan_year;
	
	/**
	 * 计划单号
	 */
	private String plan_code;
	
	/**
	 * 部门ID
	 */
	private Long dept_id;
	
	/**
	 * 部门变更ID
	 */
	private Long dept_no;
	
	/**
	 * 职工类别编码
	 */
	private String emp_type_code;
	
	/**
	 * 计划到岗日期
	 */
	private String in_month;
	/**
	 * 在岗人数
	 */
	private Long in_num;
	
	/**
	 * 增员人数
	 */
	private Long add_num;
	
	/**
	 * 增员原因
	 */
	private String reason;
	

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
	* 设置 计划年度
	* @param value 
	*/
	public void setPlan_year(String value) {
		this.plan_year = value;
	}
	
	/**
	* 获取 计划年度
	* @return String
	*/
	public String getPlan_year() {
		return this.plan_year;
	}
	/**
	* 设置 计划单号
	* @param value 
	*/
	public void setPlan_code(String value) {
		this.plan_code = value;
	}
	
	/**
	* 获取 计划单号
	* @return String
	*/
	public String getPlan_code() {
		return this.plan_code;
	}
	/**
	* 设置 部门ID
	* @param value 
	*/
	public void setDept_id(Long value) {
		this.dept_id = value;
	}
	
	/**
	* 获取 部门ID
	* @return Long
	*/
	public Long getDept_id() {
		return this.dept_id;
	}
	/**
	* 设置 部门变更ID
	* @param value 
	*/
	public void setDept_no(Long value) {
		this.dept_no = value;
	}
	
	/**
	* 获取 部门变更ID
	* @return String
	*/
	public Long getDept_no() {
		return this.dept_no;
	}
	/**
	* 设置 职工类别编码
	* @param value 
	*/
	public void setEmp_type_code(String value) {
		this.emp_type_code = value;
	}
	
	/**
	* 获取 职工类别编码
	* @return String
	*/
	public String getEmp_type_code() {
		return this.emp_type_code;
	}
	/**
	* 设置 计划到岗日期
	* @param value 
	*/
	public void setIn_month(String value) {
		this.in_month = value;
	}
	
	/**
	* 获取 计划到岗日期
	* @return String
	*/
	public String getIn_month() {
		return this.in_month;
	}
	/**
	* 设置 在岗人数
	* @param value 
	*/
	public void setIn_num(Long value) {
		this.in_num = value;
	}
	
	/**
	* 获取 在岗人数
	* @return Long
	*/
	public Long getIn_num() {
		return this.in_num;
	}
	/**
	* 设置 增员人数
	* @param value 
	*/
	public void setAdd_num(Long value) {
		this.add_num = value;
	}
	
	/**
	* 获取 增员人数
	* @return Long
	*/
	public Long getAdd_num() {
		return this.add_num;
	}
	/**
	* 设置 增员原因
	* @param value 
	*/
	public void setReason(String value) {
		this.reason = value;
	}
	
	/**
	* 获取 增员原因
	* @return String
	*/
	public String getReason() {
		return this.reason;
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