/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.entity.assdisposal.house;

import java.io.Serializable;
import java.util.*;

/**
 * 
 * @Description: 051001资产处置记录(土地)
 * @Table: ASS_DISPOSAL_R_LAND
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

public class AssDisposalRecordHouse implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 3879721969423718898L;

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
	 * 处置单号
	 */
	private String dis_r_no;

	/**
	 * 11：报废 12：报损 21：出售 22：出让 23：转让 24：置换 31：无偿调拨 32：有偿调拨 33：捐赠
	 */
	private Long dispose_type;
	
	private String dispose_type_name;

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
	 * 审核人
	 */
	private Long audit_emp;
	
	private String audit_emp_name;

	/**
	 * 申报日期
	 */
	private Date apply_date;

	/**
	 * 状态
	 */
	private Integer state;
	
	private String state_name;

	/**
	 * 导入验证信息
	 */
	private String error_type;
    
	private String is_import;
	
	
	
	public String getIs_import() {
		return is_import;
	}

	public void setIs_import(String is_import) {
		this.is_import = is_import;
	}

	/**
	 * 设置 集团ID
	 * 
	 * @param value
	 */
	public void setGroup_id(Long value) {
		this.group_id = value;
	}

	/**
	 * 获取 集团ID
	 * 
	 * @return Long
	 */
	public Long getGroup_id() {
		return this.group_id;
	}

	/**
	 * 设置 医院ID
	 * 
	 * @param value
	 */
	public void setHos_id(Long value) {
		this.hos_id = value;
	}

	/**
	 * 获取 医院ID
	 * 
	 * @return Long
	 */
	public Long getHos_id() {
		return this.hos_id;
	}

	/**
	 * 设置 账套编码
	 * 
	 * @param value
	 */
	public void setCopy_code(String value) {
		this.copy_code = value;
	}

	/**
	 * 获取 账套编码
	 * 
	 * @return String
	 */
	public String getCopy_code() {
		return this.copy_code;
	}

	/**
	 * 设置 处置单号
	 * 
	 * @param value
	 */
	public void setDis_r_no(String value) {
		this.dis_r_no = value;
	}

	/**
	 * 获取 处置单号
	 * 
	 * @return String
	 */
	public String getDis_r_no() {
		return this.dis_r_no;
	}

	/**
	 * 设置 11：报废 12：报损 21：出售 22：出让 23：转让 24：置换 31：无偿调拨 32：有偿调拨 33：捐赠
	 * 
	 * @param value
	 */
	public void setDispose_type(Long value) {
		this.dispose_type = value;
	}

	/**
	 * 获取 11：报废 12：报损 21：出售 22：出让 23：转让 24：置换 31：无偿调拨 32：有偿调拨 33：捐赠
	 * 
	 * @return Long
	 */
	public Long getDispose_type() {
		return this.dispose_type;
	}

	/**
	 * 设置 备注
	 * 
	 * @param value
	 */
	public void setNote(String value) {
		this.note = value;
	}

	/**
	 * 获取 备注
	 * 
	 * @return String
	 */
	public String getNote() {
		return this.note;
	}

	/**
	 * 设置 制单人
	 * 
	 * @param value
	 */
	public void setCreate_emp(Long value) {
		this.create_emp = value;
	}

	/**
	 * 获取 制单人
	 * 
	 * @return Long
	 */
	public Long getCreate_emp() {
		return this.create_emp;
	}

	/**
	 * 设置 制单日期
	 * 
	 * @param value
	 */
	public void setCreate_date(Date value) {
		this.create_date = value;
	}

	/**
	 * 获取 制单日期
	 * 
	 * @return Date
	 */
	public Date getCreate_date() {
		return this.create_date;
	}

	/**
	 * 设置 审核人
	 * 
	 * @param value
	 */
	public void setAudit_emp(Long value) {
		this.audit_emp = value;
	}

	/**
	 * 获取 审核人
	 * 
	 * @return Long
	 */
	public Long getAudit_emp() {
		return this.audit_emp;
	}

	/**
	 * 设置 申报日期
	 * 
	 * @param value
	 */
	public void setApply_date(Date value) {
		this.apply_date = value;
	}

	/**
	 * 获取 申报日期
	 * 
	 * @return Date
	 */
	public Date getApply_date() {
		return this.apply_date;
	}

	/**
	 * 设置 状态
	 * 
	 * @param value
	 */
	public void setState(Integer value) {
		this.state = value;
	}

	/**
	 * 获取 状态
	 * 
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

	public String getDispose_type_name() {
		return dispose_type_name;
	}

	public void setDispose_type_name(String dispose_type_name) {
		this.dispose_type_name = dispose_type_name;
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

	public String getState_name() {
		return state_name;
	}

	public void setState_name(String state_name) {
		this.state_name = state_name;
	}
	
	
}