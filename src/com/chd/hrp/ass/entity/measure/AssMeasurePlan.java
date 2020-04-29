/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.measure;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 051204 检查计量计划
 * @Table:
 * ASS_MEASURE_PLAN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AssMeasurePlan implements Serializable {

	
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
	 * 计量计划ID
	 */
	private Long plan_id;
	
	/**
	 * 计量计划编号
	 */
	private String plan_no;
	
	/**
	 * 计量计划名称
	 */
	private String plan_name;
	
	/**
	 * 计量计划年份
	 */
	private String plan_year;
	
	/**
	 * 资产性质
	 */
	private String ass_nature;
	private String naturs_name;
	
	/**
	 * 制单人
	 */
	private String create_emp;
	private String create_emp_name;
	
	/**
	 * 制单日期
	 */
	private Date create_date;
	
	/**
	 * 审核人
	 */
	private String audit_emp;
	private String audit_emp_name;
	
	/**
	 * 审核日期
	 */
	private Date audit_date;
	
	/**
	 * 状态
	 */
	private Integer state;
	private Integer measure_cycle;
	
	/**
	 * 是否内部检测（0/1）
	 */
	private Integer is_inner;
	
	/**
	 * 是否内部检测 (是/否 )
	 */
	private String is_inner_name;
	
	/**
	 * 外部计量单位
	 */
	private String outer_measure_org;
	
	public Integer getMeasure_cycle() {
		return measure_cycle;
	}

	public void setMeasure_cycle(Integer measure_cycle) {
		this.measure_cycle = measure_cycle;
	}

	private Date queryDate;
	public Date getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(Date queryDate) {
		this.queryDate = queryDate;
	}

	private String state_name;
	public String getState_name() {
		return state_name;
	}

	public void setState_name(String state_name) {
		this.state_name = state_name;
	}

	/**
	 * 检查方式
	 */
	private String check_way;
	
	/**
	 * 备注
	 */
	private String note;
	

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
	* 设置 计量计划ID
	* @param value 
	*/
	public void setPlan_id(Long value) {
		this.plan_id = value;
	}
	
	/**
	* 获取 计量计划ID
	* @return Long
	*/
	public Long getPlan_id() {
		return this.plan_id;
	}
	/**
	* 设置 计量计划编号
	* @param value 
	*/
	public void setPlan_no(String value) {
		this.plan_no = value;
	}
	
	/**
	* 获取 计量计划编号
	* @return String
	*/
	public String getPlan_no() {
		return this.plan_no;
	}
	/**
	* 设置 计量计划名称
	* @param value 
	*/
	public void setPlan_name(String value) {
		this.plan_name = value;
	}
	
	/**
	* 获取 计量计划名称
	* @return String
	*/
	public String getPlan_name() {
		return this.plan_name;
	}
	/**
	* 设置 计量计划年份
	* @param value 
	*/
	public void setPlan_year(String value) {
		this.plan_year = value;
	}
	
	/**
	* 获取 计量计划年份
	* @return String
	*/
	public String getPlan_year() {
		return this.plan_year;
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
	* 设置 制单人
	* @param value 
	*/
	public void setCreate_emp(String value) {
		this.create_emp = value;
	}
	
	/**
	* 获取 制单人
	* @return String
	*/
	public String getCreate_emp() {
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
	public void setAudit_emp(String value) {
		this.audit_emp = value;
	}
	
	/**
	* 获取 审核人
	* @return String
	*/
	public String getAudit_emp() {
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
	* 设置 检查方式
	* @param value 
	*/
	public void setCheck_way(String value) {
		this.check_way = value;
	}
	
	/**
	* 获取 检查方式
	* @return String
	*/
	public String getCheck_way() {
		return this.check_way;
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

	public String getNaturs_name() {
		return naturs_name;
	}

	public void setNaturs_name(String naturs_name) {
		this.naturs_name = naturs_name;
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

	public Integer getIs_inner() {
		return is_inner;
	}

	public void setIs_inner(Integer is_inner) {
		this.is_inner = is_inner;
	}
	
	public String getIs_inner_name() {
		return is_inner_name;
	}

	public void setIs_inner_name(String is_inner_name) {
		this.is_inner_name = is_inner_name;
	}

	public String getOuter_measure_org() {
		return outer_measure_org;
	}

	public void setOuter_measure_org(String outer_measure_org) {
		this.outer_measure_org = outer_measure_org;
	}
	
	
	
}