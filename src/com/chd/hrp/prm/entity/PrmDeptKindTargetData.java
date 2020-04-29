
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.entity;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 0313 科室类别绩效指标数据表
 * @Table:
 * PRM_DEPT_KIND_TARGET_DATA
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class PrmDeptKindTargetData implements Serializable {

	
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
	private String acc_year;
	
	/**
	 * 月份
	 */
	private String acc_month;
	
	/**
	 * 指标编码
	 */
	private String target_code;
	
	/**
	 * 科室类别
	 */
	private String dept_kind_code;
	
	/**
	 * 指标值
	 */
	private Double target_value;
	
	/**
	 * 审核标志
	 */
	private Integer is_audit;
	
	/**
	 * 审核人
	 */
	private String user_code;
	
	/**
	 * 审核时间
	 */
	private String audit_date;
	

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
	* 设置 年度
	* @param value 
	*/
	public void setAcc_year(String value) {
		this.acc_year = value;
	}
	
	/**
	* 获取 年度
	* @return String
	*/
	public String getAcc_year() {
		return this.acc_year;
	}
	/**
	* 设置 月份
	* @param value 
	*/
	public void setAcc_month(String value) {
		this.acc_month = value;
	}
	
	/**
	* 获取 月份
	* @return String
	*/
	public String getAcc_month() {
		return this.acc_month;
	}
	/**
	* 设置 指标编码
	* @param value 
	*/
	public void setTarget_code(String value) {
		this.target_code = value;
	}
	
	/**
	* 获取 指标编码
	* @return String
	*/
	public String getTarget_code() {
		return this.target_code;
	}
	/**
	* 设置 科室类别
	* @param value 
	*/
	public void setDept_kind_code(String value) {
		this.dept_kind_code = value;
	}
	
	/**
	* 获取 科室类别
	* @return String
	*/
	public String getDept_kind_code() {
		return this.dept_kind_code;
	}
	/**
	* 设置 指标值
	* @param value 
	*/
	public void setTarget_value(Double value) {
		this.target_value = value;
	}
	
	/**
	* 获取 指标值
	* @return Double
	*/
	public Double getTarget_value() {
		return this.target_value;
	}
	/**
	* 设置 审核标志
	* @param value 
	*/
	public void setIs_audit(Integer value) {
		this.is_audit = value;
	}
	
	/**
	* 获取 审核标志
	* @return Integer
	*/
	public Integer getIs_audit() {
		return this.is_audit;
	}
	/**
	* 设置 审核人
	* @param value 
	*/
	public void setUser_code(String value) {
		this.user_code = value;
	}
	
	/**
	* 获取 审核人
	* @return String
	*/
	public String getUser_code() {
		return this.user_code;
	}
	/**
	* 设置 审核时间
	* @param value 
	*/
	public void setAudit_date(String value) {
		this.audit_date = value;
	}
	
	/**
	* 获取 审核时间
	* @return String
	*/
	public String getAudit_date() {
		return this.audit_date;
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