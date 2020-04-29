/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.ass.entity.dict;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class AssFacDict implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private Long fac_no;
	private Long group_id;
	private Long hos_id;
	private Long fac_id;
	private String fac_code;
	private String fac_name;
	private String type_code;
	private Long fac_sort;
	private String note;
	private String spell_code;
	private String wbx_code;
	private String user_code;
	private Date create_date;
	private String dlog;
	private int is_stop;
	private int is_mat;
	private int is_ass;
	private int is_med;
	private int is_disable;


	public int getIs_disable() {
		return is_disable;
	}

	public void setIs_disable(int is_disable) {
		this.is_disable = is_disable;
	}

	public int getIs_mat() {
		return is_mat;
	}

	public void setIs_mat(int is_mat) {
		this.is_mat = is_mat;
	}

	public int getIs_ass() {
		return is_ass;
	}

	public void setIs_ass(int is_ass) {
		this.is_ass = is_ass;
	}

	public int getIs_med() {
		return is_med;
	}

	public void setIs_med(int is_med) {
		this.is_med = is_med;
	}

	public void setFac_no(Long value) {
		this.fac_no = value;
	}
		
	public Long getFac_no() {
		return this.fac_no;
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
	public void setFac_id(Long value) {
		this.fac_id = value;
	}
		
	public Long getFac_id() {
		return this.fac_id;
	}
	public void setFac_code(String value) {
		this.fac_code = value;
	}
		
	public String getFac_code() {
		return this.fac_code;
	}
	public void setFac_name(String value) {
		this.fac_name = value;
	}
		
	public String getFac_name() {
		return this.fac_name;
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

	public String getType_code() {
		return type_code;
	}

	public void setType_code(String type_code) {
		this.type_code = type_code;
	}

	public Long getFac_sort() {
		return fac_sort;
	}

	public void setFac_sort(Long fac_sort) {
		this.fac_sort = fac_sort;
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