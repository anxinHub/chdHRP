package com.chd.hrp.acc.entity;

public class AccPermData {
	private String obj_id;
	private String obj_code;
	private String obj_name;
	private Long is_read;
	private Long is_write;
	
	public Long getIs_read() {
		return is_read;
	}
	public void setIs_read(Long is_read) {
		this.is_read = is_read;
	}
	public Long getIs_write() {
		return is_write;
	}
	public void setIs_write(Long is_write) {
		this.is_write = is_write;
	}
	public String getObj_id() {
		return obj_id;
	}
	public void setObj_id(String obj_id) {
		this.obj_id = obj_id;
	}
	public String getObj_code() {
		return obj_code;
	}
	public void setObj_code(String obj_code) {
		this.obj_code = obj_code;
	}
	public String getObj_name() {
		return obj_name;
	}
	public void setObj_name(String obj_name) {
		this.obj_name = obj_name;
	}
	
}
