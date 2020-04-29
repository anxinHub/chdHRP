package com.chd.hrp.hr.entity.sc;

import java.io.Serializable;

public class HrTableDesignQueryColumnSort implements Serializable, Comparable<HrTableDesignQueryColumnSort> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5717140013428154481L;

	private String tab_code;
	private String tab_name;
	private String col_code;
	private String col_name;
	private String sort_mode;
	private String sort_mode_text;
	private Integer sort;

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

	public String getSort_mode() {
		return sort_mode;
	}

	public void setSort_mode(String sort_mode) {
		this.sort_mode = sort_mode;
	}

	public String getSort_mode_text() {
		return sort_mode_text;
	}

	public void setSort_mode_text(String sort_mode_text) {
		this.sort_mode_text = sort_mode_text;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Override
	public int compareTo(HrTableDesignQueryColumnSort o) {
		if (this.sort < o.getSort())
			return -1;
		if (this.sort > o.getSort())
			return 1;
		return 0;
	}

	
}
