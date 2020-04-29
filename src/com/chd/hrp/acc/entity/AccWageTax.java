package com.chd.hrp.acc.entity;

import java.io.Serializable;

public class AccWageTax implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *税率ID 
	 */
	private Long rate_id;
	/**
	 *集团ID 
	 */
	private Long group_id;
	/**
	 *医院ID 
	 */
	private Long hos_id;
	/**
	 *账套套编码
	 */
	private String copy_code;
	/**
	 *起点数
	 */
	private Long starts;
	/**
	 *终点数
	 */
	private Long ends;
	/**
	 *税率
	 */
	private Long rate;
	/**
	 *速算扣除数 
	 */
	private Long deduct;
	/**
	 *描述 
	 */
	private String note;

	public Long getRate_id() {
		return rate_id;
	}

	public void setRate_id(Long rate_id) {
		this.rate_id = rate_id;
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

	public Long getStarts() {
		return starts;
	}

	public void setStarts(Long starts) {
		this.starts = starts;
	}

	public Long getEnds() {
		return ends;
	}

	public void setEnds(Long ends) {
		this.ends = ends;
	}

	public Long getRate() {
		return rate;
	}

	public void setRate(Long rate) {
		this.rate = rate;
	}

	public Long getDeduct() {
		return deduct;
	}

	public void setDeduct(Long deduct) {
		this.deduct = deduct;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
