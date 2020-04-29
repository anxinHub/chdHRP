package com.chd.task.ass.bean;

import org.nutz.dao.entity.annotation.View;

/**
 * @Title:
 * @Description:
 * @Copyright: Copyright (c) 2017年11月2日 下午9:54:48
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@View("v_mobile_dept_dict")
public class DeptData {

	private String group_id;

	private String hos_id;

	private String dept_id;

	private String dept_no;

	private String dept_code;

	private String dept_name;

	private String spell_code;

	private String wbx_code;

	public String getDept_id() {
		return dept_id;
	}

	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}

	public String getDept_no() {
		return dept_no;
	}

	public void setDept_no(String dept_no) {
		this.dept_no = dept_no;
	}

	public String getDept_code() {
		return dept_code;
	}

	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public String getHos_id() {
		return hos_id;
	}

	public void setHos_id(String hos_id) {
		this.hos_id = hos_id;
	}

	
    public String getSpell_code() {
    	return spell_code;
    }

	
    public void setSpell_code(String spell_code) {
    	this.spell_code = spell_code;
    }

	
    public String getWbx_code() {
    	return wbx_code;
    }
	
    public void setWbx_code(String wbx_code) {
    	this.wbx_code = wbx_code;
    }

}
