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


public class Countries implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private String countries_code;
	private String countries_name;
	private String countries_simple;
	private String countries_en;
	private int is_stop;
	private String spell_code;
	private String wbx_code;
	private String note;

	public void setCountries_code(String value) {
		this.countries_code = value;
	}
		
	public String getCountries_code() {
		return this.countries_code;
	}
	public void setCountries_name(String value) {
		this.countries_name = value;
	}
		
	public String getCountries_name() {
		return this.countries_name;
	}
	public void setCountries_simple(String value) {
		this.countries_simple = value;
	}
		
	public String getCountries_simple() {
		return this.countries_simple;
	}
	public void setCountries_en(String value) {
		this.countries_en = value;
	}
		
	public String getCountries_en() {
		return this.countries_en;
	}
	public void setIs_stop(int value) {
		this.is_stop = value;
	}
		
	public int getIs_stop() {
		return this.is_stop;
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
	public void setNote(String value) {
		this.note = value;
	}
		
	public String getNote() {
		return this.note;
	}
}