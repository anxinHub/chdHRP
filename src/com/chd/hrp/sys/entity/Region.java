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


public class Region implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private String cities_code;
	private String cities_name;
	private int is_stop;
	private String spell_code;
	private String wbx_code;
	private String note;

	public void setCities_code(String value) {
		this.cities_code = value;
	}
		
	public String getCities_code() {
		return this.cities_code;
	}
	public void setCities_name(String value) {
		this.cities_name = value;
	}
		
	public String getCities_name() {
		return this.cities_name;
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