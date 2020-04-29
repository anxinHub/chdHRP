package com.chd.hrp.med.entity;

import java.io.Serializable;

public class HrpMedSelect implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String text;
	private String code;
	private String name;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
