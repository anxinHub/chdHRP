/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class DeptDict implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private Long dept_no;
	private Long group_id;
	private Long hos_id;
	private Long dept_id;
	private String dept_code;
	private String dept_name;
	private String kind_code;
	private String super_code;
	private Integer dept_level;
	private String udefine_code;
	private Integer sort_code;
	private Integer is_last;
	private String spell_code;
	private String wbx_code;
	private String note;
	private String user_code;
	private Date create_date;
	private String dlog;
	private int is_stop;
	
	

	public void setDept_no(Long value) {
		this.dept_no = value;
	}
		
	public Long getDept_no() {
		return this.dept_no;
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
	public void setDept_id(Long value) {
		this.dept_id = value;
	}
		
	public Long getDept_id() {
		return this.dept_id;
	}
	public void setDept_code(String value) {
		this.dept_code = value;
	}
		
	public String getDept_code() {
		return this.dept_code;
	}
	public void setDept_name(String value) {
		this.dept_name = value;
	}
		
	public String getDept_name() {
		return this.dept_name;
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

	public String getKind_code() {
		return kind_code;
	}

	public void setKind_code(String kind_code) {
		this.kind_code = kind_code;
	}

	public String getSuper_code() {
		return super_code;
	}

	public void setSuper_code(String super_code) {
		this.super_code = super_code;
	}

	public Integer getDept_level() {
		return dept_level;
	}

	public void setDept_level(Integer dept_level) {
		this.dept_level = dept_level;
	}

	public String getUdefine_code() {
		return udefine_code;
	}

	public void setUdefine_code(String udefine_code) {
		this.udefine_code = udefine_code;
	}

	public Integer getSort_code() {
		return sort_code;
	}

	public void setSort_code(Integer sort_code) {
		this.sort_code = sort_code;
	}

	public Integer getIs_last() {
		return is_last;
	}

	public void setIs_last(Integer is_last) {
		this.is_last = is_last;
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
	
}