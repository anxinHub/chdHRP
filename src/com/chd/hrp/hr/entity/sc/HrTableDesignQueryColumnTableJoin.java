package com.chd.hrp.hr.entity.sc;

import java.io.Serializable;

public class HrTableDesignQueryColumnTableJoin implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4186272225409132718L;

	private String conn_mode;
	private String conn_mode_text;
	private String condition;
	private String condition_text;
	private String left_field;
	private String right_field;

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

	public String getLeft_field() {
		return left_field;
	}

	public void setLeft_field(String left_field) {
		this.left_field = left_field;
	}

	public String getRight_field() {
		return right_field;
	}

	public void setRight_field(String right_field) {
		this.right_field = right_field;
	}

}
