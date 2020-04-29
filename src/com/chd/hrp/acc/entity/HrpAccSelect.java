package com.chd.hrp.acc.entity;

import java.io.Serializable;

public class HrpAccSelect implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8216112064705470050L;
	private String id;
	private String text;
	
	private String nature_code;
	
	private String nature_name;
	
	private String type_code;
	
	private String type_name;
	
	private String search ;
	
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
	public String getNature_code() {
		return nature_code;
	}
	public void setNature_code(String nature_code) {
		this.nature_code = nature_code;
	}
	public String getNature_name() {
		return nature_name;
	}
	public void setNature_name(String nature_name) {
		this.nature_name = nature_name;
	}
	public String getType_code() {
		return type_code;
	}
	public void setType_code(String type_code) {
		this.type_code = type_code;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	
	
}
