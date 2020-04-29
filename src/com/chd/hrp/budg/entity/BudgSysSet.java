/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.entity;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 业务预算编制模式（WORK_BUDG_EDIT_MODE）系统字典表01自上而下，02自下而上

 * @Table:
 * BUDG_SYS_SET
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgSysSet implements Serializable {

	
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
	 * 预算年度
	 */
	private String budg_year;
	
	/**
	 * 业务预算编制模式
	 */
	private String work_budg_mode;
	
	/**
	 * 收入预算编制模式
	 */
	private String income_budg_mode;
	
	/**
	 * 门诊业务量指标
	 */
	private String workload_index_out;
	
	/**
	 * 住院业务量指标
	 */
	private String workload_index_in;
	
	/**
	 * 检查业务量指标
	 */
	private String workload_index_check;
	
	/**
	 * 提取医疗风险基金预算科目
	 */
	private String risk_fund_subj;
	
	/**
	 * 医疗风险基金提取比例
	 */
	private String risk_fund_rate;
	

  public String getWorkload_index_out() {
		return workload_index_out;
	}

	public void setWorkload_index_out(String workload_index_out) {
		this.workload_index_out = workload_index_out;
	}

	public String getWorkload_index_in() {
		return workload_index_in;
	}

	public void setWorkload_index_in(String workload_index_in) {
		this.workload_index_in = workload_index_in;
	}

	public String getWorkload_index_check() {
		return workload_index_check;
	}

	public void setWorkload_index_check(String workload_index_check) {
		this.workload_index_check = workload_index_check;
	}

	public String getRisk_fund_subj() {
		return risk_fund_subj;
	}

	public void setRisk_fund_subj(String risk_fund_subj) {
		this.risk_fund_subj = risk_fund_subj;
	}

	public String getRisk_fund_rate() {
		return risk_fund_rate;
	}

	public void setRisk_fund_rate(String risk_fund_rate) {
		this.risk_fund_rate = risk_fund_rate;
	}

public String getIncome_budg_mode() {
		return income_budg_mode;
	}

	public void setIncome_budg_mode(String income_budg_mode) {
		this.income_budg_mode = income_budg_mode;
	}
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
	* 设置 预算年度
	* @param value 
	*/
	public void setBudg_year(String value) {
		this.budg_year = value;
	}
	
	/**
	* 获取 预算年度
	* @return String
	*/
	public String getBudg_year() {
		return this.budg_year;
	}
	/**
	* 设置 业务预算编制模式
	* @param value 
	*/
	public void setWork_budg_mode(String value) {
		this.work_budg_mode = value;
	}
	
	/**
	* 获取 业务预算编制模式
	* @return String
	*/
	public String getWork_budg_mode() {
		return this.work_budg_mode;
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