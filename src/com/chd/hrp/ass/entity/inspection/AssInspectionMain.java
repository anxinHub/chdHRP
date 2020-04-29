package com.chd.hrp.ass.entity.inspection;

import java.io.Serializable;
import java.util.*;

public class AssInspectionMain implements Serializable {
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
	 * 巡检ID
	 */
	private Long ins_id;
	/**
	 * 巡检no
	 */
    private String ins_no;
    /**
	 * 巡检名称
	 */
    private String ins_name;
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
	private String naturs_name;
	/**
	 * 安装科室ID
	 */
	private Long dept_id;
	
	/**
	 * 安装科室NO
	 */
	private Long dept_no;
	private String dept_name;
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
	 * 备注
	 */
	private String note;
	
  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	private String dept_code;
	
	public String getDept_code() {
		return dept_code;
	}

	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}

	public Long getGroup_id() {
		return group_id;
	}

	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}

	public Long getHos_id() {
		return hos_id;
	}

	public void setHos_id(Long hos_id) {
		this.hos_id = hos_id;
	}

	public String getCopy_code() {
		return copy_code;
	}

	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}

	public Long getIns_id() {
		return ins_id;
	}

	public void setIns_id(Long ins_id) {
		this.ins_id = ins_id;
	}

	public String getIns_no() {
		return ins_no;
	}

	public void setIns_no(String ins_no) {
		this.ins_no = ins_no;
	}

	public String getIns_name() {
		return ins_name;
	}

	public void setIns_name(String ins_name) {
		this.ins_name = ins_name;
	}

	public String getAss_year() {
		return ass_year;
	}

	public void setAss_year(String ass_year) {
		this.ass_year = ass_year;
	}

	public String getAss_month() {
		return ass_month;
	}

	public void setAss_month(String ass_month) {
		this.ass_month = ass_month;
	}

	public String getAss_nature() {
		return ass_nature;
	}

	public void setAss_nature(String ass_nature) {
		this.ass_nature = ass_nature;
	}

	public Long getDept_id() {
		return dept_id;
	}

	public void setDept_id(Long dept_id) {
		this.dept_id = dept_id;
	}

	public Long getDept_no() {
		return dept_no;
	}

	public void setDept_no(Long dept_no) {
		this.dept_no = dept_no;
	}

	public Long getCreate_emp() {
		return create_emp;
	}

	public void setCreate_emp(Long create_emp) {
		this.create_emp = create_emp;
	}

	public String getCreate_emp_name() {
		return create_emp_name;
	}

	public void setCreate_emp_name(String create_emp_name) {
		this.create_emp_name = create_emp_name;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public Long getAudit_emp() {
		return audit_emp;
	}

	public void setAudit_emp(Long audit_emp) {
		this.audit_emp = audit_emp;
	}

	public String getAudit_emp_name() {
		return audit_emp_name;
	}

	public void setAudit_emp_name(String audit_emp_name) {
		this.audit_emp_name = audit_emp_name;
	}

	public Date getAudit_date() {
		return audit_date;
	}

	public void setAudit_date(Date audit_date) {
		this.audit_date = audit_date;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}

	public String getNaturs_name() {
		return naturs_name;
	}

	public void setNaturs_name(String naturs_name) {
		this.naturs_name = naturs_name;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	
}
