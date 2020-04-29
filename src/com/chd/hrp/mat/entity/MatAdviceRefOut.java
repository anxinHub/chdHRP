package com.chd.hrp.mat.entity;

import java.io.Serializable;

public class MatAdviceRefOut implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer group_id;
	private Integer hos_id;
	private String copy_code;
	private Integer receive_id;
	private Integer out_id;
	private Integer out_detail_id;
	public Integer getGroup_id() {
		return group_id;
	}
	public void setGroup_id(Integer group_id) {
		this.group_id = group_id;
	}
	public Integer getHos_id() {
		return hos_id;
	}
	public void setHos_id(Integer hos_id) {
		this.hos_id = hos_id;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	public Integer getReceive_id() {
		return receive_id;
	}
	public void setReceive_id(Integer receive_id) {
		this.receive_id = receive_id;
	}
	public Integer getOut_id() {
		return out_id;
	}
	public void setOut_id(Integer out_id) {
		this.out_id = out_id;
	}
	public Integer getOut_detail_id() {
		return out_detail_id;
	}
	public void setOut_detail_id(Integer out_detail_id) {
		this.out_detail_id = out_detail_id;
	}
	
}
