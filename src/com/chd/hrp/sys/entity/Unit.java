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


public class Unit implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_id;
	private Long hos_id;
	private String unit_code;
	private String unit_name;
	private int is_stop;
	private String spell_code;
	private String wbx_code;
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
	public void setUnit_code(String value) {
		this.unit_code = value;
	}
		
	public String getUnit_code() {
		return this.unit_code;
	}
	public void setUnit_name(String value) {
		this.unit_name = value;
	}
		
	public String getUnit_name() {
		return this.unit_name;
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