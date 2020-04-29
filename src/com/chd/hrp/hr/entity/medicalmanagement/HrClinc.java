package com.chd.hrp.hr.entity.medicalmanagement;

import java.io.Serializable;

public class HrClinc  implements Serializable {
	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 集团
	 */
	private Integer group_id;

	/**
	 * 医院
	 */
	private Integer hos_id;
   
	/**
	 * 姓名
	 */
	private Integer emp_id;
	private String emp_name;

	/**
	 * 科室ID
	 */
	private Integer dept_id;
	private String dept_name;
	
	/**
	 * 出诊天数
	 */
	private Integer out_days;

	/**
	 *出诊率
	 */
	private Integer out_ratio;
	
	/**
	 * 预约率
	 */
	private Integer order_ratio;

	/**
	 * 微信好评
	 */
	private Integer good_eval;
	
	/**
	 * 微信差评
	 */
	private Integer bad_eval;

	/**
	 * 人均费用
	 */
	private Integer per_charge;
	
	/**
	 * 药占比
	 */
	private Integer med_ratio;

	/**
	 * 是否提交
	 */
	private Integer is_commit;
	
	private String commit_name;
	/**
	 * 导入验证信息
	 */
	private String error_type;

	public Integer getGroup_id() {
		return group_id;
	}

	public void setGroup_id(Integer group_id) {
		this.group_id = group_id;
	}

	public Integer getHos_id() {
		return hos_id;
	}

	public void setHos_id(Integer hos_id) {
		this.hos_id = hos_id;
	}

	public Integer getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(Integer emp_id) {
		this.emp_id = emp_id;
	}

	public String getEmp_name() {
		return emp_name;
	}

	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}

	public Integer getDept_id() {
		return dept_id;
	}

	public void setDept_id(Integer dept_id) {
		this.dept_id = dept_id;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public Integer getOut_days() {
		return out_days;
	}

	public void setOut_days(Integer out_days) {
		this.out_days = out_days;
	}

	public Integer getOut_ratio() {
		return out_ratio;
	}

	public void setOut_ratio(Integer out_ratio) {
		this.out_ratio = out_ratio;
	}

	public Integer getOrder_ratio() {
		return order_ratio;
	}

	public void setOrder_ratio(Integer order_ratio) {
		this.order_ratio = order_ratio;
	}

	public Integer getGood_eval() {
		return good_eval;
	}

	public void setGood_eval(Integer good_eval) {
		this.good_eval = good_eval;
	}

	public Integer getBad_eval() {
		return bad_eval;
	}

	public void setBad_eval(Integer bad_eval) {
		this.bad_eval = bad_eval;
	}

	public Integer getPer_charge() {
		return per_charge;
	}

	public void setPer_charge(Integer per_charge) {
		this.per_charge = per_charge;
	}

	public Integer getMed_ratio() {
		return med_ratio;
	}

	public void setMed_ratio(Integer med_ratio) {
		this.med_ratio = med_ratio;
	}

	public Integer getIs_commit() {
		return is_commit;
	}

	public void setIs_commit(Integer is_commit) {
		this.is_commit = is_commit;
	}

	
	public String getCommit_name() {
		return commit_name;
	}

	public void setCommit_name(String commit_name) {
		this.commit_name = commit_name;
	}

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	
	
}
