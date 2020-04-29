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


public class Group implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_id;
	private String group_code;
	private String group_name;
	private String group_simple;
	private Long group_sort;
	private String group_legal;
	private String group_contact;
	private String group_phone;
	private String group_email;
	private String group_address;
	private int is_stop;
	private String note;
	private String spell_code;
	private String wbx_code;
	private String user_code;
	private int user_id;

	public String getUser_code() {
		return user_code;
	}

	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}

	public void setGroup_id(Long value) {
		this.group_id = value;
	}
		
	public Long getGroup_id() {
		return this.group_id;
	}
	public void setGroup_code(String value) {
		this.group_code = value;
	}
		
	public String getGroup_code() {
		return this.group_code;
	}
	public void setGroup_name(String value) {
		this.group_name = value;
	}
		
	public String getGroup_name() {
		return this.group_name;
	}
	public void setGroup_simple(String value) {
		this.group_simple = value;
	}
		
	public String getGroup_simple() {
		return this.group_simple;
	}
	public void setGroup_sort(Long value) {
		this.group_sort = value;
	}
		
	public Long getGroup_sort() {
		return this.group_sort;
	}
	public void setGroup_legal(String value) {
		this.group_legal = value;
	}
		
	public String getGroup_legal() {
		return this.group_legal;
	}
	public void setGroup_contact(String value) {
		this.group_contact = value;
	}
		
	public String getGroup_contact() {
		return this.group_contact;
	}
	public void setGroup_phone(String value) {
		this.group_phone = value;
	}
		
	public String getGroup_phone() {
		return this.group_phone;
	}
	public void setGroup_email(String value) {
		this.group_email = value;
	}
		
	public String getGroup_email() {
		return this.group_email;
	}
	public void setGroup_address(String value) {
		this.group_address = value;
	}
		
	public String getGroup_address() {
		return this.group_address;
	}
	public void setIs_stop(int value) {
		this.is_stop = value;
	}
		
	public int getIs_stop() {
		return this.is_stop;
	}
	public void setNote(String value) {
		this.note = value;
	}
		
	public String getNote() {
		return this.note;
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

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	
}