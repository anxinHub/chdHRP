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
 * 050701 资产盘盈入库主表(专用设备)
 * @Table:
 * ASS_CHK_IN_MAIN_General
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class AssChkInMainOther implements Serializable {

	
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
	 * 业务单号
	 */
	private String ass_in_no;
	
	/**
	 * 仓库编码ID
	 */
	private Long store_id;
	
	/**
	 * 仓库编码NO
	 */
	private Long store_no;
	
	private String store_code;
	
	private String store_name;
	
	/**
	 * 领用科室ID
	 */
	private Long dept_id;
	
	/**
	 * 领用科室NO
	 */
	private Long dept_no;
	
	private String dept_code;
	
	private String dept_name;
	
	/**
	 * 用途
	 */
	private String ass_purpose;
	private String ass_purpose_name;
	
	/**
	 * 项目ID
	 */
	private Long proj_id;
	
	/**
	 * 项目变更ID
	 */
	private Long proj_no;
	
	private String proj_code;
	
	private String proj_name;
	
	/**
	 * 入库金额
	 */
	private Double in_money;
	
	/**
	 * 备注
	 */
	private String note;
	
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
	 * 入库日期
	 */
	private Date in_date;
	
	/**
	 * 确认人
	 */
	private Long confirm_emp;
	
	private String confirm_emp_name;
	
	/**
	 * 0:新建 1:审核 2:确认
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
	* 设置 业务单号
	* @param value 
	*/
	public void setAss_in_no(String value) {
		this.ass_in_no = value;
	}
	
	/**
	* 获取 业务单号
	* @return String
	*/
	public String getAss_in_no() {
		return this.ass_in_no;
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
	* 设置 领用科室ID
	* @param value 
	*/
	public void setDept_id(Long value) {
		this.dept_id = value;
	}
	
	/**
	* 获取 领用科室ID
	* @return Long
	*/
	public Long getDept_id() {
		return this.dept_id;
	}
	/**
	* 设置 领用科室NO
	* @param value 
	*/
	public void setDept_no(Long value) {
		this.dept_no = value;
	}
	
	/**
	* 获取 领用科室NO
	* @return Long
	*/
	public Long getDept_no() {
		return this.dept_no;
	}
	/**
	* 设置 用途
	* @param value 
	*/
	public void setAss_purpose(String value) {
		this.ass_purpose = value;
	}
	
	/**
	* 获取 用途
	* @return String
	*/
	public String getAss_purpose() {
		return this.ass_purpose;
	}
	/**
	* 设置 项目ID
	* @param value 
	*/
	public void setProj_id(Long value) {
		this.proj_id = value;
	}
	
	/**
	* 获取 项目ID
	* @return Long
	*/
	public Long getProj_id() {
		return this.proj_id;
	}
	/**
	* 设置 项目变更ID
	* @param value 
	*/
	public void setProj_no(Long value) {
		this.proj_no = value;
	}
	
	/**
	* 获取 项目变更ID
	* @return Long
	*/
	public Long getProj_no() {
		return this.proj_no;
	}
	/**
	* 设置 入库金额
	* @param value 
	*/
	public void setIn_money(Double value) {
		this.in_money = value;
	}
	
	/**
	* 获取 入库金额
	* @return Double
	*/
	public Double getIn_money() {
		return this.in_money;
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
	* 设置 入库日期
	* @param value 
	*/
	public void setIn_date(Date value) {
		this.in_date = value;
	}
	
	/**
	* 获取 入库日期
	* @return Date
	*/
	public Date getIn_date() {
		return this.in_date;
	}
	/**
	* 设置 确认人
	* @param value 
	*/
	public void setConfirm_emp(Long value) {
		this.confirm_emp = value;
	}
	
	/**
	* 获取 确认人
	* @return Long
	*/
	public Long getConfirm_emp() {
		return this.confirm_emp;
	}
	/**
	* 设置 0:新建 1:审核 2:确认
	* @param value 
	*/
	public void setState(Integer value) {
		this.state = value;
	}
	
	/**
	* 获取 0:新建 1:审核 2:确认
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

	public String getStore_code() {
		return store_code;
	}

	public void setStore_code(String store_code) {
		this.store_code = store_code;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public String getDept_code() {
		return dept_code;
	}

	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getProj_code() {
		return proj_code;
	}

	public void setProj_code(String proj_code) {
		this.proj_code = proj_code;
	}

	public String getProj_name() {
		return proj_name;
	}

	public void setProj_name(String proj_name) {
		this.proj_name = proj_name;
	}

	public String getCreate_emp_name() {
		return create_emp_name;
	}

	public void setCreate_emp_name(String create_emp_name) {
		this.create_emp_name = create_emp_name;
	}

	public String getConfirm_emp_name() {
		return confirm_emp_name;
	}

	public void setConfirm_emp_name(String confirm_emp_name) {
		this.confirm_emp_name = confirm_emp_name;
	}

	public String getState_name() {
		return state_name;
	}

	public void setState_name(String state_name) {
		this.state_name = state_name;
	}

	public String getAss_purpose_name() {
		return ass_purpose_name;
	}

	public void setAss_purpose_name(String ass_purpose_name) {
		this.ass_purpose_name = ass_purpose_name;
	}
	
	
}