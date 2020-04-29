package com.chd.hrp.hr.entity.attendancemanagement.scheduling;

import java.io.Serializable;

public class HrSequestration implements Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/**
	 * 集团ID
	 */
	private Integer group_id;
	/**
	 * 医院ID
	 */
	private Integer hos_id;
	/**
	 * 是否自动封存
	 */
	private Integer attend_pbisfile;
	/**
	 * 	封存设置
	 */
   private String attend_pbfile_set;
   /**
    * 封存规则值
    */
   private String attend_pbfle_val;
	 /**
	 * 错误信息
	 */
	private String error_type;
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
	public Integer getAttend_pbisfile() {
		return attend_pbisfile;
	}
	public void setAttend_pbisfile(Integer attend_pbisfile) {
		this.attend_pbisfile = attend_pbisfile;
	}
	public String getAttend_pbfile_set() {
		return attend_pbfile_set;
	}
	public void setAttend_pbfile_set(String attend_pbfile_set) {
		this.attend_pbfile_set = attend_pbfile_set;
	}
	public String getError_type() {
		return error_type;
	}
	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	public String getAttend_pbfle_val() {
		return attend_pbfle_val;
	}
	public void setAttend_pbfle_val(String attend_pbfle_val) {
		this.attend_pbfle_val = attend_pbfle_val;
	}
	
}
