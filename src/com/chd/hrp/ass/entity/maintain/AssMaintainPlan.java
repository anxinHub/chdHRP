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
 * 051202 保养计划
 * @Table:
 * ASS_MAINTAIN_PLAN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AssMaintainPlan implements Serializable {

	
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
	 * 计划ID
	 */
	private Long plan_id;
	
	/**
	 * 计划编号
	 */
	private String plan_no;
	
	/**
	 * 计划名称
	 */
	private String plan_name;
	private String state_name;
	public String getState_name() {
		return state_name;
	}

	public void setState_name(String state_name) {
		this.state_name = state_name;
	}
	/**
	 * 统计年度
	 */
	private String ass_year;
	
	/**
	 * 统计月份
	 */
	private String ass_month;
	private Date queryDate;
	public Date getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(Date queryDate) {
		this.queryDate = queryDate;
	}
	/**
	 * 资产性质
	 */
	private String sharp_name;
	public String getSharp_name() {
		return sharp_name;
	}

	public void setSharp_name(String sharp_name) {
		this.sharp_name = sharp_name;
	}
	private String ass_nature;
	private String naturs_name ;
	
	/**
	 * 保养等级
	 */
	private Integer maintain_degree;
	
	/**
	 * 周期单位
	 */
	private Integer cycle_unit;
	
	/**
	 * 保养周期
	 */
	private Integer plan_cycle;
	
	/**
	 * 计划执行人
	 */
	private Long plan_exec_emp;
	private String plan_exec_emp_name;
	
	/**
	 * 保养说明
	 */
	private String maintain_desc;
	
	/**
	 * 制单人
	 */
	private Long create_emp;
	private String create_emp_name;
	
	/**
	 * 制单日期
	 */
	private Date create_date;
	
	/**
	 * 审核人
	 */
	private Long audit_emp;
	private String audit_emp_name;
	
	/**
	 * 审核日期
	 */
	private Date audit_date;
	
	/**
	 * 状态
	 */
	private Integer state;
	
	/**
	 * 上次保养日期
	 */
	private Date last_exec_date;
	
	/**
	 * 下次保养日期
	 */
	private Date next_exec_date;
	
	/**
	 * 计划开始日期
	 */
	private Date plan_start_date;
	
	/**
	 * 计划结束日期
	 */
	private Date plan_end_date;
	
	/**
	 * 终止人
	 */
	private Long stop_emp;
	private String stop_emp_name;
	
	/**
	 * 终止日期
	 */
	private Date stop_date;
	

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
	* 设置 计划编号
	* @param value 
	*/
	public void setPlan_no(String value) {
		this.plan_no = value;
	}
	
	/**
	* 获取 计划编号
	* @return String
	*/
	public String getPlan_no() {
		return this.plan_no;
	}
	/**
	* 设置 计划名称
	* @param value 
	*/
	public void setPlan_name(String value) {
		this.plan_name = value;
	}
	
	/**
	* 获取 计划名称
	* @return String
	*/
	public String getPlan_name() {
		return this.plan_name;
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
	* 设置 保养等级
	* @param value 
	*/
	public void setMaintain_degree(Integer value) {
		this.maintain_degree = value;
	}
	
	/**
	* 获取 保养等级
	* @return Integer
	*/
	public Integer getMaintain_degree() {
		return this.maintain_degree;
	}
	/**
	* 设置 周期单位
	* @param value 
	*/
	public void setCycle_unit(Integer value) {
		this.cycle_unit = value;
	}
	
	/**
	* 获取 周期单位
	* @return Integer
	*/
	public Integer getCycle_unit() {
		return this.cycle_unit;
	}
	/**
	* 设置 保养周期
	* @param value 
	*/
	public void setPlan_cycle(Integer value) {
		this.plan_cycle = value;
	}
	
	/**
	* 获取 保养周期
	* @return Integer
	*/
	public Integer getPlan_cycle() {
		return this.plan_cycle;
	}
	/**
	* 设置 计划执行人
	* @param value 
	*/
	public void setPlan_exec_emp(Long value) {
		this.plan_exec_emp = value;
	}
	
	/**
	* 获取 计划执行人
	* @return Long
	*/
	public Long getPlan_exec_emp() {
		return this.plan_exec_emp;
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
	* 设置 制单日期
	* @param value 
	*/
	public void setCreate_date(Date value) {
		this.create_date = value;
	}
	
	/**
	* 获取 制单日期
	* @return Date
	*/
	public Date getCreate_date() {
		return this.create_date;
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
	/**
	* 设置 上次保养日期
	* @param value 
	*/
	public void setLast_exec_date(Date value) {
		this.last_exec_date = value;
	}
	
	/**
	* 获取 上次保养日期
	* @return Date
	*/
	public Date getLast_exec_date() {
		return this.last_exec_date;
	}
	/**
	* 设置 下次保养日期
	* @param value 
	*/
	public void setNext_exec_date(Date value) {
		this.next_exec_date = value;
	}
	
	/**
	* 获取 下次保养日期
	* @return Date
	*/
	public Date getNext_exec_date() {
		return this.next_exec_date;
	}
	/**
	* 设置 计划开始日期
	* @param value 
	*/
	public void setPlan_start_date(Date value) {
		this.plan_start_date = value;
	}
	
	/**
	* 获取 计划开始日期
	* @return Date
	*/
	public Date getPlan_start_date() {
		return this.plan_start_date;
	}
	/**
	* 设置 计划结束日期
	* @param value 
	*/
	public void setPlan_end_date(Date value) {
		this.plan_end_date = value;
	}
	
	/**
	* 获取 计划结束日期
	* @return Date
	*/
	public Date getPlan_end_date() {
		return this.plan_end_date;
	}
	/**
	* 设置 终止人
	* @param value 
	*/
	public void setStop_emp(Long value) {
		this.stop_emp = value;
	}
	
	/**
	* 获取 终止人
	* @return Long
	*/
	public Long getStop_emp() {
		return this.stop_emp;
	}
	/**
	* 设置 终止日期
	* @param value 
	*/
	public void setStop_date(Date value) {
		this.stop_date = value;
	}
	
	/**
	* 获取 终止日期
	* @return Date
	*/
	public Date getStop_date() {
		return this.stop_date;
	}
	
	


	public String getPlan_exec_emp_name() {
		return plan_exec_emp_name;
	}

	public void setPlan_exec_emp_name(String plan_exec_emp_name) {
		this.plan_exec_emp_name = plan_exec_emp_name;
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

	public String getStop_emp_name() {
		return stop_emp_name;
	}

	public void setStop_emp_name(String stop_emp_name) {
		this.stop_emp_name = stop_emp_name;
	}

	
	public String getNaturs_name() {
		return naturs_name;
	}

	public void setNaturs_name(String naturs_name) {
		this.naturs_name = naturs_name;
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