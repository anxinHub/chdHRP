/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.ass.entity.dict;

import java.io.Serializable;
import java.util.*;



public class AssGBcode implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;
	private String gb_code;
	private String gb_name;

	private String supper_code;
	private int gb_level;
	private int is_last;

	private String spell_code;
	private String wbx_code;

	private int is_stop;
	private String note;
	private String error_type;
	
	public String getGb_code() {
		return gb_code;
	}
	public void setGb_code(String gb_code) {
		this.gb_code = gb_code;
	}
	public String getGb_name() {
		return gb_name;
	}
	public void setGb_name(String gb_name) {
		this.gb_name = gb_name;
	}
	public String getSupper_code() {
		return supper_code;
	}
	public void setSupper_code(String supper_code) {
		this.supper_code = supper_code;
	}
	public int getGb_level() {
		return gb_level;
	}
	public void setGb_level(int gb_level) {
		this.gb_level = gb_level;
	}
	public int getIs_last() {
		return is_last;
	}
	public void setIs_last(int is_last) {
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
	public int getIs_stop() {
		return is_stop;
	}
	public void setIs_stop(int is_stop) {
		this.is_stop = is_stop;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getError_type() {
		return error_type;
	}
	public void setError_type(String error_type) {
		this.error_type = error_type;
	}

}