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


public class Mod implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private String mod_code;
	private String mod_name;
	private String parent_code;
	private int is_sys;
	
	private String start_year;
	private String start_month;
	private String create_user;
	private Date create_date;
	
	private ModStart modStart;

	
	public ModStart getModStart() {
		return modStart;
	}

	public void setModStart(ModStart modStart) {
		this.modStart = modStart;
	}

	public String getStart_year() {
		return start_year;
	}

	public void setStart_year(String start_year) {
		this.start_year = start_year;
	}

	public String getStart_month() {
		return start_month;
	}

	public void setStart_month(String start_month) {
		this.start_month = start_month;
	}

	public String getCreate_user() {
		return create_user;
	}

	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public void setMod_code(String value) {
		this.mod_code = value;
	}
		
	public String getMod_code() {
		return this.mod_code;
	}
	public void setMod_name(String value) {
		this.mod_name = value;
	}
		
	public String getMod_name() {
		return this.mod_name;
	}
	public void setParent_code(String value) {
		this.parent_code = value;
	}
		
	public String getParent_code() {
		return this.parent_code;
	}
	public void setIs_sys(int value) {
		this.is_sys = value;
	}
		
	public int getIs_sys() {
		return this.is_sys;
	}
}