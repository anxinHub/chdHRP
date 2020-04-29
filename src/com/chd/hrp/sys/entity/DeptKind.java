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


public class DeptKind implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_id;
	private Long hos_id;
	private String kind_code;
	private String kind_name;
	private int is_stop;
	private String spell_code;
	private String wbx_code;
	private String note;
	private String error_type;
	
	

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
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
	public void setKind_code(String value) {
		this.kind_code = value;
	}
		
	public String getKind_code() {
		return this.kind_code;
	}
	public void setKind_name(String value) {
		this.kind_name = value;
	}
		
	public String getKind_name() {
		return this.kind_name;
	}
	public void setIs_stop(int value) {
		this.is_stop = value;
	}
		
	public int getIs_stop() {
		return this.is_stop;
	}
	
	public String getSpell_code() {
		return spell_code;
	}

	public void setSpell_code(String spell_code) {
		this.spell_code = spell_code;
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
}