package com.chd.hrp.budg.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @Description:
 * 其他支出预算调整
 * @Table:
 * BUDG_ELSE_COST_ADJ
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */

public class BudgElseCostAdj implements Serializable{

	
	private static final long serialVersionUID = 6417481380178417527L;

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
	 * 调整单号
	 */
	private String adj_code;
	
	/**
	 * 操作员所在科室
	 */
	private String adj_file;
	
	/**
	 * 调整说明
	 */
	private String adj_remark;
	
	/**
	 * 状态
	 */
	private String state;
	
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
	private Date check_date;
	
	/**
	 * 上次下达日期
	 */
	private Date last_issue_data;
	

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
	* 设置 调整单号
	* @param value 
	*/
	public void setAdj_code(String value) {
		this.adj_code = value;
	}
	
	/**
	* 获取 调整单号
	* @return String
	*/
	public String getAdj_code() {
		return this.adj_code;
	}
	/**
	* 设置 操作员所在科室
	* @param value 
	*/
	public void setAdj_file(String value) {
		this.adj_file = value;
	}
	
	/**
	* 获取 操作员所在科室
	* @return String
	*/
	public String getAdj_file() {
		return this.adj_file;
	}
	/**
	* 设置 调整说明
	* @param value 
	*/
	public void setAdj_remark(String value) {
		this.adj_remark = value;
	}
	
	/**
	* 获取 调整说明
	* @return String
	*/
	public String getAdj_remark() {
		return this.adj_remark;
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
	* 设置 上次下达日期
	* @param value 
	*/
	public void setLast_issue_data(Date value) {
		this.last_issue_data = value;
	}
	
	/**
	* 获取 上次下达日期
	* @return Date
	*/
	public Date getLast_issue_data() {
		return this.last_issue_data;
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
