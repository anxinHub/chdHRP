package com.chd.hrp.budg.entity;

import java.io.Serializable;
import java.util.Date;

public class BudgMatPurCheck implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3355303555477127886L;
	
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
	 * 预算年度
	 */
	private String budg_year;
	
	/**
	 * 审批单号
	 */
	private String check_code;
	
	/**
	 * 审批类型
	 */
	private String check_type;
	
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
	private Date make_data;
	
	/**
	 * 审核人
	 */
	private Long checker;
	
	/**
	 * 审核日期
	 */
	private Date check_data;
	
	/**
	 * 预算下达日期
	 */
	private Date issue_data;
	
	/**
	 * 状态
	 */
	private String bc_state;
	

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
	* 设置 预算年度
	* @param value 
	*/
	public void setBudg_year(String value) {
		this.budg_year = value;
	}
	
	/**
	* 获取 预算年度
	* @return String
	*/
	public String getBudg_year() {
		return this.budg_year;
	}
	/**
	* 设置 审批单号
	* @param value 
	*/
	public void setCheck_code(String value) {
		this.check_code = value;
	}
	
	/**
	* 获取 审批单号
	* @return String
	*/
	public String getCheck_code() {
		return this.check_code;
	}
	/**
	* 设置 审批类型
	* @param value 
	*/
	public void setCheck_type(String value) {
		this.check_type = value;
	}
	
	/**
	* 获取 审批类型
	* @return String
	*/
	public String getCheck_type() {
		return this.check_type;
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
	public void setMake_data(Date value) {
		this.make_data = value;
	}
	
	/**
	* 获取 制单日期
	* @return Date
	*/
	public Date getMake_data() {
		return this.make_data;
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
	public void setCheck_data(Date value) {
		this.check_data = value;
	}
	
	/**
	* 获取 审核日期
	* @return Date
	*/
	public Date getCheck_data() {
		return this.check_data;
	}
	/**
	* 设置 预算下达日期
	* @param value 
	*/
	public void setIssue_data(Date value) {
		this.issue_data = value;
	}
	
	/**
	* 获取 预算下达日期
	* @return Date
	*/
	public Date getIssue_data() {
		return this.issue_data;
	}
	/**
	* 设置 状态
	* @param value 
	*/
	public void setBc_state(String value) {
		this.bc_state = value;
	}
	
	/**
	* 获取 状态
	* @return String
	*/
	public String getBc_state() {
		return this.bc_state;
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
