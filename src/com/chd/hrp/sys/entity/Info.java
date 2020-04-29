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


public class Info implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_id;
	private Long hos_id;
	private String hos_code;
	private String hos_name;
	private String hos_simple;
	private String super_code;
	private String hos_level;
	private String hos_city;
	private Long hos_sort;
	private String hos_contact;
	private String hos_phone;
	private String hos_email;
	private String hos_zipcode;
	private String hos_type;
	private String hos_address;
	private int is_last;
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
	public void setHos_id(Long value) {
		this.hos_id = value;
	}
		
	public Long getHos_id() {
		return this.hos_id;
	}
	public void setHos_code(String value) {
		this.hos_code = value;
	}
		
	public String getHos_code() {
		return this.hos_code;
	}
	public void setHos_name(String value) {
		this.hos_name = value;
	}
		
	public String getHos_name() {
		return this.hos_name;
	}
	public void setHos_simple(String value) {
		this.hos_simple = value;
	}
		
	public String getHos_simple() {
		return this.hos_simple;
	}
	public void setSuper_code(String value) {
		this.super_code = value;
	}
		
	public String getSuper_code() {
		return this.super_code;
	}
	public void setHos_level(String value) {
		this.hos_level = value;
	}
		
	public String getHos_level() {
		return this.hos_level;
	}
	public void setHos_city(String value) {
		this.hos_city = value;
	}
		
	public String getHos_city() {
		return this.hos_city;
	}
	public void setHos_sort(Long value) {
		this.hos_sort = value;
	}
		
	public Long getHos_sort() {
		return this.hos_sort;
	}
	public void setHos_contact(String value) {
		this.hos_contact = value;
	}
		
	public String getHos_contact() {
		return this.hos_contact;
	}
	public void setHos_phone(String value) {
		this.hos_phone = value;
	}
		
	public String getHos_phone() {
		return this.hos_phone;
	}
	public void setHos_email(String value) {
		this.hos_email = value;
	}
		
	public String getHos_email() {
		return this.hos_email;
	}
	public void setHos_zipcode(String value) {
		this.hos_zipcode = value;
	}
		
	public String getHos_zipcode() {
		return this.hos_zipcode;
	}
	public void setHos_type(String value) {
		this.hos_type = value;
	}
		
	public String getHos_type() {
		return this.hos_type;
	}
	public void setHos_address(String value) {
		this.hos_address = value;
	}
		
	public String getHos_address() {
		return this.hos_address;
	}
	public void setIs_last(int value) {
		this.is_last = value;
	}
		
	public int getIs_last() {
		return this.is_last;
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