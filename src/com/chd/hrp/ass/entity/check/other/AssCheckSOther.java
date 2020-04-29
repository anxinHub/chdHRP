/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.check.other;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 051101 仓库盘点单(专用设备)
 * @Table:
 * ASS_CHECK_S_General
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class AssCheckSOther implements Serializable {

	
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
	 * 任务单号
	 */
	private String check_plan_no;
	
	/**
	 * 盘点单号
	 */
	private String check_no;
	
	/**
	 * 仓库编码ID
	 */
	private Long store_id;
	
	/**
	 * 仓库编码NO
	 */
	private Long store_no;
	
	/**
	 * 摘要
	 */
	private String summary;
	
	/**
	 * 盘点人
	 */
	private Long check_emp;
	
	/**
	 * 盘点日期
	 */
	private Date check_date;
	
	/**
	 * 备注
	 */
	private String note;
	
	/**
	 * 状态
	 */
	private Integer state;
	

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
	* 设置 任务单号
	* @param value 
	*/
	public void setCheck_plan_no(String value) {
		this.check_plan_no = value;
	}
	
	/**
	* 获取 任务单号
	* @return String
	*/
	public String getCheck_plan_no() {
		return this.check_plan_no;
	}
	/**
	* 设置 盘点单号
	* @param value 
	*/
	public void setCheck_no(String value) {
		this.check_no = value;
	}
	
	/**
	* 获取 盘点单号
	* @return String
	*/
	public String getCheck_no() {
		return this.check_no;
	}
	/**
	* 设置 仓库编码ID
	* @param value 
	*/
	public void setStore_id(Long value) {
		this.store_id = value;
	}
	
	/**
	* 获取 仓库编码ID
	* @return Long
	*/
	public Long getStore_id() {
		return this.store_id;
	}
	/**
	* 设置 仓库编码NO
	* @param value 
	*/
	public void setStore_no(Long value) {
		this.store_no = value;
	}
	
	/**
	* 获取 仓库编码NO
	* @return Long
	*/
	public Long getStore_no() {
		return this.store_no;
	}
	/**
	* 设置 摘要
	* @param value 
	*/
	public void setSummary(String value) {
		this.summary = value;
	}
	
	/**
	* 获取 摘要
	* @return String
	*/
	public String getSummary() {
		return this.summary;
	}
	/**
	* 设置 盘点人
	* @param value 
	*/
	public void setCheck_emp(Long value) {
		this.check_emp = value;
	}
	
	/**
	* 获取 盘点人
	* @return Long
	*/
	public Long getCheck_emp() {
		return this.check_emp;
	}
	/**
	* 设置 盘点日期
	* @param value 
	*/
	public void setCheck_date(Date value) {
		this.check_date = value;
	}
	
	/**
	* 获取 盘点日期
	* @return Date
	*/
	public Date getCheck_date() {
		return this.check_date;
	}
	/**
	* 设置 备注
	* @param value 
	*/
	public void setNote(String value) {
		this.note = value;
	}
	
	/**
	* 获取 备注
	* @return String
	*/
	public String getNote() {
		return this.note;
	}
	/**
	* 设置 状态
	* @param value 
	*/
	public void setState(Integer value) {
		this.state = value;
	}
	
	/**
	* 获取 状态
	* @return Integer
	*/
	public Integer getState() {
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