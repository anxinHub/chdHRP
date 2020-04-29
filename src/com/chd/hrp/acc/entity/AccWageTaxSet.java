package com.chd.hrp.acc.entity;

import java.io.Serializable;

public class AccWageTaxSet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	 * 起征点数
	 */
	private Long tax_value;
	/**
	 * 附加起征点
	 */
	private Long ass_value;
	
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

	public Long getTax_value() {
		return tax_value;
	}

	public void setTax_value(Long tax_value) {
		this.tax_value = tax_value;
	}

	public Long getAss_value() {
		return ass_value;
	}

	public void setAss_value(Long ass_value) {
		this.ass_value = ass_value;
	}

	
}
