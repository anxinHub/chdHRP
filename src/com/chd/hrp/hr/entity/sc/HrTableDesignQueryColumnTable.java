package com.chd.hrp.hr.entity.sc;

import java.io.Serializable;
import java.util.List;

public class HrTableDesignQueryColumnTable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6041261623434938615L;

	private String tab_code;
	private String tab_name;
	private String col_code;// 列编码集合 "EMP_CODE;EMP_NAME"
	private String col_name;// 列名称集合 "职工编码;职工名称"
	private String tab_cols;// 列拼接"HOS_EMP.EMP_CODE,HOS_EMP.EMP_NAME"
	private String join_mode;
	private String join_mode_text;
	private String join_condition;
	private List<HrTableDesignQueryColumnTableJoin> join_condition_grid;
	private List<HrTableDesignQueryColumnTableFunc> func;

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

	public String getTab_cols() {
		return tab_cols;
	}

	public void setTab_cols(String tab_cols) {
		this.tab_cols = tab_cols;
	}

	public String getJoin_mode() {
		return join_mode;
	}

	public void setJoin_mode(String join_mode) {
		this.join_mode = join_mode;
	}

	public String getJoin_mode_text() {
		return join_mode_text;
	}

	public void setJoin_mode_text(String join_mode_text) {
		this.join_mode_text = join_mode_text;
	}

	public String getJoin_condition() {
		return join_condition;
	}

	public void setJoin_condition(String join_condition) {
		this.join_condition = join_condition;
	}

	public List<HrTableDesignQueryColumnTableJoin> getJoin_condition_grid() {
		return join_condition_grid;
	}

	public void setJoin_condition_grid(List<HrTableDesignQueryColumnTableJoin> join_condition_grid) {
		this.join_condition_grid = join_condition_grid;
	}

	public List<HrTableDesignQueryColumnTableFunc> getFunc() {
		return func;
	}

	public void setFunc(List<HrTableDesignQueryColumnTableFunc> func) {
		this.func = func;
	}

}
