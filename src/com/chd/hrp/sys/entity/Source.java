/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.sys.entity;

import java.io.Serializable;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class Source implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_id;
	private Long hos_id;
	private Long source_id;
	private String source_code;
	private String source_name;
	private String spell_code;
	private String wbx_code;
	private int is_stop;
	private String note;
	private int source_attr;
	private String nature_code;
	private String nature_name;
	

	public Long getSource_id() {
		return source_id;
	}

	public void setSource_id(Long source_id) {
		this.source_id = source_id;
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
	public void setSource_code(String value) {
		this.source_code = value;
	}
		
	public String getSource_code() {
		return this.source_code;
	}
	public void setSource_name(String value) {
		this.source_name = value;
	}
		
	public String getSource_name() {
		return this.source_name;
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
	public void setSource_attr(int value) {
		this.source_attr = value;
	}
		
	public int getSource_attr() {
		return this.source_attr;
	}

	public String getNature_code() {
		return nature_code;
	}

	public void setNature_code(String nature_code) {
		this.nature_code = nature_code;
	}

	public String getNature_name() {
		return nature_name;
	}

	public void setNature_name(String nature_name) {
		this.nature_name = nature_name;
	}
	
	
}