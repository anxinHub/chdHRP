package com.chd.hrp.budg.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:
 * 财政基本补助预算 
 * @Table:
 * BUDG_BASIC_FINA_AID
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 
public class BudgBasicFinaAid implements Serializable{

	private static final long serialVersionUID = 3114643370633305115L;
	
	
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
	 * 科目编码
	 */
	private String subj_code;
	
	/**
	 * 预算值
	 */
	private Double budg_value;
	
	/**
	 * 说明
	 */
	private String remark;
	
	/**
	 * 审核人
	 */
	private Long checker;
	
	/**
	 * 审核日期
	 */
	private Date checke_data;
	
	/**
	 * 状态
	 */
	private String bc_state;
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

	public String getSubj_code() {
		return subj_code;
	}

	public void setSubj_code(String subj_code) {
		this.subj_code = subj_code;
	}

	public Double getBudg_value() {
		return budg_value;
	}

	public void setBudg_value(Double budg_value) {
		this.budg_value = budg_value;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Long getChecker() {
		return checker;
	}

	public void setChecker(Long checker) {
		this.checker = checker;
	}

	public Date getChecke_data() {
		return checke_data;
	}

	public void setChecke_data(Date checke_data) {
		this.checke_data = checke_data;
	}

	public String getBc_state() {
		return bc_state;
	}

	public void setBc_state(String bc_state) {
		this.bc_state = bc_state;
	}

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	
}
