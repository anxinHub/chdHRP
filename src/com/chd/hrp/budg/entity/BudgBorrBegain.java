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
 * 期初借款
 * @Table:
 * BUDG_BORR_BEGAIN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgBorrBegain implements Serializable {

	
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
	 * 期初借款单号
	 */
	private String begin_code;
	
	/**
	 * 科室ID
	 */
	private Long dept_id;
	
	/**
	 * 科室变更ID
	 */
	private Long dept_no;
	
	/**
	 * 项目ID
	 */
	private Long proj_id;
	
	/**
	 * 项目变更ID
	 */
	private Long proj_no;
	
	/**
	 * 借款人
	 */
	private Long emp_id;
	
	/**
	 * 借款人变更ID
	 */
	private Long emp_no;
	
	/**
	 * 借款余额
	 */
	private Double remain_amount;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 制单人
	 */
	private Long maker;
	
	/**
	 * 制单日期
	 */
	private Date make_date;
	
	/**
	 * 审核人
	 */
	private Long checker;
	
	/**
	 * 审核日期
	 */
	private Date check_date;
	
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
	* 设置 期初借款单号
	* @param value 
	*/
	public void setBegin_code(String value) {
		this.begin_code = value;
	}
	
	/**
	* 获取 期初借款单号
	* @return String
	*/
	public String getBegin_code() {
		return this.begin_code;
	}
	/**
	* 设置 科室ID
	* @param value 
	*/
	public void setDept_id(Long value) {
		this.dept_id = value;
	}
	
	/**
	* 获取 科室ID
	* @return Long
	*/
	public Long getDept_id() {
		return this.dept_id;
	}
	/**
	* 设置 科室变更ID
	* @param value 
	*/
	public void setDept_no(Long value) {
		this.dept_no = value;
	}
	
	/**
	* 获取 科室变更ID
	* @return Long
	*/
	public Long getDept_no() {
		return this.dept_no;
	}
	/**
	* 设置 项目ID
	* @param value 
	*/
	public void setProj_id(Long value) {
		this.proj_id = value;
	}
	
	/**
	* 获取 项目ID
	* @return Long
	*/
	public Long getProj_id() {
		return this.proj_id;
	}
	/**
	* 设置 项目变更ID
	* @param value 
	*/
	public void setProj_no(Long value) {
		this.proj_no = value;
	}
	
	/**
	* 获取 项目变更ID
	* @return Long
	*/
	public Long getProj_no() {
		return this.proj_no;
	}
	/**
	* 设置 借款人
	* @param value 
	*/
	public void setEmp_id(Long value) {
		this.emp_id = value;
	}
	
	/**
	* 获取 借款人
	* @return Long
	*/
	public Long getEmp_id() {
		return this.emp_id;
	}
	/**
	* 设置 借款人变更ID
	* @param value 
	*/
	public void setEmp_no(Long value) {
		this.emp_no = value;
	}
	
	/**
	* 获取 借款人变更ID
	* @return Long
	*/
	public Long getEmp_no() {
		return this.emp_no;
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
	* 设置 备注
	* @param value 
	*/
	public void setRemark(String value) {
		this.remark = value;
	}
	
	/**
	* 获取 备注
	* @return String
	*/
	public String getRemark() {
		return this.remark;
	}
	/**
	* 设置 制单人
	* @param value 
	*/
	public void setMaker(Long value) {
		this.maker = value;
	}
	
	/**
	* 获取 制单人
	* @return Long
	*/
	public Long getMaker() {
		return this.maker;
	}
	/**
	* 设置 制单日期
	* @param value 
	*/
	public void setMake_date(Date value) {
		this.make_date = value;
	}
	
	/**
	* 获取 制单日期
	* @return Date
	*/
	public Date getMake_date() {
		return this.make_date;
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