package com.chd.hrp.sys.entity;

import java.io.Serializable;

public class HrpSysSelect implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String text;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}