package com.chd.hrp.htc.entity.task.basic;

import java.io.Serializable;
import java.util.*;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public class HtcPeopleDict implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;
	
	private long group_id;
	private long hos_id;
	private String copy_code;
	private String people_code;
	private String people_name;
	private String title_code;
	private String title_name;
	private String people_type_code;
	private String people_type_name;
	private long   dept_no;
	private long   dept_id;
	private String dept_code;
	private String dept_name;
	private String spell_code;
	private String wbx_code;
	private Integer is_stop;
	private String people_note;
	public long getGroup_id() {
		return group_id;
	}
	public long getHos_id() {
		return hos_id;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public String getPeople_code() {
		return people_code;
	}
	public String getPeople_name() {
		return people_name;
	}
	public String getTitle_code() {
		return title_code;
	}
	public String getTitle_name() {
		return title_name;
	}
	public String getPeople_type_code() {
		return people_type_code;
	}
	public String getPeople_type_name() {
		return people_type_name;
	}
	public long getDept_no() {
		return dept_no;
	}
	public long getDept_id() {
		return dept_id;
	}
	public String getDept_code() {
		return dept_code;
	}
	public String getDept_name() {
		return dept_name;
	}
	public String getSpell_code() {
		return spell_code;
	}
	public String getWbx_code() {
		return wbx_code;
	}
	public Integer getIs_stop() {
		return is_stop;
	}
	public String getPeople_note() {
		return people_note;
	}
	public void setGroup_id(long group_id) {
		this.group_id = group_id;
	}
	public void setHos_id(long hos_id) {
		this.hos_id = hos_id;
	}
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	public void setPeople_code(String people_code) {
		this.people_code = people_code;
	}
	public void setPeople_name(String people_name) {
		this.people_name = people_name;
	}
	public void setTitle_code(String title_code) {
		this.title_code = title_code;
	}
	public void setTitle_name(String title_name) {
		this.title_name = title_name;
	}
	public void setPeople_type_code(String people_type_code) {
		this.people_type_code = people_type_code;
	}
	public void setPeople_type_name(String people_type_name) {
		this.people_type_name = people_type_name;
	}
	public void setDept_no(long dept_no) {
		this.dept_no = dept_no;
	}
	public void setDept_id(long dept_id) {
		this.dept_id = dept_id;
	}
	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public void setSpell_code(String spell_code) {
		this.spell_code = spell_code;
	}
	public void setWbx_code(String wbx_code) {
		this.wbx_code = wbx_code;
	}
	public void setIs_stop(Integer is_stop) {
		this.is_stop = is_stop;
	}
	public void setPeople_note(String people_note) {
		this.people_note = people_note;
	}
	
	
}