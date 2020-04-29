package com.chd.hrp.mat.entity;

import java.io.Serializable;

public class MatReqStoreRela implements Serializable {
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
	 * 科室需求计划ID
	 */
	private Long dept_req_id;
	
	/**
	 * 仓库需求计划ID
	 */
	private Long store_req_id;
	
	/**
	 * 科室需求计划明细ID
	 */
	private Long dept_d_id;
	
	/**
	 * 仓库需求计划明细ID
	 */
	private Long store_d_id;
	
	/**
	 * 科室需求数量
	 */
	private Double dept_req_amt;
	
	/**
	 * 仓库采购数量
	 */
	private Double store_req_amt;

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

	public Long getDept_req_id() {
		return dept_req_id;
	}

	public void setDept_req_id(Long dept_req_id) {
		this.dept_req_id = dept_req_id;
	}

	public Long getStore_req_id() {
		return store_req_id;
	}

	public void setStore_req_id(Long store_req_id) {
		this.store_req_id = store_req_id;
	}

	public Long getDept_d_id() {
		return dept_d_id;
	}

	public void setDept_d_id(Long dept_d_id) {
		this.dept_d_id = dept_d_id;
	}

	public Long getStore_d_id() {
		return store_d_id;
	}

	public void setStore_d_id(Long store_d_id) {
		this.store_d_id = store_d_id;
	}

	public Double getDept_req_amt() {
		return dept_req_amt;
	}

	public void setDept_req_amt(Double dept_req_amt) {
		this.dept_req_amt = dept_req_amt;
	}

	public Double getStore_req_amt() {
		return store_req_amt;
	}

	public void setStore_req_amt(Double store_req_amt) {
		this.store_req_amt = store_req_amt;
	}
	
	
	
}
