/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.assremould.other;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * tabledesc
 * @Table:
 * ASS_REMOULD_A_Other
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class AssRemouldAOther implements Serializable {

	
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
	 * 改造申请单号
	 */
	private String apply_no;
	
	/**
	 * 业务类型
	 */
	private String bus_type_code;
	private String  bus_type_name;
	
	/**
	 * 供应商ID
	 */
	private Long ven_id;
	
	/**
	 * 供应商变更ID
	 */
	private Long ven_no;
	
	private String ven_code;
	
	private String ven_name;
	
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
	 * 改造日期
	 */
	private Date change_date;
	
	/**
	 * 状态
	 */
	 private Integer state;
	private String state_name;
	
	/**
	 * 摘要
	 */
	private String note;
	

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
	* 设置 改造申请单号
	* @param value 
	*/
	public void setApply_no(String value) {
		this.apply_no = value;
	}
	
	/**
	* 获取 改造申请单号
	* @return String
	*/
	public String getApply_no() {
		return this.apply_no;
	}
	/**
	* 设置 业务类型
	* @param value 
	*/
	public void setBus_type_code(String value) {
		this.bus_type_code = value;
	}
	
	/**
	* 获取 业务类型
	* @return String
	*/
	public String getBus_type_code() {
		return this.bus_type_code;
	}
	/**
	* 设置 供应商ID
	* @param value 
	*/
	public void setVen_id(Long value) {
		this.ven_id = value;
	}
	
	/**
	* 获取 供应商ID
	* @return Long
	*/
	public Long getVen_id() {
		return this.ven_id;
	}
	/**
	* 设置 供应商变更ID
	* @param value 
	*/
	public void setVen_no(Long value) {
		this.ven_no = value;
	}
	
	/**
	* 获取 供应商变更ID
	* @return Long
	*/
	public Long getVen_no() {
		return this.ven_no;
	}
	public String getVen_code() {
		return ven_code;
	}

	public void setVen_code(String ven_code) {
		this.ven_code = ven_code;
	}

	public String getVen_name() {
		return ven_name;
	}

	public void setVen_name(String ven_name) {
		this.ven_name = ven_name;
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
	* 设置 改造日期
	* @param value 
	*/
	public void setChange_date(Date value) {
		this.change_date = value;
	}
	
	/**
	* 获取 改造日期
	* @return Date
	*/
	public Date getChange_date() {
		return this.change_date;
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
	* 设置 摘要
	* @param value 
	*/
	public void setNote(String value) {
		this.note = value;
	}
	
	/**
	* 获取 摘要
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

	public String getBus_type_name() {
		return bus_type_name;
	}

	public void setBus_type_name(String bus_type_name) {
		this.bus_type_name = bus_type_name;
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