/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.acc.entity.payable;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 借款（科室）
 * @Table:
 * BUDG_BORR_DEPT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgBorrDept implements Serializable {

	
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
	 * 职工ID
	 */
	private Long emp_id;
	
	private String emp_name;
	
	/**
	 * 借款金额
	 */
	private Double borrow_amount;
	
	/**
	 * 报销冲抵金额
	 */
	private Double offset_amount;
	
	/**
	 * 退还金额
	 */
	private Double return_amount;
	
	/**
	 * 借款余额
	 */
	private Double remain_amount;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	
	public String getEmp_name() {
		return emp_name;
	}

	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
	
	private String dept_name;
	

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

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
	* 设置 职工ID
	* @param value 
	*/
	public void setEmp_id(Long value) {
		this.emp_id = value;
	}
	
	/**
	* 获取 职工ID
	* @return Long
	*/
	public Long getEmp_id() {
		return this.emp_id;
	}
	/**
	* 设置 借款金额
	* @param value 
	*/
	public void setBorrow_amount(Double value) {
		this.borrow_amount = value;
	}
	
	/**
	* 获取 借款金额
	* @return Double
	*/
	public Double getBorrow_amount() {
		return this.borrow_amount;
	}
	/**
	* 设置 报销冲抵金额
	* @param value 
	*/
	public void setOffset_amount(Double value) {
		this.offset_amount = value;
	}
	
	/**
	* 获取 报销冲抵金额
	* @return Double
	*/
	public Double getOffset_amount() {
		return this.offset_amount;
	}
	/**
	* 设置 退还金额
	* @param value 
	*/
	public void setReturn_amount(Double value) {
		this.return_amount = value;
	}
	
	/**
	* 获取 退还金额
	* @return Double
	*/
	public Double getReturn_amount() {
		return this.return_amount;
	}
	/**
	* 设置 借款余额
	* @param value 
	*/
	public void setRemain_amount(Double value) {
		this.remain_amount = value;
	}
	
	/**
	* 获取 借款余额
	* @return Double
	*/
	public Double getRemain_amount() {
		return this.remain_amount;
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