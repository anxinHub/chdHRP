/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.check.land;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 051101 盘点任务单(土地)
 * @Table:
 * ASS_CHECK_PLAN_LAND
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class AssCheckPlanLand implements Serializable {

	
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
	 * 任务说明
	 */
	private String summary;
	
	/**
	 * 盘点开始日期
	 */
	private Date beg_date;
	
	/**
	 * 盘点结束日期
	 */
	private Date end_date;
	
	/**
	 * 盘点完成日期
	 */
	private Date fin_date;
	
	/**
	 * 制单人
	 */
	private Long mak_emp;
	/**
	 * 制单人
	 */
	private String mak_emp_name;
	
	/**
	 * 制单日期
	 */
	private Date mak_date;
	
	/**
	 * 审核人
	 */
	private Long audit_emp;
	/**
	 * 审核人
	 */
	private String audit_emp_name;
	
	/**
	 * 审核日期
	 */
	private Date audit_date;
	
	/**
	 * 状态
	 */
	private Integer state;
	
   private String state_name;
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
	* 设置 任务说明
	* @param value 
	*/
	public void setSummary(String value) {
		this.summary = value;
	}
	
	/**
	* 获取 任务说明
	* @return String
	*/
	public String getSummary() {
		return this.summary;
	}
	/**
	* 设置 盘点开始日期
	* @param value 
	*/
	public void setBeg_date(Date value) {
		this.beg_date = value;
	}
	
	/**
	* 获取 盘点开始日期
	* @return Date
	*/
	public Date getBeg_date() {
		return this.beg_date;
	}
	/**
	* 设置 盘点结束日期
	* @param value 
	*/
	public void setEnd_date(Date value) {
		this.end_date = value;
	}
	
	/**
	* 获取 盘点结束日期
	* @return Date
	*/
	public Date getEnd_date() {
		return this.end_date;
	}
	/**
	* 设置 盘点完成日期
	* @param value 
	*/
	public void setFin_date(Date value) {
		this.fin_date = value;
	}
	
	/**
	* 获取 盘点完成日期
	* @return Date
	*/
	public Date getFin_date() {
		return this.fin_date;
	}
	/**
	* 设置 制单人
	* @param value 
	*/
	public void setMak_emp(Long value) {
		this.mak_emp = value;
	}
	
	/**
	* 获取 制单人
	* @return Long
	*/
	public Long getMak_emp() {
		return this.mak_emp;
	}
	/**
	* 设置 制单日期
	* @param value 
	*/
	public void setMak_date(Date value) {
		this.mak_date = value;
	}
	
	/**
	* 获取 制单日期
	* @return Date
	*/
	public Date getMak_date() {
		return this.mak_date;
	}
	/**
	* 设置 审核人
	* @param value 
	*/
	public void setAudit_emp(Long value) {
		this.audit_emp = value;
	}
	
	/**
	* 获取 审核人
	* @return Long
	*/
	public Long getAudit_emp() {
		return this.audit_emp;
	}
	/**
	* 设置 审核日期
	* @param value 
	*/
	public void setAudit_date(Date value) {
		this.audit_date = value;
	}
	
	/**
	* 获取 审核日期
	* @return Date
	*/
	public Date getAudit_date() {
		return this.audit_date;
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
	
	public String getState_name() {
		return state_name;
	}

	public void setState_name(String state_name) {
		this.state_name = state_name;
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

	
    public String getMak_emp_name() {
    	return mak_emp_name;
    }

	
    public void setMak_emp_name(String mak_emp_name) {
    	this.mak_emp_name = mak_emp_name;
    }

	
    public String getAudit_emp_name() {
    	return audit_emp_name;
    }

	
    public void setAudit_emp_name(String audit_emp_name) {
    	this.audit_emp_name = audit_emp_name;
    }

	
}