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


public class StoreDict implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	public int getIs_disable() {
		return is_disable;
	}

	public void setIs_disable(int is_disable) {
		this.is_disable = is_disable;
	}

	private Long store_no;
	private Long group_id;
	private Long hos_id;
	private Long store_id;
	private String store_code;
	private String store_name;
	private String type_code;
	private Long sort_code;
	private String note;
	private String spell_code;
	private String wbx_code;
	private String user_code;
	private Date create_date;
	private String dlog;
	private int is_stop;
	private int is_disable;

	private String type_name;
	
	private String head_emp_name;

	public void setStore_no(Long value) {
		this.store_no = value;
	}
		
	public Long getStore_no() {
		return this.store_no;
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
	public void setStore_id(Long value) {
		this.store_id = value;
	}
		
	public Long getStore_id() {
		return this.store_id;
	}
	public void setStore_code(String value) {
		this.store_code = value;
	}
		
	public String getStore_code() {
		return this.store_code;
	}
	public void setStore_name(String value) {
		this.store_name = value;
	}
		
	public String getStore_name() {
		return this.store_name;
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

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public String getHead_emp_name() {
		return head_emp_name;
	}

	public void setHead_emp_name(String head_emp_name) {
		this.head_emp_name = head_emp_name;
	}
	
	
}