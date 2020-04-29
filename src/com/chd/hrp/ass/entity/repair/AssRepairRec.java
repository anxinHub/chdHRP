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
 * tabledesc
 * @Table:
 * ASS_REPAIR_REC
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class AssRepairRec implements Serializable {

	
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
	 * 统计年度
	 */
	private String ass_year;
	private String dept_name;
	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	/**
	 * 统计月份
	 */
	private String ass_month;
	
	/**
	 * 维修记录号
	 */
	private String repair_rec_no;
	
	/**
	 * 维修班组
	 */
	private String rep_team_code;
	
	/**
	 * 维修部门ID
	 */
	private Long fixed_dept_id;
	
	/**
	 * 维修部门NO
	 */
	private Long fixed_dept_no;
	
	/**
	 * 资产性质
	 */
	private String ass_nature;
	
	/**
	 * 资产名称
	 */
	private String ass_name;
	private String audit_name;
	public String getAudit_name() {
		return audit_name;
	}

	public void setAudit_name(String audit_name) {
		this.audit_name = audit_name;
	}

	public String getCreate_name() {
		return create_name;
	}

	public void setCreate_name(String create_name) {
		this.create_name = create_name;
	}
	private String create_name;
	private String state_name;
	public String getState_name() {
		return state_name;
	}

	public void setState_name(String state_name) {
		this.state_name = state_name;
	}
	/**
	 * 资产卡片号
	 */
	private String ass_card_no;
	
	/**
	 * 申请单号
	 */
	private String apply_no;
	
	/**
	 * 是否内部维修
	 */
	private Integer is_inner;
	
	/**
	 * 故障级别
	 */
	private Integer trouble_level;
	
	/**
	 * 维修级别
	 */
	private Integer repair_level;
	
	/**
	 * 维修日期
	 */
	private Date repair_date;
	
	/**
	 * 维修工程师
	 */
	private String repair_engr;
	
	/**
	 * 维修工时
	 */
	private Integer repair_hours;
	
	/**
	 * 是否合同
	 */
	private Integer is_contract;
	
	/**
	 * 合同号
	 */
	private String contract_no;
	
	/**
	 * 维修费用
	 */
	private Double repair_money;
	
	/**
	 * 其他费用
	 */
	private Double other_money;
	
	/**
	 * 状态
	 */
	private Integer state;
	
	/**
	 * 制单人
	 */
	private Long create_emp;
	
	/**
	 * 制单日期
	 */
	private Date create_date;
	
	/**
	 * 审核人
	 */
	private Long audit_emp;
	
	/**
	 * 检修说明
	 */
	private String repair_desc;
	

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
	* 设置 维修记录号
	* @param value 
	*/
	public void setRepair_rec_no(String value) {
		this.repair_rec_no = value;
	}
	
	/**
	* 获取 维修记录号
	* @return String
	*/
	public String getRepair_rec_no() {
		return this.repair_rec_no;
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
	* 设置 维修部门ID
	* @param value 
	*/
	public void setFixed_dept_id(Long value) {
		this.fixed_dept_id = value;
	}
	
	/**
	* 获取 维修部门ID
	* @return Long
	*/
	public Long getFixed_dept_id() {
		return this.fixed_dept_id;
	}
	/**
	* 设置 维修部门NO
	* @param value 
	*/
	public void setFixed_dept_no(Long value) {
		this.fixed_dept_no = value;
	}
	
	/**
	* 获取 维修部门NO
	* @return Long
	*/
	public Long getFixed_dept_no() {
		return this.fixed_dept_no;
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
	* 设置 资产卡片号
	* @param value 
	*/
	public void setAss_card_no(String value) {
		this.ass_card_no = value;
	}
	
	/**
	* 获取 资产卡片号
	* @return String
	*/
	public String getAss_card_no() {
		return this.ass_card_no;
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
	* 设置 是否内部维修
	* @param value 
	*/
	public void setIs_inner(Integer value) {
		this.is_inner = value;
	}
	
	/**
	* 获取 是否内部维修
	* @return Integer
	*/
	public Integer getIs_inner() {
		return this.is_inner;
	}
	/**
	* 设置 故障级别
	* @param value 
	*/
	public void setTrouble_level(Integer value) {
		this.trouble_level = value;
	}
	
	/**
	* 获取 故障级别
	* @return Integer
	*/
	public Integer getTrouble_level() {
		return this.trouble_level;
	}
	/**
	* 设置 维修级别
	* @param value 
	*/
	public void setRepair_level(Integer value) {
		this.repair_level = value;
	}
	
	/**
	* 获取 维修级别
	* @return Integer
	*/
	public Integer getRepair_level() {
		return this.repair_level;
	}
	/**
	* 设置 维修日期
	* @param value 
	*/
	public void setRepair_date(Date value) {
		this.repair_date = value;
	}
	
	/**
	* 获取 维修日期
	* @return Date
	*/
	public Date getRepair_date() {
		return this.repair_date;
	}
	/**
	* 设置 维修工程师
	* @param value 
	*/
	public void setRepair_engr(String value) {
		this.repair_engr = value;
	}
	
	/**
	* 获取 维修工程师
	* @return String
	*/
	public String getRepair_engr() {
		return this.repair_engr;
	}
	/**
	* 设置 维修工时
	* @param value 
	*/
	public void setRepair_hours(Integer value) {
		this.repair_hours = value;
	}
	
	/**
	* 获取 维修工时
	* @return Integer
	*/
	public Integer getRepair_hours() {
		return this.repair_hours;
	}
	/**
	* 设置 是否合同
	* @param value 
	*/
	public void setIs_contract(Integer value) {
		this.is_contract = value;
	}
	
	/**
	* 获取 是否合同
	* @return Integer
	*/
	public Integer getIs_contract() {
		return this.is_contract;
	}
	/**
	* 设置 合同号
	* @param value 
	*/
	public void setContract_no(String value) {
		this.contract_no = value;
	}
	
	/**
	* 获取 合同号
	* @return String
	*/
	public String getContract_no() {
		return this.contract_no;
	}
	/**
	* 设置 维修费用
	* @param value 
	*/
	public void setRepair_money(Double value) {
		this.repair_money = value;
	}
	
	/**
	* 获取 维修费用
	* @return Double
	*/
	public Double getRepair_money() {
		return this.repair_money;
	}
	/**
	* 设置 其他费用
	* @param value 
	*/
	public void setOther_money(Double value) {
		this.other_money = value;
	}
	
	/**
	* 获取 其他费用
	* @return Double
	*/
	public Double getOther_money() {
		return this.other_money;
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
	* 设置 检修说明
	* @param value 
	*/
	public void setRepair_desc(String value) {
		this.repair_desc = value;
	}
	
	/**
	* 获取 检修说明
	* @return String
	*/
	public String getRepair_desc() {
		return this.repair_desc;
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