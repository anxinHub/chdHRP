package com.chd.hrp.cost.entity;

import java.io.Serializable;

public class HrpCostSelect implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8216112064705470050L;
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
