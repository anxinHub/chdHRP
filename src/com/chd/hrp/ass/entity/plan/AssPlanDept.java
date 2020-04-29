/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.plan;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 050301 购置计划单
 * @Table:
 * ASS_PLAN_DEPT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AssPlanDept implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 集体ID
	 */
	private Long group_id;
	
	/**
	 * 医院ID
	 */
	private Long hos_id;
	
	private String hos_name;
	
	
	/**
	 * 账套编码
	 */
	private String copy_code;
	
	/**
	 * 计划ID
	 */
	private Long plan_id;
	
	/**
	 * 计划号
	 */
	private String plan_no;
	
	/**
	 * 购置年度
	 */
	private String plan_year;
	
	/**
	 * 编制科室ID
	 */
	private Long dept_id;
	
	/**
	 * 编制科室NO
	 */
	private Long dept_no;
	private String dept_code;
	private String dept_name;
	
	/**
	 * 计划金额
	 */
	private Double plan_money;
	
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
	private String check_emp;
	private String check_emp_name;
	/**
	 * 审核日期
	 */
	private Date check_date;
	
	/**
	 * 审批日期
	 */
	private Date audit_date;
	
	/**
	 * 是否追加计划
	 */
	private Integer is_add;
	
	/**
	 * 状态
	 */
	private Integer state;
	
	/**
	 * 采购方式
	 */
	private String buy_code;
	
	/**
	 * 备注
	 */
	private String note;
	
	/**
	 * 集团计划
	 */
	private Integer is_group;
	
	
	private Integer is_import;
	/**
	 * 项目ID
	 */
private Long proj_id;
	/**
	 * 项目变更ID
	 */
	private Long proj_no;

	public Integer getIs_import() {
		return is_import;
	}

	public void setIs_import(Integer is_import) {
		this.is_import = is_import;
	}

/**
	 * 导入验证信息
	 */
	private String error_type;
	
	
	public String getHos_name() {
		return hos_name;
	}
	
	public void setHos_name(String hos_name) {
		this.hos_name = hos_name;
	}

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
	* 设置 计划号
	* @param value 
	*/
	public void setPlan_no(String value) {
		this.plan_no = value;
	}
	
	/**
	* 获取 计划号
	* @return String
	*/
	public String getPlan_no() {
		return this.plan_no;
	}
	/**
	* 设置 购置年度
	* @param value 
	*/
	public void setPlan_year(String value) {
		this.plan_year = value;
	}
	
	/**
	* 获取 购置年度
	* @return String
	*/
	public String getPlan_year() {
		return this.plan_year;
	}
	/**
	* 设置 编制科室ID
	* @param value 
	*/
	public void setDept_id(Long value) {
		this.dept_id = value;
	}
	
	/**
	* 获取 编制科室ID
	* @return Long
	*/
	public Long getDept_id() {
		return this.dept_id;
	}
	/**
	* 设置 编制科室NO
	* @param value 
	*/
	public void setDept_no(Long value) {
		this.dept_no = value;
	}
	
	/**
	* 获取 编制科室NO
	* @return Long
	*/
	public Long getDept_no() {
		return this.dept_no;
	}
	/**
	* 设置 计划金额
	* @param value 
	*/
	public void setPlan_money(Double value) {
		this.plan_money = value;
	}
	
	/**
	* 获取 计划金额
	* @return Double
	*/
	public Double getPlan_money() {
		return this.plan_money;
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
	public void setCheck_emp(String value) {
		this.check_emp = value;
	}
	
	/**
	* 获取 审核人
	* @return String
	*/
	public String getCheck_emp() {
		return this.check_emp;
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
	* 设置 审批日期
	* @param value 
	*/
	public void setAudit_date(Date value) {
		this.audit_date = value;
	}
	
	/**
	* 获取 审批日期
	* @return Date
	*/
	public Date getAudit_date() {
		return this.audit_date;
	}
	/**
	* 设置 是否追加计划
	* @param value 
	*/
	public void setIs_add(Integer value) {
		this.is_add = value;
	}
	
	/**
	* 获取 是否追加计划
	* @return Integer
	*/
	public Integer getIs_add() {
		return this.is_add;
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
	* 设置 采购方式
	* @param value 
	*/
	public void setBuy_code(String value) {
		this.buy_code = value;
	}
	
	/**
	* 获取 采购方式
	* @return String
	*/
	public String getBuy_code() {
		return this.buy_code;
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
	* 设置 集团计划
	* @param value 
	*/
	public void setIs_group(Integer value) {
		this.is_group = value;
	}
	
	/**
	* 获取 集团计划
	* @return Integer
	*/
	public Integer getIs_group() {
		return this.is_group;
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

	/**
	 * @return the dept_code
	 */
	public String getDept_code() {
		return dept_code;
	}

	/**
	 * @param dept_code the dept_code to set
	 */
	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}

	/**
	 * @return the dept_name
	 */
	public String getDept_name() {
		return dept_name;
	}

	/**
	 * @param dept_name the dept_name to set
	 */
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	/**
	 * @return the create_emp_name
	 */
	public String getCreate_emp_name() {
		return create_emp_name;
	}

	/**
	 * @param create_emp_name the create_emp_name to set
	 */
	public void setCreate_emp_name(String create_emp_name) {
		this.create_emp_name = create_emp_name;
	}

	/**
	 * @return the check_emp_name
	 */
	public String getCheck_emp_name() {
		return check_emp_name;
	}

	/**
	 * @param check_emp_name the check_emp_name to set
	 */
	public void setCheck_emp_name(String check_emp_name) {
		this.check_emp_name = check_emp_name;
	}

	public Long getProj_id() {
		return proj_id;
	}

	public void setProj_id(Long proj_id) {
		this.proj_id = proj_id;
	}

	public Long getProj_no() {
		return proj_no;
	}

	public void setProj_no(Long proj_no) {
		this.proj_no = proj_no;
	}
	
}