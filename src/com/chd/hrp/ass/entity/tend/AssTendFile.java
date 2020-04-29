package com.chd.hrp.ass.entity.tend;

import java.io.Serializable;

public class AssTendFile implements Serializable{

	
	private Long group_id;
	private Long hos_id;
	private String copy_code;
	private Long bid_id;
	private String file_path;
	private String file_code;
	private String file_name;
	private String file_type;
	public Long getGroup_id() {
		return group_id;
	}
	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}
	public Long getHos_id() {
		return hos_id;
	}
	public void setHos_id(Long hos_id) {
		this.hos_id = hos_id;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	public Long getBid_id() {
		return bid_id;
	}
	public void setBid_id(Long bid_id) {
		this.bid_id = bid_id;
	}
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	public String getFile_code() {
		return file_code;
	}
	public void setFile_code(String file_code) {
		this.file_code = file_code;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getFile_type() {
		return file_type;
	}
	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}
	@Override
	public String toString() {
		return "AssTendFile [group_id=" + group_id + ", hos_id=" + hos_id
				+ ", copy_code=" + copy_code + ", bid_id=" + bid_id
				+ ", file_path=" + file_path + ", file_code=" + file_code
				+ ", file_name=" + file_name + ", file_type=" + file_type + "]";
	}
	
	
}
