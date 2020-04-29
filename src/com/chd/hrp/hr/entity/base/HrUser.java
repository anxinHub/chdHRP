/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.hr.entity.base;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class HrUser implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private Long user_id;
	private Long group_id;
	private Long hos_id;
	private Long hos_no;
	private String group_code;
	private String hos_code;
	private String user_code;
	private String user_name;
	private String user_pwd;
	private String emp_id;
	private String emp_code;
	private String emp_name;
	private int type_code;
	private int is_stop;
	private String spell_code;
	private String wbx_code;
	private String note;
	private String mod_code;
	private String group_name;
	private String group_simple;
	private String hos_name;
	private String hos_simple;
	private String copy_code;
	private String copy_name;
	
	public String getEmp_name() {
		return emp_name;
	}

	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}

	public void setUser_id(Long value) {
		this.user_id = value;
	}
		
	public Long getUser_id() {
		return this.user_id;
	}
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
		
	public Long getGroup_id() {
		return this.group_id;
	}
	public void setHos_id(Long value) {
		this.hos_id = value;
	}		
	public Long getHos_id() {
		return this.hos_id;
	}
	
	public void setHos_no(Long value) {
		this.hos_no = value;
	}		
	public Long getHos_no() {
		return this.hos_no;
	}
	
	public void setUser_code(String value) {
		this.user_code = value;
	}
		
	public String getUser_code() {
		return this.user_code;
	}
	public void setUser_name(String value) {
		this.user_name = value;
	}
		
	public String getUser_name() {
		return this.user_name;
	}
	public void setUser_pwd(String value) {
		this.user_pwd = value;
	}
		
	public String getUser_pwd() {
		return this.user_pwd;
	}
	public void setEmp_code(String value) {
		this.emp_code = value;
	}
		
	public String getEmp_code() {
		return this.emp_code;
	}
	public void setType_code(int value) {
		this.type_code = value;
	}
		
	public int getType_code() {
		return this.type_code;
	}
	public void setIs_stop(int value) {
		this.is_stop = value;
	}
		
	public int getIs_stop() {
		return this.is_stop;
	}
	public void setSpell_code(String value) {
		this.spell_code = value;
	}
		
	public String getSpell_code() {
		return this.spell_code;
	}
	public void setWbx_code(String value) {
		this.wbx_code = value;
	}
		
	public String getWbx_code() {
		return this.wbx_code;
	}
	public void setNote(String value) {
		this.note = value;
	}
		
	public String getNote() {
		return this.note;
	}
	public void setMod_code(String value) {
		this.mod_code = value;
	}
		
	public String getMod_code() {
		return this.mod_code;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	public String getHos_name() {
		return hos_name;
	}

	public void setHos_name(String hos_name) {
		this.hos_name = hos_name;
	}

	public String getHos_simple() {
		return hos_simple;
	}

	public void setHos_simple(String hos_simple) {
		this.hos_simple = hos_simple;
	}

	public String getCopy_code() {
		return copy_code;
	}

	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}

	public String getGroup_code() {
		return group_code;
	}

	public void setGroup_code(String group_code) {
		this.group_code = group_code;
	}

	public String getHos_code() {
		return hos_code;
	}

	public void setHos_code(String hos_code) {
		this.hos_code = hos_code;
	}

	public String getGroup_simple() {
		return group_simple;
	}

	public void setGroup_simple(String group_simple) {
		this.group_simple = group_simple;
	}

	public String getCopy_name() {
		return copy_name;
	}

	public void setCopy_name(String copy_name) {
		this.copy_name = copy_name;
	}

	
    public String getEmp_id() {
    	return emp_id;
    }

	
    public void setEmp_id(String emp_id) {
    	this.emp_id = emp_id;
    }
	
}