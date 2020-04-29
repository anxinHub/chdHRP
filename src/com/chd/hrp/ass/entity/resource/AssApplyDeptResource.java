package com.chd.hrp.ass.entity.resource;

import java.io.Serializable;

public class AssApplyDeptResource implements Serializable{
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
	 * 购置计划单ID
	 */
	private Long apply_id;
    /**
     * 明细序号
     */
	private Long detail_id;
	/**
	 * 购置计划单号
	 */
	private String apply_no;
	/**
	 * 资产名称
	 */
	private String ass_name;
	/**
	 * 资产ID
	 */
	private Long ass_id;
	/**
	 * 资产变更ID
	 */
	private Long ass_no;
	/**
	 * 资金来源
	 */
	private Long source_id;
	/**
	 * 资金来源名称
	 */
	private String source_name;
	/**
	 * 资金来源code
	 */
	private String source_code;
	/**
	 * 金额
	 */
	private Double price;
   
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



	public Long getApply_id() {
		return apply_id;
	}

	public void setApply_id(Long apply_id) {
		this.apply_id = apply_id;
	}

	public Long getDetail_id() {
		return detail_id;
	}

	public void setDetail_id(Long detail_id) {
		this.detail_id = detail_id;
	}

	public String getApply_no() {
		return apply_no;
	}

	public void setApply_no(String apply_no) {
		this.apply_no = apply_no;
	}

	public String getAss_name() {
		return ass_name;
	}

	public void setAss_name(String ass_name) {
		this.ass_name = ass_name;
	}

	public Long getAss_id() {
		return ass_id;
	}

	public void setAss_id(Long ass_id) {
		this.ass_id = ass_id;
	}

	public Long getAss_no() {
		return ass_no;
	}

	public void setAss_no(Long ass_no) {
		this.ass_no = ass_no;
	}

	public Long getSource_id() {
		return source_id;
	}

	public void setSource_id(Long source_id) {
		this.source_id = source_id;
	}

	public String getSource_name() {
		return source_name;
	}

	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}

	
	public String getSource_code() {
		return source_code;
	}

	public void setSource_code(String source_code) {
		this.source_code = source_code;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
}
