/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.repair;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 资产维修
 * @Table:
 * ASS_REPAIR_APPLY
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class AssRepairApply implements Serializable {

	
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
	 * 申请单号
	 */
	private String apply_no;
	
	/**
	 * 统计年度
	 */
	private String ass_year;
	
	/**
	 * 统计月份
	 */
	private String ass_month;
	
	/**
	 * 资产性质
	 */
	private String ass_nature;
	
	/**
	 * 维修部门ID
	 */
	private Long repair_dept_id;
	private String dept_name ;
	/**
	 * 维修部门NO
	 */
	private Long repair_dept_no;
	
	/**
	 * 资产名称
	 */
	private String ass_name;
	
	/**
	 * 申请人
	 */
	private Long apply_emp;
	private String apply_name;
	/**
	 * 申请日期
	 */
	private Date apply_date;
	
	/**
	 * 制单人
	 */
	private Long create_emp;
	private String create_name;
	/**
	 * 制单日期
	 */
	private Date create_date;
	
	/**
	 * 审核人
	 */
	private Long audit_emp;
	private String audit_name;
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
	 * 紧急程度
	 */
	private Integer sharp_degree;
	private String sharp_name;
	/**
	 * 报修人电话
	 */
	private String rep_phone;
	
	/**
	 * 维修班组
	 */
	private String rep_team_code;
	private String rep_team_name;
	/**
	 * 故障内容
	 */
	private String note;
	
	/**
	 * 资产卡片
	 */
	private String ass_card_no;
	

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
	* 设置 申请单号
	* @param value 
	*/
	public void setApply_no(String value) {
		this.apply_no = value;
	}
	
	/**
	* 获取 申请单号
	* @return String
	*/
	public String getApply_no() {
		return this.apply_no;
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
	* 设置 维修部门ID
	* @param value 
	*/
	public void setRepair_dept_id(Long value) {
		this.repair_dept_id = value;
	}
	
	/**
	* 获取 维修部门ID
	* @return Long
	*/
	public Long getRepair_dept_id() {
		return this.repair_dept_id;
	}
	/**
	* 设置 维修部门NO
	* @param value 
	*/
	public void setRepair_dept_no(Long value) {
		this.repair_dept_no = value;
	}
	
	/**
	* 获取 维修部门NO
	* @return Long
	*/
	public Long getRepair_dept_no() {
		return this.repair_dept_no;
	}
	/**
	* 设置 资产名称
	* @param value 
	*/
	public void setAss_name(String value) {
		this.ass_name = value;
	}
	
	/**
	* 获取 资产名称
	* @return String
	*/
	public String getAss_name() {
		return this.ass_name;
	}
	/**
	* 设置 申请人
	* @param value 
	*/
	public void setApply_emp(Long value) {
		this.apply_emp = value;
	}
	
	/**
	* 获取 申请人
	* @return Long
	*/
	public Long getApply_emp() {
		return this.apply_emp;
	}
	/**
	* 设置 申请日期
	* @param value 
	*/
	public void setApply_date(Date value) {
		this.apply_date = value;
	}
	
	/**
	* 获取 申请日期
	* @return Date
	*/
	public Date getApply_date() {
		return this.apply_date;
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
	* 设置 紧急程度
	* @param value 
	*/
	public void setSharp_degree(Integer value) {
		this.sharp_degree = value;
	}
	
	/**
	* 获取 紧急程度
	* @return Integer
	*/
	public Integer getSharp_degree() {
		return this.sharp_degree;
	}
	/**
	* 设置 报修人电话
	* @param value 
	*/
	public void setRep_phone(String value) {
		this.rep_phone = value;
	}
	
	/**
	* 获取 报修人电话
	* @return String
	*/
	public String getRep_phone() {
		return this.rep_phone;
	}
	/**
	* 设置 维修班组
	* @param value 
	*/
	public void setRep_team_code(String value) {
		this.rep_team_code = value;
	}
	
	/**
	* 获取 维修班组
	* @return String
	*/
	public String getRep_team_code() {
		return this.rep_team_code;
	}
	/**
	* 设置 故障内容
	* @param value 
	*/
	public void setNote(String value) {
		this.note = value;
	}
	
	/**
	* 获取 故障内容
	* @return String
	*/
	public String getNote() {
		return this.note;
	}
	/**
	* 设置 资产卡片
	* @param value 
	*/
	public void setAss_card_no(String value) {
		this.ass_card_no = value;
	}
	
	/**
	* 获取 资产卡片
	* @return String
	*/
	public String getAss_card_no() {
		return this.ass_card_no;
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

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}


	public String getCreate_name() {
		return create_name;
	}

	public void setCreate_name(String create_name) {
		this.create_name = create_name;
	}

	public String getAudit_name() {
		return audit_name;
	}

	public void setAudit_name(String audit_name) {
		this.audit_name = audit_name;
	}

	public String getState_name() {
		return state_name;
	}

	public void setState_name(String state_name) {
		this.state_name = state_name;
	}

	public String getSharp_name() {
		return sharp_name;
	}

	public void setSharp_name(String sharp_name) {
		this.sharp_name = sharp_name;
	}

	public String getRep_team_name() {
		return rep_team_name;
	}

	public void setRep_team_name(String rep_team_name) {
		this.rep_team_name = rep_team_name;
	}

	public String getApply_name() {
		return apply_name;
	}

	public void setApply_name(String apply_name) {
		this.apply_name = apply_name;
	}
}