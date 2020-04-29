package com.chd.hrp.hpm.entity;

import java.io.Serializable;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public class AphiDeptTarget implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_id;

	private Long hos_id;
	
	
	
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

	private String copy_code;

	private String target_code;

	private String dept_kind_code;

	private String target_name;

	private String target_note;

	private String dept_kind_name;

	private Integer is_acc;
	
	private String error_type;

	public void setCopy_code(String value) {
		this.copy_code = value;
	}

	public String getCopy_code() {
		return this.copy_code;
	}

	public void setTarget_code(String value) {
		this.target_code = value;
	}

	public String getTarget_code() {
		return this.target_code;
	}

	public void setDept_kind_code(String value) {
		this.dept_kind_code = value;
	}

	public String getDept_kind_code() {
		return this.dept_kind_code;
	}

	public void setIs_acc(Integer value) {
		this.is_acc = value;
	}

	public Integer getIs_acc() {
		return this.is_acc;
	}

	
    public String getTarget_name() {
    	return target_name;
    }

	
    public void setTarget_name(String target_name) {
    	this.target_name = target_name;
    }

	
    public String getTarget_note() {
    	return target_note;
    }

	
    public void setTarget_note(String target_note) {
    	this.target_note = target_note;
    }

	
    public String getDept_kind_name() {
    	return dept_kind_name;
    }

	
    public void setDept_kind_name(String dept_kind_name) {
    	this.dept_kind_name = dept_kind_name;
    }

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
    
    
	
    
}