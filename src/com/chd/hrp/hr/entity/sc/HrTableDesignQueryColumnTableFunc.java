package com.chd.hrp.hr.entity.sc;

import java.io.Serializable;

public class HrTableDesignQueryColumnTableFunc implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1716275945901837327L;
	private String tab_code;
	private String col_code;
	private String func;
	private String func_text;
	private String param;

	public String getTab_code() {
		return tab_code;
	}

	public void setTab_code(String tab_code) {
		this.tab_code = tab_code;
	}

	public String getCol_code() {
		return col_code;
	}

	public void setCol_code(String col_code) {
		this.col_code = col_code;
	}

	public String getFunc() {
		return func;
	}

	public void setFunc(String func) {
		this.func = func;
	}

	public String getFunc_text() {
		return func_text;
	}

	public void setFunc_text(String func_text) {
		this.func_text = func_text;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

}
