/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.sys.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class ProjDict implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private Long proj_no;
	private Long group_id;
	private Long hos_id;
	private Long proj_id;
	private String proj_code;
	private String proj_name;
	private String type_code;
	private String proj_simple;
	private Long sort_code;
	private String note;
	private String spell_code;
	private String wbx_code;
	private String user_code;
	private Date create_date;
	private String dlog;
	private int is_stop;
	private String level_name;
	private String con_emp_name;
	private int is_disable;
	public int getIs_disable() {
		return is_disable;
	}

	public void setIs_disable(int is_disable) {
		this.is_disable = is_disable;
	}

	public void setProj_no(Long value) {
		this.proj_no = value;
	}
		
	public Long getProj_no() {
		return this.proj_no;
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
	public void setProj_id(Long value) {
		this.proj_id = value;
	}
		
	public Long getProj_id() {
		return this.proj_id;
	}
	public void setProj_code(String value) {
		this.proj_code = value;
	}
		
	public String getProj_code() {
		return this.proj_code;
	}
	public void setProj_name(String value) {
		this.proj_name = value;
	}
		
	public String getProj_name() {
		return this.proj_name;
	}
	public void setUser_code(String value) {
		this.user_code = value;
	}
		
	public String getUser_code() {
		return this.user_code;
	}
	public void setCreate_date(Date value) {
		this.create_date = value;
	}
		
	public Date getCreate_date() {
		return this.create_date;
	}
	public void setNote(String value) {
		this.note = value;
	}
		
	public String getNote() {
		return this.note;
	}
	public void setIs_stop(int value) {
		this.is_stop = value;
	}
		
	public int getIs_stop() {
		return this.is_stop;
	}

	public String getType_code() {
		return type_code;
	}

	public void setType_code(String type_code) {
		this.type_code = type_code;
	}

	public String getProj_simple() {
		return proj_simple;
	}

	public void setProj_simple(String proj_simple) {
		this.proj_simple = proj_simple;
	}

	public Long getSort_code() {
		return sort_code;
	}

	public void setSort_code(Long sort_code) {
		this.sort_code = sort_code;
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

	public String getDlog() {
		return dlog;
	}

	public void setDlog(String dlog) {
		this.dlog = dlog;
	}

	public String getLevel_name() {
		return level_name;
	}

	public void setLevel_name(String level_name) {
		this.level_name = level_name;
	}

	public String getCon_emp_name() {
		return con_emp_name;
	}

	public void setCon_emp_name(String con_emp_name) {
		this.con_emp_name = con_emp_name;
	}
	
}