/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.maintain;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 051203 保养记录
 * @Table:
 * ASS_MAINTAIN_REC
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AssMaintainRec implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 集体ID
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
	 * 保养ID
	 */
	private Long rec_id;
	
	/**
	 * 保养序号
	 */
	private String rec_no;
	
	/**
	 * 统计年度
	 */
	private String ass_year;
	
	/**
	 * 统计月份
	 */
	private String ass_month;
	
	/**
	 * 计划ID
	 */
	private Long plan_id;
	
	private String plan_no;
	
	private String plan_name ;
	
	
	/**
	 * 资产性质
	 */
	private String ass_nature;
	
	private String naturs_name ;
	
	/**
	 * 执行人
	 */
	private Long fact_exec_emp;
	
	private String fact_exec_emp_name;
	
	/**
	 * 计划执行日期
	 */
	private Date plan_exec_date;
	
	/**
	 * 实际执行日期
	 */
	private Date fact_exec_date;
	
	/**
	 * 保养工时
	 */
	private Integer maintain_hours;
	
	/**
	 * 保养费用
	 */
	private Double maintain_money;
	
	/**
	 * 保养说明
	 */
	private String maintain_desc;
	
	/**
	 * 状态
	 */
	private Integer state;
	
	/**
	 * 保养部门ID
	 */
	private String maintain_dept_id;
	
	/**
	 * 保养部门NO
	 */
	private String maintain_dept_no;
	
	private String maintain_dept_code;
	
    private String maintain_dept_name ; 
	
	/**
	 * 制单人
	 */
	private Long create_emp;
	
	private String create_emp_name;
	
	/**
	 * 制单时间
	 */
	private Date create_date;
	
	/**
	 * 审核时间
	 */
	private Date audit_date;
	
	/**
	 * 审核人
	 */
	private Long audit_emp;
	
	private String audit_emp_name;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 集体ID
	* @param value 
	*/
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	
	/**
	* 获取 集体ID
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
	* 设置 保养ID
	* @param value 
	*/
	public void setRec_id(Long value) {
		this.rec_id = value;
	}
	
	/**
	* 获取 保养ID
	* @return Long
	*/
	public Long getRec_id() {
		return this.rec_id;
	}
	/**
	* 设置 保养序号
	* @param value 
	*/
	public void setRec_no(String value) {
		this.rec_no = value;
	}
	
	/**
	* 获取 保养序号
	* @return String
	*/
	public String getRec_no() {
		return this.rec_no;
	}
	/**
	* 设置 统计年度
	* @param value 
	*/
	public void setAss_year(String value) {
		this.ass_year = value;
	}
	
	/**
	* 获取 统计年度
	* @return String
	*/
	public String getAss_year() {
		return this.ass_year;
	}
	/**
	* 设置 统计月份
	* @param value 
	*/
	public void setAss_month(String value) {
		this.ass_month = value;
	}
	
	/**
	* 获取 统计月份
	* @return String
	*/
	public String getAss_month() {
		return this.ass_month;
	}
	/**
	* 设置 计划ID
	* @param value 
	*/
	public void setPlan_id(Long value) {
		this.plan_id = value;
	}
	
	/**
	* 获取 计划ID
	* @return Long
	*/
	public Long getPlan_id() {
		return this.plan_id;
	}
	/**
	* 设置 资产性质
	* @param value 
	*/
	public void setAss_nature(String value) {
		this.ass_nature = value;
	}
	
	/**
	* 获取 资产性质
	* @return String
	*/
	public String getAss_nature() {
		return this.ass_nature;
	}
	/**
	* 设置 执行人
	* @param value 
	*/
	public void setFact_exec_emp(Long value) {
		this.fact_exec_emp = value;
	}
	
	/**
	* 获取 执行人
	* @return Long
	*/
	public Long getFact_exec_emp() {
		return this.fact_exec_emp;
	}
	/**
	* 设置 计划执行日期
	* @param value 
	*/
	public void setPlan_exec_date(Date value) {
		this.plan_exec_date = value;
	}
	
	/**
	* 获取 计划执行日期
	* @return Date
	*/
	public Date getPlan_exec_date() {
		return this.plan_exec_date;
	}
	/**
	* 设置 实际执行日期
	* @param value 
	*/
	public void setFact_exec_date(Date value) {
		this.fact_exec_date = value;
	}
	
	/**
	* 获取 实际执行日期
	* @return Date
	*/
	public Date getFact_exec_date() {
		return this.fact_exec_date;
	}
	/**
	* 设置 保养工时
	* @param value 
	*/
	public void setMaintain_hours(Integer value) {
		this.maintain_hours = value;
	}
	
	/**
	* 获取 保养工时
	* @return Integer
	*/
	public Integer getMaintain_hours() {
		return this.maintain_hours;
	}
	/**
	* 设置 保养费用
	* @param value 
	*/
	public void setMaintain_money(Double value) {
		this.maintain_money = value;
	}
	
	/**
	* 获取 保养费用
	* @return Double
	*/
	public Double getMaintain_money() {
		return this.maintain_money;
	}
	/**
	* 设置 保养说明
	* @param value 
	*/
	public void setMaintain_desc(String value) {
		this.maintain_desc = value;
	}
	
	/**
	* 获取 保养说明
	* @return String
	*/
	public String getMaintain_desc() {
		return this.maintain_desc;
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
	* 设置 保养部门ID
	* @param value 
	*/
	public void setMaintain_dept_id(String value) {
		this.maintain_dept_id = value;
	}
	
	/**
	* 获取 保养部门ID
	* @return String
	*/
	public String getMaintain_dept_id() {
		return this.maintain_dept_id;
	}
	/**
	* 设置 保养部门NO
	* @param value 
	*/
	public void setMaintain_dept_no(String value) {
		this.maintain_dept_no = value;
	}
	
	/**
	* 获取 保养部门NO
	* @return String
	*/
	public String getMaintain_dept_no() {
		return this.maintain_dept_no;
	}
	/**
	* 设置 制单人
	* @param value 
	*/
	public void setCreate_emp(Long value) {
		this.create_emp = value;
	}
	
	/**
	* 获取 制单人
	* @return Long
	*/
	public Long getCreate_emp() {
		return this.create_emp;
	}
	/**
	* 设置 制单时间
	* @param value 
	*/
	public void setCreate_date(Date value) {
		this.create_date = value;
	}
	
	/**
	* 获取 制单时间
	* @return Date
	*/
	public Date getCreate_date() {
		return this.create_date;
	}
	/**
	* 设置 审核时间
	* @param value 
	*/
	public void setAudit_date(Date value) {
		this.audit_date = value;
	}
	
	/**
	* 获取 审核时间
	* @return Date
	*/
	public Date getAudit_date() {
		return this.audit_date;
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

	public String getPlan_no() {
		return plan_no;
	}

	public void setPlan_no(String plan_no) {
		this.plan_no = plan_no;
	}

	public String getPlan_name() {
		return plan_name;
	}

	public void setPlan_name(String plan_name) {
		this.plan_name = plan_name;
	}

	public String getNaturs_name() {
		return naturs_name;
	}

	public void setNaturs_name(String naturs_name) {
		this.naturs_name = naturs_name;
	}

	public String getFact_exec_emp_name() {
		return fact_exec_emp_name;
	}

	public void setFact_exec_emp_name(String fact_exec_emp_name) {
		this.fact_exec_emp_name = fact_exec_emp_name;
	}

	

	public String getMaintain_dept_code() {
		return maintain_dept_code;
	}

	public void setMaintain_dept_code(String maintain_dept_code) {
		this.maintain_dept_code = maintain_dept_code;
	}

	public String getMaintain_dept_name() {
		return maintain_dept_name;
	}

	public void setMaintain_dept_name(String maintain_dept_name) {
		this.maintain_dept_name = maintain_dept_name;
	}

	public String getCreate_emp_name() {
		return create_emp_name;
	}

	public void setCreate_emp_name(String create_emp_name) {
		this.create_emp_name = create_emp_name;
	}

	public String getAudit_emp_name() {
		return audit_emp_name;
	}

	public void setAudit_emp_name(String audit_emp_name) {
		this.audit_emp_name = audit_emp_name;
	}
	
	
}