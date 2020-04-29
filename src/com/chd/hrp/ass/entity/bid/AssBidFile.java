package com.chd.hrp.ass.entity.bid;

import java.io.Serializable;

public class AssBidFile  implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 集体ID
	 */
	private Long group_id;
	
	/**
	 * 医院ID
	 */
	private Long hos_id;
	
	/**
	 * 账套编码
	 */
	private String copy_code;
	
	/**
	 * 招标ID
	 */
	private Long bid_id;
	
	/**
	 * 招标文件ID
	 */
	private Long bid_file_id;
	
	private String file_name;
	
	private String file_url;

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

	public Long getBid_file_id() {
		return bid_file_id;
	}

	public void setBid_file_id(Long bid_file_id) {
		this.bid_file_id = bid_file_id;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getFile_url() {
		return file_url;
	}

	public void setFile_url(String file_url) {
		this.file_url = file_url;
	}
	
}
