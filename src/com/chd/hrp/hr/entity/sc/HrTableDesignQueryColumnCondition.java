package com.chd.hrp.hr.entity.sc;

import java.io.Serializable;

public class HrTableDesignQueryColumnCondition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2900110748430225285L;

	private String tab_code;
	private String tab_name;
	private String col_code;
	private String col_name;
	private String conn_mode;
	private String conn_mode_text;
	private String left_bracket;
	private String condition;
	private String condition_text;
	private String value_mode_code;
	private String value_mode_name;
	private String item_value;
	private String item_value_text;
	private String right_bracket;

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

	public String getConn_mode() {
		return conn_mode;
	}

	public void setConn_mode(String conn_mode) {
		this.conn_mode = conn_mode;
	}

	public String getConn_mode_text() {
		return conn_mode_text;
	}

	public void setConn_mode_text(String conn_mode_text) {
		this.conn_mode_text = conn_mode_text;
	}

	public String getLeft_bracket() {
		return left_bracket;
	}

	public void setLeft_bracket(String left_bracket) {
		this.left_bracket = left_bracket;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getCondition_text() {
		return condition_text;
	}

	public void setCondition_text(String condition_text) {
		this.condition_text = condition_text;
	}

	public String getValue_mode_code() {
		return value_mode_code;
	}

	public void setValue_mode_code(String value_mode_code) {
		this.value_mode_code = value_mode_code;
	}

	public String getValue_mode_name() {
		return value_mode_name;
	}

	public void setValue_mode_name(String value_mode_name) {
		this.value_mode_name = value_mode_name;
	}

	public String getItem_value() {
		return item_value;
	}

	public void setItem_value(String item_value) {
		this.item_value = item_value;
	}

	public String getItem_value_text() {
		return item_value_text;
	}

	public void setItem_value_text(String item_value_text) {
		this.item_value_text = item_value_text;
	}

	public String getRight_bracket() {
		return right_bracket;
	}

	public void setRight_bracket(String right_bracket) {
		this.right_bracket = right_bracket;
	}

}
