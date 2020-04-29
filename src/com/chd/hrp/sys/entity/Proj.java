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


public class Proj implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_id;
	private Long hos_id;
	private Long proj_id;
	private String proj_code;
	private String type_code;
	private String type_name;
	private String proj_name;
	private String proj_simple;
	private Long sort_code;
	private String spell_code;
	private String wbx_code;
	private int is_stop;
	private String note;
	private String error_type;

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
	public void setType_code(String value) {
		this.type_code = value;
	}
		
	public String getType_code() {
		return this.type_code;
	}
	public void setProj_name(String value) {
		this.proj_name = value;
	}
		
	public String getProj_name() {
		return this.proj_name;
	}
	public void setProj_simple(String value) {
		this.proj_simple = value;
	}
		
	public String getProj_simple() {
		return this.proj_simple;
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

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the error_type
	 */
	public String getError_type() {
		return error_type;
	}

	/**
	 * @param error_type the error_type to set
	 */
	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	
	
}