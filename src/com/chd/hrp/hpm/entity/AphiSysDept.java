package com.chd.hrp.hpm.entity;

import java.io.Serializable;
/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

public class AphiSysDept implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	
	private String sys_dept_code;
	private String sys_dept_name;

	private String spell_code;
	
	public String getSys_dept_code() {
		return sys_dept_code;
	}

	public void setSys_dept_code(String sys_dept_code) {
		this.sys_dept_code = sys_dept_code;
	}

	public String getSys_dept_name() {
		return sys_dept_name;
	}

	public void setSys_dept_name(String sys_dept_name) {
		this.sys_dept_name = sys_dept_name;
	}

	public String getSpell_code() {
		return spell_code;
	}

	public void setSpell_code(String spell_code) {
		this.spell_code = spell_code;
	}

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}

	private String error_type;
	
	
}