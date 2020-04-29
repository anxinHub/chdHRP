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


public class Emp implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_id;
	private Long hos_id;
	private Long emp_id;
	private Long emp_no;
	private String emp_code;
	private String emp_name;
	private Long dept_no;
	private String dept_code;
	private String dept_name;
	private Long dept_id;
	public int getIs_disable() {
		return is_disable;
	}

	public void setIs_disable(int is_disable) {
		this.is_disable = is_disable;
	}

	private String kind_code;
	private String kind_name;
	private Long sort_code;
	private String spell_code;
	private String wbx_code;
	private int is_stop;
	private String note;
	private int is_disable;
	private String error_type;
	
	
	
	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
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

	public String getKind_name() {
		return kind_name;
	}

	public void setKind_name(String kind_name) {
		this.kind_name = kind_name;
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
	public void setEmp_id(Long value) {
		this.emp_id = value;
	}
		
	public Long getEmp_id() {
		return this.emp_id;
	}
	public void setEmp_code(String value) {
		this.emp_code = value;
	}
		
	public String getEmp_code() {
		return this.emp_code;
	}
	public void setEmp_name(String value) {
		this.emp_name = value;
	}
		
	public String getEmp_name() {
		return this.emp_name;
	}
	public void setDept_no(Long value) {
		this.dept_no = value;
	}
		
	public Long getDept_no() {
		return this.dept_no;
	}
	public void setDept_id(Long value) {
		this.dept_id = value;
	}
		
	public Long getDept_id() {
		return this.dept_id;
	}
	public void setKind_code(String value) {
		this.kind_code = value;
	}
		
	public String getKind_code() {
		return this.kind_code;
	}
	
	public Long getSort_code() {
		return sort_code;
	}

	public void setSort_code(Long sort_code) {
		this.sort_code = sort_code;
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

	public Long getEmp_no() {
		return emp_no;
	}

	public void setEmp_no(Long emp_no) {
		this.emp_no = emp_no;
	}
	
}