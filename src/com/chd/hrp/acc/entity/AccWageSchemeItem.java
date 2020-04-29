package com.chd.hrp.acc.entity;

import java.io.Serializable;

public class AccWageSchemeItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long detail_id;
	
	private Long scheme_id;
	
	private String column_item;
	
	private String item_code;
	
	private String item_name;

	public Long getDetail_id() {
		return detail_id;
	}

	public void setDetail_id(Long detail_id) {
		this.detail_id = detail_id;
	}

	public Long getScheme_id() {
		return scheme_id;
	}

	public void setScheme_id(Long scheme_id) {
		this.scheme_id = scheme_id;
	}

	public String getColumn_item() {
		return column_item;
	}

	public void setColumn_item(String column_item) {
		this.column_item = column_item;
	}

	public String getItem_code() {
		return item_code;
	}

	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	
}
