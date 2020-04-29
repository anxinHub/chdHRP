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


public class GroupDict implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_no;
	private Long group_id;
	private String group_code;
	private String group_name;
	private String group_simple;
	private String user_code;
	private Date create_date;
	private String note;
	private int is_stop;

	public void setGroup_no(Long value) {
		this.group_no = value;
	}
		
	public Long getGroup_no() {
		return this.group_no;
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
}