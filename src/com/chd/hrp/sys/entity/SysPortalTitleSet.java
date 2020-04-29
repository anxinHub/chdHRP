package com.chd.hrp.sys.entity;

import java.io.Serializable;

public class SysPortalTitleSet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long group_id;
	private Long hos_id;
	private String copy_code;
	//模块编码 
	private String mode_code;
	// 栏目编码 
	private String title_code;
	//显示条数
	private int show_rows;
	// 是否更多
	private int is_more;
	// 是否合并
	private int is_colspan;
	// 参数JSON
	private String para_json;
	
	private String error_type;

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

	public String getMode_code() {
		return mode_code;
	}

	public void setMode_code(String mode_code) {
		this.mode_code = mode_code;
	}

	public String getTitle_code() {
		return title_code;
	}

	public void setTitle_code(String title_code) {
		this.title_code = title_code;
	}

	public int getShow_rows() {
		return show_rows;
	}

	public void setShow_rows(int show_rows) {
		this.show_rows = show_rows;
	}

	public int getIs_more() {
		return is_more;
	}

	public void setIs_more(int is_more) {
		this.is_more = is_more;
	}

	public int getIs_colspan() {
		return is_colspan;
	}

	public void setIs_colspan(int is_colspan) {
		this.is_colspan = is_colspan;
	}

	public String getPara_json() {
		return para_json;
	}

	public void setPara_json(String para_json) {
		this.para_json = para_json;
	}

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	
}
