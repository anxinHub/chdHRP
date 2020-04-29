package com.chd.hrp.hr.entity.sc;

import java.io.Serializable;

public class HrTableDesignQueryColumnGroup implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7987006010987013955L;
	
	private String tab_code;
	private String tab_name;
	private String col_code;
	private String col_name;

	public String getTab_code() {
		return tab_code;
	}

	public void setTab_code(String tab_code) {
		this.tab_code = tab_code;
	}

	public String getTab_name() {
		return tab_name;
	}

	public void setTab_name(String tab_name) {
		this.tab_name = tab_name;
	}

	public String getCol_code() {
		return col_code;
	}

	public void setCol_code(String col_code) {
		this.col_code = col_code;
	}

	public String getCol_name() {
		return col_name;
	}

	public void setCol_name(String col_name) {
		this.col_name = col_name;
	}

}
