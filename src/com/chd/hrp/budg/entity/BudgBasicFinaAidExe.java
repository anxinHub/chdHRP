package com.chd.hrp.budg.entity;

import java.io.Serializable;

/**
 * @Description:
 * 财政基本补助预算执行 
 * @Table:
 * BUDG_BASIC_FINA_AID_EXE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
public class BudgBasicFinaAidExe implements Serializable{

	
	private static final long serialVersionUID = 1479619891426464332L;
	
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
	 * 年度
	 */
	private String budg_year;
	
	/**
	 * 月份
	 */
	private String month;
	
	/**
	 * 科目编码
	 */
	private String subj_code;
	
	/**
	 * 执行数据
	 */
	private Double exe_value;
	
	/**
	 * 导入验证信息
	 */
	private String error_type;

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

	public String getBudg_year() {
		return budg_year;
	}

	public void setBudg_year(String budg_year) {
		this.budg_year = budg_year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getSubj_code() {
		return subj_code;
	}

	public void setSubj_code(String subj_code) {
		this.subj_code = subj_code;
	}

	public Double getExe_value() {
		return exe_value;
	}

	public void setExe_value(Double exe_value) {
		this.exe_value = exe_value;
	}

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	
}
